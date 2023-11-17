package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.framework.Error;
import tgpr.framework.ErrorList;
import tgpr.framework.Tools;
import tgpr.tricount.model.Operation;
import tgpr.tricount.model.Security;
import tgpr.tricount.model.User;
import tgpr.tricount.view.ChangePasswordView;

import java.util.regex.Pattern;

public class ChangePasswordController extends Controller {
    public ChangePasswordController() {
        this.view = new ChangePasswordView(this);
    }

    private ChangePasswordView view;

    @Override
    public Window getView() {
        return view;
    }
    public Error validatePassword(String password){
        if(password == null || password.isBlank())
            return new Error("password is required");
        if(!Pattern.matches("^.{8,}$", password))
            return new Error("minimum 8 chars");
        if(!Pattern.matches("^(?=.*[A-Z]).*$", password))
            return new Error("missing an uppercase char");
        if(!Pattern.matches("^(?=.*[0-9]).*$", password))
            return new Error("missing a number char ");
        if(!Pattern.matches( "^(?=.*[^A-Za-z0-9]).*$", password))
            return new Error("missing a digit char");
        return Error.NOERROR;
    }

    public ErrorList validate(String oldPassword, String password, String comfirmPassword) {
        var errors = new ErrorList();
        String hashedOldPassword = Security.getLoggedUser().getHashedPassword();
        if(!hashedOldPassword.equals(Tools.hash(oldPassword)))
            errors.add("incorrect password", User.Fields.OldPassword);
        if(hashedOldPassword.equals(Tools.hash(password)))
            errors.add("must be different", User.Fields.Password);
        if(validatePassword(password) != Error.NOERROR)
            errors.add(validatePassword(password).toString(),User.Fields.Password);
        if(validatePassword(comfirmPassword) != Error.NOERROR)
            errors.add(validatePassword(comfirmPassword).toString(), User.Fields.ConfirmPassword);
        if(!comfirmPassword.equals(password))
            errors.add("must match password", User.Fields.ConfirmPassword);

        return errors;
    }

    public void save(String oldPassword, String password, String comfirmPassword) {
        var errors = validate(oldPassword, password, comfirmPassword);
        if(errors.isEmpty()) {
            String hashedPassword = Tools.hash(password);
            Security.getLoggedUser().setHashedPassword(hashedPassword);
            Security.getLoggedUser().save();
            view.close();
        } else
            showErrors(errors);

    }
}