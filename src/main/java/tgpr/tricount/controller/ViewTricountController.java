package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.tricount.model.Security;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.model.User;
import tgpr.tricount.view.ViewTricountView;

public class ViewTricountController extends Controller {
    ViewTricountView view = new ViewTricountView(this);
    @Override
    public Window getView() {
        return view;
    }

    public User getUser() {
        return Security.getLoggedUser();
    }
}
