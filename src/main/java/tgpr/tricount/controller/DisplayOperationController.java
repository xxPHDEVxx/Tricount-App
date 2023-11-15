package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.tricount.model.Operation;
import tgpr.tricount.model.Repartition;
import tgpr.tricount.view.DisplayOperationView;

import java.util.List;

public class DisplayOperationController extends Controller {
    private final DisplayOperationView view;
    private final Operation operation;

    public DisplayOperationController(Operation operation) {
        this.operation = operation;
        view = new DisplayOperationView(this, operation);

    }

    @Override
    public Window getView() {
        return view;
    }
    public Operation getOperation() {
        return operation;
    }
    public List<Repartition> getRepartitions(){
        return operation.getRepartitions();
    }

    // affiche la fenêtre d'édition de l'opération.
    public Operation update(){
        var controller = new EditOperationController(operation.getTricount(), operation);
        navigateTo(controller);
        view.refresh();
        return controller.getOperation();
    }
}
