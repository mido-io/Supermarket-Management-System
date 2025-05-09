package com.supermarket.service;

import com.supermarket.model.Employee;
import com.supermarket.util.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmployeeService {

    public ObservableList<Employee> getAllEmployees() throws SQLException {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        String query = "SELECT * FROM employee";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getString("id"),
                        rs.getString("employee_fullName"),
                        rs.getString("employee_username"),
                        rs.getString("employee_password"),
                        rs.getDouble("employee_salary"),
                        rs.getString("employee_address"),
                        rs.getString("employee_gender"),
                        rs.getDate("employee_data") != null ? rs.getDate("employee_data").toLocalDate() : null
                ));
            }
        }
        return employees;
    }

    public void addEmployee(Employee employee) throws SQLException {
        String query = "INSERT INTO employee (id, employee_fullName, employee_username, employee_password, employee_salary, employee_address, employee_gender, employee_data) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employee.getId());
            stmt.setString(2, employee.getFullName());
            stmt.setString(3, employee.getUsername());
            stmt.setString(4, employee.getPassword());
            stmt.setDouble(5, employee.getSalary());
            stmt.setString(6, employee.getAddress());
            stmt.setString(7, employee.getGender());
            stmt.setDate(8, employee.getDate() != null ? java.sql.Date.valueOf(employee.getDate()) : null);
            stmt.executeUpdate();
        }
    }

    public void updateEmployee(Employee employee) throws SQLException {
        String query = "UPDATE employee SET employee_fullName = ?, employee_username = ?, employee_password = ?, employee_salary = ?, employee_address = ?, employee_gender = ?, employee_data = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employee.getFullName());
            stmt.setString(2, employee.getUsername());
            stmt.setString(3, employee.getPassword());
            stmt.setDouble(4, employee.getSalary());
            stmt.setString(5, employee.getAddress());
            stmt.setString(6, employee.getGender());
            stmt.setDate(7, employee.getDate() != null ? java.sql.Date.valueOf(employee.getDate()) : null);
            stmt.setString(8, employee.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteEmployee(String employeeId) throws SQLException {
        String query = "DELETE FROM employee WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employeeId);
            stmt.executeUpdate();
        }
    }
}