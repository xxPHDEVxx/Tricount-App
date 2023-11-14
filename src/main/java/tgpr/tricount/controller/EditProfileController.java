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

    public void saveProfile(String mail, String fullname, String iban){
        var errors = validate(mail, fullname, iban);
        if (errors.isEmpty()) {
            user.setMail(mail);
            user.setFullName(fullname);
            user.setIban(iban);
            view.close();
            // Ajouter modifications Bases de Données
        } else
            showErrors(errors);
    }

    public ErrorList validate(String mail, String fullname, String iban) {
        var errors = new ErrorList();

        // Ajouter fonctions de validation, exemple :
        // -> errors.add(UserValidator.isValidMail(mail));

        return errors;
    }
}
