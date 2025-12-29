# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy WAR file from build stage
COPY --from=build /app/target/teacher-crud.war app.war

# Expose Spring Boot port
EXPOSE 8080

# Start Spring Boot application
ENTRYPOINT ["java", "-jar", "app.war"]
