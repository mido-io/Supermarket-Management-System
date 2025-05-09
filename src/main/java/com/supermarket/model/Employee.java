package com.supermarket.model;

import java.time.LocalDate;

public class Employee {
    private String id;
    private String fullName;
    private String username;
    private String password;
    private double salary;
    private String address;
    private String gender;
    private LocalDate date;

    public Employee(String id, String fullName, String username, String password, double salary, String address, String gender, LocalDate date) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.salary = salary;
        this.address = address;
        this.gender = gender;
        this.date = date;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}