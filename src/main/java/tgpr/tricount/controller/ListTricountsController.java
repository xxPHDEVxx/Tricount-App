package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.view.EditOperationView;
import tgpr.tricount.view.ListTricountsView;
import tgpr.tricount.view.ViewTricountView;

public class ListTricountsController extends Controller {
    private final ListTricountsView view;
    private Tricount tricount;

    public ListTricountsController(Tricount tricount) {
        this.tricount = tricount;
        view = new ListTricountsView(this, tricount);
    }
    @Override
    public Window getView() {
        return view;
    }
}