package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.framework.ErrorList;
import tgpr.tricount.model.ProfileValidator;
import tgpr.tricount.model.User;
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
    public ErrorList validate(String mail, String fullname, String iban, String password, String confirmpassword) {
        var errors = new ErrorList();

        errors.add(ProfileValidator.isValidMail(mail));
        errors.add(ProfileValidator.isValidFullname(fullname));
        errors.add(ProfileValidator.isValidIban(iban));
        errors.add(ProfileValidator.isValidPassword(password).toString(), User.Fields.Password);
        errors.add(ProfileValidator.isValidConfirmPassword(password, confirmpassword).toString(), User.Fields.ConfirmPassword);

        return errors;
    }
}