package tgpr.tricount.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.framework.Controller;
import tgpr.framework.Margin;
import tgpr.framework.Spacing;
import tgpr.framework.Tools;
import tgpr.tricount.controller.AddTricountController;
import tgpr.tricount.model.Operation;
import tgpr.tricount.model.Security;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.model.User;

import java.util.List;
import java.util.regex.Pattern;

public class AddTricountView extends DialogWindow {
    private final AddTricountController controller;
    private final TextBox txtTitle;
    private final TextBox txtDesc;
    private final Label errTitle = new Label("");
    private final Label errDesc = new Label("");
    private final Button btnCreate;


    public AddTricountView(AddTricountController controller) {
        super("Create a new tricount");
        setHints(List.of(Hint.CENTERED));

        this.controller = controller;

        Panel vert = new Panel().setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel root = new Panel();
        root.addTo(vert);
        root.setLayoutManager(new GridLayout(2).setTopMarginSize(1));

        new Label("Title:").addTo(root);
        txtTitle = new TextBox(new TerminalSize(21, 1)).addTo(root)
                .setTextChangeListener((txt, byUser) -> validate());
        new EmptySpace().addTo(root);
        errTitle.addTo(root)
                .setForegroundColor(TextColor.ANSI.RED);

        new Label("Description:").addTo(root);
        txtDesc = new TextBox(new TerminalSize(31, 3)).addTo(root)
                .setTextChangeListener((txt, byUser) -> validate());
        new EmptySpace().addTo(root);
        errDesc.addTo(root)
                .setForegroundColor(TextColor.ANSI.RED);

        new EmptySpace().addTo(root);
        new EmptySpace().addTo(root);


        var buttons = new Panel().addTo(vert).setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        buttons.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.End));
        btnCreate = new Button("Create", this::create).addTo(buttons);
        new Button("Cancel", this::close).addTo(buttons);

        setComponent(vert);
    }

    private void create() {
        Tricount tricount = new Tricount(txtTitle.getText(), txtDesc.getText(), Security.getLoggedUserId());
        controller.createTricount(tricount);
    }

    private void validate() {
        var errors = controller.validate(
                txtTitle.getText(),
                txtDesc.getText()
        );
        errTitle.setText(errors.getFirstErrorMessage(Operation.Fields.Title));
        errDesc.setText(errors.getFirstErrorMessage(Operation.Fields.Amount));
    }
}