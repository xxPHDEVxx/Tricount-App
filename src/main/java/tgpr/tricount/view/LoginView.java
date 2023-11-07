package tgpr.tricount.view;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import tgpr.framework.Configuration;
import tgpr.framework.Margin;
import tgpr.framework.Spacing;
import tgpr.tricount.controller.LoginController;

import java.util.List;

public class LoginView extends BasicWindow {

    private final LoginController controller;

    private final TextBox txtPseudo = new TextBox();
    private final TextBox txtPassword = new TextBox();
    private Button btnLogin;

    public LoginView(LoginController controller) {
        this.controller = controller;

        setTitle("Login");
        setHints(List.of(Hint.CENTERED));

        Panel root = Panel.verticalPanel(1);
        setComponent(root);

        createFieldsPanel().addTo(root);
        createButtonsPanel().addTo(root);
        createDebugPanel().addTo(root);

        btnLogin.takeFocus();
    }

    private Panel createFieldsPanel() {
        Panel panel = Panel.gridPanel(2,
                        Margin.of(1, 1, 1, 0),
                        Spacing.of(1))
                .center();

        new Label("User:").addTo(panel);
        txtPseudo.addTo(panel).takeFocus();

        new Label("Password:").addTo(panel);
        txtPassword.setMask('*').addTo(panel);

        return panel;
    }

    private Panel createButtonsPanel() {
        Panel panel = Panel.horizontalPanel(1).center();
        btnLogin = new Button("Login", this::login).addTo(panel);
        Button btnExit = new Button("Exit", this::exit).addTo(panel);

        addShortcut(btnLogin, KeyStroke.fromString("<A-l>"));
        addShortcut(btnExit, KeyStroke.fromString("<A-e>"));

        return panel;
    }

    private Border createDebugPanel() {
        Button btnSeedData = new Button("Reset Database", this::seedData);
        Panel panel = Panel.verticalPanel(LinearLayout.Alignment.Center,
                new Button("Login as default admin", this::logAsDefaultAdmin),
                new Button("Login as default member", this::logAsDefaultMember),
                btnSeedData
        );
        Border border = panel.withBorder(Borders.singleLine(" For debug purpose "));

        addShortcut(btnSeedData, KeyStroke.fromString("<A-r>"));

        return border;
    }

    private void seedData() {
        controller.seedData();
    }

    private void exit() {
        controller.exit();
    }

    private void login() {
        var errors = controller.login(txtPseudo.getText(), txtPassword.getText());
        if (!errors.isEmpty()) {
            txtPseudo.takeFocus();
        }
    }

    private void logAsDefaultAdmin() {
        controller.login(Configuration.get("default.admin.pseudo"), Configuration.get("default.admin.password"));
    }

    private void logAsDefaultMember() {
        controller.login(Configuration.get("default.member.pseudo"), Configuration.get("default.member.password"));
    }
}
