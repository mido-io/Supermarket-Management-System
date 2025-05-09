package com.supermarket.controller.admin;

import com.supermarket.model.Product;
import com.supermarket.service.ProductService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ProductController {

    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, String> productColID, productColName, productColBrand, productColStatus;
    @FXML
    private TableColumn<Product, Double> productColPrice;
    @FXML
    private TextField txtProductID, txtproudctname, txtBrandname, txtProductPrice, txtSearch;
    @FXML
    private ComboBox<String> txtStatus;

    private ProductService productService = new ProductService();

    @FXML
    private void initialize() {
        productColID.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProductId()));
        productColName.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProductName()));
        productColBrand.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getBrandName()));
        productColPrice.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        productColStatus.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus()));

        txtStatus.setItems(FXCollections.observableArrayList("Available", "Not Available"));
        loadProducts();
    }

    @FXML
    private void openHomePage() throws IOException {
        loadPage("/com/supermarket/fxml/admin/home-page.fxml", "Admin Dashboard");
    }

    @FXML
    private void openEmployeePage() throws IOException {
        loadPage("/com/supermarket/fxml/admin/employee-page.fxml", "Employee Management");
    }

    @FXML
    private void ADD() {
        try {
            Product product = new Product(
                    txtProductID.getText(),
                    txtproudctname.getText(),
                    txtBrandname.getText(),
                    Double.parseDouble(txtProductPrice.getText()),
                    txtStatus.getValue()
            );
            productService.addProduct(product);
            loadProducts();
            Clear();
        } catch (SQLException | NumberFormatException e) {
            showAlert("Error", "Failed to add product: " + e.getMessage());
        }
    }

    @FXML
    private void Update() {
        try {
            Product product = new Product(
                    txtProductID.getText(),
                    txtproudctname.getText(),
                    txtBrandname.getText(),
                    Double.parseDouble(txtProductPrice.getText()),
                    txtStatus.getValue()
            );
            productService.updateProduct(product);
            loadProducts();
            Clear();
        } catch (SQLException | NumberFormatException e) {
            showAlert("Error", "Failed to update product: " + e.getMessage());
        }
    }

    @FXML
    private void delete() {
        try {
            productService.deleteProduct(txtProductID.getText());
            loadProducts();
            Clear();
        } catch (SQLException e) {
            showAlert("Error", "Failed to delete product: " + e.getMessage());
        }
    }

    @FXML
    private void Clear() {
        txtProductID.clear();
        txtproudctname.clear();
        txtBrandname.clear();
        txtProductPrice.clear();
        txtStatus.setValue(null);
    }

    @FXML
    private void addProductSelect() {
        Product selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtProductID.setText(selected.getProductId());
            txtproudctname.setText(selected.getProductName());
            txtBrandname.setText(selected.getBrandName());
            txtProductPrice.setText(String.valueOf(selected.getPrice()));
            txtStatus.setValue(selected.getStatus());
        }
    }

    private void loadProducts() {
        try {
            tableView.setItems(productService.getAllProducts());
        } catch (SQLException e) {
            showAlert("Error", "Failed to load products: " + e.getMessage());
        }
    }

    private void loadPage(String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
        Stage currentStage = (Stage) tableView.getScene().getWindow();
        currentStage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}