package client;

import client.controller.OrderController;

public class ClientTest {

	private static OrderController orderController;
	
	public static void startOrder(String ip) {
		Client client = new Client();
		client.connect(ip, 3306);
		client.listen();
		
		client.setOrderController(orderController);
		orderController.setClient(client);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		orderController = new OrderController();
	}

}
