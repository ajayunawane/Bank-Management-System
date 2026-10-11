
# Bank Management System

A web-based Bank Management System developed using Java, Spring Boot, Hibernate, MySQL, HTML, CSS, and JavaScript.

## Features

- Customer Registration and Management
- Create and View Bank Accounts
- Search Customers by Name
- Deposit Money
- Withdraw Money
- Transfer Money
- View Transaction History
- Dashboard for Banking Operations
- Responsive Web Interface

## Technologies Used

- **Backend:** Java, Spring Boot
- **ORM:** Hibernate / Spring Data JPA
- **Database:** MySQL
- **Frontend:** HTML, CSS, JavaScript
- **Build Tool:** Maven
- **IDE:** IntelliJ IDEA
- **Version Control:** Git and GitHub

## Prerequisites

Install the following before running the project:

- Java JDK
- MySQL Server
- IntelliJ IDEA or another Java IDE
- Maven (or use the included Maven Wrapper)

## How to Run

1. Clone or download this repository.
2. Create a MySQL database named `bank_management`.
3. Configure your local database username, password, and JDBC URL in `application.properties`.
4. Open the project in IntelliJ IDEA.
5. Wait for Maven to download the required dependencies.
6. Run `BankManagementApplication.java`.
7. Open your browser and visit:

   http://localhost:8080/

## Database Configuration

Create the database in MySQL:

    CREATE DATABASE bank_management;

Configure your local `application.properties` with your MySQL connection details. Do not commit passwords or other sensitive credentials to GitHub.

## Project Structure

    src/main/java/com/
    ├── Controller/
    ├── Entity/
    ├── Repository/
    ├── Service/
    └── BankManagementApplication.java

    src/main/resources/static/
    ├── HTML pages
    ├── JavaScript files
    └── style.css

## Future Enhancements

- User authentication and authorization
- Role-based access control
- Improved input validation and error handling
- Automated testing

## Author

Ajay Unawane

---

This project was developed for learning and demonstrating full-stack Java development concepts.
