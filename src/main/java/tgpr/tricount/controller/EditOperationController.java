package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.tricount.model.Operation;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.view.EditOperationView;

public class EditOperationController extends Controller {
    private final EditOperationView view;
    private Operation operation;
    private Tricount tricount;
    private final boolean isNew;

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
}
