package server;

import java.util.*;

import server.controller.ConnectionController;
import server.domain.Customer;

public class OrderRoom {
	private Customer customer;
	private List<String> menus = new ArrayList<>();
	private List<Integer> numberOfMenu = new ArrayList<>();
	
	public int waitingTime = 10;
	
	public OrderRoom() {};
	
	public void sendOrder(String msg, ConnectionController c) {
		msg = customer.getName()+"님의 주문목록";
		c.send(msg);
		for (int i=0; i<menus.size(); i++) {
			msg = "\nmenu: " + menus.get(i) + "\n수량: " + numberOfMenu.get(i);
			c.send(msg);
		}
	}

}
