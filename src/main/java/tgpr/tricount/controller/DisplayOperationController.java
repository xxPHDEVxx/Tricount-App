package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.framework.Model;
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

    // Affiche la fenêtre d'édition de l'opération et check si une supression a été faite.
    public void update(){
        Controller edit = new EditOperationController(operation.getTricount(), operation);
        navigateTo(edit);
        if (((EditOperationController) edit).isDelete()) {
            view.close();
            return;
        }
        refresh(operation);
    }

    // Raffraichis la vue
    public void refresh(Model model){
        model.reload();
        view.close();
        navigateTo(new DisplayOperationController(operation));
    }
}
