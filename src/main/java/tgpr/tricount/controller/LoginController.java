package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.tricount.TricountApp;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.model.User;
import tgpr.tricount.model.Security;
import tgpr.tricount.view.LoginView;
import tgpr.framework.Controller;
import tgpr.framework.Error;
import tgpr.framework.ErrorList;
import tgpr.framework.Model;

import java.util.List;

public class LoginController extends Controller {
    public void exit() {
        System.exit(0);
    }

    public List<Error> login(String pseudo, String password) {
        var errors = new ErrorList();
        //errors.add(MemberValidator.isValidPseudo(pseudo));
        //errors.add(MemberValidator.isValidPassword(password));

        if (errors.isEmpty()) {
            var member = User.checkCredentials(pseudo, password);
            if (member != null) {
                Security.login(member);
                Tricount tricount = Tricount.getByKey(4);
                navigateTo(new ListTricountsController(tricount));
            } else
                showError(new Error("invalid credentials"));
        } else
            showErrors(errors);

        return errors;
    }

    public void seedData() {
        Model.seedData(TricountApp.DATABASE_SCRIPT_FILE);
    }

    @Override
    public Window getView() {
        return new LoginView(this);
    }
}
