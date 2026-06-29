CREATE DATABASE IF NOT EXISTS laundry_service_db;
USE laundry_service_db;

CREATE TABLE IF NOT EXISTS Services (
    service_id INT AUTO_INCREMENT PRIMARY KEY,
    service_name VARCHAR(100) NOT NULL UNIQUE,
    fixed_price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS Customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS Orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT, 
    service_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    weight_kg DECIMAL(10, 2) NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    payment_status ENUM('Unpaid', 'Paid') DEFAULT 'Unpaid',
    order_status ENUM('Pending', 'Ready', 'Claimed') DEFAULT 'Pending',

    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id) ON DELETE SET NULL,
    FOREIGN KEY (service_id) REFERENCES Services(service_id) ON DELETE RESTRICT
);

ALTER TABLE Services 
ADD CONSTRAINT unique_service_name UNIQUE (service_name);

INSERT IGNORE INTO Services (service_name, fixed_price)
VALUES ('Full Service', 175.00);

ALTER TABLE Customers 
ADD COLUMN IF NOT EXISTS is_active TINYINT(1) DEFAULT 1;

ALTER TABLE Orders 
ADD COLUMN IF NOT EXISTS price_at_order DECIMAL(10, 2) NOT NULL AFTER weight_kg,
ADD COLUMN IF NOT EXISTS notes VARCHAR(255) DEFAULT NULL AFTER order_status;
