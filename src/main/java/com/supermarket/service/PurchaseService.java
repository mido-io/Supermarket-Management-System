package com.supermarket.service;

import com.supermarket.model.CustomerPurchase;
import com.supermarket.util.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PurchaseService {

    public ObservableList<CustomerPurchase> getPurchasesByCustomerId(int customerId) throws SQLException {
        ObservableList<CustomerPurchase> purchases = FXCollections.observableArrayList();
        String query = "SELECT * FROM customer WHERE customer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                purchases.add(new CustomerPurchase(
                        rs.getInt("customer_id"),
                        rs.getString("product_name"),
                        rs.getString("brand_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getDate("date") != null ? rs.getDate("date").toLocalDate() : null
                ));
            }
        }
        return purchases;
    }

    public void addPurchase(CustomerPurchase purchase) throws SQLException {
        String query = "INSERT INTO customer (customer_id, product_name, brand_name, quantity, price, date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, purchase.getCustomerId());
            stmt.setString(2, purchase.getProductName());
            stmt.setString(3, purchase.getBrandName());
            stmt.setInt(4, purchase.getQuantity());
            stmt.setDouble(5, purchase.getPrice());
            stmt.setDate(6, purchase.getDate() != null ? java.sql.Date.valueOf(purchase.getDate()) : null);
            stmt.executeUpdate();
        }
    }

    public void saveReceipt(int customerId, double total, LocalDate date) throws SQLException {
        String query = "INSERT INTO customer_receipt (customer_id, total, date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            stmt.setDouble(2, total);
            stmt.setDate(3, java.sql.Date.valueOf(date));
            stmt.executeUpdate();
        }
    }

    public int generateCustomerId() throws SQLException {
        String query = "SELECT MAX(customer_id) AS max_id FROM customer_receipt";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("max_id") + 1;
            }
            return 1;
        }
    }
}