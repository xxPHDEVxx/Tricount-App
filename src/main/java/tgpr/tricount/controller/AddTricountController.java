package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.framework.ErrorList;
import tgpr.tricount.model.Subscription;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.model.TricountValidator;
import tgpr.tricount.model.User;
import tgpr.tricount.view.AddTricountView;

public class AddTricountController extends Controller {

    private final AddTricountView view = new AddTricountView(this);
    @Override
    public Window getView() {
        return view;
    }

    public void createTricount(Tricount tricount) {
        var errors = validate(tricount.getTitle(), tricount.getDescription());
        if (errors.isEmpty()) {
            tricount.save();
            view.close();
            Controller.navigateTo(new EditTricountController(tricount));
        } else
            showErrors(errors);
    }

    public ErrorList validate(String title, String description) {
        var errors = new ErrorList();

        errors.add(TricountValidator.isValidTitle(title));
        errors.add(TricountValidator.isValidDescription(description));

        return errors;
    }
}
