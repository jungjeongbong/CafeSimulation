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
    
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null ||
            getClass() != obj.getClass())
            return false;

        Product other = (Product) obj;

        return productID == other.productID;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(productID);
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