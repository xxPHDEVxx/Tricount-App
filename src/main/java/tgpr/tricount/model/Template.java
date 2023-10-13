package tgpr.tricount.model;

import org.springframework.util.Assert;
import tgpr.framework.Model;
import tgpr.framework.Params;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class Template extends Model {
    public enum Fields {
        Id, Title, Repartition, Tricount
    }

    public static final Template DUMMY = new Template("No, I'll use custom repartition", 0);

    public Template() {
    }

    public Template(String title, int tricountId) {
        this.title = title;
        this.tricountId = tricountId;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TemplateItem> getTemplateItems() {
        return queryList(TemplateItem.class, "select * from template_items where template=:id",
                new Params("id", id));
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private int tricountId;

    public int getTricountId() {
        return tricountId;
    }

    public void setTricountId(int tricountId) {
        this.tricountId = tricountId;
    }

    public Tricount getTricount() {
        return Tricount.getByKey(tricountId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return id == template.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    protected void mapper(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        title = rs.getString("title");
        tricountId = rs.getInt("tricount");
    }

    @Override
    public void reload() {
        reload("select * from templates where id=:id",
                new Params("id", id));
    }

    public static Template getByKey(int id) {
        return queryOne(Template.class, "select * from templates where id=:id",
                new Params("id", id));
    }

    public static Template getByTitle(int id, String title) {
        return queryOne(Template.class, "select * from templates where tricount=:id and title=:title",
                new Params("id", id).add("title", title));
    }

    public static List<Template> getAll() {
        return queryList(Template.class, "select * from templates");
    }

    public Template save() {
        int c;
        Template obj = getByKey(id);
        String sql;
        var params = new Params()
                .add("id", id)
                .add("title", title)
                .add("tricount", tricountId);
        if (obj == null) {
            sql = "insert into templates (title,tricount) " +
                    "values (:title,:tricount)";
            int id = insert(sql, params);
            if (id > 0)
                this.id = id;
        } else {
            sql = "update templates set title=:title," +
                    "tricount=:tricount " +
                    "where id=:id";
            c = execute(sql, params);
            Assert.isTrue(c == 1, "Something went wrong");
        }
        return this;
    }

    public void delete() {
        int c = execute("delete from templates where id=:id",
                new Params("id", id));
        Assert.isTrue(c == 1, "Something went wrong");
    }
}
