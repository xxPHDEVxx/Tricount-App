package tgpr.tricount.controller;

import com.googlecode.lanterna.gui2.Window;
import tgpr.framework.Controller;
import tgpr.framework.Error;
import tgpr.framework.ErrorList;
import tgpr.tricount.model.Repartition;
import tgpr.tricount.model.Template;
import tgpr.tricount.model.TemplateItem;
import tgpr.tricount.model.Tricount;
import tgpr.tricount.view.AddTemplateView;

import java.util.List;

public class AddTemplateController extends Controller {
    private List<Repartition> repartitions;
    int tricountId;
    public AddTemplateController(List<Repartition> repartitions, int tricountId) {
        this.repartitions = repartitions;
        this.tricountId = tricountId;
    }
    public static Error isValidTitle(String title) {
        if (title != null && !title.trim().isEmpty())
            return Error.NOERROR;
        return new Error("Title is required", Template.Fields.Title);
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
