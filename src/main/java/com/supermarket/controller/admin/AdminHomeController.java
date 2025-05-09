package com.supermarket.controller.admin;

import com.supermarket.util.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AdminHomeController {

    @FXML
    private Label totalIncoumcount, todaySalles;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private void initialize() {
        updateDashboard();
        populateChart();
    }

    @FXML
    private void openProductPage() throws IOException {
        loadPage("/com/supermarket/fxml/admin/product-page.fxml", "Product Management");
    }

    @FXML
    private void openEmployeePage() throws IOException {
        loadPage("/com/supermarket/fxml/admin/employee-page.fxml", "Employee Management");
    }

    @FXML
    private void logout() {
        Stage stage = (Stage) totalIncoumcount.getScene().getWindow();
        stage.close();
    }

    private void updateDashboard() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Total Income
            String incomeQuery = "SELECT SUM(total) AS total_income FROM customer_receipt WHERE date = ?";
            PreparedStatement incomeStmt = conn.prepareStatement(incomeQuery);
            incomeStmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            ResultSet incomeRs = incomeStmt.executeQuery();
            if (incomeRs.next()) {
                totalIncoumcount.setText(String.format("%.2f$", incomeRs.getDouble("total_income")));
            }

            // Total Sales
            String salesQuery = "SELECT COUNT(*) AS total_sales FROM customer_receipt WHERE date = ?";
            PreparedStatement salesStmt = conn.prepareStatement(salesQuery);
            salesStmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            ResultSet salesRs = salesStmt.executeQuery();
            if (salesRs.next()) {
                todaySalles.setText(String.valueOf(salesRs.getInt("total_sales")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Daily Income");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT date, SUM(total) AS total FROM customer_receipt GROUP BY date ORDER BY date LIMIT 7")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(rs.getDate("date").toString(), rs.getDouble("total")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        lineChart.getData().add(series);
    }

    private void loadPage(String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
        Stage currentStage = (Stage) totalIncoumcount.getScene().getWindow();
        currentStage.close();
    }
}