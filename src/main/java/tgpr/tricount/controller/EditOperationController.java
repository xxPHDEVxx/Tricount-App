package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.framework.Error;
import tgpr.framework.ErrorList;
import tgpr.tricount.model.*;
import tgpr.tricount.view.EditOperationView;

import java.time.LocalDateTime;
import java.util.List;

public class EditOperationController extends Controller {
    private final EditOperationView view;
    private Operation operation;
    private Tricount tricount;
    private final boolean isNew;

    public EditOperationController(Tricount tricount) {
        this(tricount, null);
    }

    public EditOperationController(Tricount tricount, Operation operation) {
        this.tricount = tricount;
        this.operation = operation;
        isNew = operation == null;
        view = new EditOperationView(this, tricount, operation);
    }


    @Override
    public Window getView() {
        return view;
    }

    public Operation getOperation() {
        return operation;
    }

    public Tricount getTricount() {
        return tricount;
    }


    public void save(String title, String amount, String date, String user, List<Repartition> repartitions) {
        var errors = validate(title, amount, date, repartitions);
        if (errors.isEmpty()) {
            LocalDateTime createdAt = LocalDateTime.now();
            operation = new Operation(title, tricount.getId(), Double.parseDouble(amount), date.toDate(), User.getByFullName(user).getId(), createdAt);
            Operation saved = operation.save();
            for (var rep : repartitions) {
                Repartition repartition = new Repartition(saved.getId(), rep.getUserId(), rep.getWeight());
                repartition.save();
            }
            reloadData();
            view.close();
        } else
            showErrors(errors);
    }

    // Affiche un message de confirmation à l'utilisateur avant de supprimer  l'operation.
    public void delete(){
        if (askConfirmation("Voulez-vous vraiment supprimer cette opération ? ","Confirmation")){
            operation.delete();
            operation = null;
            view.close();
        }
    }

    public ErrorList validate(String title, String amount, String date, List<Repartition> repartitions) {
        var errors = new ErrorList();

        errors.add(OperationValidator.isValidTitle(title));
        errors.add(OperationValidator.isValidAmount(amount));
        if (!date.isBlank() && !date.isValidDate())
            errors.add("invalid date", Operation.Fields.OperationDate);
        errors.add(OperationValidator.isvalidDate(date.toDate()));
        errors.add(OperationValidator.isValideRepartitions(repartitions));

        return errors;

    }

    public Error validRepartition(List<Repartition> repartitions) {
        return OperationValidator.isValideRepartitions(repartitions);
    }

    public void saveRepAsTemp(List<Repartition> repartitions) {
        if (OperationValidator.isValideRepartitions(repartitions) == Error.NOERROR)
            navigateTo(new AddTemplateController(repartitions, tricount.getId()));
        else
            showError(OperationValidator.isValideRepartitions(repartitions));


    }

    // Gère la mise à jour des données
    public void reloadData(){
        view.reloadData();
    }
}
