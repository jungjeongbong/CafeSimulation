package server.service;

import server.controller.Basket;
import server.controller.ConnectionController;
import server.controller.Store;

public class CheckRemainService {
	public boolean availableNumber(Basket basket, Store store,  ConnectionController session, int msg) {
		int basketProductNumber = basket.getProductNumber;
		int remainNumber = store.getRemainNumber;
		
		if (basketProductNumber > remainNumber) {
			return false;
		}
		else return true;
	}
}
