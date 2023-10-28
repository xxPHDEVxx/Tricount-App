package tgpr.tricount.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.input.KeyStroke;
import tgpr.framework.Margin;
import tgpr.tricount.controller.EditOperationController;
import tgpr.tricount.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class EditOperationView extends DialogWindow {
    private final EditOperationController controller;
    private final Operation operation;
    private final Tricount tricount;
    private TextBox txtTitle;
    private TextBox txtAmount;
    private TextBox txtDate;
    private ComboBox<String> cboUsers;
    private ComboBox<String> cboTemplates;
    private final CheckBoxList<String> cklParticipantBalance = new CheckBoxList<>();
    private final Label errTitle = new Label("");
    private final Label errAmount = new Label("");
    private final Label errDate = new Label("");
    private final Label errUsers = new Label("");
    private final Label errTemplates = new Label("");
    private final Label errParticipantBalance = new Label("");
    private Button btnAddUpdate;

    public EditOperationView(EditOperationController controller,Tricount tricount, Operation operation) {
        super((operation == null ? "Add " : "Update ") + "Operation");
        this.operation = operation;
        this.controller = controller;
        this.tricount = tricount;

        setHints(List.of(Hint.CENTERED, Hint.FIXED_SIZE));
        setCloseWindowWithEscape(true);
        setFixedSize(new TerminalSize(67, 19));

        Panel root = Panel.verticalPanel();
        setComponent(root);

        createFieldsGrid().addTo(root);
        createButtonsPanel().addTo(root);

        refresh();

        txtTitle.takeFocus();

    }

    private Panel createFieldsGrid() {
        var panel = Panel.gridPanel(2, Margin.of(1));

        new Label("Title:").addTo(panel);
        txtTitle = new TextBox().sizeTo(33).addTo(panel)
                .setValidationPattern(Pattern.compile("[a-z][a-z\\d]{0,7}"))
                .setTextChangeListener((txt, byUser) -> validate())
                .setReadOnly(operation != null);
        panel.addEmpty();
        errTitle.addTo(panel)
                .setForegroundColor(TextColor.ANSI.RED);
        new Label(("Amount:")).addTo(panel);
        txtAmount = new TextBox().sizeTo(10).addTo(panel)
                .setValidationPattern(Pattern.compile("^[1-9]\\d*(\\.\\d+)?$")) //<-à remplacer par une expression régulière pour les nombres décimaux car celle-ci empêche aussi le'.' et le'-'
                .setTextChangeListener((txt, byUser) -> validate());
        panel.addEmpty();
        errAmount.addTo(panel).setForegroundColor(TextColor.ANSI.RED);

        new Label("Date:").addTo(panel);
        txtDate = new TextBox().sizeTo(11).addTo(panel)
                .setValidationPattern(Pattern.compile("[/\\d]{0,10}"))
                .setTextChangeListener((txt, byUser) -> validate());
        panel.addEmpty();
        errDate.addTo(panel).setForegroundColor(TextColor.ANSI.RED);

        new Label("Paid by:").addTo(panel);
        List<String> participants = participantsName();
        cboUsers = new ComboBox<>(participants).addTo(panel).sizeTo(11);
        assert operation != null;
        cboUsers.setSelectedItem(Security.getLoggedUser().getFullName());
        cboUsers.addListener((selectedIndex, previousSelection, changedByUserInteraction) -> validate());
        panel.addEmpty();
        errUsers.addTo(panel).setForegroundColor(TextColor.ANSI.RED);

        new Label("Use a repartition \ntemplate (optional):").addTo(panel);
        List<String> templatesTitle = templatesTitle();
        cboTemplates = new ComboBox<>(templatesTitle).addTo(panel).sizeTo(35);
        cboTemplates.setSelectedItem(Template.DUMMY.getTitle());
        cboUsers.addListener((selectedIndex, previousSelection, changedByUserInteraction) -> validate());
        panel.addEmpty();
        panel.addEmpty();

        new Label("For whom: \n(weight: ←/→ or -/+)").addTo(panel);
        for (String s : participants){
            cklParticipantBalance.addItem(s);
            cklParticipantBalance.setChecked(s, true);
        }
        cklParticipantBalance.addListener((idx, isChecked) -> {
        }).addTo(panel);
        panel.addEmpty();
        errParticipantBalance.addTo(panel).setForegroundColor(TextColor.ANSI.RED);

        return panel;

    }

    private void reloadData() {
    }

    private Panel createButtonsPanel() {
        var panel = Panel.horizontalPanel().center();

        btnAddUpdate = new Button(operation == null ? "Add" : "Update", this::add).addTo(panel).setEnabled(false);
        new Button("Cancel", this::close).addTo(panel);

        addShortcut(btnAddUpdate, KeyStroke.fromString(operation == null ? "<A-a>" : "<A-u>"));
        return panel;
    }

    private void refresh() {
        if (operation != null) {
            txtTitle.setText(operation.getTitle());
            txtAmount.setText(((String.valueOf(operation.getAmount()))));
            for (String s : templatesTitle()){
                cboTemplates.addItem(s);
            }
            for (String userName : participantsName()) {
                cboUsers.addItem(userName);

            }
        }
    }

    private void add() {
        controller.save(
                txtTitle.getText(),
                txtAmount.getText(),
                txtDate.getText(),
                cboTemplates.getText()
        );
    }/*txtDate.getText());*/

    private void validate() {
        var errors = controller.validate(
                txtTitle.getText(),
                txtAmount.getText(),
                txtDate.getText(),
                cklParticipantBalance.getCheckedItems());

        errTitle.setText(errors.getFirstErrorMessage(Operation.Fields.Title));
        errAmount.setText(errors.getFirstErrorMessage(Operation.Fields.Amount));
        errDate.setText(errors.getFirstErrorMessage(Operation.Fields.OperationDate));
        errParticipantBalance.setText(errors.getFirstErrorMessage(Operation.Fields.Repartition));

    }

    public List<String> participantsName() {
        List<String> ls = new ArrayList<>();

        for (User user :
                tricount.getParticipants()) {
            ls.add(user.getFullName());
        }
        return ls;
    }

    public List<String> templatesTitle() {
        List<String> ls = new ArrayList<>();
        ls.add(Template.DUMMY.getTitle());

        for (Template template :
                tricount.getTemplates()) {
            ls.add(template.getTitle());
        }
        return ls;
    }



}



