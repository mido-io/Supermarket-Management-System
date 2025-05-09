package com.supermarket.model;

public class Product {
    private String productId;
    private String productName;
    private String brandName;
    private double price;
    private String status;

    public Product(String productId, String productName, String brandName, double price, String status) {
        this.productId = productId;
        this.productName = productName;
        this.brandName = brandName;
        this.price = price;
        this.status = status;
    }

    // Getters and Setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}