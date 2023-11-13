package tgpr.tricount.view;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.tricount.controller.ChangePasswordController;

import java.util.List;

public class ChangePasswordView extends DialogWindow {
    private final ChangePasswordController controller;
    private final TextBox txtOldPassword;
    private final TextBox txtPassword;
    private final TextBox txtConfirmPassword;
    private final Button btnSave;
    private final Label errOldPassword = new Label("");
    private final Label errPassword = new Label("");
    private final Label errConfirmPassword = new Label("");
    public ChangePasswordView(ChangePasswordController controller){
        super("Change Password");
        this.controller = controller;

        setHints(List.of(Hint.CENTERED, Hint.MODAL));
        setCloseWindowWithEscape(true);

        Panel root = new Panel();
        setComponent(root);

        Panel fields = new Panel().setLayoutManager(new GridLayout(2).setTopMarginSize(1)).addTo(root);

        new Label("Old Password: ").addTo(fields);
        txtOldPassword = new TextBox().sizeTo(25)
                .addTo(fields).takeFocus();

        new Label("Password: ").addTo(fields);
        txtPassword = new TextBox().sizeTo(25)
                .addTo(fields);
        new Label("Confirm Password :").addTo(fields);
        txtConfirmPassword= new TextBox().sizeTo(25)
                .addTo(fields);
        new EmptySpace().addTo(fields);
        Panel buttons = new Panel().setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        btnSave = new Button("Save", this::save).addTo(buttons).setEnabled(false);
        new Button("Cancel", this::close).addTo(buttons);
        root.addComponent(buttons, LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

    }

    private void save() {

    }


}
