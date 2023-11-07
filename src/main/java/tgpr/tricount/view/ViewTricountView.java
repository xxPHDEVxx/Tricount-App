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
import tgpr.tricount.TricountApp;
import tgpr.tricount.controller.ViewTricountController;
import tgpr.tricount.model.Operation;
import tgpr.tricount.model.Repartition;
import tgpr.tricount.model.Security;
import tgpr.tricount.model.Tricount;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ViewTricountView extends DialogWindow {
    private final ViewTricountController controller;
    private final Button btnBalance;
    private final Button btnNewExpense;
    private final Button btnEditTricount;

    public ViewTricountView(ViewTricountController controller) {
        super("View Tricount Detail");
        setHints(List.of(Hint.CENTERED));

        this.controller = controller;
        Tricount tricount = Tricount.getByKey(4);

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

        List<Operation> operations = tricount.getOperations();
        double totalExpense = 0, myExpenses = 0,myBalance = 0, weight = 0, myPaid = 0;
        for (Operation operation : operations) {
            if (operation.getTricountId() == tricount.getId()) {
                totalExpense += operation.getAmount();
                if (operation.getInitiator().equals(Security.getLoggedUser()))
                    myPaid += operation.getAmount();
                List<Repartition> repartitions = operation.getRepartitions();
                int userWeight =0;
                for (int i = 0; i<repartitions.size();i++) {
                    weight += repartitions.get(i).getWeight();
                    if (repartitions.get(i).getUser().equals(Security.getLoggedUser()))
                        userWeight = i;
                }
                myExpenses += operation.getAmount()*(repartitions.get(userWeight).getWeight()/weight);
                weight=0;
            }
        }
        myBalance = myPaid - myExpenses;

        new Label("Total Expense:").addTo(description);
        new Label((double) Math.round(totalExpense *100)/100 + " €").setForegroundColor(TextColor.ANSI.BLACK_BRIGHT).addTo(description);
        new Label("My Expense:").addTo(description);
        new Label((double) Math.round(myExpenses *100)/100 + " €").setForegroundColor(TextColor.ANSI.BLACK_BRIGHT).addTo(description);
        new Label("My Balance:").addTo(description);
        TextColor balanceColor = myBalance < 0 ? TextColor.ANSI.RED : TextColor.ANSI.GREEN;
        new Label((double) Math.round(myBalance *100)/100 + " €").setForegroundColor(balanceColor).addTo(description);

        Panel panelOperation = new Panel();
        panelOperation.addTo(root);
        panelOperation.setLayoutManager(new GridLayout(4).setTopMarginSize(1));
        new Label("Operation\t\t\t\t").setForegroundColor(TextColor.ANSI.BLACK_BRIGHT).addStyle(SGR.UNDERLINE).setLabelWidth(500).addTo(panelOperation);
        new Label("\tAmount").setForegroundColor(TextColor.ANSI.BLACK_BRIGHT).addStyle(SGR.UNDERLINE).addTo(panelOperation);
        new Label("Paid By\t\t").setForegroundColor(TextColor.ANSI.BLACK_BRIGHT).addStyle(SGR.UNDERLINE).addTo(panelOperation);
        new Label("Date").setForegroundColor(TextColor.ANSI.BLACK_BRIGHT).addStyle(SGR.UNDERLINE).addTo(panelOperation);
        for (int i = operations.size(); i>0; i--) {
            new Label(operations.get(i-1).getTitle()).addTo(panelOperation);
            new Label((double) Math.round(operations.get(i-1).getAmount() *100)/100 + " €").addTo(panelOperation);
            new Label(operations.get(i-1).getInitiator().getFullName()).addTo(panelOperation);
            LocalDate date = operations.get(i-1).getOperationDate();
            new Label(date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear()).addTo(panelOperation);
        }

        new EmptySpace().addTo(root);
        new EmptySpace().addTo(root);

        var buttons = new Panel().addTo(root).setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        buttons.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        btnBalance = new Button("Balance", this::balance).addTo(buttons);
        btnNewExpense = new Button("New Expense", this::newExpense).addTo(buttons);
        btnEditTricount = new Button("Edit Tricount", this::editTricount).addTo(buttons);
        new Button("Close", this::close).addTo(buttons);

        setComponent(root);
    }

    private void editTricount() {
    }

    private void newExpense() {
    }

    private void balance() {
    }
}
