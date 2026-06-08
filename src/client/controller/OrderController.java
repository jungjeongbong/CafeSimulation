package client.controller;

import server.controller.Basket;
import server.domain.Customer;
import server.repository.DBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.Client;
import client.ClientTest;
import server.service.*;
import server.controller.ConnectionController;

public class OrderController {
	private Client client;
	private Basket basket;
	
	public void accessOrder(String ip, String name) {
		ClientTest.startOrder(ip);
		System.out.print("주문하려면 전화번호로 로그인하세요: ");

		client.send("name: "+ name +"\norder: " );
		
	}

	public void exitOrder() {
		System.out.println("주문을 중단하고 연결을 종료합니다.");
		client.send("disconnect");
	}
	
	public void endConnect() {
		client.disconnect();
		System.exit(0);
	}
	
	public void processMessage(String msg) {
		// TODO Auto-generated method stub
		if (msg.startsWith("disconnect")) endConnect();
	}
	
	public void giveDrinks(Customer customer) {
		client.send(customer.getName() + "님 주문하신 음료 나왔습니다.");
	}
	
	public void setClient(Client client) {
		// TODO Auto-generated method stub
		this.client = client;
	}
}
