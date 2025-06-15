# Read Me First
# Employee Task API

## Overview
This is a Spring Boot application for managing employees and their tasks. The API provides RESTful endpoints for performing CRUD operations on employee and task resources.

## Features
- Employee management (create, read, update, delete)
- Task assignment and management
- RESTful API design
- Data persistence with JPA/Hibernate
- Containerized deployment with Docker

## Technologies
- Java 17
- Spring Boot
- Spring Data JPA
- Maven
- Docker
- H2 Database (configurable for other databases)

## Getting Started

### Prerequisites
- JDK 17 or higher
- Maven 3.6+
- Docker (optional, for containerized deployment)

### Running Locally
1. Clone the repository:
   git clone https://github.com/yourusername/EmpAnitha

2. Build the application:
   mvn clean package

3. Run the application:
   java -jar target/employee-task-api-0.0.1-SNAPSHOT.jar

4. Access the API at http://localhost:8080

### Running with Docker
1. Build the Docker image from Repo root path:
   docker build -f employee-task-api\dockerfile -t employee-task-api .

2. Run the container:
   docker run -p 8080:8080 employee-task-api

## API Endpoints

### Employee Endpoints
- `GET /api/employees` - Get all employees
- `GET /api/employees/{id}` - Get employee by ID
- `POST /api/employees` - Create a new employee
- `PUT /api/employees/{id}` - Update an employee
- `DELETE /api/employees/{id}` - Delete an employee

### Task Endpoints
- `GET /api/tasks` - Get all tasks
- `GET /api/tasks/{id}` - Get task by ID
- `POST /api/tasks` - Create a new task
- `PUT /api/tasks/{id}` - Update a task
- `DELETE /api/tasks/{id}` - Delete a task

## Testing
1. Use SampleAuthTokenGenerator
   curl --location --request POST 'http://localhost:8080/auth/token?username=rootuser' \
  Execute this curl to get a sample bearer toke to access employee task api.
2. Create Employee
   curl --location 'http://localhost:8080/employees?=null' \
   --header 'Authorization: Bearer "token from step 1" \
   --header 'Content-Type: application/json' \
   --data-raw '{
   "name": "John Doe2",
   "email": "john2@example.com",
   "department": "Engineering"
   }'
3. Get Tasks with Optional Status filter
    curl --location 'http://localhost:8080/employees/1/tasks' \
   --header 'Authorization: Bearer "token from step 1" \
4. Get Task Statistics
   curl --location 'http://localhost:8080/employees/stats' \
   --header 'Authorization: Bearer "token from step 1" \
5. Assign Task to an employee
   curl --location 'http://localhost:8080/task/assign/1' \
   --header 'Authorization: Bearer "token from step 1" \
   --header 'Content-Type: application/json' \
   --data '{
   "title": "Task17",
   "description": "Description 15",
   "status": "PENDING"
   }'
6. Update the task status
   curl --location --request PATCH 'http://localhost:8080/task/1/status?status=COMPLETED' \
   --header 'Authorization: Bearer "token from step 1" \

