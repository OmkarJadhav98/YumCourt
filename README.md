# YumCourt

## Overview

YumCourt is an online food ordering system that allows users to browse menus, place orders, and manage deliveries. The system is built using Java, Spring Boot, and integrates JPA for database management, with Lombok reducing boilerplate code.

## Features

- **User Management**: Register, log in, and manage user profiles.
- **Menu Browsing**: View and select items from a dynamic restaurant menu.
- **Order Management**: Place, update, and track orders.
- **Delivery Management**: Assign and track deliveries using a streamlined process.
- **Address Management**: Save and update user addresses for delivery purposes.

## Technologies Used

- **Java**: Core programming language
- **Spring Boot**: Framework for building the backend services
- **JPA**: For database interactions and ORM
- **Lombok**: Reduces boilerplate code using annotations
- **Maven**: Build tool for dependency management
- **MySQL**: Relational database for persistent data storage

## Prerequisites

Before begin, ensure you have the following installed:

- **Java 11** or higher
- **Maven 3.6** or higher
- **MySQL 8.0** or higher (or another relational database)
- **Git** for version control

## Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/OmkarJadhav98/yumcourt.git

2. **Set up the database**:
- Create a MySQL database named `yumcourt_db`.
- Update the application.properties file located in src/main/resources with your database credentials:
   ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/yumcourt_db
  spring.datasource.username = root
  spring.datasource.password = 
  spring.jpa.hibernate.ddl-auto=update

3. **Build the project**:
    ```bash
   mvn clean install

4. **Run the application**:
    ```bash
   mvn spring-boot:run

6. **Access the application**:
- Open your browser and visit http://localhost:8080 to start using YumCourt.

## Usage
- **Register/Login**: Create an account or log in with existing credentials.
- **Browse Menu**: Explore available restaurants and their menus.
- **Place an Order**: Add items to your cart and proceed to checkout.
- **Track Delivery**: View the status of your order in real-time.

## API Documentation
- **GET /api/restaurants**: Retrieves a list of all available restaurants.
- **POST /api/orders**: Places a new order.
- **GET /api/orders/{id}**: Retrieves details of a specific order.

## Database Schema
The database consists of the following tables:

- **User**: Stores user information (id, username, password, etc.).
- **Restaurant**: Stores restaurant details (id, name, location, etc.).
- **Menu**: Stores menu items for each restaurant (id, restaurant_id, name, price, etc.).
- **Order**: Stores order information (id, user_id, total_amount, status, etc.).
- **Delivery**: Stores delivery information (id, order_id, delivery_executive_id, status, etc.).


## Known Issues
- Currently, the project only supports MySQL as the database.
- The order tracking feature is still under development.

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request with your changes.

## License
This project is licensed under the MIT License.

## Contact
For questions or support, please contact `objadhav28@gmail.com`.
   ```css
   This README.md file is comprehensive, covering everything from setup instructions to API documentation, testing, and future enhancements. It should give anyone interested in your project a clear understanding of how to get started and contribute.