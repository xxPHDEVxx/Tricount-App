package tgpr.tricount.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.framework.ColumnSpec;
import tgpr.framework.Controller;
import tgpr.framework.ObjectTable;
import tgpr.tricount.TricountApp;
import tgpr.tricount.controller.ViewTricountController;
import tgpr.tricount.model.Operation;
import tgpr.tricount.model.Repartition;
import tgpr.tricount.model.Security;
import tgpr.tricount.model.Tricount;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class ViewTricountView extends DialogWindow {
    private final ViewTricountController controller;
    private final Button btnBalance;
    private final Button btnNewExpense;
    private final Button btnEditTricount;
    private final Tricount tricount;
    private ObjectTable<Operation> tbl;

    public ViewTricountView(ViewTricountController controller, Tricount tricount) {
        super("View Tricount Detail");
        setHints(List.of(Hint.CENTERED));

        this.tricount = tricount;
        this.controller = controller;
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");

        Panel root = new Panel().setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel description = new Panel();
        description.addTo(root);
        description.setLayoutManager(new GridLayout(2).setTopMarginSize(1));

        new Label("Title:").addTo(description);
        new Label(tricount.getTitle()).setForegroundColor(TextColor.ANSI.BLACK_BRIGHT).addTo(description);
        new Label("Description:").addTo(description);
        String desc = tricount.getDescription() != null ? tricount.getDescription() : "-";
        new Label(desc).setForegroundColor(TextColor.ANSI.BLACK_BRIGHT).addTo(description);
        new Label("Created by:").addTo(description);
        new Label(tricount.getCreator().getFullName()).setForegroundColor(TextColor.ANSI.BLACK_BRIGHT).addTo(description);
        new Label("Date:").addTo(description);
        new Label(tricount.getCreatedAt().toString().replace("T", "  ")).setForegroundColor(TextColor.ANSI.BLACK_BRIGHT).addTo(description);

        new Label("Total Expense:").addTo(description);
        new Label(decimalFormat.format(controller.getMyExpenses(tricount).get(0)) + " €").setForegroundColor(TextColor.ANSI.BLACK_BRIGHT).addTo(description);
        new Label("My Expense:").addTo(description);
        new Label(decimalFormat.format(controller.getMyExpenses(tricount).get(1)) + " €").setForegroundColor(TextColor.ANSI.BLACK_BRIGHT).addTo(description);
        new Label("My Balance:").addTo(description);
        TextColor balanceColor = controller.getMyExpenses(tricount).get(2) < 0 ? TextColor.ANSI.RED : TextColor.ANSI.GREEN;
        new Label(decimalFormat.format(controller.getMyExpenses(tricount).get(2)) + " €").setForegroundColor(balanceColor).addTo(description);

        new EmptySpace().addTo(root);

        Panel panelOperation = new Panel();
        panelOperation.addTo(root);

        tbl = new ObjectTable<>(
                new ColumnSpec<>("Operation", Operation::getTitle).setWidth(30),
                new ColumnSpec<>("Amount", Operation::getAmount).setFormat("%.2f €").setWidth(10).alignRight(),
                new ColumnSpec<>("Paid By", Operation::getInitiator).setWidth(10),
                new ColumnSpec<>("Date", operation -> {
                    LocalDate date = operation.getOperationDate();
                    return date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
                })
        );
        List<Operation> operations = tricount.getOperations();
        for (int i = operations.size(); i > 0; i--) {
            tbl.add(operations.get(i - 1));
        }

        tbl.setSelectAction(() -> {
            Operation operation = tbl.getSelected();
            controller.openOperation(operation);
        });

        tbl.addTo(panelOperation);

        new EmptySpace().addTo(root);

        var buttons = new Panel().addTo(root).setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        buttons.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        btnBalance = new Button("Balance", controller::balance).addTo(buttons);
        btnNewExpense = new Button("New Expense", controller::newExpense).addTo(buttons);
        btnEditTricount = new Button("Edit Tricount", controller::editTricount).addTo(buttons);
        new Button("Close", this::close).addTo(buttons);

        setComponent(root);
    }
}
