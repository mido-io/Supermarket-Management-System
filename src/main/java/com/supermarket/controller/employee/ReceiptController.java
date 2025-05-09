package com.supermarket.controller.employee;

import com.supermarket.model.CustomerPurchase;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReceiptController {

    @FXML
    private Label dateLabel, timeLabel, cashierLabel, totalLabel;
    @FXML
    private VBox productList;

    @FXML
    private void closeReceipt() {
        Stage stage = (Stage) dateLabel.getScene().getWindow();
        stage.close();
    }

    public void setPurchaseData(ObservableList<CustomerPurchase> purchases, double total, int customerId) {
        dateLabel.setText(LocalDate.now().toString());
        timeLabel.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        cashierLabel.setText("Employee #" + customerId);
        totalLabel.setText(String.format("%.2f $", total));

        productList.getChildren().clear();
        for (CustomerPurchase purchase : purchases) {
            Label item = new Label(String.format("%s (%s) x%d - %.2f $",
                    purchase.getProductName(), purchase.getBrandName(), purchase.getQuantity(), purchase.getPrice()));
            item.setStyle("-fx-font-size: 12;");
            productList.getChildren().add(item);
        }
    }
}