package com.example.karinarkzmobile;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;

import com.facebook.drawee.backends.pipeline.Fresco;

public class App extends Application {

    public static final String NOTIFICATION_HIDE_CHANNEL_ID = "hide";
    public static final String NOTIFICATION_DEFAULT_CHANNEL_ID = "default";

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceLocator.init();
        startService(new Intent(this, EventService.class));

        createNotificationChannel();

        Fresco.initialize(this);

    }

    private void createNotificationChannel() {

        NotificationChannel chan1 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            chan1 = new NotificationChannel(NOTIFICATION_HIDE_CHANNEL_ID, NOTIFICATION_HIDE_CHANNEL_ID,
                    NotificationManager.IMPORTANCE_LOW); // без звука
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(chan1);
        }

        NotificationChannel chan2 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            chan2 = new NotificationChannel(NOTIFICATION_DEFAULT_CHANNEL_ID, NOTIFICATION_DEFAULT_CHANNEL_ID,
                    NotificationManager.IMPORTANCE_DEFAULT); // обычный звук
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(chan2);
        }
    }
}
