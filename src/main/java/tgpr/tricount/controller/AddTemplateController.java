package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.framework.Error;
import tgpr.framework.ErrorList;
import tgpr.tricount.model.*;
import tgpr.tricount.view.AddTemplateView;

import java.util.List;
import java.util.regex.Pattern;

public class AddTemplateController extends Controller {
    private List<Repartition> repartitions;
    int tricountId;
    private AddTemplateView view;
    public AddTemplateController(List<Repartition> repartitions, int tricountId) {
        this.repartitions = repartitions;
        this.tricountId = tricountId;
        view = new AddTemplateView(this);
    }
    public  Error isValidTitle(String title) {
        if(title == null || title.isBlank())
            return new Error("Title required", Template.Fields.Title);
        if(!Pattern.matches("^[A-Za-z0-9\\s]{3,256}", title))
            return new Error("minimum 3 chars", Template.Fields.Title);

        return Error.NOERROR;
    }
    public ErrorList validate(String title) {
        var errors = new ErrorList();
        errors.add(isValidTitle(title));
        return errors;
    }
    public void create(String title){
        var error = isValidTitle(title);
        Template template = new Template(title, tricountId);
        if(error == Error.NOERROR)
            template.save();
        else
            showError(error);
        for (var rep: repartitions) {
            TemplateItem templateItem = new TemplateItem();
            templateItem.setUserId(rep.getUserId());
            templateItem.setWeight(rep.getWeight());
            templateItem.setTemplateId(template.getId());
            templateItem.save();
        }
    }
    @Override
    public Window getView() {
        return new AddTemplateView(this);
    }
}
