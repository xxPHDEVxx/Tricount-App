package tgpr.tricount.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import tgpr.framework.ColumnSpec;
import tgpr.framework.ObjectTable;
import tgpr.tricount.controller.AddTemplateController;
import tgpr.tricount.controller.ViewTemplatesController;
import tgpr.tricount.model.Repartition;
import tgpr.tricount.model.Template;
import tgpr.tricount.model.TemplateItem;
import tgpr.tricount.model.Tricount;

import java.util.ArrayList;
import java.util.List;

public class ViewTemplatesView extends DialogWindow {
    private final ViewTemplatesController controller;
    private final ObjectTable<Template> tmpTable;
    private final Label errRepartitions = new Label("");
    private final CheckBoxList<TemplateItem> tmpItem = new CheckBoxList<>();


    public ViewTemplatesView(ViewTemplatesController controller) {
        super("Tricount Repartition Templates");

        this.controller = controller;

        setHints(List.of(Hint.CENTERED));
        setCloseWindowWithEscape(true);

        //créations panneaux root et grid
        Panel root = new Panel().setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel panelGrid = new Panel().asGridPanel(1);

        //création de l'objectTable templates
        tmpTable = new ObjectTable<>(
                new ColumnSpec<>("Templates:", Template::getTitle)
        );
        tmpTable.addTo(root);

        new EmptySpace().addTo(root);

        //création de la checkboxlist repartition
        Label labelRepartitions = new Label("Repartition:").addTo(root);
        labelRepartitions.addStyle(SGR.BOLD).addStyle(SGR.UNDERLINE);

        var templates = controller.getTemplates();
        for (var temp : getTemplatesItem()) {
            tmpItem.addItem(temp, temp.getWeight() > 0);
            tmpItem.setChecked(temp, true);
        }

        tmpItem.addListener((idx, isChecked) -> {
        }).addTo(root);
        panelGrid.addEmpty();
        errRepartitions.addTo(panelGrid).setForegroundColor(TextColor.ANSI.RED);

        addKeyboardListener(
                tmpItem,
                this::handleWeightKeyStroke);

        new EmptySpace().addTo(root);
        //création panel grid pour les boutons
        Panel panelButtons = new Panel().asGridPanel(5);
        Button btnNew = new Button("New",this::addTemplate).addTo(panelButtons);
        Button btnEditTitle = new Button("Edit Title",this::editTemplate).addTo(panelButtons);
        Button btnDelete = new Button("Delete",this::delete).addTo(panelButtons);
        Button btnSave = new Button("Save").addTo(panelButtons);
        new Button("Close", this::close).addTo(panelButtons);

        panelGrid.addTo(root);
        panelButtons.addTo(root);
        setComponent(root);
        refresh();
    }

    public void refresh() {
        tmpTable.clear();
        var templates = controller.getTemplates();
        tmpTable.add(templates);

    }
    public List<TemplateItem> getTemplatesItem(){
        List<TemplateItem> tmpItem = new ArrayList<>();
        for (Template temp: controller.getTricount().getTemplates()){
            tmpItem.addAll(temp.getTemplateItems());
      }
        return tmpItem;
    }

    private Boolean handleWeightKeyStroke(KeyStroke keyStroke) {
        Character character = keyStroke.getCharacter();
        KeyType type = keyStroke.getKeyType();
        int idx = tmpItem.getSelectedIndex();
        TemplateItem rep = tmpItem.getItemAt(idx);
        int weight = rep.getWeight();
        boolean changement = false;
        if (character != null) {
            if (character == '+') {
                ++weight;
                changement = true;
            }
            if (character == '-')  {
                --weight;
                changement = true;
            }
        }else {
            if (type == KeyType.ArrowRight) {
                ++weight;
                changement = true;
            }
            if (type == KeyType.ArrowLeft) {
                --weight;
                changement = true;
            }
        }
        if (changement) {
            rep.setWeight(weight);
            tmpItem.invalidate();
        }
        return true;
    }
    private void add(){

    }
    public void addTemplate(){
        controller.addTemplate();

    }
    public void editTemplate(){
       controller.editTemplate(tmpTable.getSelected());

    }
    public void delete(){
        controller.delete();
    }

}
