package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.framework.ErrorList;
import tgpr.tricount.model.*;
import tgpr.tricount.view.EditTricountView;

import java.time.LocalDateTime;
import java.util.List;

public class EditTricountController extends Controller {
    private final EditTricountView view ;
    private Tricount tricount;
    private Integer idTricount;
    private LocalDateTime createdAt;
    private final boolean isNew;
    private boolean delete;
    public ListTricountsController listTricountController;

    public EditTricountController(Tricount tricount, ListTricountsController listTricountController){
        this.tricount = tricount;
        this.idTricount = tricount.getId();
        this.createdAt = tricount.getCreatedAt();
        this.delete = false;
        this.listTricountController=listTricountController;

        isNew = tricount == null;
        view = new EditTricountView(this, tricount);
    }

    public void save(String title, String description, List<User> nvParticipants) {
        var errors = validate(title, description, idTricount);
        if (errors.isEmpty()) {
            // rajouter l'user id de l'utilisateur connecté
            tricount = new Tricount( title, description, Security.getLoggedUserId());
            tricount.setId(idTricount);
            tricount.setCreatedAt(createdAt);
            tricount.save();
            for (User partic :
                    nvParticipants) {
                Subscription sub = new Subscription(tricount.getId(), partic.getId());
                sub.save();
            }

            this.listTricountController.fetchTricounts();
            this.listTricountController.reloadData("");
            view.close();

       } else
            showErrors(errors);
    }
    public ErrorList validate(String title, String description, int idTricount) {
        var errors = new ErrorList();

        if (isNew) {
            errors.add(TricountValidator.isValidTitle(title));
            errors.add(TricountValidator.isValidDescription(description));
        }


        var tric = new Tricount(title, description, Security.getLoggedUserId());
        errors.addAll(TricountValidator.validate(tric, idTricount));

        return errors;
    }

    public Tricount getTricount() {return tricount;}
    @Override
    public Window getView() {
        return view;
    }

    public boolean isDelete() {return delete;}
    public void delete() {
        if (askConfirmation("You're about to delete this tricount.\n" +
                "Do you confirm ! ", "Delete tricount")) {
            if (Security.isAdmin() || Security.getLoggedUserId() == tricount.getCreatorId()) {
                tricount.delete();
                delete = true;
                this.listTricountController.fetchTricounts();
                this.listTricountController.reloadData("");
                view.close();
            }
        }
    }
    public void viewTemplates(){
        navigateTo(new ViewTemplatesController(tricount));
    }
}
