package tgpr.tricount.view;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.gui2.menu.Menu;
import com.googlecode.lanterna.gui2.menu.MenuBar;
import com.googlecode.lanterna.gui2.menu.MenuItem;
import com.googlecode.lanterna.input.KeyStroke;
import tgpr.tricount.controller.ListTricountsController;
import tgpr.tricount.controller.ViewTricountController;
import tgpr.tricount.model.Security;
import tgpr.tricount.model.Tricount;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class ListTricountsView extends BasicWindow {
    private final ListTricountsController controller;
    private final Tricount tricount;
    private final Menu menuFile = new Menu("File");

    public ListTricountsView (ListTricountsController controller, Tricount tricount) {
        //super("Tricount");
        this.tricount = tricount;
        this.controller = controller;

        setTitle(getTitleWithUser());
        setHints(List.of(Hint.CENTERED));

        Panel mainPanel = new Panel().setLayoutManager(new LinearLayout(Direction.VERTICAL));
        setComponent(mainPanel);
        createMenu().addTo(mainPanel);

        Panel filtre = new Panel().setLayoutManager(new GridLayout(2));
        filtre.addTo(mainPanel);
        Label labelFiltre = new Label("Filtrer : ").addTo(filtre);
        TextBox textBoxFiltre = new TextBox().addTo(filtre);




        /*Panel menuPanel = new Panel().setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        setComponent(menuPanel);
        createMenu().addTo(menuPanel);*/

        /*Panel root = new Panel().setLayoutManager(new GridLayout(3)
                .setHorizontalSpacing(0).setVerticalSpacing(0));
        createMenu().addTo(root);
        createCell(1).addTo(root);
        createCell(2).addTo(root);
        createCell(3).addTo(root);
        createCell(4).addTo(root);
        createCell(5).addTo(root);
        createCell(6).addTo(root);
        createCell(7).addTo(root);
        createCell(8).addTo(root);
        createCell(9).addTo(root);
        createCell(10).addTo(root);
        setComponent(root.withBorder(Borders.singleLine()));*/
    }
    private Border createCell (int i){
        return new Panel().addComponent(new Label(String.valueOf(i))).withBorder(Borders.singleLine());
    }
    private String getTitleWithUser() {
        return "Tricount (" + Security.getLoggedUser() + " - " + (Security.isAdmin() ? "Admin" : "Member") + ")";
    }
    private Panel createBody() {
        Panel panel = new Panel().setLayoutManager(new LinearLayout(Direction.VERTICAL));
        return panel;
    }
    private MenuBar createMenu() {
        MenuBar menuBar = new MenuBar();
        menuBar.add(menuFile);
        addShortcut(menuFile, KeyStroke.fromString("<A-f>"));
        //MenuItem menuLogout = new MenuItem("Logout", controller::logout);
        //menuFile.add(menuLogout);
        MenuItem menuExit = new MenuItem("Exit", controller::exit);
        menuFile.add(menuExit);
        return menuBar;
    }
}