package tgpr.tricount.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import tgpr.framework.Margin;
import tgpr.framework.Model;
import tgpr.tricount.controller.EditOperationController;
import tgpr.tricount.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static tgpr.tricount.model.DateUtils.localDateToString;

public class EditOperationView extends DialogWindow {
    private final EditOperationController controller;
    private Operation operation;
    private Tricount tricount;
    private TextBox txtTitle;
    private TextBox txtAmount;
    private TextBox txtDate;
    private ComboBox<User> cboUsers;
    private ComboBox<Template> cboTemplates;
    private final CheckBoxList<Repartition> cklRepartitions = new CheckBoxList<>();
    private final Label errTitle = new Label("");
    private final Label errAmount = new Label("");
    private final Label errDate = new Label("");
    private final Label errUsers = new Label("");
    private final Label errRepartitions = new Label("");
    private Button btnAddUpdate;
    private Button btnApplay;
    private Button btnSaveRepAtTemplate;
    private Button buttonDelete;

    public EditOperationView(EditOperationController controller, Tricount tricount, Operation operation) {
        super((operation == null ? "Add " : "Edit ") + "Operation");
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

        populateFields();
        txtTitle.takeFocus();
    }

    private Panel createFieldsGrid() {
        var panel = Panel.gridPanel(2, Margin.of(1));

        new Label("Title:").addTo(panel);
        txtTitle = new TextBox().sizeTo(33).addTo(panel)
                .setTextChangeListener((txt, byUser) -> validate());
        panel.addEmpty();
        errTitle.addTo(panel)
                .setForegroundColor(TextColor.ANSI.RED);
        new Label(("Amount:")).addTo(panel);
        txtAmount = new TextBox().sizeTo(10).addTo(panel)
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
        cboUsers = new ComboBox<>(tricount.getParticipants()).addTo(panel).sizeTo(11);
        cboUsers.setSelectedItem(operation == null ? Security.getLoggedUser() : operation.getInitiator());
        cboUsers.addListener((selectedIndex, previousSelection, changedByUserInteraction) -> validate());
        panel.addEmpty();

        errUsers.addTo(panel).setForegroundColor(TextColor.ANSI.RED);


        new Label("Use a repartition \ntemplate (optional):").addTo(panel);
        var templatePanel = Panel.horizontalPanel().addTo(panel);
        List<Template> templates = templates();
        cboTemplates = new ComboBox<>(templates).addTo(templatePanel).sizeTo(35);
        cboTemplates.setSelectedItem(Template.DUMMY);
        cboTemplates.addListener((selectedIndex, previousSelection, changedByUserInteraction) -> validate());

        btnApplay = new Button("Apply", this::applyTemplate).addTo(templatePanel);
        panel.addEmpty();
        panel.addEmpty();

        new Label("For whom: \n(weight: ←/→ or -/+)").addTo(panel);
        List<Repartition> lstRepartitions = getRepartitions();
        for (var rep : lstRepartitions) {
            cklRepartitions.addItem(rep, rep.getWeight() > 0);

        }
        cklRepartitions.addListener((idx, isChecked) -> {
            validate();
        }).addTo(panel);
        panel.addEmpty();
        errRepartitions.addTo(panel).setForegroundColor(TextColor.ANSI.RED);
        
        addKeyboardListener(
                cklRepartitions,
                this::handleWeightKeyStroke);
        return panel;
    }

    private Boolean handleWeightKeyStroke(KeyStroke keyStroke) {
        Character character = keyStroke.getCharacter();
        KeyType type = keyStroke.getKeyType();
        int idx = cklRepartitions.getSelectedIndex();
        Repartition rep = cklRepartitions.getItemAt(idx);
        int weight = rep.getWeight();
        boolean changement = false;
            if ((character == null && type == KeyType.ArrowRight) || (character != null && character == '+')) {
                ++weight;
                changement = true;
            }
            if (character == null &&  type == KeyType.ArrowLeft || character != null && character == '-') {
                if (weight > 0) {
                    --weight;
                    changement = true;
                }
            }
            if (changement) {
            rep.setWeight(weight);
            cklRepartitions.setChecked(rep, weight != 0);
            cklRepartitions.invalidate();
        }
        return true;
    }
            //récupérer la liste de répartions que ce soit pour une nouvelle opération ou bien pour une opération existante
    public List<Repartition> getRepartitions() {
        List<Repartition> getParticipantRep = new ArrayList<>();
        for (User participant : tricount.getParticipants()) {
            if (operation != null) {
                int i = 0;
                while (i < operation.getRepartitions().size() && !participant.equals(operation.getRepartitions()[i].getUser()))
                    ++i;
                if (i < operation.getRepartitions().size()) {
                    Repartition rep = new Repartition(operation.getId(), participant.getId(), operation.getRepartitions()[i].getWeight());
                    getParticipantRep.add(rep);
                } else {
                    Repartition rep = new Repartition(operation.getId(), participant.getId(), 0);
                    getParticipantRep.add(rep);
                }
            } else {
                Repartition repartition = new Repartition(0, participant.getId(), 1);
                getParticipantRep.add(repartition);

            }
        }
        return getParticipantRep;
    }

