package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.tricount.view.AddTricountView;

public class AddTricountController extends Controller {

    private final AddTricountView view = new AddTricountView(this);
    @Override
    public Window getView() {
        return view;
    }
}
