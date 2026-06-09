package cafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**

- DBConnection
- MySQL HeatWave 연결을 관리하는 클래스
- 팀원 모두 이 클래스를 통해 DB 연결
*/
public class DBConnection {
    
    // ※ 실행 전 아래 3가지 정보를 팀 DB에 맞게 수정하세요
    private static final String URL  = "jdbc:mysql://<host>:<port>/<database>?serverTimezone=Asia/Seoul";
    private static final String USER = "your_username";
    private static final String PASS = "your_password";
    
    /**
    
    - DB 연결 객체 반환
    - 사용 예: Connection conn = DBConnection.getConnection();
    */
    public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASS);
    }
    }