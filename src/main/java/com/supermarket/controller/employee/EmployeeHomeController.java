package com.supermarket.controller.employee;

import com.supermarket.model.CustomerPurchase;
import com.supermarket.model.Product;
import com.supermarket.service.ProductService;
import com.supermarket.service.PurchaseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmployeeHomeController {

    @FXML
    private TableView<CustomerPurchase> purchase_tableView;
    @FXML
    private TableColumn<CustomerPurchase, String> purchase_col_product, purchase_col_brand;
    @FXML
    private TableColumn<CustomerPurchase, Integer> purchase_col_quantity;
    @FXML
    private TableColumn<CustomerPurchase, Double> purchase_col_price;
    @FXML
    private TextField purchase_brand;
    @FXML
    private ComboBox<String> purchase_productName;
    @FXML
    private Spinner<Integer> purchase_Quantity;
    @FXML
    private Label purchase_total;
    @FXML
    private Button purchase_AddBtn, purchase_PayBtn, purchase_RecipitBtn, purchase_ResetBtn1, btnCalculateChange;

    private ProductService productService = new ProductService();
    private PurchaseService purchaseService = new PurchaseService();
    private ObservableList<CustomerPurchase> purchases = FXCollections.observableArrayList();
    private int customerId;
    private double total = 0.0;

    @FXML
    private void initialize() {
        purchase_col_product.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProductName()));
        purchase_col_brand.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getBrandName()));
        purchase_col_quantity.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        purchase_col_price.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        purchase_tableView.setItems(purchases);
        purchase_Quantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        loadProducts();
        try {
            customerId = purchaseService.generateCustomerId();
        } catch (SQLException e) {
            showAlert("Error", "Failed to generate customer ID: " + e.getMessage());
        }
    }

    @FXML
    private void close() {
        Stage stage = (Stage) purchase_tableView.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void purchaseSearchBrand() {
        String brand = purchase_brand.getText();
        try {
            ObservableList<String> productNames = FXCollections.observableArrayList();
            for (Product product : productService.getAllProducts()) {
                if (product.getBrandName().equalsIgnoreCase(brand)) {
                    productNames.add(product.getProductName());
                }
            }
            purchase_productName.setItems(productNames);
        } catch (SQLException e) {
            showAlert("Error", "Failed to search products: " + e.getMessage());
        }
    }

    @FXML
    private void purchaseAdd() {
        String productName = purchase_productName.getValue();
        String brand = purchase_brand.getText();
        int quantity = purchase_Quantity.getValue();

        try {
            for (Product product : productService.getAllProducts()) {
                if (product.getProductName().equals(productName) && product.getBrandName().equals(brand)) {
                    double price = product.getPrice() * quantity;
                    CustomerPurchase purchase = new CustomerPurchase(customerId, productName, brand, quantity, price, LocalDate.now());
                    purchases.add(purchase);
                    total += price;
                    purchaseService.addPurchase(purchase);
                    purchase_total.setText(String.format("%.2f $", total));
                    break;
                }
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to add purchase: " + e.getMessage());
        }
    }

    @FXML
    private void purchasePay() {
        try {
            purchaseService.saveReceipt(customerId, total, LocalDate.now());
            showAlert("Success", "Payment processed successfully.");
            purchaseReset();
        } catch (SQLException e) {
            showAlert("Error", "Failed to process payment: " + e.getMessage());
        }
    }

    @FXML
    private void showReceipt() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/supermarket/fxml/employee/receipt.fxml"));
        Parent root = loader.load();
        ReceiptController controller = loader.getController();
        controller.setPurchaseData(purchases, total, customerId);
        Stage stage = new Stage();
        stage.setTitle("Receipt");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void purchaseReset() {
        purchases.clear();
        total = 0.0;
        purchase_total.setText("0.00 $");
        purchase_brand.clear();
        purchase_productName.setValue(null);
        purchase_Quantity.getValueFactory().setValue(1);
        try {
            customerId = purchaseService.generateCustomerId();
        } catch (SQLException e) {
            showAlert("Error", "Failed to reset: " + e.getMessage());
        }
    }

    @FXML
    private void openChangeCalcolator() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/supermarket/fxml/employee/calculator-change.fxml"));
        Parent root = loader.load();
        CalculatorChangeController controller = loader.getController();
        controller.setTotal(total);
        Stage stage = new Stage();
        stage.setTitle("Change Calculator");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void loadProducts() {
        try {
            ObservableList<String> productNames = FXCollections.observableArrayList();
            for (Product product : productService.getAllProducts()) {
                productNames.add(product.getProductName());
            }
            purchase_productName.setItems(productNames);
        } catch (SQLException e) {
            showAlert("Error", "Failed to load products: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}