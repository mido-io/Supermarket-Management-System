package com.supermarket.controller;

import com.supermarket.util.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private AnchorPane admin_form, employee_form, main_form;

    @FXML
    private TextField admin_username, employee_username;

    @FXML
    private PasswordField admin_password, employee_password;

    @FXML
    private Label lblDisplayError, lblDisplayError1, admin_hyperLink, employee_huperLink;

    @FXML
    private void close() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void switchForm() {
        admin_form.setVisible(!admin_form.isVisible());
        employee_form.setVisible(!employee_form.isVisible());
    }

    @FXML
    private void adminLogin() {
        String username = admin_username.getText();
        String password = admin_password.getText();

        if (username.isEmpty() || password.isEmpty()) {
            lblDisplayError.setText("Please fill in all fields.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM admin WHERE username = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                loadPage("/com/supermarket/fxml/admin/home-page.fxml", "Admin Dashboard");
            } else {
                lblDisplayError.setText("Invalid username or password.");
            }
        } catch (SQLException | IOException e) {
            lblDisplayError.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void employeeLogin() {
        String username = employee_username.getText();
        String password = employee_password.getText();

        if (username.isEmpty() || password.isEmpty()) {
            lblDisplayError1.setText("Please fill in all fields.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM employee WHERE employee_username = ? AND employee_password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                loadPage("/com/supermarket/fxml/employee/home-page.fxml", "Employee Dashboard");
            } else {
                lblDisplayError1.setText("Invalid username or password.");
            }
        } catch (SQLException | IOException e) {
            lblDisplayError1.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadPage(String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
        Stage currentStage = (Stage) main_form.getScene().getWindow();
        currentStage.close();
    }
}