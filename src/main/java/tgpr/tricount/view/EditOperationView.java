package tgpr.tricount.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.tricount.controller.EditOperationController;
import tgpr.tricount.model.Operation;
import tgpr.tricount.model.Template;

import java.util.List;

public class EditOperationView extends DialogWindow {
    private final EditOperationController controller;
    private final Operation operation;
    private TextBox txtTitle;
    private TextBox txtAmount;
    private TextBox txtDate;
    private ComboBox<Template> cboTemplate;
    private final Label errTitle = new Label("");
    private final Label errAmount = new Label("");
    private final Label errDate = new Label("");
    private final Label errTemplate = new Label("");
    private Button btnAddUpdate;
    public EditOperationView(EditOperationController controller, Operation operation){
        super((operation == null ? "Add " : "Update ") + "Operation");
        this.operation = operation;
        this.controller = controller;
        setHints(List.of(Hint.CENTERED, Hint.FIXED_SIZE));
        setCloseWindowWithEscape(true);
        setFixedSize(new TerminalSize(70, 20));

    }
}
