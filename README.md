# Product Management System

A Java JSP-based web application for managing products with CRUD operations.

## Setup Instructions

### 1. Database Setup

Create a MySQL database:

```sql
CREATE DATABASE productdb;
```

Update database credentials in `src/main/java/com/example/util/DatabaseUtil.java`

### 2. Build the Project

```bash
mvn clean package
```

### 3. Deploy to Tomcat

Copy the generated `target/product-crud.war` file to your Tomcat `webapps` directory.

### 4. Run the Application

Start Tomcat and navigate to:
```
http://localhost:8080/products
```

## Docker Deployment

The project includes Docker support:

```bash
docker-compose up -d
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/
│   │       ├── dao/          # Data Access Objects
│   │       ├── model/        # Entity models
│   │       ├── servlet/      # Servlet controllers
│   │       └── util/         # Utility classes
│   └── webapp/
│       ├── index.jsp         # Home page
│       ├── products.jsp      # Products management page
│       └── WEB-INF/
│           └── web.xml       # Web configuration
```

## API Endpoints

- `GET /products` - List all products
- `GET /products/{id}` - Get product by ID
- `POST /products` - Create new product
- `PUT /products/{id}` - Update product
- `DELETE /products/{id}` - Delete product

## License

MIT License


