package server.controller;

import java.util.ArrayList;

public class Basket {
	
	private ArrayList<Product> products = new ArrayList<Product>();
	private ArrayList<Integer> product_number = new ArrayList<Integer>();
	ArrayList<Integer> product_price = new ArrayList<Integer>();
	
	public void putProductIn(Product pro) {
		int productIndex = getProducts().indexOf(pro);
		
		if (getProducts().contains(pro)) {
			getProduct_number().set(productIndex, getProduct_number().get(productIndex)+1);
		}
		else {
			getProducts().add(pro);
			getProduct_number().add(1);
			product_price.add(pro.getUnitPrice());
		}
	}
	
	public void getProductOut(Product pro) {
		int productIndex = getProducts().indexOf(pro);
		
		if (getProduct_number().get(productIndex) == 1) {
			getProducts().remove(pro);
			getProduct_number().remove(productIndex);
			product_price.remove(productIndex);
		}
		else {
			getProduct_number().set(productIndex, getProduct_number().get(productIndex)-1);
		}
	}

	public int getTotalPrice() {
		int totalPrice = 0;
		int productPrice;
		for(int i=0; i<getProducts().size(); i++) {
			productPrice = getProduct_number().get(i)*product_price.get(i);
			totalPrice += productPrice;
		}
		
		return totalPrice;
	}
	
	public void printBasket() {

	    System.out.println("===== 장바구니 =====");

	    for(int i = 0; i < products.size(); i++) {

	        System.out.println(
	            products.get(i).getProductName()
	            + " x "
	            + product_number.get(i));
	    }

	    System.out.println(
	        "총 금액 : "
	        + getTotalPrice()
	        + "원");
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public ArrayList<Integer> getProduct_number() {
		return product_number;
	}

	public void setProduct_number(ArrayList<Integer> product_number) {
		this.product_number = product_number;
	}
}
