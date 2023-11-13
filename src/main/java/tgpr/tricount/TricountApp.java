package tgpr.tricount;

import tgpr.framework.Controller;
import tgpr.framework.Model;


import tgpr.tricount.controller.*;

import tgpr.tricount.model.Tricount;


import tgpr.tricount.model.Operation;
import tgpr.tricount.model.Security;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.model.User;


public class TricountApp {
    public final static String DATABASE_SCRIPT_FILE = "/database/tgpr-2324-a04.sql";

    public static void main(String[] args) {
        if (!Model.checkDb(DATABASE_SCRIPT_FILE))
            Controller.abort("Database is not available!");

        else {


            Tricount tricount = Tricount.getByKey(4);
            User Boris =  User.getByFullName("Boris");
            Security.login(Boris);
            Controller.navigateTo(new EditOperationController(tricount, null));

            //Controller.navigateTo(new EditTricountController(Tricount.getByKey(1)));


            //Controller.navigateTo(new DisplayOperationController(Operation.getByKey(5)));
            //Controller.navigateTo(new AddTricountController());
           // Controller.navigateTo(new ViewTricountController(tricount));

        }
    }
}
