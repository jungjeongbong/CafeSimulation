package server.controller;

public class Product {

    private int productID;
    private String productName;
    private String category;
    private int unitPrice;

    public Product() {}

    public Product(int productID, String productName, String category, int unitPrice) {
        this.productID = productID;
        this.productName = productName;
        this.category = category;
        this.unitPrice = unitPrice;
    }

    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public int getUnitPrice() {
        return unitPrice;
    }
}