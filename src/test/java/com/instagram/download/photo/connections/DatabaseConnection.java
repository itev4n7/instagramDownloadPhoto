package com.instagram.download.photo.connections;

import com.instagram.download.photo.configs.DatabaseConfig;
import org.aeonbits.owner.ConfigFactory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class);
    private static Connection instance;

    private DatabaseConnection() {
    }

    public static synchronized Connection getInstance() throws SQLException {
        if (instance == null || instance.isClosed()) {
            try {
                LOGGER.info("Create connection");
                DatabaseConfig config = ConfigFactory.create(DatabaseConfig.class);
                instance = DriverManager.getConnection(config.url(), config.username(), config.password());
            } catch (SQLException e) {
                LOGGER.error("Connection didn`t create");
                throw new RuntimeException("Connection didn`t create " + e.getMessage());
            }
        }
        return instance;
    }
}
