package server.controller;

import server.Server;
import server.MessageSender;
import server.OrderRoom;
import server.domain.Customer;
import server.repository.CustomerRepository;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

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

	            System.out.println("수신 : " + msg);

	            if(msg.startsWith("ORDER:")) {

	                System.out.println(
	                    "주문 수신 : " + msg);
	            }
	        }

	    } catch(IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void send(String msg) {
	    out.println(msg);
	    out.flush();
	}
}
