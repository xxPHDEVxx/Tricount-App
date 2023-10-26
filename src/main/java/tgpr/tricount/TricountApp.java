package tgpr.tricount;

import tgpr.framework.Controller;
import tgpr.framework.Model;
import tgpr.tricount.controller.AddTricountController;
import tgpr.tricount.controller.TestController;
import tgpr.tricount.model.Security;
import tgpr.tricount.model.User;

public class TricountApp {
    public final static String DATABASE_SCRIPT_FILE = "/database/tgpr-2324-a04.sql";

    public static void main(String[] args) {
        if (!Model.checkDb(DATABASE_SCRIPT_FILE))
            Controller.abort("Database is not available!");
        else {
            Security.login(User.getByKey(3));
            Controller.navigateTo(new AddTricountController());
        }
    }
}
