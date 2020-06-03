package com.example.karinarkzmobile;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;

import com.facebook.drawee.backends.pipeline.Fresco;

public class App extends Application {

    public static final String NOTIFICATION_HIDE_CHANNEL_ID = "hide";
    public static final String NOTIFICATION_DEFAULT_CHANNEL_ID = "default";
    private static SharedPreferences sharedPreferences;

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceLocator.init();

        createNotificationChannel();
//        startService(new Intent(this, EventService.class));
        Fresco.initialize(this);

        sharedPreferences = getSharedPreferences("app_settings", MODE_PRIVATE);
        instance = this;
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
                    NotificationManager.IMPORTANCE_HIGH); // обычный звук
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(chan2);
        }
    }

    public static App getInstance() {
        return instance;
    }

    public void stopEventService(){
        Intent cancelIntent = new Intent(this, EventService.class);
//        cancelIntent.setAction("STOP_SERVICE");
        stopService(cancelIntent);
    }

    public void startEventService(){
        startService(new Intent(this, EventService.class));
    }


}
