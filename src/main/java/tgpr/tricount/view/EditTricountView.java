package tgpr.tricount.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.framework.Error;
import tgpr.framework.ErrorList;
import tgpr.framework.Margin;
import tgpr.framework.Tools;
import tgpr.tricount.controller.EditTricountController;
import tgpr.tricount.model.Operation;
import tgpr.tricount.model.Subscription;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.model.User;

import java.util.ArrayList;
import java.util.List;

public class EditTricountView extends DialogWindow {
    private final EditTricountController controller;

    private Tricount tricount;

    private User user;
    private TextBox txtTitle;
    private TextBox txtDescription;
    private final Label errTitle = new Label("");
    private final Label errDescription = new Label("");

    private List<User> grpSubscribers;
    private ActionListBox lstSubscriber = new ActionListBox();
    private ComboBox<String> cbAddParticipant = new ComboBox<>();
    private List<User> lstDepense = new ArrayList<>() ;

    private List<User> lstNvParticipants = new ArrayList<>();



    public EditTricountView(EditTricountController controller, Tricount tricount)  {
        super("Edit tricount");
        this.controller = controller;
        this.tricount = tricount;
        this.grpSubscribers = tricount.getParticipants();

        //liste des utilisateurs qui sont impliqué au tricount
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
        txtDescription = new TextBox().sizeTo(25 ,9).addTo(panel)
                .setTextChangeListener((txt, byUser) -> validate());
        ;
        panel.addEmpty();
        errDescription.addTo(panel).setForegroundColor(TextColor.ANSI.RED);
        new Label("Subscribers:").addTo(panel);
        comboBoxSub().addTo(panel);


        return panel;
    }

    private Panel listAddParticipants() {
        var panel = Panel.horizontalPanel().center();
        var users = User.getAll();
        cbAddParticipant.addItem("-- Select a User -- ");
        for (User user : users)
            if (!grpSubscribers.contains(user)) {
                cbAddParticipant.addItem(user.getFullName());
                }
            cbAddParticipant.addTo(panel);

        var btnAdd = new Button("Add", this::addParticipant);
        btnAdd.addTo(panel);
        return panel;
    }
    private Panel createButtonsPanel() {
        var panel = Panel.horizontalPanel().center();

        new Button("Delete", this::delete).addTo(panel);
        new Button("Save", this::add).addTo(panel);
        new Button("Templates").addTo(panel);
        new Button("Cancel", this::close).addTo(panel);


        return panel;
    }

    private Panel comboBoxSub() {
        var panel = new Panel();
        grpSubscribers = tricount.getParticipants();
        for (var parti : grpSubscribers) {
            lstSubscriber.addItem(parti.getFullName() + star(parti)

                    , () -> deleteLstParticipant(parti));
        }

        lstSubscriber.addTo(panel);
        return panel;
    }

    private void deleteLstParticipant(User parti) {
        var errors = new ErrorList();
        if (lstDepense.contains(parti) || parti.getId() == tricount.getCreatorId()) {
            errors.add(new Error("You may not remove this participant because \nhe is the creator or he is implied in one or\nmore expenses"));
        } else {
            Subscription delSub = Subscription.getByKey(tricount.getId(), parti.getId());
            delSub.delete();
            //supprime de l'actionList
            lstSubscriber.removeItem(lstSubscriber.getSelectedIndex());

        }

        if (!errors.isEmpty()) {
            controller.showErrors(errors);
        }



    }
    private void refresh() {
        if (tricount != null) {
            txtTitle.setText(tricount.getTitle());
            txtDescription.setText(Tools.ifNull(tricount.getDescription(), ""));

        }

    }


    private void delete() {
        controller.delete();
    }

    private void add() {
        controller.save(
                txtTitle.getText(),
                txtDescription.getText(),
                lstNvParticipants);


    }

    private void addParticipant() {
        User selected = user.getByFullName(cbAddParticipant.getSelectedItem());
        lstNvParticipants.add(selected);
        lstSubscriber.addItem(selected.getFullName()

                , () -> deleteLstParticipant(selected));
        cbAddParticipant.removeItem(cbAddParticipant.getSelectedItem());
        // remettre le premier item
        cbAddParticipant.setSelectedIndex(0);

    }

    private String star(User user) {
        return lstDepense.contains(user) || tricount.getCreatorId() == user.getId()? "(*)" : "";



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
