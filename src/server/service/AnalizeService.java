package server.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.repository.DBConnector;

public class AnalizeService {
	public void analizeAboutTime() {
		try (Connection conn = DBConnector.connectDB()){
			String timeSql = "SELECT\n"
					+ "    HOUR(sales_timestamp) AS order_hour,\n"
					+ "    COUNT(*) AS order_count\n"
					+ "FROM sales\r\n"
					+ "GROUP BY HOUR(sales_timestamp)\n"
					+ "ORDER BY order_hour;";
				
			try (PreparedStatement pstmt = conn.prepareStatement(timeSql);
				ResultSet rs = pstmt.executeQuery()){
				System.out.println("-----------------------시간대별 주문량------------------------");
				System.out.println(rs);
			}
		} catch (SQLException e) {
			System.out.println("시간대별 주문량 조회 실패");
			e.printStackTrace();
		}
		
	}
	
	public void analizeTimeAndCategory() {
		try (Connection conn = DBConnector.connectDB()){
			String timeCategorySql = "SELECT\r\n"
					+ "    HOUR(v.sales_timestamp) AS order_hour,\r\n"
					+ "    v.category,\r\n"
					+ "    SUM(v.quantity) AS total_quantity\r\n"
					+ "FROM view_order_detail v\r\n"
					+ "GROUP BY HOUR(v.sales_timestamp), v.category\r\n"
					+ "ORDER BY order_hour, total_quantity DESC;";
				
			try (PreparedStatement pstmt = conn.prepareStatement(timeCategorySql);
				ResultSet rs = pstmt.executeQuery()){
				System.out.println("-----------------------시간대별 카테고리 주문량------------------------");
				System.out.println(rs);
			}
		} catch (SQLException e) {
			System.out.println("시간대별 카테고리 주문량 조회 실패");
			e.printStackTrace();
		}
	}
	
	public void analizeAboutAge() {
		try (Connection conn = DBConnector.connectDB()){
			String ageSql = "SELECT\r\n"
					+ "    CASE\r\n"
					+ "        WHEN age < 20 THEN '10s'\r\n"
					+ "        WHEN age BETWEEN 20 AND 29 THEN '20s'\r\n"
					+ "        WHEN age BETWEEN 30 AND 39 THEN '30s'\r\n"
					+ "        WHEN age BETWEEN 40 AND 49 THEN '40s'\r\n"
					+ "        ELSE '50s or older'\r\n"
					+ "    END AS age_group,\r\n"
					+ "    category,\r\n"
					+ "    SUM(quantity) AS total_quantity,\r\n"
					+ "    SUM(amount) AS total_sales_amount\r\n"
					+ "FROM view_order_detail\r\n"
					+ "GROUP BY age_group, category\r\n"
					+ "ORDER BY age_group, total_quantity DESC;";
				
			try (PreparedStatement pstmt = conn.prepareStatement(ageSql);
				ResultSet rs = pstmt.executeQuery()){
				System.out.println("-----------------------연령대별 주문량------------------------");
				System.out.println(rs);
			}
		} catch (SQLException e) {
			System.out.println("연령대별 주문량 및 카테고리 조회 실패");
			e.printStackTrace();
		}
	}
	
	public void analizeAboutCategory() {
		try (Connection conn = DBConnector.connectDB()){
			String categorySql = "SELECT \r\n"
					+ "    category,\r\n"
					+ "    SUM(quantity) AS total_quantity,\r\n"
					+ "    SUM(amount) AS total_amount\r\n"
					+ "FROM view_order_detail\r\n"
					+ "GROUP BY category\r\n"
					+ "ORDER BY total_amount DESC;";
				
			try (PreparedStatement pstmt = conn.prepareStatement(categorySql);
				ResultSet rs = pstmt.executeQuery()){
				System.out.println("-----------------------카테고리별 총 매출------------------------");
				System.out.println(rs);
			}
		} catch (SQLException e) {
			System.out.println("카테고리별 총 매출 조회 실패");
			e.printStackTrace();
		}
	}
	
	public void analizeAboutStore() {
		try (Connection conn = DBConnector.connectDB()){
			String storeSql = "SELECT\r\n"
					+ "    store_name,\r\n"
					+ "    SUM(amount) AS total_amount\r\n"
					+ "FROM view_order_detail\r\n"
					+ "GROUP BY store_name\r\n"
					+ "ORDER BY total_amount DESC;";
				
			try (PreparedStatement pstmt = conn.prepareStatement(storeSql);
				ResultSet rs = pstmt.executeQuery()){
				System.out.println("-----------------------지점별 총 매출------------------------");
				System.out.println(rs);
			}
		} catch (SQLException e) {
			System.out.println("지점별 총 매출 조회 실패");
			e.printStackTrace();
		}
	}
	
