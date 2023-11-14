package tgpr.tricount.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.tricount.controller.EditProfileController;
import tgpr.tricount.model.Operation;
import tgpr.tricount.model.User;

import java.util.List;
import java.util.regex.Pattern;

public class EditProfileView extends DialogWindow {

    private final EditProfileController controller;
    private final TextBox txtMail;
    private final TextBox txtFullname;
    private final TextBox txtIBAN;
    private final Label errMail = new Label("");
    private final Label errFullname = new Label("");
    private final Label errIBAN = new Label("");
    private final Button btnSave;

    public EditProfileView(EditProfileController controller) {
        super("Edit Profile");
        setHints(List.of(Hint.CENTERED));

        this.controller = controller;

        Panel vert = new Panel().setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel root = new Panel();
        root.addTo(vert);
        root.setLayoutManager(new GridLayout(2).setTopMarginSize(1));

        new Label("Mail:").addTo(root);
        txtMail = new TextBox(new TerminalSize(20, 1)).addTo(root)
                .setTextChangeListener((txt, byUser) -> validate());
        new EmptySpace().addTo(root);
        errMail.addTo(root)
                .setForegroundColor(TextColor.ANSI.RED);

        new Label("Full Name:").addTo(root);
        txtFullname = new TextBox(new TerminalSize(25, 1)).addTo(root)
                .setValidationPattern(Pattern.compile("^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$"))
                .setTextChangeListener((txt, byUser) -> validate());
        new EmptySpace().addTo(root);
        errFullname.addTo(root)
                .setForegroundColor(TextColor.ANSI.RED);

        new Label("IBAN:").addTo(root);
        txtIBAN = new TextBox(new TerminalSize(20, 1)).addTo(root)
                .setValidationPattern(Pattern.compile("^[A-Z0-9\\s]+$"))
                .setTextChangeListener((txt, byUser) -> validate());
        new EmptySpace().addTo(root);
        errIBAN.addTo(root)
                .setForegroundColor(TextColor.ANSI.RED);

        new EmptySpace().addTo(root);
        new EmptySpace().addTo(root);


        var buttons = new Panel().addTo(vert).setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        buttons.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        btnSave = new Button("Save", this::save).addTo(buttons);
        new Button("Cancel", this::close).addTo(buttons);

        setComponent(vert);
    }

    private void save(){
        controller.saveProfile(txtMail.getText(), txtFullname.getText(),txtIBAN.getText());
    }

    private void validate() {
        var errors = controller.validate(
                txtMail.getText(),
                txtFullname.getText(),
                txtIBAN.getText());

        errMail.setText(errors.getFirstErrorMessage(User.Fields.Mail));
        errFullname.setText(errors.getFirstErrorMessage(User.Fields.FullName));
        errIBAN.setText(errors.getFirstErrorMessage(User.Fields.Iban));
    }
}
