package cafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL  = "jdbc:mysql://localhost:3306/cafe_db?serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "username";
    private static final String PASS = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
