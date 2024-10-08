package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.framework.Model;
import tgpr.tricount.model.*;
import tgpr.tricount.view.TestView;
import tgpr.tricount.view.ViewTricountView;

import java.util.ArrayList;
import java.util.List;

public class ViewTricountController extends Controller {
    private final ViewTricountView view;
    private Tricount tricount;
    public ListTricountsController listTricountController ;


    public ViewTricountController(Tricount tricount, ListTricountsController listTricountController) {
        this.tricount = tricount;
        this.listTricountController = listTricountController;
        view = new ViewTricountView(this, tricount);
    }

    @Override
    public Window getView() {
        return view;
    }

    public User getUser() {
        return Security.getLoggedUser();
    }

    public List<Double> getMyExpenses(Tricount tricount) {
        List<Double> result = new ArrayList<>();
        List<Operation> operations = tricount.getOperations();
        double totalExpense = 0, myExpenses = 0,myBalance = 0, weight = 0, myPaid = 0;
        for (Operation operation : operations) {
            if (operation.getTricountId() == tricount.getId()) {
                totalExpense += operation.getAmount();
                if (operation.getInitiator().equals(Security.getLoggedUser()))
                    myPaid += operation.getAmount();
                List<Repartition> repartitions = operation.getRepartitions();
                int userWeight =0;
                for (int i = 0; i<repartitions.size();i++) {
                    weight += repartitions.get(i).getWeight();
                    if (repartitions.get(i).getUser().equals(Security.getLoggedUser()))
                        userWeight = i;
                }
                myExpenses += operation.getAmount()*(repartitions.get(userWeight).getWeight()/weight);
                weight=0;
            }
        }
        result.add(totalExpense);
        result.add(myExpenses);
        result.add(myPaid - myExpenses);
        return result;
    }

    public void openOperation(Operation operation) {
        Controller.navigateTo(new DisplayOperationController(operation));
        refresh(operation);
    }

    public void editTricount() {
        Controller edit = new EditTricountController(tricount, listTricountController);
        navigateTo(edit);
        if(((EditTricountController) edit).isDelete()) {
            view.close();
            return;
        }
        refresh(tricount);
    }

    public void newExpense() {
        Controller.navigateTo(new EditOperationController(tricount));
        refresh(tricount);
    }

    public void balance() {
        Controller.navigateTo(new ViewBalanceController(tricount));
    }

    // refresh la vue
    public void refresh(Model model){
        model.reload();
        view.close();
        navigateTo(new ViewTricountController(tricount, listTricountController));
    }
}
