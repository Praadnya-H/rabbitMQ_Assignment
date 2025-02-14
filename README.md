# Order Processing System with RabbitMQ

A robust order processing system built with Spring Boot and RabbitMQ, implementing asynchronous communication between services for efficient order management.

## Features

### Core Functionality
- Asynchronous order processing using message queuing
- Product inventory management
- Real-time stock updates
- Order validation and verification
- Transactional order processing
- Swagger UI integration for API documentation

### Technical Implementation
- Spring Boot backend
- RabbitMQ message broker
- MySQL database
- OpenAPI (Swagger) documentation
- JPA/Hibernate ORM
- Input validation using Bean Validation

## System Architecture

### Components
1. **Order Service (Producer)**
   - Receives order requests via REST API
   - Validates input data
   - Publishes validated orders to RabbitMQ
   - Provides immediate response to clients

2. **Product Service**
   - Manages product inventory
   - Validates product existence
   - Checks product price
   - Monitors stock levels
   - Updates inventory after successful orders

3. **Order Processing Service (Consumer)**
   - Consumes messages from RabbitMQ queue
   - Performs business validations
   - Updates product inventory
   - Creates order records
   - Handles processing failures

### Message Flow
1. Client submits order via REST API
2. Order Service validates basic input (quantity > 0, price > 0, etc.)
3. Valid orders are published to RabbitMQ exchange
4. Exchange routes message to order queue using routing key
5. Consumer picks up message and processes order
6. Product inventory is checked and updated
7. Order status is updated in database

## Database Schema

### Products Table
- ID (Primary Key)
- Name (Unique)
- Price
- Available Quantity

*Product table is pre-populated with 5 products*
```sql
INSERT INTO products (name, price, available_quantity) VALUES 
('iPhone 15 Pro', 999.99, 50),
('Samsung Galaxy S24', 899.99, 75),
('MacBook Pro M3', 1499.99, 30),
('Sony PS5', 499.99, 100),
('Xbox Series X', 479.99, 85);
```

### Orders Table
- ID (Primary Key)
- Order Number (UUID)
- Product Name
- Quantity
- Price
- Status
- Created At

## API Endpoints

### Order API
- `POST /api/orders` - Create new order
  - Validates input data
  - Publishes to RabbitMQ
  - Returns immediate acknowledgment

- POSTMAN request JSON payload:
  ` {
    "productName": "iPhone 15 Pro",
    "quantity": 3,
    "price": 999.99
    }`

## Validation Rules

### Input Validation
- Product name cannot be empty
- Quantity must be positive
- Price must be greater than zero

### Business Validation
- Product must exist in inventory
- Requested quantity must be available
- Price must match product's listed price

## RabbitMQ Configuration

### Exchange and Queue Setup
- Exchange Type: Topic
- Queue: Orders Queue
- Routing Key: order.create
- Message Persistence: Enabled
- Consumer Acknowledgment: Automatic

## Error Handling

### Validation Errors
- Invalid input data returns 400 Bad Request
- Detailed error messages for validation failures
- Logging of validation errors

### Processing Errors
- Failed orders are logged
- Transactional processing ensures data consistency
- Automatic retry for failed message processing

## Security Considerations
- Input validation to prevent injection attacks
- Transactional integrity for database operations
- Error handling without exposing internal details

## Monitoring
- RabbitMQ management console
- Application logs with different severity levels
- Consumer health checks
- Queue monitoring

## Getting Started

### Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- RabbitMQ 3.9 or higher
- Maven 3.6 or higher

### Configuration
1. Database configuration in application.properties
2. RabbitMQ connection settings
3. Server port and application name

### Running the Application
1. Start MySQL server
2. Start RabbitMQ server
3. Run Spring Boot application
4. Access Swagger UI at: http://localhost:8080/swagger-ui.html

