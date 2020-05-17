package com.example.karinarkzmobile;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.karinarkzmobile.mainActivity.MainActivity;
import com.example.karinarkzmobile.mainActivity.alarmEventsFragment.IAlarmEvents;

import java.util.concurrent.TimeUnit;

import static com.example.karinarkzmobile.App.NOTIFICATION_CHANNEL_ID;

public class EventService extends Service {

    private final String LOG_TAG = "My logs";
    private IAlarmEvents.Repository repository = ServiceLocator.getRepository();

    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                for (int i = 1; i <= 5; i++) {
                    Log.d(LOG_TAG, "i = " + i);
                    repository.loadAlarmEventList();
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf();
            }
        }).start();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("New alarm event")
                .setContentText("Number of events: " + repository.loadEventCount())
                .setTicker("setTicker")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .build();

        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
