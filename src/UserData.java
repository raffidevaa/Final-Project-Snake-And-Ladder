import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserData {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/snakeandladder";
    private static final String USERNAME = "snakeandladder";
    private static final String PASSWORD = "snl123";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}
