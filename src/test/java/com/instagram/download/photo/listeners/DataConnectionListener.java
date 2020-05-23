package com.instagram.download.photo.listeners;

import com.instagram.download.photo.connections.DataPoolingConnection;
import org.testng.IExecutionListener;

public class DataConnectionListener implements IExecutionListener {

    @Override
    public void onExecutionStart() {
        DataPoolingConnection.openConnection();
    }

    @Override
    public void onExecutionFinish() {
        DataPoolingConnection.closeConnection();
    }
}
