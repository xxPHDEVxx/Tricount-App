package tgpr.tricount.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.tricount.controller.ViewTricountController;
import tgpr.tricount.model.Tricount;

import java.util.List;

public class ViewTricountView extends DialogWindow {
    private final ViewTricountController controller;
    public ViewTricountView(ViewTricountController controller) {
        super("View Tricount Detail");
        setHints(List.of(Hint.CENTERED));

        this.controller = controller;
        Tricount tricount = controller.getTricount();

        Panel description = new Panel();
        description.setLayoutManager(new GridLayout(2).setTopMarginSize(1));

        new Label("Title:").addTo(description);
        new Label(tricount.getTitle()).addTo(description);
        new Label("Description:").addTo(description);
        new Label(tricount.getDescription()).addTo(description);

    }
}
