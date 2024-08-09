
CREATE DATABASE IF NOT EXISTS yumcourt;
USE yumcourt;

-- Table: Address
CREATE TABLE Address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    flatNo VARCHAR(50),
    buildingName VARCHAR(255),
    street VARCHAR(255),
    city VARCHAR(100),
    pinCode VARCHAR(20),
    state VARCHAR(100)
);

-- Table: Contact
CREATE TABLE Contact (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    phone BIGINT NOT NULL,
    address_id BIGINT,
    FOREIGN KEY (address_id) REFERENCES Address(id)
);

-- Table: Customer
CREATE TABLE Customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    contact_id BIGINT,
    FOREIGN KEY (contact_id) REFERENCES Contact(id)
);

-- Table: DeliveryExecutive
CREATE TABLE DeliveryExecutive (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone BIGINT NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    address_id BIGINT,
    FOREIGN KEY (address_id) REFERENCES Address(id)
);

-- Table: Restaurant
CREATE TABLE Restaurant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address_id BIGINT,
    contact_id BIGINT,
    FOREIGN KEY (address_id) REFERENCES Address(id),
    FOREIGN KEY (contact_id) REFERENCES Contact(id)
);

-- Table: Menu
CREATE TABLE Menu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    restaurant_id BIGINT,
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id)
);

-- Table: Kart
CREATE TABLE Kart (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

-- Table: Order
CREATE TABLE `Order` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    orderDate DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    customer_id BIGINT,
    delivery_executive_id BIGINT,
    restaurant_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES Customer(id),
    FOREIGN KEY (delivery_executive_id) REFERENCES DeliveryExecutive(id),
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id)
);

-- Table: OrderDetails
CREATE TABLE OrderDetails (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    menu_id BIGINT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES `Order`(id),
    FOREIGN KEY (menu_id) REFERENCES Menu(id)
);
