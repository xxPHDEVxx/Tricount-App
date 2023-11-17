package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.framework.Params;
import tgpr.framework.Tools;
import tgpr.tricount.model.Operation;
import tgpr.tricount.model.Security;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.model.User;
import tgpr.tricount.view.EditOperationView;
import tgpr.tricount.view.ListTricountsView;
import tgpr.tricount.view.ViewTricountView;
import java.util.stream.Collectors;



import java.util.ArrayList;
import java.util.List;

import static tgpr.framework.Model.queryList;

public class ListTricountsController extends Controller {
    private final ListTricountsView view;
    private List<Tricount> listTricounts = new ArrayList<>();
    private List<Tricount> listTricountsDisplayed = new ArrayList<>();



    public ListTricountsController() {
        this.listTricountsDisplayed = fetchTricounts();
        view = new ListTricountsView(this, listTricountsDisplayed);
    }
    public List<Tricount> fetchTricounts(){
        listTricounts=Tricount.getAll();
        return listTricounts;
    }

    public void logout() {
        Security.logout();
        navigateTo(new LoginController());
    }

    public void profile() {
        navigateTo(new ProfileController());
    }

    public void exit() {
        System.exit(0);
    }
    public void openTricount(Tricount tricount) {
        navigateTo(new ViewTricountController(tricount, this));
    }
    public void addTricount() {
        navigateTo(new AddTricountController(this));
    }
    @Override
    public Window getView() {
        return view;
    }
    /*public List<Tricout> getSearch(String txt) {
        Tricount.getByTitleAndUser("Van", User.getByFullName("Xavier"));
        return queryList(Tricount.class, "select * from users join tricounts on users.id=tricounts.id where title like ':%txt%' ",
                new Params("txt", txt));
    }*/
    public List<Tricount> getSearch(String txt){
        List<Tricount> triedLst = listTricounts.stream()
                .filter(tricount ->
                        tricount.getTitle().contains(txt)
                        || Tools.ifNull(tricount.getDescription(), "").contains(txt)
                        || tricount.getCreator().getFullName().contains(txt)
                        ).collect(Collectors.toList());
        return triedLst;
    }
    public void reloadData(String txt) {
        this.listTricountsDisplayed=new ArrayList<>();
        for (Tricount tricount : getSearch(txt)){
            listTricountsDisplayed.add(tricount);
        }
        view.loadTricountContainer(0, listTricountsDisplayed);
    }

}

    //