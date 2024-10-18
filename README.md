# 🚗 Parking Management System

## 📜 Overview
The **Parking Management System** is a web application developed using Spring Boot, which utilizes Thymeleaf for templating and connects to a MySQL database for data storage. This application provides functionality for users to register, log in, buy parking tickets, and view their purchased tickets. An admin user can manage parking lots and view user data.

## 📚 Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Running the Application](#running-the-application)
- [Usage](#usage)
- [Application Structure](#application-structure)
- [License](#license)

## ✨ Features
- User registration and login
- Buy parking tickets
- View purchased tickets
- Admin dashboard for user and parking management
- Ticket history with restore functionality
- Dynamic pricing based on selected duration

## 💻 Technologies
- **Backend:** Spring Boot
- **Database:** MySQL
- **Templating:** Thymeleaf
- **Frontend:** Bootstrap
- **Build Tool:** Maven
- **Containerization:** Docker

## 🚀 Getting Started

### 📋 Prerequisites
- Docker installed on your machine
- Docker Compose installed

### ⚙️ Running the Application
1. Clone the repository:
   ```bash
   git clone https://github.com/muhamed1ism/springboot-parking-management
   cd your-repo-directory
   ```

2. Build and run the application using Docker Compose:
   ```bash
   docker-compose up --build
   ```

3. Access the application at `http://localhost:8080` in your web browser.

### ⚙️ Application Configuration
The MySQL database is set up through Docker, with the following environment variables defined in `docker-compose.yml`:
- `MYSQL_ROOT_PASSWORD`: The root password for MySQL.
- `MYSQL_DATABASE`: The database name to be created (`parking`).

## 📝 Usage
1. **Registration:**
   - Users can register by providing their first name, last name, password, password repeat, and phone number.
   - Upon successful registration, users are redirected to a success page with a button to log in.

2. **Login:**
   - Registered users log in to access the home page, which displays a welcome message and a button to buy a ticket.

3. **Buying Tickets:**
   - Users can select a parking spot based on available parking options.
   - They need to provide car name, license plate, duration, and duration unit.
   - After purchasing a ticket, users can view their tickets on the "My Tickets" page.

4. **Admin Features:**
   - Admin users can view a list of all registered users and their data.
   - Admins can manage parking lots, including adding, editing, or deleting parking spots.
   - The admin dashboard displays a list of tickets sold, including actions to delete or restore tickets.

## 📁 Application Structure
The application follows a structured format with the following components:

- **Models:** `Parking`, `Price`, `Spot`, `SpotHistory`, `User`, `UserDetails`
- **Repositories:** Data access layer for models.
- **Controllers:** Handles incoming requests and responses.
- **Services:** Business logic layer.
- **Configs:** Configuration settings.

## 🐳 Dockerfile
The application is built using a multi-stage Dockerfile:
```dockerfile
# Build Stage
FROM maven:3.8.7-openjdk-18 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Runtime Stage
FROM openjdk:18-jdk-slim
COPY --from=build /home/app/target/Parking-0.0.1-SNAPSHOT.jar /usr/local/lib/parking.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/parking.jar"]
```

## 🐋 Docker Compose Configuration
The application and MySQL database are defined in the `docker-compose.yml`:
```yaml
version: '3.7'
services:
  parking:
    container_name: parking
    build: .
    image: parking:latest
    ports:
      - 8080:8080
    networks:
      - parking-network
    depends_on:
      - mysql
  mysql:
    image: mysql:latest
    command: --character-set-server=utf8 --collation-server=utf8_general_ci
    ports:
      - 3306:3306
    networks:
      - parking-network
    environment:
      - MYSQL_ROOT_PASSWORD=lozinka
      - MYSQL_DATABASE=parking
    volumes:
      - mysql-data:/var/lib/mysql
    restart: always
volumes:
  mysql-data:
networks:
  parking-network:
    driver: bridge
```

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
