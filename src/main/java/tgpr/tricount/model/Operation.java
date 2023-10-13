package tgpr.tricount.model;

import org.springframework.util.Assert;
import tgpr.framework.Model;
import tgpr.framework.Params;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Operation extends Model {
    public enum Fields {
        Id, Title, Tricount, Amount, OperationDate, Initiator, Repartition, CreatedAt
    }

    public Operation() {
    }

    public Operation(String title, int tricountId, double amount, LocalDate operationDate, int initiatorId,
                     LocalDateTime createdAt) {
        this.title = title;
        this.tricountId = tricountId;
        this.amount = amount;
        this.operationDate = operationDate;
        this.initiatorId = initiatorId;
        this.createdAt = createdAt;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNew() {
        return id <= 0;
    }

    public List<Repartition> getRepartitions() {
        return queryList(Repartition.class, """
                        select *
                        from repartitions
                        where operation=:id
                        """,
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

    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    private LocalDate operationDate;

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    private int initiatorId;

    public int getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(int initiatorId) {
        this.initiatorId = initiatorId;
    }

    public User getInitiator() {
        return User.getByKey(initiatorId);
    }

    private LocalDateTime createdAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return id == operation.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Operation[" +
                "id=" + id +
                ", title=" + title +
                ", tricountId=" + tricountId +
                ", amount=" + amount +
                ", operationDate=" + operationDate +
                ", initiatorId=" + initiatorId +
                ", createdAt=" + createdAt +
                "]";
    }

    @Override
    protected void mapper(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        title = rs.getString("title");
        tricountId = rs.getInt("tricount");
        amount = rs.getDouble("amount");
        operationDate = rs.getObject("operation_date", LocalDate.class);
        initiatorId = rs.getInt("initiator");
        createdAt = rs.getObject("created_at", LocalDateTime.class);
    }

    @Override
    public void reload() {
        reload("select * from operations where id=:id",
                new Params("id", id));
    }

    public static Operation getByKey(int id) {
        return queryOne(Operation.class, "select * from operations where id=:id",
                new Params("id", id));
    }

    public static List<Operation> getAll() {
        return queryList(Operation.class, "select * from operations");
    }

    public Operation save() {
        int c;
        Operation obj = getByKey(id);
        String sql;
        var params = new Params()
                .add("id", id)
                .add("title", title)
                .add("tricount", tricountId)
                .add("amount", amount)
                .add("operation_date", operationDate)
                .add("initiator", initiatorId)
                .add("created_at", createdAt);
        if (obj == null) {
            sql = "insert into operations (title,tricount,amount,operation_date,initiator,created_at) " +
                    "values (:title,:tricount,:amount,:operation_date,:initiator,:created_at)";
            int id = insert(sql, params);
            if (id > 0)
                this.id = id;
        } else {
            sql = "update operations set title=:title," +
                    "tricount=:tricount," +
                    "amount=:amount," +
                    "operation_date=:operation_date," +
                    "initiator=:initiator," +
                    "created_at=:created_at " +
                    "where id=:id";
            c = execute(sql, params);
            Assert.isTrue(c == 1, "Something went wrong");
        }
        return this;
    }

    public void delete() {
        int c = execute("delete from operations where id=:id",
                new Params("id", id));
        Assert.isTrue(c == 1, "Something went wrong");
    }

}
