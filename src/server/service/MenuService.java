package server.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.repository.DBConnector;
import server.repository.MarketProductRepository;

public class MenuService {
	public void showAllMenu() {

	    String sql =
	        "SELECT * FROM product";

	    try(Connection conn =
	            DBConnector.connectDB();
	        PreparedStatement pstmt =
	            conn.prepareStatement(sql);
	        ResultSet rs =
	            pstmt.executeQuery()) {

	        System.out.println("====== 메뉴 ======");

	        while(rs.next()) {

	            System.out.println(
	                rs.getInt("product_id")
	                + ". "
	                + rs.getString("product_name")
	                + " / "
	                + rs.getString("category")
	                + " / "
	                + rs.getInt("unit_price")
	                + "원"
	            );
	        }

	    } catch(SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void showCategoryMenu(String category) {
		try (Connection conn = DBConnector.connectDB()){
			String showSql = "SELECT * FROM product WHERE category = " + category + ";";
				
			try (PreparedStatement pstmt = conn.prepareStatement(showSql);
				ResultSet rs = pstmt.executeQuery()){
			}
		} catch (SQLException e) {
			System.out.println(category + " 메뉴 조회 실패");
			e.printStackTrace();
		}
	}
}
