package tgpr.tricount.model;

import org.springframework.util.Assert;
import tgpr.framework.Model;
import tgpr.framework.Params;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class Subscription extends Model {
    public enum Fields {
        Tricount, User
    }

    public Subscription() {
    }

    public Subscription(int tricountId, int userId) {
        this.tricountId = tricountId;
        this.userId = userId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription subscription = (Subscription) o;
        return tricountId == subscription.tricountId && userId == subscription.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tricountId, userId);
    }

    @Override
    public String toString() {
        return "Subscription[" +
                "tricountId=" + tricountId +
                ", userId=" + userId +
                "]";
    }

    @Override
    protected void mapper(ResultSet rs) throws SQLException {
        tricountId = rs.getInt("tricount");
        userId = rs.getInt("user");
    }

    @Override
    public void reload() {
        reload("select * from subscriptions where tricount=:tricount and user=:user",
                new Params("tricount", tricountId).add("user", userId));
    }

    public static Subscription getByKey(int tricountId, int userId) {
        return queryOne(Subscription.class, "select * from subscriptions where tricount=:tricount and user=:user",
                new Params("tricount", tricountId).add("user", userId));
    }

    public static List<Subscription> getAll() {
        return queryList(Subscription.class, "select * from subscriptions");
    }

    public Subscription save() {
        int c;
        Subscription obj = getByKey(tricountId, userId);
        String sql;
        if (obj == null)
            sql = "insert into subscriptions (tricount,user) " +
                    "values (:tricount,:user)";
        else
            return this;
        var params = new Params()
                .add("tricount", tricountId)
                .add("user", userId);
        c = execute(sql, params);
        Assert.isTrue(c == 1, "Something went wrong");
        return this;
    }

    public void delete() {
        int c = execute("delete from subscriptions where tricount=:tricount and user=:user",
                new Params("tricount", tricountId).add("user", userId));
        Assert.isTrue(c == 1, "Something went wrong");
    }
}
