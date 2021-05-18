package com.pixium.clinitick;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import java.util.Calendar;

public class NotificationStatic {
    public static String intentTitleKey = "com.pixium.clinitick.notification.title";
    public static String intentTextKey = "com.pixium.clinitick.notification.title";
    public static String intentIdKey = "com.pixium.clinitick.notification.title";

    private static String lastTitle;
    private static String lastText;
    private static int lastId;

    public static void createNotificationChannel(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "DentistReminderChannel";
            String description = "Channel for clinitick reminder";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("notifyDentist", name
                    , importance);
            channel.setDescription(description);

            NotificationManager notificationManager =
                    context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static String getLastTitle() {
        return lastTitle;
    }

    public static void setLastTitle(String title) {
        NotificationStatic.lastTitle = title;
    }

    public static String getLastText() {
        return lastText;
    }

    public static void setLastText(String text) {
        NotificationStatic.lastText = text;
    }

    public static int getLastId() {
        return lastId;
    }

    public static void setLastId(int lastId) {
        NotificationStatic.lastId = lastId;
    }

    public static void setAlarm(Context context, Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }
}
