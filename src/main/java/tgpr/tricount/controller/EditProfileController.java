package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.framework.ErrorList;
import tgpr.tricount.model.*;
import tgpr.tricount.view.AddTricountView;
import tgpr.tricount.view.EditProfileView;
public class EditProfileController extends Controller{

    private final EditProfileView view;
    private final User user;

    public EditProfileController(User user) {
        this.user = user;
        view = new EditProfileView(this);
    }
    @Override
    public Window getView() {
        return view;
    }

    public String getFullName() {
        return user.getFullName();
    }

    public String getMail() {
        return user.getMail();
    }

    public String getIban(){ return user.getIban();}

    public void saveProfile(String mail, String fullname, String iban){
        var errors = validate(mail, fullname, iban);
        if (errors.isEmpty()) {
            user.setMail(mail);
            user.setFullName(fullname);
            user.setIban(iban);
            user.save();
            view.close();
        } else
            showErrors(errors);
    }

    public ErrorList validate(String mail, String fullname, String iban) {
        var errors = new ErrorList();

        errors.add(ProfileValidator.isValidMail(mail));
        errors.add(ProfileValidator.isValidFullname(fullname));
        errors.add(ProfileValidator.isValidIban(iban));

        return errors;
    }
    public User getUser() {
        return user;
    }
}
