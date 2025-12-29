package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String DB_HOST = System.getenv().getOrDefault("MYSQL_HOST", "localhost");
    private static final String DB_PORT = System.getenv().getOrDefault("MYSQL_PORT", "3306");
    private static final String DB_NAME = System.getenv().getOrDefault("MYSQL_DATABASE", "teacherdb");
    private static final String DB_USER = System.getenv().getOrDefault("MYSQL_USER", "root");
    private static final String DB_PASSWORD = System.getenv().getOrDefault("MYSQL_PASSWORD", "root");
    
    private static final String URL = String.format("jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", 
            DB_HOST, DB_PORT, DB_NAME);

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }
    }
}
