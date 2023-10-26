package tgpr.tricount.view;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.tricount.controller.EditTricountController;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.model.User;

public class EditTricountView extends DialogWindow {
    private final EditTricountController controller;

    private User user;
    private Tricount tricount;
    private TextBox txtTitle;
    private TextBox txtDescription;
    private final Label errTitle = new Label("");
    private final Label errDescription = new Label("");

    private ComboBox<String> grpSubscribers;
    private Button btnAddUpdate;


    public EditTricountView(EditTricountController controller, Tricount tricount)  {
        super("Edit tricount");
        this.controller = controller;
        this.tricount = tricount;
    }

    private void refresh() {

    }

    private void add() {

    }

}
