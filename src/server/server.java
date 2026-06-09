package server;

import server.controller.ConnectionController;
import server.repository.CustomerRepository;
import server.service.*;
import server.repository.MarketProductRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{
	private ServerSocket serverSocket;
	private final List<OrderRoom> orderRooms = new ArrayList<>();
	private AnalizeService analizeService;
	private CheckAlreadyInDB checkAlreadyInService;
	private MenuService menuService;
	private CustomerRepository customerRepository;
	private MarketProductRepository marketProductRepository;
	
	public Server() {
		this.customerRepository = new CustomerRepository();
		this.marketProductRepository = new MarketProductRepository();
		this.checkAlreadyInService = new CheckAlreadyInDB();
		this.menuService = new MenuService();
		this.analizeService = new AnalizeService();
	}
	
	public void run() {
		makeOrderRoom();
		
		try {
			serverSocket = new ServerSocket(50002);
		
			while(true) {
				System.out.println("주문 서버 연결 대기중");
				Socket socket = serverSocket.accept();
				System.out.println(socket.getInetAddress().getHostAddress()+"와(과) 연결되었습니다");
				addCustomer(socket);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addCustomer(Socket socket) {
		// TODO Auto-generated method stub
		OrderRoom orderRoom = orderRooms.get(0);
		
		ConnectionController handler = new ConnectionController(socket, this);
		handler.setOrderRoom(orderRoom);
		handler.start();
	}

	private void makeOrderRoom() {
		// TODO Auto-generated method stub
		OrderRoom newOrderRoom = new OrderRoom();
		this.orderRooms.add(newOrderRoom);
	}
}
