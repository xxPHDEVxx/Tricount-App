package tgpr.tricount.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.tricount.controller.AddTemplateController;
import tgpr.tricount.model.Template;

import java.util.List;
import java.util.regex.Pattern;


public class AddTemplateView extends DialogWindow {
    private final AddTemplateController controller;
    private final TextBox txtTitle;
    private final Button btnCreate;
    private final Label errTitle = new Label("");
    public AddTemplateView(AddTemplateController controller) {
        super("create a new template");
        this.controller = controller;

        setHints(List.of(Hint.CENTERED, Hint.MODAL));
        setCloseWindowWithEscape(true);

        Panel root = new Panel();
        setComponent(root);

        Panel fields = new Panel().setLayoutManager(new GridLayout(2).setTopMarginSize(1)).addTo(root);

        new Label("Title: ").addTo(fields);
        txtTitle = new TextBox().sizeTo(25)
                .addTo(fields).takeFocus();



        new EmptySpace().addTo(fields);
        errTitle.addTo(fields).setForegroundColor(TextColor.ANSI.RED).addTo(fields);
        txtTitle.setTextChangeListener((txt, byUser) -> validate());
        new EmptySpace().addTo(fields);
        Panel buttons = new Panel().setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        btnCreate = new Button("Create", this::create).addTo(buttons).setEnabled(false);
        new Button("Cancel", this::close).addTo(buttons);
        root.addComponent(buttons, LinearLayout.createLayoutData(LinearLayout.Alignment.Center));


    }

    private void validate() {
        var errors = controller.validate(txtTitle.getText());
        errTitle.setText(errors.getFirstErrorMessage(Template.Fields.Title));
        btnCreate.setEnabled(errors.isEmpty());
    }
    private void create() {
        controller.create(txtTitle.getText());
        close();
    }


}
