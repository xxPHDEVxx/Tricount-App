package tgpr.tricount.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.input.KeyStroke;
import tgpr.framework.Margin;
import tgpr.framework.Tools;
import tgpr.tricount.controller.EditTricountController;
import tgpr.tricount.model.Operation;
import tgpr.tricount.model.Subscription;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class EditTricountView extends DialogWindow {
    private final EditTricountController controller;

    private Tricount tricount;

    private User user;
    private TextBox txtTitle;
    private TextBox txtDescription;
    private final Label errTitle = new Label("");
    private final Label errDescription = new Label("");

    private List<User> grpSubscribers;
    private ActionListBox lst = new ActionListBox();
    private ComboBox<String> cb2 = new ComboBox<>();
    private List<User> lstDepense = new ArrayList<>() ;



    public EditTricountView(EditTricountController controller, Tricount tricount)  {
        super("Edit tricount");
        this.controller = controller;
        this.tricount = tricount;

        for (Operation op: tricount.getOperations()
             ) {
            User us = User.getByKey(op.getInitiatorId());
            if (!this.lstDepense.contains(us)) {
                this.lstDepense.add(us);
            }
        }

        setHints(List.of(Hint.CENTERED, Hint.FIXED_SIZE));
        setCloseWindowWithEscape(true);
        setFixedSize(new TerminalSize(60, 20));



        Panel root = new Panel();
        setComponent(root);
        createFieldsGrid().addTo(root);
        listAddParticipants().addTo(root);
        root.addEmpty();
        createButtonsPanel().addTo(root);


        refresh();
    }

    private Panel createFieldsGrid() {
        var panel = Panel.gridPanel(2, Margin.of(1));

        new Label("Title:").addTo(panel);
        txtTitle = new TextBox().sizeTo(19).addTo(panel)
                .setTextChangeListener((txt, byUser) -> validate());
        ;
        panel.addEmpty();
        errTitle.addTo(panel)
                .setForegroundColor(TextColor.ANSI.RED);

        new Label("Description:").addTo(panel);
        txtDescription = new TextBox().sizeTo(30 ,9).addTo(panel)
                .setTextChangeListener((txt, byUser) -> validate());
        ;
        panel.addEmpty();
        errDescription.addTo(panel).setForegroundColor(TextColor.ANSI.RED);
        new Label("Subscribers:").addTo(panel);

        grpSubscribers = tricount.getParticipants();
        for (var parti : grpSubscribers) {
            lst.addItem(parti.getFullName() + star(parti)

                    , () -> doSomethingWithUser(parti));

        }

        lst.addTo(panel);
        return panel;
    }

    private Panel listAddParticipants() {
        var panel = Panel.horizontalPanel().center();
        var users = User.getAll();
        cb2.addItem("-- Select a User -- ");
        for (User user : users)
            if (!grpSubscribers.contains(user)) {
                cb2.addItem(user.getFullName());
                }
            cb2.addTo(panel);

        var btnAdd = new Button("Add", this::addParticipant);
        btnAdd.addTo(panel);
        return panel;
    }
    private Panel createButtonsPanel() {
        var panel = Panel.horizontalPanel().center();

        new Button("Delete").addTo(panel);
        new Button("Save", this::add).addTo(panel);
        new Button("Templates").addTo(panel);
        new Button("Cancel", this::close).addTo(panel);


        return panel;
    }

    private void doSomethingWithUser(User parti) {
        System.out.println(parti.getFullName());
    }
    private void refresh() {
        if (tricount != null) {
            txtTitle.setText(tricount.getTitle());
            txtDescription.setText(Tools.ifNull(tricount.getDescription(), ""));
        }

    }

    private void add() {
        controller.save(
                txtTitle.getText(),
                txtDescription.getText());

    }

    private void addParticipant() {
        User selected = user.getByFullName( cb2.getSelectedItem());
        System.out.println(selected.getId());
    }

    private String star(User user) {
        return lstDepense.contains(user) || grpSubscribers.contains(user)? "(*)" : "";



    }


    private void validate() {
        var errors = controller.validate(
                txtTitle.getText(),
                txtDescription.getText()

        );

        errTitle.setText(errors.getFirstErrorMessage(Tricount.Fields.Title));
        errDescription.setText(errors.getFirstErrorMessage(Tricount.Fields.Description));

    }
}
