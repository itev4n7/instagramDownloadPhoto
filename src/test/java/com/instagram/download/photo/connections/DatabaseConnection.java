package com.instagram.download.photo.connections;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class);
    private final Connection connection;

    public DatabaseConnection(String url, String user, String password) {
        try {
            LOGGER.info("Create connection");
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            LOGGER.error("Connection didn`t create");
            throw new RuntimeException("Connection didn`t create " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
