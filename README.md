# Teacher Management System

A Spring Boot web application for managing teachers with CRUD operations using JSP views.

## Features

- Create, Read, Update, and Delete teachers
- RESTful API endpoints
- JSP-based user interface
- MySQL database integration
- Spring Data JPA for data access

## Setup Instructions

### 1. Database Setup

Create a MySQL database:

```sql
CREATE DATABASE teacherdb;
```

Database configuration is managed through environment variables or `application.properties`.

### 2. Build the Project

```bash
mvn clean package
```

### 3. Run the Application

#### Using Maven:
```bash
mvn spring-boot:run
```

#### Using the JAR file:
```bash
java -jar target/teacher-crud.jar
```

The application will be available at:
```
http://localhost:8080
```

## Docker Deployment

The project includes Docker support with MySQL database:

```bash
# Build and start containers
docker-compose up -d --build

# View logs
docker-compose logs -f

# Stop containers
docker-compose down

# Stop and remove volumes (clean slate)
docker-compose down -v
```

The application will be available at:
- Application: http://localhost:8080
- MySQL: localhost:3306

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/
│   │       ├── controller/     # Spring MVC controllers
│   │       ├── model/         # JPA entity models
│   │       ├── repository/    # Spring Data JPA repositories
│   │       └── TeacherManagementApplication.java
│   ├── resources/
│   │   └── application.properties  # Spring Boot configuration
│   └── webapp/
│       └── WEB-INF/
│           └── views/         # JSP view files
│               ├── index.jsp
│               └── teachers.jsp
```

## API Endpoints

- `GET /teachers` - List all teachers (renders JSP view)
- `GET /teachers/{id}` - Get teacher by ID (returns JSON)
- `POST /teachers` - Create new teacher (form-urlencoded)
- `PUT /teachers/{id}` - Update teacher (form-urlencoded)
- `DELETE /teachers/{id}` - Delete teacher

## Configuration

The application uses Spring Boot's configuration system. Database settings can be configured via:

1. Environment variables:
   - `MYSQL_HOST` (default: localhost)
   - `MYSQL_PORT` (default: 3306)
   - `MYSQL_DATABASE` (default: teacherdb)
   - `MYSQL_USER` (default: root)
   - `MYSQL_PASSWORD` (default: root)

2. `application.properties` file in `src/main/resources/`

## Technology Stack

- Spring Boot 3.2.0
- Spring Data JPA
- Spring MVC
- MySQL
- JSP/JSTL
- Maven

## License

MIT License
