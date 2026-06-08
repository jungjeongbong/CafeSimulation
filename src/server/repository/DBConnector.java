package server.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	//여기에 DB주소랑 비번 등 넣기
	private static final String DB_URL = "";
	private static final String DB_USERNAME="root";
	private static final String DB_PASSWORD = "root";
	
	public static Connection connectDB() {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			System.out.println("DB 연결 성공");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void main(String [] args) {
		connectDB();
	}
}
