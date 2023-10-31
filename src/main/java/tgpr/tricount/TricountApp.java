package tgpr.tricount;

import tgpr.framework.Controller;
import tgpr.framework.Model;
import tgpr.tricount.controller.AddTemplateController;
import tgpr.tricount.controller.TestController;
import tgpr.tricount.model.Repartition;

import java.util.ArrayList;
import java.util.List;

public class TricountApp {
    public final static String DATABASE_SCRIPT_FILE = "/database/tgpr-2324-a04.sql";

    public static void main(String[] args) {
        if (!Model.checkDb(DATABASE_SCRIPT_FILE))
            Controller.abort("Database is not available!");

        else {
            List<Repartition> repartitions = new ArrayList<>();
            repartitions.add(new  Repartition(2, 1, 1));
            repartitions.add(new Repartition(2, 2, 1));
            Controller.navigateTo(new AddTemplateController(repartitions, 4 ));
        }
    }
}
