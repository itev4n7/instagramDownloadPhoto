package com.instagram.download.photo.databases;

import com.instagram.download.photo.connections.DataPoolingConnection;
import com.instagram.download.photo.listeners.ConcurrentNameListener;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MariaDB {
    private static final Logger LOGGER = Logger.getLogger(MariaDB.class);

    private MariaDB() {
    }

    public static void writeBlob(int id, InputStream inputStream) {
        LOGGER.info("Try write blob to database");
        String insertSQL = "insert into " + ConcurrentNameListener.tableName.get() + " VALUES(?,?);";
        try (PreparedStatement pstmt = DataPoolingConnection.getInstance().prepareStatement(insertSQL)) {
            pstmt.setInt(1, id);
            pstmt.setBinaryStream(2, inputStream);
            if (pstmt.executeUpdate() == 0) {
                LOGGER.debug("Blob didn't write");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void saveAllBlobs() {
        for (int id = 1; id <= getRows(); id++) {
            saveBlob(id);
        }
    }

    public static void saveBlob(int id) {
        LOGGER.info("Save blob");
        String selectSQL = String.format("select photo from %s where id = %d;", ConcurrentNameListener.tableName.get(), id);
        try (PreparedStatement pstmt = DataPoolingConnection.getInstance().prepareStatement(selectSQL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                InputStream input = rs.getBinaryStream("photo");
                downloadPhoto(input, id);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static synchronized void downloadPhoto(InputStream inputStream, int id) {
        if (inputStream != null) {
            String path = ClassLoader.getSystemResource(".").getPath() + String.format("/photo%d.png", id);
            try (OutputStream fileOutputStream = new FileOutputStream(path)) {
                IOUtils.copy(inputStream, fileOutputStream);
                inputStream.close();
            } catch (IOException e) {
                LOGGER.error("File didn't write");
            }
        }
    }

    public static int getRows() {
        LOGGER.info("Get rows from database");
        int rows = 0;
        try (Statement stmt = DataPoolingConnection.getInstance().createStatement()) {
            ResultSet resultSet = stmt.executeQuery("select count(*) from " + ConcurrentNameListener.tableName.get() + ";");
            resultSet.next();
            rows = resultSet.getInt(1);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return rows;
    }

    public static void initTable(String tableName) {
        LOGGER.info("Create new table " + tableName);
        try (Statement stmt = DataPoolingConnection.getInstance().createStatement()) {
            stmt.execute("create table " + tableName + "(" +
                      "id INT NOT NULL," +
                      " photo LONGBLOB NOT NULL," +
                      " PRIMARY KEY ( id ));");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void dropTable(String tableName) {
        LOGGER.info("Drop table " + tableName);
        try (Statement stmt = DataPoolingConnection.getInstance().createStatement()) {
            stmt.execute("drop table " + tableName + ";");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
