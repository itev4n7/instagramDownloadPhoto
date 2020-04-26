package com.instagram.download.photo.selenide.databases;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.sql.*;

public class MariaDB {
    private static final Logger logger = Logger.getLogger(MariaDB.class);
    private final String url;
    private final String user;
    private final String password;

    public MariaDB(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void writeBlob(int id, InputStream inputStream) {
        logger.info("try write blod to database");
        String insertSQL = "insert into savedPhotos VALUES(?,?);";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setInt(1, id);
            pstmt.setBinaryStream(2, inputStream);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public int getRows() {
        logger.info("get rows from database");
        int rows = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement stmt = connection.createStatement()) {
            logger.debug("try to -> select count(*) from savedPhotos");
            ResultSet resultSet = stmt.executeQuery("select count(*) from savedPhotos;");
            resultSet.next();
            rows = resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return rows;
    }

    public void clearTable() {
        logger.info("clear table in database");
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement stmt = connection.createStatement()) {
            logger.debug("try to -> delete from savedPhotos");
            stmt.execute("delete from savedPhotos;");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
