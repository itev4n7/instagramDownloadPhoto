package com.instagram.download.photo.listeners;

import com.instagram.download.photo.databases.MariaDB;
import org.testng.ITestContext;
import org.testng.ITestListener;

public class DatabaseListener implements ITestListener {

    @Override
    public void onStart(ITestContext arg0) {
        MariaDB.initTable();
    }

    @Override
    public void onFinish(ITestContext arg0) {
        MariaDB.dropTable();
    }
}
