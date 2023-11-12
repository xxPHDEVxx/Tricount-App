package tgpr.tricount.view;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
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


    public ListTricountsView (ListTricountsController controller, Tricount tricount) {
        //super("Tricount");
        this.tricount = tricount;
        setTitle(getTitleWithUser());
        setHints(List.of(Hint.CENTERED));

        this.controller = controller;

        Panel root = new Panel().setLayoutManager(
                new GridLayout(3).setHorizontalSpacing(0).setVerticalSpacing(0));
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
        setComponent(root.withBorder(Borders.singleLine()));
    }
    private Border createCell (int i){
        return new Panel().addComponent(new Label(String.valueOf(i))).withBorder(Borders.singleLine());
    }
    private String getTitleWithUser() {
        return "Tricount (" + Security.getLoggedUser() + " - " + (Security.isAdmin() ? "Admin" : "Member") + ")";
    }
}