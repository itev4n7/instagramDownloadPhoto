package com.instagram.download.photo.listeners;

import com.instagram.download.photo.customdrivers.SelenoidDriver;
import org.testng.IExecutionListener;

public class SelenoidSetUpListener implements IExecutionListener {

    @Override
    public void onExecutionStart() {
        SelenoidDriver.setUp();
    }

}
