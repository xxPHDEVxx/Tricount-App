package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.tricount.model.Operation;
import tgpr.tricount.model.Template;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.view.ViewTemplatesView;

import java.util.List;

public class ViewTemplatesController extends Controller {

    private final ViewTemplatesView view;
    private final Tricount tricount;
    public Tricount getTricount() {
        return tricount;
    }


    public ViewTemplatesController(Tricount tricount){
        this.tricount = tricount;
        view = new ViewTemplatesView(this);
    }
    public void addTemplate(){
        navigateTo(new AddTemplateController(null, getTricount().getId(),null));
        view.refresh();
    }
    @Override
    public Window getView() {
        return view;
    }
    public List<Template> getTemplates(){
        return Template.getAll();
    }

    public void editTemplate(Template template){
        navigateTo(new AddTemplateController(null, getTricount().getId(),template));
        view.refresh();
    }
    public void delete(Template template){
        if (askConfirmation("Voulez-vous vraiment supprimer ce template ? ","Confirmation")){
            template.delete();
            view.refresh();
        }
    }
    public void save(Template template){
        showMessage("La template a été sauvegardée!","Ok");
        template.save();
        view.refresh();
    }

}

