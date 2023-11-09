package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.tricount.model.*;
import tgpr.tricount.view.ViewBalanceView;

import java.util.List;

public class ViewBalanceController extends Controller {
    private final ViewBalanceView view;
    private final Tricount tricount;

    public ViewBalanceController(Tricount tricount) {
        this.tricount = tricount;
        view = new ViewBalanceView(this, tricount);
    }

    @Override
    public Window getView() {
        return view;
    }

    public List<User> getUsers() {
        return tricount.getParticipants();
    }

    public Double getBalance(Tricount tricount, User user) {
        List<Operation> operations = tricount.getOperations();

        double userExpenses = 0, weight = 0, userPaid = 0;
        for (Operation operation : operations) {
            if (operation.getInitiator().equals(user))
                userPaid += operation.getAmount();
            List<Repartition> repartitions = operation.getRepartitions();
            int userWeight = 0;
            for (int i = 0; i < repartitions.size(); i++) {
                weight += repartitions.get(i).getWeight();
                if (repartitions.get(i).getUser().equals(user))
                    userWeight = i;
            }
            userExpenses += operation.getAmount()*(repartitions.get(userWeight).getWeight()/weight);
            weight = 0;
        }
        return userPaid - userExpenses;
    }
}
