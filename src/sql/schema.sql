CREATE DATABASE IF NOT EXISTS supermarket;
USE supermarket;

CREATE TABLE admin (
                       username VARCHAR(50) PRIMARY KEY,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE employee (
                          id VARCHAR(50) PRIMARY KEY,
                          employee_fullName VARCHAR(100) NOT NULL,
                          employee_username VARCHAR(50) UNIQUE NOT NULL,
                          employee_password VARCHAR(255) NOT NULL,
                          employee_salary DOUBLE NOT NULL,
                          employee_address VARCHAR(255),
                          employee_gender VARCHAR(10),
                          employee_data DATE
);

CREATE TABLE product (
                         product_id VARCHAR(50) PRIMARY KEY,
                         product_name VARCHAR(100) NOT NULL,
                         brand_name VARCHAR(100),
                         price DOUBLE NOT NULL,
                         status VARCHAR(20) NOT NULL
);

CREATE TABLE customer (
                          customer_id INT,
                          product_name VARCHAR(100),
                          brand_name VARCHAR(100),
                          quantity INT,
                          price DOUBLE,
                          date DATE
);

CREATE TABLE customer_receipt (
                                  customer_id INT PRIMARY KEY,
                                  total DOUBLE,
                                  date DATE
);