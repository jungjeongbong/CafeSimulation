package server.controller;

import java.util.ArrayList;

public class Basket {
	
	ArrayList<Product> products = new ArrayList<Product>();
	ArrayList<Integer> product_number = new ArrayList<Integer>();
	ArrayList<Integer> product_price = new ArrayList<Integer>();
	
	public void putProductIn(Product pro) {
		int productIndex = products.indexOf(pro);
		
		if (products.contains(pro)) {
			product_number.set(productIndex, product_number.get(productIndex)+1);
		}
		else {
			products.add(pro);
			product_number.add(1);
			product_price.add(pro.unitPrice);
		}
	}
	
	public void getProductOut(Product pro) {
		int productIndex = products.indexOf(pro);
		
		if (product_number.get(productIndex) == 1) {
			products.remove(pro);
			product_number.remove(productIndex);
			product_price.remove(productIndex);
		}
		else {
			product_number.set(productIndex, product_number.get(productIndex)-1);
		}
	}

	public int getTotalPrice() {
		int totalPrice = 0;
		int productPrice;
		for(int i=0; i<products.size(); i++) {
			productPrice = product_number.get(i)*product_price.get(i);
			totalPrice += productPrice;
		}
		
		return totalPrice;
	}
}
