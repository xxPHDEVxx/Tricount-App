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
    private final TerminalSize terminalSize = new TerminalSize(30,1);
    private final TextColor red = new TextColor.RGB(255, 0, 0);
    private final TextColor green = new TextColor.RGB(0, 255, 0);
    private final TextColor blue = new TextColor.RGB(0, 0, 255);
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
            if (userBalance < 0) userBalance *= -1;
            if (userBalance > balanceMax) {
                balanceMax = userBalance;
                indexMax = i;
            }
        }
        for (int i = 0; i < users.size(); ++i) {
            Double userBalance = controller.getBalance(tricount, users.get(i));

            int numberOfSpacing = (int) Math.round((balanceMax/userBalance)*10);
            if (numberOfSpacing < 0) numberOfSpacing *= -1;
            String spacing = " ".repeat(Math.max(0, numberOfSpacing));

            if (userBalance < 0) {
                //Prix et barre de couleur pour les négatifs
                if (i == indexMax)
                    createPanel("                    " + decimalFormat.format(userBalance) + " €",
                            red, true).addTo(balance);
                else
                    createPanel(spacing + decimalFormat.format(userBalance) + " €",
                            red, true).addTo(balance);

                new Label("|").addTo(balance);

                //Pseudos pour négatifs
                if (users.get(i).equals(Security.getLoggedUser()))
                    createPanel(users.get(i).getFullName() + " (me)", blue, false).addTo(balance);
                else
                    createPanel(users.get(i).getFullName(), null, false).addTo(balance);
            } else {
                //Pseudos pour positifs
                if (users.get(i).equals(Security.getLoggedUser()))
                    createPanel(users.get(i).getFullName() + " (me)", blue, true).addTo(balance);
                else
                    createPanel(users.get(i).getFullName(), null, true).addTo(balance);

                new Label("|").addTo(balance);

                //Prix et barre de couleur pour les positifs
                if (i == indexMax)
                    createPanel(decimalFormat.format(userBalance) + " €                    ",
                            green, false).addTo(balance);
                else
                    createPanel(decimalFormat.format(userBalance) + " €" + spacing,
                            green, false).addTo(balance);
            }
        }

        balance.addEmpty();

        var buttons = new Panel().addTo(root).setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        buttons.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        new Button("Close", this::close).addTo(buttons);

        setComponent(root);
    }

    public Panel createPanel(String text, TextColor backGroundColor, boolean alignRight) {
        LinearLayout.Alignment align = alignRight ? LinearLayout.Alignment.End : LinearLayout.Alignment.Beginning;
        Panel panel = new Panel().asHorizontalPanel().setLayoutData(LinearLayout.createLayoutData(align));

        if (backGroundColor == null)
            new Label(text)
                    .setPreferredSize(terminalSize).addTo(panel);
        else if (backGroundColor == blue)
            new Label(text)
                    .setForegroundColor(backGroundColor)
                    .setPreferredSize(terminalSize).addTo(panel);
        else
            new Label(text)
                    .setBackgroundColor(backGroundColor)
                    .setPreferredSize(terminalSize).addTo(panel);
        return panel;
    }
}
