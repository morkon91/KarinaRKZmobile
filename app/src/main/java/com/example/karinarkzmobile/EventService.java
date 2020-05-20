package com.example.karinarkzmobile;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.karinarkzmobile.data.AlarmData;
import com.example.karinarkzmobile.mainActivity.MainActivity;
import com.example.karinarkzmobile.mainActivity.alarmEventsFragment.IAlarmEvents;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.karinarkzmobile.App.NOTIFICATION_CHANNEL_ID;

public class EventService extends Service implements INewEventObserver{

    private final String LOG_TAG = "My logs";
    private IAlarmEvents.Repository repository = ServiceLocator.getRepository();

    Thread thread;
    Intent notificationIntent;
    PendingIntent pendingIntent;
    Intent cancelIntent;
    PendingIntent cancelPendingIntent;

    public EventService() {
        if (repository instanceof INewEventObserved){
            ((INewEventObserved) repository).addNewEventObserver(this);
        }
    }

    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate();
        notificationIntent = new Intent(this, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        cancelIntent = new Intent(this, EventService.class);
        cancelIntent.setAction("STOP_SERVICE");
        cancelPendingIntent = PendingIntent.getService(this, 0, cancelIntent, 0);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand" + this);
        if ("STOP_SERVICE".equals(intent.getAction())) {
            thread.interrupt();
            Log.d(LOG_TAG, "STOP_SERVICE");
        } else {
            Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setContentTitle("New alarm event")
                    .setContentText("Number of events: " + repository.loadEventCount())
                    .setTicker("setTicker")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentIntent(pendingIntent)
                    .setWhen(System.currentTimeMillis())
                    .build();

            startForeground(1, notification);
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (!Thread.currentThread().isInterrupted()) {
                            Log.d(LOG_TAG, "loadAlarmEvent");
                            repository.loadAlarmEventList();
                            TimeUnit.SECONDS.sleep(3);
                        }
                        Log.d(LOG_TAG, "STOP THREAD");
                        stopForeground(true);
                        stopSelf();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        stopForeground(true);
                        stopSelf();
                    }
                }
            });
            thread.start();
        }

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

    @Override
    public void handleEvent(List<AlarmData> updatedList, List<AlarmData> newEventList) {

        if (newEventList.size() > 0){
            Notification notification = new NotificationCompat.Builder(EventService.this, NOTIFICATION_CHANNEL_ID)
                    .setContentTitle("number of new events: " + newEventList.size())
                    .setContentText("Number of events: " + updatedList.size())
                    .setTicker("setTicker")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentIntent(pendingIntent)
                    .setWhen(System.currentTimeMillis())
                    .addAction(0, "Cancel service", cancelPendingIntent)
                    .build();

            NotificationManager manager = (NotificationManager) EventService.this.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(1, notification);


        }
    }
}
