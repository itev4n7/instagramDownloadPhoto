package com.instagram.download.photo.listeners;

import com.instagram.download.photo.connections.DatabaseConnection;
import com.instagram.download.photo.databases.MariaDB;
import org.testng.IExecutionListener;

public class DatabaseListener implements IExecutionListener {

    @Override
    public void onExecutionStart() {
        DatabaseConnection.openConnection();
        MariaDB.initTable();
    }

    @Override
    public void onExecutionFinish() {
        //MariaDB.dropTable(); //commented out for use blob-to-html maven plugin
        DatabaseConnection.closeConnection();
    }
}
