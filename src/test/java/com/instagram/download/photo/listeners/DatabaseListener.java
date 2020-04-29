package com.instagram.download.photo.listeners;

import com.instagram.download.photo.databases.MariaDB;
import org.testng.ITestContext;
import org.testng.ITestListener;

public class DatabaseListener implements ITestListener {
   // MariaDB db;

    @Override
    public void onStart(ITestContext arg0) {
       // db = new MariaDB();
      //  db.initTable();
    }

    @Override
    public void onFinish(ITestContext arg0) {
        //db.dropTable();
    }
}
