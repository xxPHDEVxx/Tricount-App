package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.tricount.model.Template;
import tgpr.tricount.view.ViewTemplatesView;

import java.util.List;

public class ViewTemplatesController extends Controller {

    private final ViewTemplatesView view;
    public ViewTemplatesController(){

        view = new ViewTemplatesView(this);
    }
    @Override
    public Window getView() {
        return view;

    }
    public List<Template> getTemplates(){
        return Template.getAll();
    }

}

