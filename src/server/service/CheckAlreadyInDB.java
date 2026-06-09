package server.service;

import server.repository.CustomerRepository;
import server.repository.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.controller.ConnectionController;
import server.domain.Customer;

public class CheckAlreadyInDB {
	public boolean availableNumber(String phoneNumber) {

		String newPhoneNumber = phoneNumber;

		String selectSql =
				"SELECT EXISTS (SELECT * FROM customer WHERE phone_number = ?) AS is_exist";

		try (Connection conn = DBConnector.connectDB();
				PreparedStatement pstmt = conn.prepareStatement(selectSql)) {

			pstmt.setString(1, newPhoneNumber);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return !rs.getBoolean("is_exist");
				}
			}

		} catch (SQLException e) {
			System.out.println("전화번호 중복 여부 확인 실패");
			e.printStackTrace();
		}

		return false;
	}
}
