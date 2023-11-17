package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.CheckBoxList;
import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.tricount.model.Operation;
import tgpr.tricount.model.Template;
import tgpr.tricount.model.TemplateItem;
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
        template.reload();
        view.close();
        navigateTo(new ViewTemplatesController(tricount));
    }
    public void delete(Template template){
        if (askConfirmation("Voulez-vous vraiment supprimer ce template ? ","Confirmation")){
            if(template!=null){
                template.delete();
                template.reload();
                view.close();
                navigateTo(new ViewTemplatesController(tricount));
            }
        }
    }
    public void save(TemplateItem templateItem){
        showMessage("La template a été sauvegardée!","Ok");
        templateItem.save();
        view.refresh();
    }

}

