package com.instagram.download.photo.listeners;

import com.codeborne.selenide.Configuration;
import com.instagram.download.photo.connections.DatabaseConnection;
import org.testng.IExecutionListener;

public class DatabaseListener implements IExecutionListener {

    @Override
    public void onExecutionStart() {
        Configuration.startMaximized = true;
        DatabaseConnection.openConnection();
        //MariaDB.initTable(); //commented out for parallel test
    }

    @Override
    public void onExecutionFinish() {
        //MariaDB.dropTable(); //commented out for use blob-to-html maven plugin & parallel test
        DatabaseConnection.closeConnection();
    }
}
