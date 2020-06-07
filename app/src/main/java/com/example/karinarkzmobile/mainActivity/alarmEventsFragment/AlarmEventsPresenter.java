package com.example.karinarkzmobile.mainActivity.alarmEventsFragment;

import android.app.ActivityManager;
import android.content.Context;

import com.example.karinarkzmobile.App;
import com.example.karinarkzmobile.EventService;
import com.example.karinarkzmobile.INewEventObserved;
import com.example.karinarkzmobile.INewEventObserver;
import com.example.karinarkzmobile.ServiceLocator;
import com.example.karinarkzmobile.data.AlarmData;

import java.util.ArrayList;
import java.util.List;

public class AlarmEventsPresenter implements IAlarmEvents.Presenter, INewEventObserver{

    private IAlarmEvents.View mView;
    private IAlarmEvents.Repository repository = ServiceLocator.getRepository();


    List<AlarmData> listOfAlarmEvents = new ArrayList<>();


    public AlarmEventsPresenter(IAlarmEvents.View mView) {
        this.mView = mView;
        if (repository instanceof INewEventObserved){
            ((INewEventObserved) repository).addNewEventObserver(this);
        }
    }


    @Override
    public void getAlarmEvents() {
        if (!isMyServiceRunning(EventService.class)){
            repository.loadEventCount();
        }
        listOfAlarmEvents = repository.getAllEvents();
        mView.showAlarmEvents(listOfAlarmEvents);
    }

    @Override
    public void removeListener() {
        if (repository instanceof INewEventObserved) {
            ((INewEventObserved) repository).removeNewEventObserver(this);}
    }

    @Override
    public void setEventsSeenList() {
        repository.setEventsSeenList();
    }

    @Override
    public void handleEvent(List<AlarmData> updatedList, int newEventsCount) {
        mView.showAlarmEvents(updatedList);
    }

    @Override
    public void handleDisconnect(String message) {
        mView.showDisconnect(message);
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) App.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

