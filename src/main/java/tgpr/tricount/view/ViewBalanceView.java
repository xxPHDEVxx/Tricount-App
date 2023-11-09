package tgpr.tricount.view;

import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.tricount.controller.ViewBalanceController;
import tgpr.tricount.model.Tricount;

public class ViewBalanceView extends DialogWindow {
    public ViewBalanceView(ViewBalanceController controller, Tricount tricount) {
        super("Balance");
    }
}
