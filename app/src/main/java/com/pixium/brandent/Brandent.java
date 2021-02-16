package com.pixium.brandent;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class Brandent extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Brandent.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Brandent.context;
    }
}
