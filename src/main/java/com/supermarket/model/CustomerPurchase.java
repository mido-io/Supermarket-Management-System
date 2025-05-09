package com.supermarket.model;

import java.time.LocalDate;

public class CustomerPurchase {
    private int customerId;
    private String productName;
    private String brandName;
    private int quantity;
    private double price;
    private LocalDate date;

    public CustomerPurchase(int customerId, String productName, String brandName, int quantity, double price, LocalDate date) {
        this.customerId = customerId;
        this.productName = productName;
        this.brandName = brandName;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    // Getters and Setters
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}