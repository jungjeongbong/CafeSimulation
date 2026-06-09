package server.controller;

import server.Server;
import server.MessageSender;
import server.OrderRoom;
import server.domain.Customer;
import server.repository.CustomerRepository;
import server.repository.DBConnector;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class ConnectionController extends Thread implements MessageSender{

	public Socket socket;
	private Server server;
	private BufferedReader in;
	private PrintWriter out;
	private Customer customer;
	private OrderRoom orderRoom;
	
	public void setOrderRoom(OrderRoom orderRoom) {
	    this.orderRoom = orderRoom;
	}
	
	public ConnectionController(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
		
		InputStreamReader input;
		try {
			input = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
			in = new BufferedReader(input);
			OutputStreamWriter output = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
			out = new PrintWriter(output, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.customer = new Customer();
		customer.setMessageSender(this);
	}
	
	@Override
	public void run() {

	    try {

	        String msg;

	        while((msg = in.readLine()) != null) {

	            System.out.println(
	                "[서버 수신] " + msg);

	            if(msg.startsWith("ORDER:")) {

	                saveOrder(msg);
	            }
	        }

	    } catch (IOException e) {

	        e.printStackTrace();
	    }
	}
	
	private void saveOrder(String msg) {

	    try {

	        String[] parts = msg.split(":", 4);

	        int customerId =
	                Integer.parseInt(parts[1]);

	        int storeId =
	                Integer.parseInt(parts[2]);

	        String orderData =
	                parts[3];

	        String[] items =
	                orderData.split(";");

	        Connection conn =
	                DBConnector.connectDB();

	        conn.setAutoCommit(false);

	        int basketId =
	                (int)(System.currentTimeMillis() / 1000);

	        int basketItemId =
	                getNextBasketItemId(conn);

	        double total = 0;

	        for(String item : items) {

	            if(item.isEmpty())
	                continue;

	            String[] data =
	                    item.split(",");

	            int productId =
	                    Integer.parseInt(data[0]);

	            int quantity =
	                    Integer.parseInt(data[1]);

	            double price =
	                    getProductPrice(conn, productId);

	            total += price * quantity;

	            PreparedStatement pstmt =
	                conn.prepareStatement(
	                    "INSERT INTO market_basket "
	                  + "(basket_item_id, market_basket_id, product_id, quantity, unit_price_at_order) "
	                  + "VALUES (?, ?, ?, ?, ?)");

	            pstmt.setInt(1, basketItemId++);
	            pstmt.setInt(2, basketId);
	            pstmt.setInt(3, productId);
	            pstmt.setInt(4, quantity);
	            pstmt.setDouble(5, price);

	            pstmt.executeUpdate();
	        }

	        insertSales(
	            conn,
	            basketId,
	            customerId,
	            storeId);

	        insertTotalSales(
	            conn,
	            basketId,
	            total);

	        conn.commit();

	        System.out.println(
	            "주문 저장 완료");

	    } catch(Exception e) {

	        e.printStackTrace();
	    }
	}
	
	private double getProductPrice(
	        Connection conn,
	        int productId)
	        throws SQLException {

	    PreparedStatement pstmt =
	        conn.prepareStatement(
	            "SELECT unit_price "
	          + "FROM product "
	          + "WHERE product_id=?");

	    pstmt.setInt(1, productId);

	    ResultSet rs =
	        pstmt.executeQuery();

	    if(rs.next()) {

	        return rs.getDouble(
	                "unit_price");
	    }

	    return 0;
	}
	
	private void insertSales(
	        Connection conn,
	        int basketId,
	        int customerId,
	        int storeId)
	        throws SQLException {

	    int salesId =
	            getNextSalesId(conn);

	    PreparedStatement pstmt =
	        conn.prepareStatement(
	            "INSERT INTO sales "
	          + "(sales_id, sales_timestamp, store_id, customer_id, market_basket_id) "
	          + "VALUES (?, NOW(), ?, ?, ?)");

	    pstmt.setInt(1, salesId);
	    pstmt.setInt(2, storeId);
	    pstmt.setInt(3, customerId);
	    pstmt.setInt(4, basketId);

	    pstmt.executeUpdate();
	}
	
	private void insertTotalSales(
	        Connection conn,
	        int basketId,
	        double total)
	        throws SQLException {

	    PreparedStatement pstmt =
	        conn.prepareStatement(
	            "INSERT INTO total_sales "
	          + "(market_basket_id, total_amount) "
	          + "VALUES (?, ?)");

	    pstmt.setInt(1, basketId);
	    pstmt.setDouble(2, total);

	    pstmt.executeUpdate();
	}
	
	private int getNextBasketItemId(
	        Connection conn)
	        throws SQLException {

	    Statement stmt =
	        conn.createStatement();

	    ResultSet rs =
	        stmt.executeQuery(
	            "SELECT COALESCE(MAX(basket_item_id),0)+1 "
	          + "AS next_id "
	          + "FROM market_basket");

	    rs.next();

	    return rs.getInt("next_id");
	}
	
	private int getNextSalesId(
	        Connection conn)
	        throws SQLException {

	    Statement stmt =
	        conn.createStatement();

	    ResultSet rs =
	        stmt.executeQuery(
	            "SELECT COALESCE(MAX(sales_id),0)+1 "
	          + "AS next_id "
	          + "FROM sales");

	    rs.next();

	    return rs.getInt("next_id");
	}
	
	@Override
	public void send(String msg) {
	    out.println(msg);
	    out.flush();
	}
}
