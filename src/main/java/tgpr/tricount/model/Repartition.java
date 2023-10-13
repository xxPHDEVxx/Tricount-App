package tgpr.tricount.model;

import org.springframework.util.Assert;
import tgpr.framework.Model;
import tgpr.framework.Params;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class Repartition extends Model {
    public enum Fields {
        Operation, User, Weight
    }

    public Repartition() {
    }

    public Repartition(int operationId, int userId, int weight) {
        this.operationId = operationId;
        this.userId = userId;
        this.weight = weight;
    }

    private int operationId;

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    public Operation getOperation() {
        return Operation.getByKey(operationId);
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

    private int weight;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repartition repartition = (Repartition) o;
        return operationId == repartition.operationId && userId == repartition.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationId, userId);
    }

    @Override
    public String toString() {
        var user = getUser();
        if (weight <= 0) return user.toString();
        return user.toString() + " (" + weight + ")";
    }

    @Override
    protected void mapper(ResultSet rs) throws SQLException {
        operationId = rs.getInt("operation");
        userId = rs.getInt("user");
        weight = rs.getInt("weight");
    }

    @Override
    public void reload() {
        reload("select * from repartitions where operation=:operation and user=:user",
                new Params("operation", operationId).add("user", userId));
    }

    public static Repartition getByKey(int operationId, int userId) {
        return queryOne(Repartition.class, "select * from repartitions where operation=:operation and user=:user",
                new Params("operation", operationId).add("user", userId));
    }

    public static List<Repartition> getAll() {
        return queryList(Repartition.class, "select * from repartitions");
    }

    public Repartition save() {
        int c;
        Repartition obj = getByKey(operationId, userId);
        String sql;
        if (obj == null)
            sql = "insert into repartitions (operation,user,weight) " +
                    "values (:operation,:user,:weight)";
        else
            sql = "update repartitions set weight=:weight " +
                    "where operation=:operation and user=:user";
        var params = new Params()
                .add("operation", operationId)
                .add("user", userId)
                .add("weight", weight);
        c = execute(sql, params);
        Assert.isTrue(c == 1, "Something went wrong");
        return this;
    }

    public void delete() {
        int c = execute("delete from repartitions where operation=:operation and user=:user",
                new Params("operation", operationId).add("user", userId));
        Assert.isTrue(c == 1, "Something went wrong");
    }
}
