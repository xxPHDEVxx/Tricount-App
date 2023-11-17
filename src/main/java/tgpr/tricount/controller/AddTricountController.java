package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.framework.ErrorList;
import tgpr.tricount.model.*;
import tgpr.tricount.view.AddTricountView;

public class AddTricountController extends Controller {

    private final AddTricountView view = new AddTricountView(this);
    public ListTricountsController listTricountController ;
    @Override
    public Window getView() {
        return view;
    }

    public AddTricountController(ListTricountsController listTricountController){
        this.listTricountController=listTricountController;
    }

    public void createTricount(Tricount tricount) {
        int id = tricount.save().getId();
        Subscription sub = new Subscription(id, tricount.getCreatorId());
        sub.save();
        this.listTricountController.fetchTricounts();
        this.listTricountController.reloadData("");
        view.close();
    }

    public ErrorList validate(String title, String description) {
        var errors = new ErrorList();
        errors.add(TricountValidator.isValidTitle(title));
        if (!description.isBlank())
            errors.add(TricountValidator.isValidDescription(description));

        return errors;
    }
}
