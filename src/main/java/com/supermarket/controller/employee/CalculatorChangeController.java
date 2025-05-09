package com.supermarket.controller.employee;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CalculatorChangeController {

    @FXML
    private Label lblPriceRequired, lblChange;
    @FXML
    private TextField txtPricePaid;

    private double total;

    public void setTotal(double total) {
        this.total = total;
        lblPriceRequired.setText(String.format("%.2f $", total));
    }

    @FXML
    private void initialize() {
        txtPricePaid.textProperty().addListener((observable, oldValue, newValue) -> calculateChange());
    }

    @FXML
    private void esc() {
        Stage stage = (Stage) lblPriceRequired.getScene().getWindow();
        stage.close();
    }

    private void calculateChange() {
        try {
            double paid = Double.parseDouble(txtPricePaid.getText());
            double change = paid - total;
            lblChange.setText(String.format("%.2f $", change >= 0 ? change : 0));
        } catch (NumberFormatException e) {
            lblChange.setText("0.00 $");
        }
    }
}