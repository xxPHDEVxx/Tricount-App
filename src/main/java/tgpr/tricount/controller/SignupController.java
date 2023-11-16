package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.tricount.view.ListTricountsView;
import tgpr.tricount.view.SignupView;

public class SignupController extends Controller {
    private final SignupView view;

    public SignupController() {
        view = new SignupView(this);
    }
    @Override
    public Window getView() {
        return view;
    }
}
