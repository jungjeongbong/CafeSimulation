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
	
	public void run() {
		String msg;
		try {
			String name = in.readLine();
			String[] tokens = name.split(":");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void send(String msg) {
		// TODO Auto-generated method stub
		System.out.println(msg);
		out.println(msg);
	}

	public void setOrderRoom(OrderRoom orderRoom) {
		// TODO Auto-generated method stub
		
	}
	
}
