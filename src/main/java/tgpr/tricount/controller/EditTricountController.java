package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.framework.ErrorList;
import tgpr.framework.Tools;
import tgpr.tricount.model.*;
import tgpr.tricount.view.EditTricountView;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class EditTricountController extends Controller {
    private final EditTricountView view ;
    private Tricount tricount;
    private Integer idTricount;
    private LocalDateTime createdAt;
    private final boolean isNew;

    public EditTricountController(Tricount tricount){
        this.tricount = tricount;
        this.idTricount = tricount.getId();
        this.createdAt = tricount.getCreatedAt();
        isNew = tricount == null;
        view = new EditTricountView(this, tricount);
    }

    public void save(String title, String description, List<User> nvParticipants) {
        var errors = validate(title, description);
        if (errors.isEmpty()) {
            // rajouter l'user id de l'utilisateur connecté
            tricount = new Tricount( title, description, 1);
            tricount.setId(idTricount);
            tricount.setCreatedAt(createdAt);
            tricount.save();
            for (User partic :
                    nvParticipants) {
                Subscription sub = new Subscription(tricount.getId(), partic.getId());
                sub.save();
            }

            view.close();

       } else
            showErrors(errors);
    }
    public ErrorList validate(String title, String description) {
        var errors = new ErrorList();

        if (isNew) {
            errors.add(TricountValidator.isValidTitle(title));
            errors.add(TricountValidator.isValidDescription(description));
        }


        var tric = new Tricount(title, description, Security.getLoggedUserId());
        errors.addAll(TricountValidator.validate(tric));

        return errors;
    }

    public Tricount getTricount() {return tricount;}
    @Override
    public Window getView() {
        return view;
    }

    public void delete() {
        if (askConfirmation("You're about to delete this tricount.\n" +
                "Do you confirm ! ", "Delete tricount")) {
            if (Security.isAdmin() || Security.getLoggedUserId() == tricount.getCreatorId()) {
                tricount.delete();
                view.close();
            }
        }
    }
}
