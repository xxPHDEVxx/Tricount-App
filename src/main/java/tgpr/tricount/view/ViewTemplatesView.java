package tgpr.tricount.view;

import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.framework.ObjectTable;
import tgpr.tricount.controller.ViewTemplatesController;
import tgpr.tricount.model.Template;

import java.util.List;

public class ViewTemplatesView extends DialogWindow {
    private final ViewTemplatesController controller;
    public ViewTemplatesView(ViewTemplatesController controller) {
        super("Tricount Repartition Templates");
        setHints(List.of(Hint.CENTERED));
        this.controller = controller;

        Panel panel = new Panel().setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel templates = new Panel().asGridPanel(1);
        templates.addTo(panel);
        //ObjectTable<Template> table = new ObjectTable<>();
        setComponent(panel);

    }

}
