package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.model.User;
import tgpr.tricount.view.EditTricountView;

public class EditTricountController extends Controller {
    private final EditTricountView view ;
    private Tricount tricount;
    private User user;

    public EditTricountController(Tricount tricount) {
        this.tricount = tricount;
        view = new EditTricountView(this, tricount);
    }


    @Override
    public Window getView() {
        return view;
    }
}
