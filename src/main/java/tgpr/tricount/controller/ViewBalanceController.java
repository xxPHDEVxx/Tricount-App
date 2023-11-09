package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.view.ViewBalanceView;
import tgpr.tricount.view.ViewTricountView;

public class ViewBalanceController extends Controller {
    private final ViewBalanceView view;
    private Tricount tricount;

    public ViewBalanceController(Tricount tricount) {
        this.tricount = tricount;
        view = new ViewBalanceView(this, tricount);
    }

    @Override
    public Window getView() {
        return view;
    }
}
