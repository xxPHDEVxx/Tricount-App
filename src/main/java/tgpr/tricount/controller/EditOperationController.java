package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.tricount.model.Operation;
import tgpr.tricount.view.EditOperationView;

import java.awt.*;

public class EditOperationController extends Controller {
    private final EditOperationView view;
    private Operation operation;
    private final boolean isNew;
    public EditOperationController(){
        this(null);
    }
    public EditOperationController(Operation operation){
        this.operation = operation;
        isNew = operation == null;
        view = new EditOperationView(this, operation);
    }
    @Override
    public Window getView(){
        return view;
    }
    public Operation getOperation(){
        return operation;
    }

}
