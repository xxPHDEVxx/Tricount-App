package tgpr.tricount.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.framework.ColumnSpec;
import tgpr.framework.ObjectTable;
import tgpr.tricount.controller.ViewTemplatesController;
import tgpr.tricount.model.Repartition;
import tgpr.tricount.model.Template;

import java.util.ArrayList;
import java.util.List;

public class ViewTemplatesView extends DialogWindow {
    private final ViewTemplatesController controller;

    public ViewTemplatesView(ViewTemplatesController controller) {
        super("Tricount Repartition Templates");
        setHints(List.of(Hint.CENTERED));
        this.controller = controller;

        Panel root = new Panel().setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel templates = new Panel().asGridPanel(1);

        ObjectTable<Template> tbl = new ObjectTable<>(
                new ColumnSpec<>("Templates:",Template::getTemplateItems)
        );

        tbl.addTo(root);

        new EmptySpace().addTo(root);

        Label labelRepartitions = new Label("Repartition:").addTo(root);
        labelRepartitions.addStyle(SGR.BOLD).addStyle(SGR.UNDERLINE);

        new EmptySpace().addTo(root);

        Panel panelButtons = new Panel().asGridPanel(5);
        Button btnNew = new Button("New").addTo(panelButtons);
        Button btnEditTitle = new Button("Edit Title").addTo(panelButtons);
        Button btnDelete = new Button("Delete").addTo(panelButtons);
        Button btnSave = new Button("Save").addTo(panelButtons);
        new Button("Close",this::close).addTo(panelButtons);


        templates.addTo(root);
        panelButtons.addTo(root);
        setComponent(root);

    }

}
