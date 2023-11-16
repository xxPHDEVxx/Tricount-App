package tgpr.tricount.view;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.tricount.controller.SignupController;

import java.util.List;

public class SignupView extends DialogWindow {
    private final SignupController controller;
    private final TextBox txtMail = new TextBox();
    private final TextBox txtFullName = new TextBox();
    private final TextBox txtIBAN = new TextBox();
    private final TextBox txtPassword = new TextBox();
    private final TextBox txtConfirmPassword = new TextBox();
    private final Button buttonSignup = new Button("Signup");
    private final Button buttonClose = new Button("Close");
    private final Label errMail = new Label("");
    private final Label errFullName = new Label("name required");
    private final Label errIBAN = new Label("bad format (BE 99 9999 9999 9999)");
    private final Label errPassword = new Label("pass1");
    private final Label errConfirmPassword = new Label("pass1");


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
        txtMail.addTo(panel);
        new EmptySpace().addTo(panel);
        errMail.addTo(panel);

        new Label("Full Name : ").addTo(panel);
        txtFullName.addTo(panel);
        new EmptySpace().addTo(panel);
        errFullName.addTo(panel);

        new Label("IBAN : ").addTo(panel);
        txtIBAN.addTo(panel);
        new EmptySpace().addTo(panel);
        errIBAN.addTo(panel);

        new Label("Password : ").addTo(panel);
        txtPassword.addTo(panel);
        new EmptySpace().addTo(panel);
        errPassword.addTo(panel);

        new Label("Confirm Password : ").addTo(panel);
        txtConfirmPassword.addTo(panel);
        new EmptySpace().addTo(panel);
        errConfirmPassword.addTo(panel);


        return panel;
    }
    private Panel createButtonPanel(){
        Panel panel = new Panel().setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        buttonSignup.addTo(panel);
        buttonClose.addTo(panel);

        return panel;
    }

}
