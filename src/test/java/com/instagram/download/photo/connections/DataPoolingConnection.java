package com.instagram.download.photo.connections;

import com.instagram.download.photo.configs.DataBaseConfig;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.aeonbits.owner.ConfigFactory;
import org.apache.log4j.Logger;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class DataPoolingConnection {
    private static final Logger LOGGER = Logger.getLogger(DataPoolingConnection.class);
    private static Connection instance;
    private static ComboPooledDataSource cpds = new ComboPooledDataSource();

    private DataPoolingConnection() {
    }

    public static Connection getInstance() throws SQLException {
        if (instance == null || instance.isClosed()) {
            try {
                LOGGER.info("Create pooling connection");
                DataBaseConfig config = ConfigFactory.create(DataBaseConfig.class);
                cpds.setDriverClass("org.mariadb.jdbc.Driver");
                cpds.setJdbcUrl(config.url());
                cpds.setUser(config.username());
                cpds.setPassword(config.password());
                cpds.setMinPoolSize(5);
                cpds.setAcquireIncrement(5);
                cpds.setMaxPoolSize(20);
                instance = cpds.getConnection();
            } catch (SQLException e) {
                LOGGER.error("Pooling connection hasn't created");
            } catch (PropertyVetoException e) {
                LOGGER.error(e.getMessage());
            }
        }
        return instance;
    }

    public static void openConnection() {
        try {
            DataPoolingConnection.getInstance();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void closeConnection() {
        if (instance == null) {
            try {
                LOGGER.debug("Close pooling connection");
                instance.close();
            } catch (SQLException e) {
                LOGGER.error("Pooling connection hasn't created");
            }
        }
    }
}
