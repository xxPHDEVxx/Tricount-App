package tgpr.tricount;

import tgpr.framework.Controller;
import tgpr.framework.Model;

import tgpr.tricount.controller.DisplayOperationController;
import tgpr.tricount.controller.EditOperationController;
import tgpr.tricount.controller.LoginController;
import tgpr.tricount.controller.ViewTricountController;
import tgpr.tricount.model.Operation;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.model.Security;
import tgpr.tricount.model.User;


public class TricountApp {
    public final static String DATABASE_SCRIPT_FILE = "/database/tgpr-2324-a04.sql";

    public static void main(String[] args) {
        if (!Model.checkDb(DATABASE_SCRIPT_FILE))
            Controller.abort("Database is not available!");

        else {

            Tricount tricount = Tricount.getByKey(4);
            User Xavier = User.getByFullName("Xavier");

            Model.seedData(TricountApp.DATABASE_SCRIPT_FILE);// reset la database (pour les tests)
            Controller.navigateTo(new LoginController());
        }
    }
}