package com.pixium.clinitick;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "notifyDentist")
                        .setContentTitle(intent.getStringExtra(NotificationStatic.intentTitleKey))
                        .setContentText(intent.getStringExtra(NotificationStatic.intentTextKey))
                        .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(intent.getIntExtra(NotificationStatic.intentIdKey, 200)
                , builder.build());
    }
}
