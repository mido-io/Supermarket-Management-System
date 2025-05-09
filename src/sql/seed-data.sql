USE supermarket;

INSERT INTO admin (username, password) VALUES
    ('MideoHubAdmin', 'MideoHub');

INSERT INTO employee (id, employee_fullName, employee_username, employee_password, employee_salary, employee_address, employee_gender, employee_data) VALUES
    ('E001', 'Abdelehmid Farhat', 'MideoHubEmp', 'MideoHub', 3000.00, '123 St', 'Male', '2023-01-01');

INSERT INTO product (product_id, product_name, brand_name, price, status) VALUES
                                                                              ('P001', 'Milk', 'DairyCo', 2.50, 'Available'),
                                                                              ('P002', 'Bread', 'BakeryCo', 1.50, 'Available');