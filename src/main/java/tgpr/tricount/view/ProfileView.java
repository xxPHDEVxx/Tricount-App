package tgpr.tricount.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.tricount.controller.ProfileController;
import tgpr.tricount.controller.ViewTricountController;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.model.User;

import java.util.List;

public class ProfileView extends DialogWindow {
    private final ProfileController controller;
    private final Button btnEditProfile;
    private final Button btnChangePsswrd;
    public ProfileView(ProfileController controller) {
        super("View Profile");
        this.controller = controller;
        setHints(List.of(Hint.CENTERED));
        TextColor blue = new TextColor.RGB(0, 128, 255);

        Panel root = new Panel().setLayoutManager(new LinearLayout(Direction.VERTICAL));
        root.addEmpty();

        Panel name = new Panel().addTo(root).setLayoutManager(new GridLayout(3).setHorizontalSpacing(0));
        new Label("Hey ").addTo(name);
        new Label(controller.getFullName()).setForegroundColor(blue).addTo(name);
        new Label("!").addTo(name);
        root.addEmpty();

        Panel mail = new Panel().addTo(root).setLayoutManager(new GridLayout(3).setHorizontalSpacing(0));
        new Label("I know your email adress is ").addTo(mail);
        new Label(controller.getMail()).setForegroundColor(blue).addTo(mail);
        new Label(".").addTo(mail);
        root.addEmpty();

        Panel help = new Panel().addTo(root);
        new Label(" What can I do for you?").addTo(help);
        root.addEmpty();

        var buttons = new Panel().addTo(root).setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        buttons.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        btnEditProfile = new Button("Edite Profile", controller::editProfile).addTo(buttons);
        btnChangePsswrd = new Button("Change Password", controller::changePasswrd).addTo(buttons);
        new Button("Close", this::close).addTo(buttons);

        setComponent(root);
    }
}
