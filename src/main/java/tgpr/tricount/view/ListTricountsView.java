package tgpr.tricount.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.gui2.menu.Menu;
import com.googlecode.lanterna.gui2.menu.MenuBar;
import com.googlecode.lanterna.gui2.menu.MenuItem;
import com.googlecode.lanterna.input.KeyStroke;
import tgpr.framework.Paginator;
import tgpr.tricount.controller.ListTricountsController;
import tgpr.tricount.controller.ViewTricountController;
import tgpr.tricount.model.Security;
import tgpr.tricount.model.Tricount;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class ListTricountsView extends BasicWindow {
    private final ListTricountsController controller;
    private List<Tricount> listTricounts = new ArrayList<>();
    private final Menu menuFile = new Menu("File");
    private Panel tricountContainer;
    private Paginator paginator;
    private TextBox textBoxFiltre = new TextBox();

    public ListTricountsView (ListTricountsController controller, List<Tricount> listTricounts) {
        this.listTricounts = listTricounts;
        this.controller = controller;

        setTitle(getTitleWithUser());
        setHints(List.of(Hint.EXPANDED));

        Panel mainPanel = new Panel().setLayoutManager(new LinearLayout(Direction.VERTICAL));
        setComponent(mainPanel);
        createMenu().addTo(mainPanel);

        Panel filtre = new Panel().setLayoutManager(new GridLayout(2));
        Label labelFiltre = new Label("Filtrer : ");
        filtre.addComponent(labelFiltre);
        filtre.addComponent(textBoxFiltre);
        textBoxFiltre.takeFocus().setTextChangeListener((txt, byUser) -> this.controller.reloadData(txt));                       //ajout listener
        mainPanel.addComponent(filtre);


        GridLayout gridTricount = new GridLayout(3);
        this.tricountContainer = new Panel(gridTricount).setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Fill));
        this.paginator = new Paginator(this, 12,this::pageChanged);
        loadTricountContainer(0, listTricounts);
        mainPanel.addComponent(this.tricountContainer);


        Panel bottom = new Panel().setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        bottom.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Fill));
        Button newButton = new Button("Create a new Tricount", () -> controller.addTricount()).addTo(bottom); //ajouter action sur le button

        bottom.addComponent(this.paginator.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.End)));
        mainPanel.addComponent(bottom);


    }

    /*private void reloadData(String txt) {
        this.listTricounts=new ArrayList<>();
        System.out.println("1 tes ici");
        for (Tricount tricount : this.controller.getSearch(txt)){
            listTricounts.add(tricount);
            System.out.println("test2");
        }
        loadTricountContainer(0);
    }*/


    private Border createCell (int i){
        return new Panel().addComponent(new Label(String.valueOf(i))).withBorder(Borders.singleLine());
    }
    private String getTitleWithUser() {
        return "Tricount (" + Security.getLoggedUser() + " - " + (Security.isAdmin() ? "Admin" : "Member") + ")";
    }
    private Border cardTricount(Tricount tricount) {
        GridLayout.Alignment horizontalAlignment = GridLayout.Alignment.FILL;
        GridLayout.Alignment verticalAlignment = GridLayout.Alignment.FILL;
        Panel panel = new Panel().setLayoutData((GridLayout.createLayoutData(horizontalAlignment, verticalAlignment, true, true)));
        panel.addComponent(new Label(tricount.getTitle()).center().setForegroundColor(TextColor.ANSI.BLUE));
        panel.addComponent(new Label((tricount.getDescription() != null) ? tricount.getDescription() : "pas de description").center().setForegroundColor(TextColor.ANSI.BLACK_BRIGHT).addStyle(SGR.ITALIC));
        panel.addComponent(new Label(tricount.getCreator().getFullName()).center());
        String numberParticipants = String.valueOf(tricount.getParticipants().size()-1);
        panel.addComponent(new Label("with " + ((!numberParticipants.equals("0")) ? numberParticipants : "no") + " friends").center());
        panel.addComponent(new Button("Open", () -> {controller.openTricount(tricount);}).center());

        return panel.withBorder(Borders.singleLine());
    }
    private MenuBar createMenu() {
        MenuBar menuBar = new MenuBar();
        menuBar.add(menuFile);
        addShortcut(menuFile, KeyStroke.fromString("<A-f>"));
        MenuItem menuProfile = new MenuItem("Profile", controller::profile);
        menuFile.add(menuProfile);
        MenuItem menuLogout = new MenuItem("Logout", controller::logout);
        menuFile.add(menuLogout);
        MenuItem menuExit = new MenuItem("Exit", controller::exit);
        menuFile.add(menuExit);
        return menuBar;
    }
    private void pageChanged(int page){
        loadTricountContainer(page*12, this.listTricounts);
    }
    public void loadTricountContainer(int startId, List<Tricount> listTricountsDisp){
        this.listTricounts = listTricountsDisp;
        this.paginator.setCount(this.listTricounts.size());
        textBoxFiltre.takeFocus();
        this.tricountContainer.removeAllComponents();
        for (int i = startId; i < Math.min(startId + 12, listTricountsDisp.size()); i++) {
            this.tricountContainer.addComponent(cardTricount(listTricountsDisp[i]));
        }
    }
}