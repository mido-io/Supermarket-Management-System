package com.supermarket.controller.admin;

import com.supermarket.model.Employee;
import com.supermarket.service.EmployeeService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmployeeController {

    @FXML
    private TableView<Employee> tableView;
    @FXML
    private TableColumn<Employee, String> EmployeColID, EmployeColName, EmployeColAdress, EmployeColGender, EmployeColDataentery;
    @FXML
    private TableColumn<Employee, Double> employee_salary;
    @FXML
    private TextField txtID, txtName, txtUsername, txtPassword, txtSalary, txtAddress;
    @FXML
    private ComboBox<String> txtGender;
    @FXML
    private DatePicker txtDate;

    private EmployeeService employeeService = new EmployeeService();

    @FXML
    private void initialize() {
        EmployeColID.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getId()));
        EmployeColName.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFullName()));
        employee_salary.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getSalary()).asObject());
        EmployeColAdress.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAddress()));
        EmployeColGender.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getGender()));
        EmployeColDataentery.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDate() != null ? cellData.getValue().getDate().toString() : ""));

        txtGender.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
        loadEmployees();
    }

    @FXML
    private void openHomePage() throws IOException {
        loadPage("/com/supermarket/fxml/admin/home-page.fxml", "Admin Dashboard");
    }

    @FXML
    private void openProductPage() throws IOException {
        loadPage("/com/supermarket/fxml/admin/product-page.fxml", "Product Management");
    }

    @FXML
    private void addEmployee() {
        try {
            Employee employee = new Employee(
                    txtID.getText(),
                    txtName.getText(),
                    txtUsername.getText(),
                    txtPassword.getText(),
                    Double.parseDouble(txtSalary.getText()),
                    txtAddress.getText(),
                    txtGender.getValue(),
                    txtDate.getValue()
            );
            employeeService.addEmployee(employee);
            loadEmployees();
            Clear();
        } catch (SQLException | NumberFormatException e) {
            showAlert("Error", "Failed to add employee: " + e.getMessage());
        }
    }

    @FXML
    private void Update() {
        try {
            Employee employee = new Employee(
                    txtID.getText(),
                    txtName.getText(),
                    txtUsername.getText(),
                    txtPassword.getText(),
                    Double.parseDouble(txtSalary.getText()),
                    txtAddress.getText(),
                    txtGender.getValue(),
                    txtDate.getValue()
            );
            employeeService.updateEmployee(employee);
            loadEmployees();
            Clear();
        } catch (SQLException | NumberFormatException e) {
            showAlert("Error", "Failed to update employee: " + e.getMessage());
        }
    }

    @FXML
    private void delete() {
        try {
            employeeService.deleteEmployee(txtID.getText());
            loadEmployees();
            Clear();
        } catch (SQLException e) {
            showAlert("Error", "Failed to delete employee: " + e.getMessage());
        }
    }

    @FXML
    private void Clear() {
        txtID.clear();
        txtName.clear();
        txtUsername.clear();
        txtPassword.clear();
        txtSalary.clear();
        txtAddress.clear();
        txtGender.setValue(null);
        txtDate.setValue(null);
    }

    @FXML
    private void addProductSelect() {
        Employee selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtID.setText(selected.getId());
            txtName.setText(selected.getFullName());
            txtUsername.setText(selected.getUsername());
            txtPassword.setText(selected.getPassword());
            txtSalary.setText(String.valueOf(selected.getSalary()));
            txtAddress.setText(selected.getAddress());
            txtGender.setValue(selected.getGender());
            txtDate.setValue(selected.getDate());
        }
    }

    private void loadEmployees() {
        try {
            tableView.setItems(employeeService.getAllEmployees());
        } catch (SQLException e) {
            showAlert("Error", "Failed to load employees: " + e.getMessage());
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