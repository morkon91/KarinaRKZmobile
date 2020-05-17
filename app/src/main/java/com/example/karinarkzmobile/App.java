package com.example.karinarkzmobile;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;

public class App extends Application {

    public static final String NOTIFICATION_CHANNEL_ID = "serviceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceLocator.init();
        startService(new Intent(this, EventService.class));

        createNotificationChannel();

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "Service channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
