package com.supermarket.service;

import com.supermarket.model.Product;
import com.supermarket.util.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductService {

    public ObservableList<Product> getAllProducts() throws SQLException {
        ObservableList<Product> products = FXCollections.observableArrayList();
        String query = "SELECT * FROM product";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(new Product(
                        rs.getString("product_id"),
                        rs.getString("product_name"),
                        rs.getString("brand_name"),
                        rs.getDouble("price"),
                        rs.getString("status")
                ));
            }
        }
        return products;
    }

    public void addProduct(Product product) throws SQLException {
        String query = "INSERT INTO product (product_id, product_name, brand_name, price, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, product.getProductId());
            stmt.setString(2, product.getProductName());
            stmt.setString(3, product.getBrandName());
            stmt.setDouble(4, product.getPrice());
            stmt.setString(5, product.getStatus());
            stmt.executeUpdate();
        }
    }

    public void updateProduct(Product product) throws SQLException {
        String query = "UPDATE product SET product_name = ?, brand_name = ?, price = ?, status = ? WHERE product_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getBrandName());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getStatus());
            stmt.setString(5, product.getProductId());
            stmt.executeUpdate();
        }
    }

    public void deleteProduct(String productId) throws SQLException {
        String query = "DELETE FROM product WHERE product_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, productId);
            stmt.executeUpdate();
        }
    }
}