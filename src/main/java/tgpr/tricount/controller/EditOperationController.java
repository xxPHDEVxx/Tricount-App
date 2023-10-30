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
    public EditOperationController(Tricount tricount){
        this(tricount, null);
    }

    public EditOperationController(Tricount tricount, Operation operation){
        this.tricount = tricount;
        this.operation = operation;
        isNew = operation == null;
        view = new EditOperationView(this, tricount, operation);
    }




    @Override
    public Window getView(){
        return view;
    }
    public Operation getOperation(){
        return operation;
    }
    public Tricount getTricount() {return tricount; }


    public void save(String title, String amount, String date, String user) {
        var errors = validate(title, amount, date);
        if(errors.isEmpty()) {
            LocalDateTime createdAt = LocalDateTime.now();
            operation = new Operation(title, tricount.getId(), Double.parseDouble(amount), date.toDate(), User.getByFullName(user).getId(), createdAt);
            operation.save();


            view.close();
        }else
            showErrors(errors);

    }
    public void saveRepartition(List<Repartition> repartitions){
        var error = validRepartition(repartitions);
        if (error == null) {
            for (var rep : repartitions) {
                Repartition repartition = new Repartition(7, rep.getUserId(), rep.getWeight());
                repartition.save();
            }
            view.close();
        }
        else
            showError(error);

    }
    public ErrorList validate(String title, String amount, String date){
        var errors = new ErrorList();

            errors.add(OperationValidator.isValidTitle(title));
            errors.add(OperationValidator.isValidAmount(amount));
            if(!date.isBlank() && !date.isValidDate())
                errors.add("invalid date", Operation.Fields.OperationDate);
            errors.add(OperationValidator.isvalidDate(date.toDate()));

        return errors;

    }
    public Error validRepartition(List<Repartition> repartitions){
        return OperationValidator.isValideRepartitions(repartitions);
    }
}