	public void sumOfOneCustomer() {
		try (Connection conn = DBConnector.connectDB()){
			String sumSql = "SELECT\r\n"
					+ "    customer_id,\r\n"
					+ "    customer_name,\r\n"
					+ "    SUM(amount) AS total_spent\r\n"
					+ "FROM view_order_detail\r\n"
					+ "GROUP BY customer_id, customer_name\r\n"
					+ "ORDER BY total_spent DESC;";
				
			try (PreparedStatement pstmt = conn.prepareStatement(sumSql);
				ResultSet rs = pstmt.executeQuery()){
				System.out.println("-----------------------고객별 총 구매액------------------------");
				System.out.println(rs);
			}
		} catch (SQLException e) {
			System.out.println("고객별 총 구매액 조회 실패");
			e.printStackTrace();
		}
	}
	
	public void analizeAboutCustomer() {
		try (Connection conn = DBConnector.connectDB()){
			String customerSql = "SELECT\r\n"
					+ "    ch.customer_id,\r\n"
					+ "    ch.name,\r\n"
					+ "    ch.city,\r\n"
					+ "    ch.age,\r\n"
					+ "    ch.start_timestamp,\r\n"
					+ "    ch.end_timestamp,\r\n"
					+ "    COUNT(s.sales_id) AS order_count,\r\n"
					+ "    SUM(ts.total_amount) AS total_spent\r\n"
					+ "FROM customer_history ch\r\n"
					+ "JOIN sales s \r\n"
					+ "    ON ch.customer_id = s.customer_id\r\n"
					+ "   AND s.sales_timestamp >= ch.start_timestamp\r\n"
					+ "   AND (ch.end_timestamp IS NULL OR s.sales_timestamp < ch.end_timestamp)\r\n"
					+ "JOIN total_sales ts\r\n"
					+ "    ON s.market_basket_id = ts.market_basket_id\r\n"
					+ "WHERE ch.customer_id = ?\r\n"
					+ "GROUP BY \r\n"
					+ "    ch.customer_history_id,\r\n"
					+ "    ch.customer_id,\r\n"
					+ "    ch.name,\r\n"
					+ "    ch.city,\r\n"
					+ "    ch.age,\r\n"
					+ "    ch.start_timestamp,\r\n"
					+ "    ch.end_timestamp\r\n"
					+ "ORDER BY ch.start_timestamp;";
				
			try (PreparedStatement pstmt = conn.prepareStatement(customerSql);
				ResultSet rs = pstmt.executeQuery()){
				System.out.println("-----------------------고객 정보 변경 전후 판매 분석------------------------");
				System.out.println(rs);
			}
		} catch (SQLException e) {
			System.out.println("고객 정보 변경 전후 판매 분석 실패");
			e.printStackTrace();
		}
	}
	
	public void analizeBeforeAfterPrice() {
		try (Connection conn = DBConnector.connectDB()){
			String priceSql = "SELECT\r\n"
					+ "    ph.product_id,\r\n"
					+ "    ph.product_name,\r\n"
					+ "    ph.unit_price_history,\r\n"
					+ "    ph.start_timestamp,\r\n"
					+ "    ph.end_timestamp,\r\n"
					+ "    SUM(mb.quantity) AS total_quantity,\r\n"
					+ "    SUM(mb.quantity * ph.unit_price_history) AS total_sales_amount\r\n"
					+ "FROM product_history ph\r\n"
					+ "JOIN market_basket mb\r\n"
					+ "    ON ph.product_id = mb.product_id\r\n"
					+ "JOIN sales s\r\n"
					+ "    ON mb.market_basket_id = s.market_basket_id\r\n"
					+ "   AND s.sales_timestamp >= ph.start_timestamp\r\n"
					+ "   AND (ph.end_timestamp IS NULL OR s.sales_timestamp < ph.end_timestamp)\r\n"
					+ "WHERE ph.product_id = ?\r\n"
					+ "GROUP BY\r\n"
					+ "    ph.product_history_id,\r\n"
					+ "    ph.product_id,\r\n"
					+ "    ph.product_name,\r\n"
					+ "    ph.unit_price_history,\r\n"
					+ "    ph.start_timestamp,\r\n"
					+ "    ph.end_timestamp\r\n"
					+ "ORDER BY ph.start_timestamp;";
				
			try (PreparedStatement pstmt = conn.prepareStatement(priceSql);
				ResultSet rs = pstmt.executeQuery()){
				System.out.println("-----------------------가격 변경 전후 판매 분석------------------------");
				System.out.println(rs);
			}
		} catch (SQLException e) {
			System.out.println("가격 변경 전후 판매 분석 실패");
			e.printStackTrace();
		}
	}
}
