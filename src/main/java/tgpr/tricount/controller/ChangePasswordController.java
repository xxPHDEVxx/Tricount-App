package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.tricount.view.ChangePasswordView;

public class ChangePasswordController extends Controller {
    public ChangePasswordController() {
        this.view = new ChangePasswordView(this);
    }

    private ChangePasswordView view;

    @Override
    public Window getView() {
        return view;
    }
}
