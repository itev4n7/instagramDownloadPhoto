package com.instagram.download.photo.listeners;

import com.instagram.download.photo.annotations.TableName;
import com.instagram.download.photo.databases.MariaDB;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.lang.reflect.Method;

public class ConcurrentDataBaseListener implements IInvokedMethodListener {
    public static ThreadLocal<String> tableName = new ThreadLocal<>();

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        Method annotationPresent = method.getTestMethod().getConstructorOrMethod().getMethod();
        if (method.isTestMethod() && annotationPresent.isAnnotationPresent(TableName.class)) {
            tableName.set(annotationPresent.getAnnotation(TableName.class).value());
            MariaDB.initTable(tableName.get());
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        Method annotationPresent = method.getTestMethod().getConstructorOrMethod().getMethod();
        if (method.isTestMethod() && annotationPresent.isAnnotationPresent(TableName.class)) {
            MariaDB.dropTable(annotationPresent.getAnnotation(TableName.class).value());
        }
    }
}
