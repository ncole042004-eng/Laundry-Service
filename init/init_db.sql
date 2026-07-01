-- Clean DATABASE & TABLE Creation, Delete previous DATABASE and initialize with the following script

DROP DATABASE IF EXISTS laundry_service_db;

CREATE DATABASE IF NOT EXISTS laundry_service_db;
USE laundry_service_db;

CREATE TABLE IF NOT EXISTS Employees (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Services (
    service_id INT AUTO_INCREMENT PRIMARY KEY,
    service_name VARCHAR(100) NOT NULL UNIQUE,
    fixed_price DECIMAL(19, 4) NOT NULL
);

CREATE TABLE IF NOT EXISTS Customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,
    is_active TINYINT(1) DEFAULT 1,
    address VARCHAR(255) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    claim_number VARCHAR(20) UNIQUE DEFAULT NULL,
    customer_id INT DEFAULT NULL,
    employee_id INT DEFAULT NULL,
    service_id INT DEFAULT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ready_at TIMESTAMP NULL DEFAULT NULL,
    claimed_at TIMESTAMP NULL DEFAULT NULL,
    cancelled_at TIMESTAMP NULL DEFAULT NULL,
    weight_kg DECIMAL(10, 2) NOT NULL,
    price_at_order DECIMAL(19, 4) NOT NULL,
    total_amount DECIMAL(19, 4) NOT NULL,
    payment_status ENUM('Unpaid', 'Paid') DEFAULT 'Unpaid',
    order_status ENUM('Pending', 'Processing', 'Ready', 'Claimed', 'Cancelled') DEFAULT 'Pending',
    notes VARCHAR(255) DEFAULT NULL,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES Customers(customer_id) ON DELETE SET NULL,
    CONSTRAINT fk_service FOREIGN KEY (service_id) REFERENCES Services(service_id) ON DELETE RESTRICT,
    CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES Employees(employee_id) ON DELETE SET NULL
);

INSERT IGNORE INTO Services (service_name, fixed_price)
VALUES ('Full Service', 175.0000);