package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.tricount.model.Operation;
import tgpr.tricount.model.Security;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.view.EditOperationView;
import tgpr.tricount.view.ListTricountsView;
import tgpr.tricount.view.ViewTricountView;


import java.util.ArrayList;
import java.util.List;

public class ListTricountsController extends Controller {
    private final ListTricountsView view;
    private List<Tricount> listTricounts = new ArrayList<>();;

    public ListTricountsController() {
        fetchTricounts("");
        view = new ListTricountsView(this, listTricounts);
    }
    public void fetchTricounts(String filtre){
        if (filtre.isEmpty())
            listTricounts=Tricount.getAll();
    }

    /*public void logout() {
        Security.logout();
        navigateTo(new LoginController());
    }*/

    public void exit() {
        System.exit(0);
    }
    public void openTricount(Tricount tricount) {
        navigateTo(new ViewTricountController(tricount));
    }
    public void addTricount() {
        navigateTo(new AddTricountController());
    }
    @Override
    public Window getView() {
        return view;
    }
}