    private Panel createButtonsPanel() {
        var panel = Panel.horizontalPanel().center();

        buttonDelete = new Button("Delete", this::delete).addTo(panel).setEnabled(operation != null);
        btnAddUpdate = new Button("Save", this::add).addTo(panel).setEnabled(false);
        btnSaveRepAtTemplate = new Button("Save Repartition as Template", this::saveRepAsTemp).addTo(panel).setEnabled(false);
        new Button("Cancel", this::close).addTo(panel);
        addShortcut(btnAddUpdate, KeyStroke.fromString(operation == null ? "<A-a>" : "<A-u>"));


        return panel;
    }
    private void saveRepAsTemp() {
        List<Repartition> repartitions = cklRepartitions.getCheckedItems();
        controller.saveRepAsTemp(repartitions);



    }
    // Définis les champs de texte avec les données de l'opération concernée.
    private void populateFields() {
        if (operation != null) {
            txtTitle.setText(operation.getTitle());
            txtAmount.setText(String.valueOf((operation.getAmount())).replace('.', ','));
            txtDate.setText(localDateToString(operation.getOperationDate()).replace('-', '/'));
            cboUsers.setSelectedItem(operation.getInitiator()); // pas nécessaire en théorie, à tester
            updateRepartitionsView();
        }
    }

    // Récupère la répartition de poid de l'opération
    private void updateRepartitionsView() {
        if (operation != null) {
            List<Repartition> operationRepartitions = operation.getRepartitions();

            for (var rep : cklRepartitions.getItems()) {
                boolean found = false;

                for (var operationRep : operationRepartitions) {
                    if (rep.getUser().getId() == operationRep.getUser().getId()) {
                        rep.setWeight(operationRep.getWeight());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    rep.setWeight(0);
                }
                cklRepartitions.invalidate();
            }
        }
    }
    private void delete() {
        controller.delete();
    }

    private void add() {
        int operationId = (operation != null) ? operation.getId() : 0;
        String amountText = txtAmount.getText();
        controller.save(
                txtTitle.getText(),
                amountText,
                txtDate.getText(),
                cboUsers.getSelectedItem().getFullName(),
                cklRepartitions.getCheckedItems(),
                operationId);
    }

    private void applyTemplate() {
        Template template = cboTemplates.getSelectedItem();
        List<TemplateItem> templateItems = template.getTemplateItems();
        for (var rep : cklRepartitions.getItems()) {
            int i = 0;
            while (i < templateItems.size() && !rep.getUser().equals(templateItems[i].getUser())) {
                ++i;
            }
            if (i < templateItems.size())
                rep.setWeight(templateItems[i].getWeight());
            else rep.setWeight(0);
            cklRepartitions.setChecked(rep, rep.getWeight() > 0);
            cboTemplates.setSelectedItem(Template.DUMMY);
        }
    }

    private void validate() {
        var errors = controller.validate(
                txtTitle.getText(),
                txtAmount.getText(),
                txtDate.getText(),
                cklRepartitions.getCheckedItems());

        errTitle.setText(errors.getFirstErrorMessage(Operation.Fields.Title));
        errAmount.setText(errors.getFirstErrorMessage(Operation.Fields.Amount));
        errDate.setText(errors.getFirstErrorMessage(Operation.Fields.OperationDate));
        errRepartitions.setText(errors.getFirstErrorMessage(Operation.Fields.Repartition));
        btnApplay.setEnabled(true);
        btnSaveRepAtTemplate.setEnabled(true);
        btnAddUpdate.setEnabled(errors.isEmpty());

    }
    public List<Template> templates() {
        List<Template> ls = new ArrayList<>();
        ls.add(Template.DUMMY);
        ls.addAll(tricount.getTemplates());
        return ls;
    }

}



