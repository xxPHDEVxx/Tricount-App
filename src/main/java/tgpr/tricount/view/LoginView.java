package tgpr.tricount.view;

import com.googlecode.lanterna.gui2.*;
import tgpr.tricount.controller.LoginController;
import tgpr.framework.Configuration;
import tgpr.framework.Layouts;

import java.util.List;

public class LoginView extends BasicWindow {

    private final LoginController controller;
    private final TextBox txtPseudo;
    private final TextBox txtPassword;
    private final Button btnLogin;

    public LoginView(LoginController controller) {
        this.controller = controller;

        setTitle("Login");
        setHints(List.of(Hint.CENTERED));

        Panel root = new Panel();
        setComponent(root);

        Panel panel = new Panel().setLayoutManager(new GridLayout(2).setTopMarginSize(1).setVerticalSpacing(1))
                .setLayoutData(Layouts.LINEAR_CENTER).addTo(root);
        panel.addComponent(new Label("User:"));
        txtPseudo = new TextBox().addTo(panel);
        panel.addComponent(new Label("Password:"));
        txtPassword = new TextBox().setMask('*').addTo(panel);

        new EmptySpace().addTo(root);

        Panel buttons = new Panel().setLayoutManager(new LinearLayout(Direction.HORIZONTAL))
                .setLayoutData(Layouts.LINEAR_CENTER).addTo(root);
        btnLogin = new Button("Login", this::login).addTo(buttons);
        Button btnExit = new Button("Exit", this::exit).addTo(buttons);

        new EmptySpace().addTo(root);

        Button btnSeedData = new Button("Reset Database", this::seedData);
        Panel debug = Panel.verticalPanel(LinearLayout.Alignment.Center,
                new Button("Login as default admin", this::logAsDefaultAdmin),
                new Button("Login as boverhaegen@epfc.eu", this::logAsDefaultMember),
                btnSeedData
        );
        debug.withBorder(Borders.singleLine(" For debug purpose ")).addTo(root);

        txtPseudo.takeFocus();
    }

    private void seedData() {
        controller.seedData();
        btnLogin.takeFocus();
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
        controller.login(Configuration.get("default.admin.mail"), Configuration.get("default.admin.password"));
    }

    private void logAsDefaultMember() {
        controller.login(Configuration.get("default.user.mail"), Configuration.get("default.user.password"));
    }
}
