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
import com.example.karinarkzmobile.mainActivity.alarmEventsFragment.IAlarmEvents;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.karinarkzmobile.App.NOTIFICATION_DEFAULT_CHANNEL_ID;
import static com.example.karinarkzmobile.App.NOTIFICATION_HIDE_CHANNEL_ID;

public class EventService extends Service implements INewEventObserver {

    private final String LOG_TAG = "myLogs";
    private IAlarmEvents.Repository repository = ServiceLocator.getRepository();

    private static Thread thread;
    private Intent notificationIntent;
    private PendingIntent pendingIntent;
    private Intent cancelIntent;
    private PendingIntent cancelPendingIntent;
    private int newEventList;
    private int newAlarmDataListCount;
    private boolean isServiceWork;

    public static Thread getThread() {
        return thread;
    }

    public EventService() {
        if (repository instanceof INewEventObserved) {
            ((INewEventObserved) repository).addNewEventObserver(this);
        }
    }

    @Override
    public void onCreate() {
        isServiceWork = true;
        Log.d(LOG_TAG, "onCreate");
        super.onCreate();
        notificationIntent = new Intent(this, SplashActivity.class);
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
            Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_HIDE_CHANNEL_ID)
                    .setContentTitle("Karina.R.KZ.mobile starting")
                    .setContentText("Receiving data from the server...")
                    .setTicker("setTicker")
                    .setSmallIcon(R.drawable.ic_warning_black_24dp)
                    .setContentIntent(pendingIntent)
                    .setWhen(System.currentTimeMillis())
                    .build();

            startForeground(1, notification);
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (!Thread.currentThread().isInterrupted()) {
                            Log.d(LOG_TAG, "loadAlarmEvent in Service");
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
        if (!thread.isInterrupted()) {
            thread.interrupt();
            Log.d(LOG_TAG, "THREAD Interrupted");
        }
        isServiceWork = false;
        Log.d(LOG_TAG, "onDestroy");

        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void handleEvent(List<AlarmData> updatedList, int newEventsCount) {

        String message = "";
        if (newEventsCount > 0) {
            message = getString(R.string.new_alarm_notification);
        } else {
            message = getString(R.string.no_alarm_notification);
        }

        if ((this.newAlarmDataListCount != updatedList.size() && isServiceWork) || (newEventsCount > 0 && isServiceWork)) {
            if ((newEventsCount == this.newEventList) || newEventsCount == 0){
                showNotification(updatedList.size(), message, NOTIFICATION_HIDE_CHANNEL_ID);
            }else{
                showNotification(updatedList.size(), message, NOTIFICATION_DEFAULT_CHANNEL_ID);
            }
        } else {
            showNotification(updatedList.size(), message, NOTIFICATION_HIDE_CHANNEL_ID);
        }
        this.newEventList = newEventsCount;
        this.newAlarmDataListCount = updatedList.size();
    }


    @Override
    public void handleDisconnect(int message) {

    }

    private void showNotification(int updatedEventsCount, String message, String notificationDefaultChannelId) {
        Notification notification = new NotificationCompat.Builder(EventService.this, notificationDefaultChannelId)
                .setContentTitle(message)
                .setContentText(getString(R.string.count_of_events) + " " + updatedEventsCount)
                .setTicker("setTicker")
                .setSmallIcon(R.drawable.ic_warning_black_24dp)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .addAction(0, getString(R.string.disconnect_from_server), cancelPendingIntent)

                .build();

        NotificationManager manager = (NotificationManager) EventService.this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }
}



