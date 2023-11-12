package tgpr.tricount.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.gui2.table.TableCellRenderer;
import com.googlecode.lanterna.input.KeyStroke;
import tgpr.framework.ColumnSpec;
import tgpr.framework.Controller;
import tgpr.framework.ObjectTable;
import tgpr.tricount.controller.DisplayOperationController;
import tgpr.tricount.controller.EditOperationController;
import tgpr.tricount.controller.EditTricountController;
import tgpr.tricount.model.Operation;
import tgpr.tricount.model.Repartition;

import javax.naming.ldap.Control;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DisplayOperationView extends DialogWindow {
    private final DisplayOperationController controller;
    private final Operation operation;
    private final Label lblTitle;
    private final Label lblAmount;
    private final Label lblDate;
    private final Label lblPaidBy;
    private final ObjectTable<Repartition> repartitionTable;
    private Button btnUp;
    private Button btnDown;
    private Button btnEdit;


    public DisplayOperationView(DisplayOperationController controller, Operation operation) {
        super("View Expense Detail");

        this.controller = controller;
        this.operation = operation;

        setHints(List.of(Hint.CENTERED, Hint.FIXED_SIZE));
        setCloseWindowWithEscape(true);
        setFixedSize(new TerminalSize(47, 12));

        Panel root = Panel.verticalPanel();
        setComponent(root);

        Panel fields = new Panel().setLayoutManager(new GridLayout(2).setTopMarginSize(1)).addTo(root);

        fields.addComponent(new Label("Title:"));
        lblTitle = new Label("").addTo(fields).addStyle(SGR.BOLD);

        fields.addComponent(new Label("Amount:"));
        lblAmount = new Label("").addTo(fields).addStyle(SGR.BOLD);

        fields.addComponent(new Label("Date:"));
        lblDate = new Label("").addTo(fields).addStyle(SGR.BOLD);

        fields.addComponent(new Label("Paid by:"));
        lblPaidBy = new Label("").addTo(fields).addStyle(SGR.BOLD);
        fields.addEmpty();
        fields.addEmpty();

        fields.addComponent(new Label("For whom:"));

        repartitionTable = new ObjectTable<>(
                new ColumnSpec<>("Participant  ", r -> r.getUser().getFullName()),
                new ColumnSpec<>("Weight   ", Repartition::getWeight).alignRight(),
                new ColumnSpec<>("Amount", repartition -> {
                    int weight = repartition.getWeight();
                    double operationAmount = operation.getAmount();
                    int sommeWeight = 0;
                    for (var rep : operation.getRepartitions()) {
                        sommeWeight += rep.getWeight();
                    }
                    double calculedAmount = (weight * operationAmount) / sommeWeight;
                    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
                    return decimalFormat.format(calculedAmount) + " €";
                })

        ).addTo(fields);

        root.addEmpty();
        createButtonPanel().addTo(root);

        refresh();
    }

    private Panel createButtonPanel() {
        var panel = new Panel()
                .setLayoutManager(new LinearLayout(Direction.HORIZONTAL))
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.End));


        btnUp = new Button("Up", this::up).addTo(panel).setEnabled(false);
        btnDown = new Button("Down", this::down).addTo(panel).setEnabled(false);
        btnEdit = new Button("Edit", this::edit).addTo(panel).setEnabled(false);
        new Button("Close", this::close).addTo(panel);
        return panel;
    }

    private void edit() {
        Controller.navigateTo(new EditOperationController(operation.getTricount(), operation));
    }

    private void down() {
        List<Operation> operations = operation.getTricount().getOperations();
        int i = 0;
        while (i < operations.size() && !operations[i].equals(operation))
            ++i;
        if (i < operations.size() - 1 && operations[i + 1] != null) {
            close();
            Controller.navigateTo(new DisplayOperationController(operations[i + 1]));

        }

    }

    private void up() {
        List<Operation> operations = operation.getTricount().getOperations();
        int i = 0;
        while (i < operations.size() && !operations[i].equals(operation))
            ++i;

        if (i > 0 && operations[i - 1] != null) {
            close();
            Controller.navigateTo(new DisplayOperationController(operations[i - 1]));
        }
    }


    private void refresh() {
        if (operation != null) {
            lblTitle.setText(operation.getTitle());
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            lblAmount.setText(decimalFormat.format(operation.getAmount()) + " €");
            lblDate.setText(operation.getOperationDate().asString());
            lblPaidBy.setText(operation.getInitiator().getFullName());
            repartitionTable.clear();
            var repartitions = controller.getRepartitions();
            repartitionTable.add(repartitions);
            btnUp.setEnabled(true);
            btnDown.setEnabled(true);
            btnEdit.setEnabled(true);
        }
    }
}
