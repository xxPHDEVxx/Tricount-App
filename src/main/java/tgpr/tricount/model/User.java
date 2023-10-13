package tgpr.tricount.model;

import org.springframework.util.Assert;
import tgpr.framework.Model;
import tgpr.framework.Params;
import tgpr.framework.Tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class User extends Model {
    public enum Fields {
        Id, Mail, Password, ConfirmPassword, FullName, Role, OldPassword, Iban
    }

    public enum Role {
        User, Admin;

        public static Role valueOfIgnoreCase(String str) {
            return values().stream().filter((Role v) -> v.name().equalsIgnoreCase(str)).findFirst().orElse(null);
        }
    }

    public User() {
    }

    public User(String mail, String hashedPassword, String fullName, Role role) {
        this.mail = mail;
        this.hashedPassword = hashedPassword;
        this.fullName = fullName;
        this.role = role;
    }

    public User(String mail, String hashedPassword, String fullName, Role role, String iban) {
        this.mail = mail;
        this.hashedPassword = hashedPassword;
        this.fullName = fullName;
        this.role = role;
        this.iban = iban;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Operation> getOperations() {
        return queryList(Operation.class, "select * from operations where initiator=:id",
                new Params("id", id));
    }

    public List<Repartition> getRepartitions() {
        return queryList(Repartition.class, "select * from repartitions where user=:id",
                new Params("id", id));
    }

    public List<Subscription> getSubscriptions() {
        return queryList(Subscription.class, "select * from subscriptions where user=:id",
                new Params("id", id));
    }

    public List<TemplateItem> getTemplateItems() {
        return queryList(TemplateItem.class, "select * from template_items where user=:id",
                new Params("id", id));
    }

    public List<Tricount> getTricounts() {
        return queryList(Tricount.class, "select * from tricounts where creator=:id",
                new Params("id", id));
    }

    private String mail;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    private String hashedPassword;

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isUser() {
        return role == Role.User;
    }

    public boolean isAdmin() {
        return role == Role.Admin;
    }

    private String iban;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    protected void mapper(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        mail = rs.getString("mail");
        hashedPassword = rs.getString("hashed_password");
        fullName = rs.getString("full_name");
        role = Role.valueOfIgnoreCase(rs.getString("role"));
        iban = rs.getString("iban");
    }

    @Override
    public void reload() {
        reload("select * from users where id=:id",
                new Params("id", id));
    }

    public static User getByKey(int id) {
        return queryOne(User.class, "select * from users where id=:id",
                new Params("id", id));
    }

    public static User getByMail(String mail) {
        return queryOne(User.class, "select * from users where mail=:mail",
                new Params("mail", mail));
    }

    public static User getByFullName(String fullName) {
        return queryOne(User.class, "select * from users where full_name=:fullName",
                new Params("fullName", fullName));
    }

    public static List<User> getAll() {
        return queryList(User.class, "select * from users order by full_name");
    }

    public User save() {
        int c;
        User obj = getByKey(id);
        String sql;
        var params = new Params()
                .add("id", id)
                .add("mail", mail)
                .add("hashed_password", hashedPassword)
                .add("full_name", fullName)
                .add("role", role.name().toLowerCase())
                .add("iban", iban);
        if (obj == null) {
            sql = "insert into users (mail,hashed_password,full_name,role,iban) " +
                    "values (:mail,:hashed_password,:full_name,:role,:iban)";
            int id = insert(sql, params);
            if (id > 0)
                this.id = id;
        } else {
            sql = "update users set mail=:mail," +
                    "hashed_password=:hashed_password," +
                    "full_name=:full_name," +
                    "role=:role," +
                    "iban=:iban " +
                    "where id=:id";
            c = execute(sql, params);
            Assert.isTrue(c == 1, "Something went wrong");
        }
        return this;
    }

    public void delete() {
        int c = execute("delete from users where id=:id",
                new Params("id", id));
        Assert.isTrue(c == 1, "Something went wrong");
    }
}
