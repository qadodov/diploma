package data;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {

    private static QueryRunner runner;
    private static Connection connection;

    private static String url = System.getProperty("test_db_url");
    private static String user = System.getProperty("test_db_user");
    private static String password = System.getProperty("test_db_pass");

    @SneakyThrows
    public static void setUp() {
        runner = new QueryRunner();
        connection = DriverManager.getConnection(url, user, password);
    }

    @SneakyThrows
    public static EntryHelper.PaymentEntity getEntryFromPaymentEntityTable() {
        var query = "SELECT * FROM payment_entity ORDER BY created DESC LIMIT 1;";
        return runner.query(connection, query, new BeanHandler<>(EntryHelper.PaymentEntity.class));
    }

    @SneakyThrows
    public static EntryHelper.CreditRequestEntity getEntryFromCreditRequestEntityTable() {
        var query = "SELECT * FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        return runner.query(connection, query, new BeanHandler<>(EntryHelper.CreditRequestEntity.class));
    }

    @SneakyThrows
    public static EntryHelper.OrderEntity getEntryFromOrderEntityTable() {
        var query = "SELECT * FROM order_entity ORDER BY created DESC LIMIT 1;";
        return runner.query(connection, query, new BeanHandler<>(EntryHelper.OrderEntity.class));
    }

    @SneakyThrows
    public static void cleanDatabase() {
        setUp();
        runner.update(connection, "DELETE from payment_entity");
        runner.update(connection, "DELETE from credit_request_entity");
        runner.update(connection, "DELETE from order_entity");
    }

}
