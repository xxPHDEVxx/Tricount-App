package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.tricount.model.User;
import tgpr.tricount.view.ProfileView;

public class ProfileController extends Controller {
    private final ProfileView view;
    private final User user;
    public ProfileController(User user) {
        this.user = user;
        view = new ProfileView(this);
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
    public void editProfile() {
        navigateTo(new EditProfileController(user));
    }
    public void changePasswrd() {
        //ChangePasswordController(user);
    }
}
