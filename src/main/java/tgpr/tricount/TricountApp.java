package tgpr.tricount;

import tgpr.framework.Controller;
import tgpr.framework.Model;

import tgpr.tricount.controller.EditTricountController;
import tgpr.tricount.controller.TestController;
import tgpr.tricount.model.Tricount;


import tgpr.tricount.controller.DisplayOperationController;
import tgpr.tricount.model.Operation;


public class TricountApp {
    public final static String DATABASE_SCRIPT_FILE = "/database/tgpr-2324-a04.sql";

    public static void main(String[] args) {
        if (!Model.checkDb(DATABASE_SCRIPT_FILE))
            Controller.abort("Database is not available!");

        else {

            Controller.navigateTo(new EditTricountController(Tricount.getByKey(1)));

           /* Tricount tricount = Tricount.getByKey(4);
            User Xavier =  User.getByFullName("Xavier");
            Security.login(Xavier);
            Controller.navigateTo(new EditOperationController(tricount, null));*/

            //Controller.navigateTo(new DisplayOperationController(Operation.getByKey(5)));

        }
    }



}