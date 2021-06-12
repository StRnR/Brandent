package com.brandent.clinitick;

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
                        .setSmallIcon(R.drawable.ic_appicon)
                        .setContentTitle(NotificationStatic.getLastTitle())
                        .setContentText(NotificationStatic.getLastText())
                        .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(NotificationStatic.getLastId(), builder.build());
    }
}
