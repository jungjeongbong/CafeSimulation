package server.domain;

import server.MessageSender;
import server.controller.ConnectionController;
import server.repository.CustomerRepository;

public class Customer {
	private int customerID;
	private String name;
	private String phoneNumber;
	private String city;
	private int age;
	
	public Customer() {
		
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setMessageSender(ConnectionController connectionController) {
		// TODO Auto-generated method stub
		
	}
	
}
