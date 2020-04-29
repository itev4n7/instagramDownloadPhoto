package com.instagram.download.photo.databases;

import com.instagram.download.photo.connections.DatabaseConnection;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;

public class MariaDB {
    private static final Logger LOGGER = Logger.getLogger(MariaDB.class);

    private MariaDB() {
    }

    public static void writeBlob(int id, InputStream inputStream) {
        LOGGER.info("Try write blob to database");
        String insertSQL = "insert into savedPhotos VALUES(?,?);";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setInt(1, id);
            pstmt.setBinaryStream(2, inputStream);
            if (pstmt.executeUpdate() == 0) {
                LOGGER.debug("Blob didn't write");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void saveAllBlobs() {
        for (int id = 1; id <= getRows(); id++) {
            saveBlob(id);
        }
    }

    public static void saveBlob(int id) {
        LOGGER.info("Save blob");
        String selectSQL = "select photo from savedPhotos where id=?;";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL);
             ResultSet rs = pstmt.executeQuery()) {
            pstmt.setInt(1, id);
            while (rs.next()) {
                InputStream input = rs.getBinaryStream("photo");
                downloadPhoto(input, id);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    private static void downloadPhoto(InputStream inputStream, int id) {
        if (inputStream != null) {
            try (OutputStream fileOutputStream = new FileOutputStream(ClassLoader.getSystemResource(".").getPath()
                      + String.format("/photo%d.png", id))) {
                IOUtils.copy(inputStream, fileOutputStream);
                inputStream.close();
            } catch (IOException e) {
                LOGGER.error("File didn't write");
                throw new RuntimeException("File didn't write " + e.getMessage());
            }
        }
    }

    public static int getRows() {
        LOGGER.info("Get rows from database");
        int rows = 0;
        try (Connection conn = DatabaseConnection.getInstance();
             Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery("select count(*) from savedPhotos;");
            resultSet.next();
            rows = resultSet.getInt(1);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return rows;
    }

    public static void initTable() {
        LOGGER.info("Create new table");
        try (Connection conn = DatabaseConnection.getInstance();
             Statement stmt = conn.createStatement()) {
            stmt.execute("create table savedPhotos(id INT NOT NULL, photo LONGBLOB NOT NULL, PRIMARY KEY ( id ));");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void dropTable() {
        LOGGER.info("Drop table");
        try (Connection conn = DatabaseConnection.getInstance();
             Statement stmt = conn.createStatement()) {
            stmt.execute("drop table savedPhotos;");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
