package com.example.bookhub.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    private static final String URL = "jdbc:mariadb://localhost:3306/bookHub";
    private static final String USER = "root";
    private static final String PASSWORD = "123";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}