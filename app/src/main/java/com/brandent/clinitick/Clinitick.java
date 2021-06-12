package com.brandent.clinitick;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class Clinitick extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getAppContext() {
        return Clinitick.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Clinitick.context = getApplicationContext();
    }
}
