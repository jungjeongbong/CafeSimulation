package server.repository;

import server.domain.Customer;
import server.controller.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarketProductRepository {
	
	public void insertProduct(int product_history_id, int product_id, String name, String category, int price, int num) {
		
		try (Connection conn = DBConnector.connectDB()){
			String insertSql = "INSERT INTO product VALUES product_id, product_name, category, unit_price) VALUES ("
					 + name + ", " + category + ", " + price + ");";
				
			String writeSql = "INSERT INTO product_history "
			+ "(product_history_id, product_name, category, unit_price_history, start_timestamp, end_timestamp"
			+ " VALUES (" + product_history_id + ", " + product_id + ", " + name + ", "
			+ category + ", " + price + ", NOW(), NULL);";
			try (PreparedStatement pstmt = conn.prepareStatement(insertSql);
				 
				ResultSet rs = pstmt.executeQuery()){
				
			}
			
			try (PreparedStatement pstmt = conn.prepareStatement(writeSql);
					 
					ResultSet rs = pstmt.executeQuery()){
					
				}
			
		} catch (SQLException e) {
			System.out.println("상품 추가 실패");
			e.printStackTrace();
		}
	}
	
	public void deleteProduct(int product_id) {
		try (Connection conn = DBConnector.connectDB()){
			String deleteSql = "DELETE FROM product WHERE product_id="
					+ product_id + ";";
				
			try (PreparedStatement pstmt = conn.prepareStatement(deleteSql);
				 
				ResultSet rs = pstmt.executeQuery()){
				
			}
			
		} catch (SQLException e) {
			System.out.println("상품 삭제 실패");
			e.printStackTrace();
		}
	}
	
	public void changeProductPrice(int product_history_id, int product_id, int new_price) {
		try (Connection conn = DBConnector.connectDB()){
			String sql = "START TRANSACTION;"
					+ "\n\n"
					+ "UPDATE product_history "
					+ "SET end_timestamp = NOW() "
					+ "WHERE product_id = " + product_id
					+ " AND end_timestamp IS NULL;"
					+ "\n\n"
					+ "UPDATE product "
					+ "SET unit_price = " + new_price
					+ " WHERE product_id = " + product_id + ";"
					+ "\n\n"
					+ "INSERT INTO product_history (product_history_id, product_id, product_name, category, "
					+ "unit_price_history, start_timestamp, end_timestamp) "
					+ "SELECT " + product_history_id + ", product_id, product_name, category, unit_price, NOW(), NULL "
					+ "FROM product"
					+ "WHERE product_id = " + product_id + ";"
					+ "\n\n"
					+ "COMMIT;";
		
			try (PreparedStatement pstmt = conn.prepareStatement(sql);
				 
				ResultSet rs = pstmt.executeQuery()){
				
			}
			
		} catch (SQLException e) {
			System.out.println("상품 가격 변경 실패");
			e.printStackTrace();
		}
	}
	
	public Product findProductById(int productId) {

	    String sql =
	        "SELECT * FROM product WHERE product_id = ?";

	    try (Connection conn = DBConnector.connectDB();
	         PreparedStatement pstmt =
	             conn.prepareStatement(sql)) {

	        pstmt.setInt(1, productId);

	        ResultSet rs = pstmt.executeQuery();

	        if(rs.next()) {

	            return new Product(
	                rs.getInt("product_id"),
	                rs.getString("product_name"),
	                rs.getString("category"),
	                rs.getInt("unit_price")
	            );
	        }

	    } catch(SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}
}
