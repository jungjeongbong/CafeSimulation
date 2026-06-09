package server.repository;

import java.sql.*;

public class OrderRepository {
	public void saveOrder(String orderData) {

	    Connection conn = null;

	    try {

	        conn = DBConnector.connectDB();

	        conn.setAutoCommit(false);

	        String insertSales =
	            "INSERT INTO sales "
	            + "(sales_timestamp) "
	            + "VALUES (NOW())";

	        PreparedStatement salesStmt =
	            conn.prepareStatement(
	                insertSales,
	                Statement.RETURN_GENERATED_KEYS);

	        salesStmt.executeUpdate();

	        ResultSet key =
	            salesStmt.getGeneratedKeys();

	        int salesId = 0;

	        if(key.next()) {
	            salesId = key.getInt(1);
	        }

	        String[] items =
	            orderData.split(";");

	        for(String item : items) {

	            if(item.isBlank()) {
	                continue;
	            }

	            String[] parts =
	                item.split(",");

	            int productId =
	                Integer.parseInt(parts[0]);

	            int quantity =
	                Integer.parseInt(parts[1]);

	            saveBasketItem(
	                conn,
	                salesId,
	                productId,
	                quantity);
	        }

	        conn.commit();

	        System.out.println(
	            "주문 저장 완료");

	    } catch(Exception e) {

	        try {

	            if(conn != null) {
	                conn.rollback();
	            }

	        } catch(SQLException ex) {
	            ex.printStackTrace();
	        }

	        e.printStackTrace();

	    } finally {

	        try {

	            if(conn != null) {
	                conn.close();
	            }

	        } catch(SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	private void saveBasketItem(
		    Connection conn,
		    int salesId,
		    int productId,
		    int quantity)
		    throws SQLException {

		    String sql =
		        "INSERT INTO market_basket "
		        + "(sales_id, product_id, quantity) "
		        + "VALUES (?, ?, ?)";

		    PreparedStatement pstmt =
		        conn.prepareStatement(sql);

		    pstmt.setInt(1, salesId);
		    pstmt.setInt(2, productId);
		    pstmt.setInt(3, quantity);

		    pstmt.executeUpdate();
		}
}