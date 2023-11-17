package tgpr.tricount.view;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.framework.Controller;
import tgpr.framework.Tools;
import tgpr.tricount.controller.LoginController;
import tgpr.tricount.controller.SignupController;
import tgpr.tricount.model.User;

import java.util.List;

public class SignupView extends DialogWindow {
    private final SignupController controller;
    private final TextBox txtMail = new TextBox();
    private final TextBox txtFullName = new TextBox();
    private final TextBox txtIBAN = new TextBox();
    private final TextBox txtPassword = new TextBox().setMask('*');
    private final TextBox txtConfirmPassword = new TextBox().setMask('*');
    private final Button buttonSignup = new Button("Signup", this::signup);
    private final Button buttonClose = new Button("Close", () -> {Controller.navigateTo(new LoginController());});
    private final Label errMail = new Label("");
    private final Label errFullName = new Label("");
    private final Label errIBAN = new Label("");
    private final Label errPassword = new Label("");
    private final Label errConfirmPassword = new Label("");



    public SignupView (SignupController controller){
        super("Signup");

        this.controller = controller;
        setTitle("Signup");
        setHints(List.of(Hint.CENTERED));
        setCloseWindowWithEscape(true);

        Panel root =  new Panel().setLayoutManager(new LinearLayout(Direction.VERTICAL));
        setComponent(root);
        root.addComponent(createFieldPanel());
        root.addComponent(createButtonPanel());

    }

    private Panel createFieldPanel(){
        Panel panel = new Panel().setLayoutManager(new GridLayout(2));

        new Label("Mail : ").addTo(panel);
        txtMail.addTo(panel).setTextChangeListener((txt, byUser) -> validate());
        new EmptySpace().addTo(panel);
        errMail.addTo(panel).setForegroundColor(TextColor.ANSI.RED);

        new Label("Full Name : ").addTo(panel);
        txtFullName.addTo(panel).setTextChangeListener((txt, byUser) -> validate());
        new EmptySpace().addTo(panel);
        errFullName.addTo(panel).setForegroundColor(TextColor.ANSI.RED);

        new Label("IBAN : ").addTo(panel);
        txtIBAN.addTo(panel).setTextChangeListener((txt, byUser) -> validate());
        new EmptySpace().addTo(panel);
        errIBAN.addTo(panel).setForegroundColor(TextColor.ANSI.RED);

        new Label("Password : ").addTo(panel);
        txtPassword.addTo(panel).setTextChangeListener((txt, byUser) -> validate());
        new EmptySpace().addTo(panel);
        errPassword.addTo(panel).setForegroundColor(TextColor.ANSI.RED);

        new Label("Confirm Password : ").addTo(panel);
        txtConfirmPassword.addTo(panel).setTextChangeListener((txt, byUser) -> validate());
        new EmptySpace().addTo(panel);
        errConfirmPassword.addTo(panel).setForegroundColor(TextColor.ANSI.RED);


        return panel;
    }
    private Panel createButtonPanel(){
        Panel panel = new Panel().setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        buttonSignup.addTo(panel).setEnabled(false);
        buttonClose.addTo(panel);

        return panel;
    }

    private void validate() {
        var errors = controller.validate(
                txtMail.getText(),
                txtFullName.getText(),
                txtIBAN.getText(), txtPassword.getText(), txtConfirmPassword.getText());

        errMail.setText(errors.getFirstErrorMessage(User.Fields.Mail));
        errFullName.setText(errors.getFirstErrorMessage(User.Fields.FullName));
        errIBAN.setText(errors.getFirstErrorMessage(User.Fields.Iban));
        errPassword.setText(errors.getFirstErrorMessage(User.Fields.Password));
        errConfirmPassword.setText((errors.getFirstErrorMessage(User.Fields.ConfirmPassword)));
        var enabled = true;
        for (Object element : errors) {
            String strElement = String.valueOf(element);
            if (!strElement.isEmpty()) {
                enabled = false;
            }
        }
        buttonSignup.setEnabled(enabled);
    }
    private void signup() {
            User user = new User(txtMail.getText(), Tools.hash(txtPassword.getText()),txtFullName.getText(), User.Role.User, txtIBAN.getText());
            user.save();
            this.close();
    }
}