package tgpr.tricount.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.tricount.controller.ViewBalanceController;
import tgpr.tricount.model.Security;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.model.User;

import java.text.DecimalFormat;
import java.util.List;

public class ViewBalanceView extends DialogWindow {
    private final ViewBalanceController controller;
    private final Tricount tricount;

    public ViewBalanceView(ViewBalanceController controller, Tricount tricount) {
        super("Balance");
        setHints(List.of(Hint.CENTERED));

        this.tricount = tricount;
        this.controller = controller;

        DecimalFormat decimalFormat = new DecimalFormat("#0.00");

        Panel root = new Panel().setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel balance = new Panel();
        balance.addTo(root);
        balance.setLayoutManager(new GridLayout(3).setTopMarginSize(1));

        List<User> users = controller.getUsers();
        Double balanceMax = 0.00;
        int indexMax = 0;
        for (int i = 0; i < users.size(); ++i) {
            Double userBalance = controller.getBalance(tricount, users.get(i));
            if (userBalance > balanceMax)
                balanceMax = userBalance;
            indexMax = i;
        }
        for (int i = 0; i < users.size(); ++i) {
            Double userBalance = controller.getBalance(tricount, users.get(i));
            System.out.println((int) Math.round((balanceMax/userBalance)*-100));
            System.out.println((int) Math.round((balanceMax/userBalance)*100));
            if (userBalance < 0) {
                if (i == indexMax)
                    new Label(decimalFormat.format(userBalance) + " €").setBackgroundColor(new TextColor.RGB(255, 0, 0)).setLabelWidth(100).addTo(balance);
                else
                    new Label(decimalFormat.format(userBalance) + " €").setBackgroundColor(new TextColor.RGB(255, 0, 0)).setLabelWidth((int) Math.round((balanceMax/userBalance)*-100)).addTo(balance);
                new Label("|").addTo(balance);
                if (users.get(i).equals(Security.getLoggedUser()))
                    new Label(users.get(i).getFullName() + " (me)").setForegroundColor(new TextColor.RGB(0, 0, 255)).addTo(balance);
                else
                    new Label(users.get(i).getFullName()).addTo(balance);
            } else {
                if (users.get(i).equals(Security.getLoggedUser()))
                    new Label(users.get(i).getFullName() + " (me)").setForegroundColor(new TextColor.RGB(0, 0, 255)).addTo(balance);
                else
                    new Label(users.get(i).getFullName()).addTo(balance);
                new Label("|").addTo(balance);
                if (i == indexMax)
                    new Label(decimalFormat.format(userBalance) + " €").setBackgroundColor(new TextColor.RGB(0, 255, 0)).setLabelWidth(100).addTo(balance);
                else
                    new Label(decimalFormat.format(userBalance) + " €").setBackgroundColor(new TextColor.RGB(0, 255, 0)).setLabelWidth((int) Math.round((balanceMax/userBalance)*100)).addTo(balance);
            }
        }

        balance.addEmpty();

        var buttons = new Panel().addTo(root).setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        buttons.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        new Button("Close", this::close).addTo(buttons);

        setComponent(root);
    }
}
