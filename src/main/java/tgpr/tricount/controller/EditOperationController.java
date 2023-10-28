package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.CheckBoxList;
import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.framework.Error;
import tgpr.framework.ErrorList;
import tgpr.tricount.model.Operation;
import tgpr.tricount.model.OperationValidator;
import tgpr.tricount.model.Security;
import tgpr.tricount.model.Tricount;
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


    public void save(String text, String text1, String text2, String text3) {


    }
    public ErrorList validate(String title, String amount, String date, List<String> participant){
        var errors = new ErrorList();

            errors.add(OperationValidator.isValidTitle(title));
            errors.add(OperationValidator.isValidAmount(amount));
            if(!date.isBlank() && !date.isValidDate())
                errors.add("invalid date", Operation.Fields.OperationDate);
            errors.add(OperationValidator.isvalidDate(date.toDate()));
            errors.add(OperationValidator.isValideCklParticipantBalance(participant));
        return errors;

    }
}
