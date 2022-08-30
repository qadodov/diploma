package data;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    private static QueryRunner runner;
    private static Connection connection;

    private static String url = System.getProperty("test_db_url");
    private static String user = System.getProperty("test_db_user");
    private static String password = System.getProperty("test_db_pass");


    static {
        runner = new QueryRunner();
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @SneakyThrows
    public static PaymentEntity getEntryFromPaymentEntityTable() {
        var query = "SELECT * FROM payment_entity ORDER BY created DESC LIMIT 1;";
        return runner.query(connection, query, new BeanHandler<>(PaymentEntity.class));
    }

    @SneakyThrows
    public static CreditRequestEntity getEntryFromCreditRequestEntityTable() {
        var query = "SELECT * FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        return runner.query(connection, query, new BeanHandler<>(CreditRequestEntity.class));
    }

    @SneakyThrows
    public static OrderEntity getEntryFromOrderEntityTable() {
        var query = "SELECT * FROM order_entity ORDER BY created DESC LIMIT 1;";
        return runner.query(connection, query, new BeanHandler<>(OrderEntity.class));
    }

    @SneakyThrows
    public static void cleanDatabase() {
        runner.update(connection, "DELETE from payment_entity");
        runner.update(connection, "DELETE from credit_request_entity");
        runner.update(connection, "DELETE from order_entity");
    }

}
