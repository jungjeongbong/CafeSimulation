package client;

import server.controller.Basket;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import client.controller.OrderController;

public class Client {
	private Basket basket;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private OrderController orderController;
	
	public void send(String msg) {
		out.println(msg);
		out.flush();
	}
	
	public void listen() {
		Thread listen = new Thread(() -> {
			String msg;
			try {
				while((msg = in.readLine())!=null) {
					System.out.println("수신: " + msg);
					orderController.processMessage(msg);
				}
			} catch(IOException e) {

			} finally {
				disconnect();
			}
		});
		listen.start();
	}

	public void connect(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			InputStreamReader input = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
			in = new BufferedReader(input);
			OutputStreamWriter output = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
			out = new PrintWriter(output, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void disconnect() {
		// TODO Auto-generated method stub
		try {
			socket.close();
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setOrderController(OrderController orderController) {
	    this.orderController = orderController;
	}
}
