package tgpr.tricount.model;

import org.springframework.util.Assert;
import tgpr.framework.Model;
import tgpr.framework.Params;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class TemplateItem extends Model {
    public enum Fields {
        User, Template, Weight
    }

    public TemplateItem() {
    }

    public TemplateItem(int userId, int templateId, int weight) {
        this.userId = userId;
        this.templateId = templateId;
        this.weight = weight;
    }

    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return User.getByKey(userId);
    }

    private int templateId;

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public Template getTemplate() {
        return Template.getByKey(templateId);
    }

    private int weight;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int incrementWeight() {
        return ++weight;
    }

    public int decrementWeight() {
        if (weight > 0)
            --weight;
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateItem templateItem = (TemplateItem) o;
        return templateId == templateItem.templateId && userId == templateItem.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(templateId, userId);
    }

    @Override
    public String toString() {
        var user = getUser();
        if (weight <= 0) return user.toString();
        return user.toString() + " (" + weight + ")";
    }

    @Override
    protected void mapper(ResultSet rs) throws SQLException {
        userId = rs.getInt("user");
        templateId = rs.getInt("template");
        weight = rs.getInt("weight");
    }

    @Override
    public void reload() {
        reload("select * from template_items where template=:template and user=:user",
                new Params("template", templateId).add("user", userId));
    }

    public static TemplateItem getByKey(int templateId, int userId) {
        return queryOne(TemplateItem.class, "select * from template_items where template=:template and user=:user",
                new Params("template", templateId).add("user", userId));
    }

    public static List<TemplateItem> getAll() {
        return queryList(TemplateItem.class, "select * from template_items");
    }

    public TemplateItem save() {
        int c;
        TemplateItem obj = getByKey(templateId, userId);
        String sql;
        if (obj == null)
            sql = "insert into template_items (user,template,weight) " +
                    "values (:user,:template,:weight)";
        else
            sql = "update template_items set weight=:weight " +
                    "where template=:template and user=:user";
        var params = new Params()
                .add("user", userId)
                .add("template", templateId)
                .add("weight", weight);
        c = execute(sql, params);
        Assert.isTrue(c == 1, "Something went wrong");
        return this;
    }

    public void delete() {
        int c = execute("delete from template_items where template=:template and user=:user",
                new Params("template", templateId).add("user", userId));
        Assert.isTrue(c == 1, "Something went wrong");
    }
}
