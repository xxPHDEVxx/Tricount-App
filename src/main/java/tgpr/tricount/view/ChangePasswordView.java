package tgpr.tricount.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.tricount.controller.ChangePasswordController;
import tgpr.tricount.model.User;

import java.util.List;



public class ChangePasswordView extends DialogWindow {
    private final ChangePasswordController controller;
    private final TextBox txtOldPassword;
    private final TextBox txtPassword;
    private final TextBox txtConfirmPassword;
    private final Button btnSave;
    private final Label errOldPassword;
    private final Label errPassword;
    private final Label errConfirmPassword;
    public ChangePasswordView(ChangePasswordController controller){
        super("Change Password");
        this.controller = controller;

        setHints(List.of(Hint.CENTERED, Hint.FIXED_SIZE));
        setCloseWindowWithEscape(true);

        Panel root = new Panel();
        setFixedSize(new TerminalSize(50, 9));
        setComponent(root);

        Panel fields = new Panel().setLayoutManager(new GridLayout(2).setTopMarginSize(1)).addTo(root);

        new Label("Old Password: ").addTo(fields);
        txtOldPassword = new TextBox().sizeTo(21)
                .addTo(fields).takeFocus().setMask('*')
                .setTextChangeListener((txt, byUser) -> validate());
        new EmptySpace().addTo(fields);
        errOldPassword = new Label("").addTo(fields).setForegroundColor(TextColor.ANSI.RED);

        new Label("Password: ").addTo(fields);
        txtPassword = new TextBox().sizeTo(21)
                .addTo(fields).setMask('*')
                .setTextChangeListener((txt, byUser) -> validate());
        new EmptySpace().addTo(fields);
        errPassword = new Label("").addTo(fields).setForegroundColor(TextColor.ANSI.RED);


        new Label("Confirm Password :").addTo(fields);
        txtConfirmPassword= new TextBox().sizeTo(21)
                .addTo(fields).setMask('*')
                .setTextChangeListener((txt, byUser) -> validate());
        new EmptySpace().addTo(fields);
        errConfirmPassword = new Label("").addTo(fields).setForegroundColor(TextColor.ANSI.RED);

        new EmptySpace().addTo(fields);

        Panel buttons = new Panel().setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        btnSave = new Button("Save", this::save).addTo(buttons).setEnabled(false);
        new Button("Cancel", this::close).addTo(buttons);
        root.addComponent(buttons, LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

    }

    private void validate() {
        var errors = controller.validate(
                txtOldPassword.getText(),
                txtPassword.getText(),
                txtConfirmPassword.getText()
        );
        errOldPassword.setText(errors.getFirstErrorMessage(User.Fields.OldPassword));
        errPassword.setText(errors.getFirstErrorMessage(User.Fields.Password));
        errConfirmPassword.setText(errors.getFirstErrorMessage(User.Fields.ConfirmPassword));
        btnSave.setEnabled(errors.isEmpty());
    }

    private void save() {
        controller.save(
                txtOldPassword.getText(),
                txtPassword.getText(),
                txtConfirmPassword.getText()
        );
        close();

    }


}
