package server.service;

import server.repository.CustomerRepository;
import server.controller.ConnectionController;
import server.domain.Customer;

public class CheckAlreadyInDB {
	public boolean availableNumber(Customer customer, ConnectionController session, int msg) {
		String newPhoneNumber = customer.getPhoneNumber();
		
		//여기에 입력한 newPhoneNumber과 같은 전화번호가 Custosmer 테이블에 있는지 조회하는 sql 작성
		
		return false;
	}
}
