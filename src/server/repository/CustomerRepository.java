package server.repository;

import server.domain.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
	private final Connection conn;
	public CustomerRepository() {
		this.conn = DBConnector.connectDB();
		
		System.out.println("고객 레포지토리 DB 연결 성공");
	}
	public Customer findPlayerByName(String name) {
		String sql = "";
		Customer customer = null;
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql);){
			pstmt.setString(0, sql);
			
			try(ResultSet rs = pstmt.executeQuery()){
				if (rs.next()) {
					System.out.println("고객 찾음");
					customer = new Customer();
					customer.setCustomerID(rs.getInt("customerID"));
					customer.setName(rs.getString("name"));
					customer.setPhoneNumber(rs.getString("phoneNumber"));
					customer.setCity(rs.getString("city"));
					customer.setAge(rs.getInt("age"));
				}
			};
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}
}
