# DataSource Aggregator Service

## Overview

This project is a DataSource Aggregator service built with Spring Boot and Spring JDBC. It interacts with multiple data sources, providing filtering, sorting,  features for efficient data retrieval.

## Features

- **Multiple Data Sources:** Connects to multiple data sources using Spring JDBC.

- **Filtering:** Supports filtering by various parameters such as username, name, and surname.

- **Sorting:** Enables sorting of data by different fields like id, username, name, and surname.

- **Swagger Documentation:** Swagger is integrated to provide detailed API documentation.

## Technologies Used

- Spring Boot
- Spring JDBC
- Java 17
- Swagger
- Docker

## Getting Started

### Prerequisites

- Java 17
- Maven
- Docker

### Build and Run

```bash
# Clone the repository
git clone https://github.com/k0sN/DatasourceAgregatorService.git

# Navigate to the project directory
cd DatasourceAgregatorService

# Build the project
mvn clean install

# Run Docker Compose to set up databases
docker-compose up -d

# Run the application
mvn spring-boot:run
```

## Configuration

- Configure data sources in the `application.yml file.

```yml
- name: postgres-data-base 
  strategy: postgres  
  url: jdbc:postgresql://localhost:5432/database  
  table: customers  
  user: root  
  password: root  
  mapping:  
    id: id  
    username: login  
    name: name  
    surname: surname  
  
- name: mysql-data-base  
  strategy: mysql  
  url: jdbc:mysql://localhost:3306/database  
  table: users 
  user: user  
  password: password  
  mapping:  
    id: user_id  
    username: user_login  
    name: user_name  
    surname: user_surname
```

## Usage

- Access the service through RESTful APIs to retrieve data based on your requirements.
- Explore the Swagger documentation for detailed API specifications.
## API Endpoints

### Retrieve All Users

```http
GET /users
```

Retrieve all users with optional sorting

#### Query Parameters

- `sortBy`: Field by which the result will be sorted (id, username, name, surname).
- `sortOrder`: Sort order (asc, desc).

### Retrieve Users with Filters

```http
GET /users/filter
```

Retrieve users by selecting filters with optional sorting and pagination.

#### Query Parameters

- `username`: Username of the user to be searched.
- `name`: Name of the user to be searched.
- `surname`: Surname of the user to be searched.
- `sortBy`: Field by which the result will be sorted (id, username, name, surname).
- `sortOrder`: Sort order (asc, desc).

## Swagger Documentation

Explore the detailed API documentation using Swagger at http://localhost:8080/swagger

Visit http://localhost:8080/api-docs in your browser to explore the API documentation.
