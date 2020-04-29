package com.example.karinarkzmobile.mainActivity.alarmEventsFragment;

import com.example.karinarkzmobile.AlarmEventsRepository;
import com.example.karinarkzmobile.ServiceLocator;
import com.example.karinarkzmobile.data.AlarmData;

import java.util.List;

public class AlarmEventsPresenter implements IAlarmEvents.Presenter, IAlarmEvents.Repository.IEventsListener {

    private IAlarmEvents.View mView;
    private IAlarmEvents.Repository repository = ServiceLocator.getRepository();


    List<AlarmData> listOfAlarmEvents;


    public AlarmEventsPresenter(IAlarmEvents.View mView) {
        this.mView = mView;
        repository.setEventsListener(this);
    }


    @Override
    public void getAlarmEvents() {
        listOfAlarmEvents = repository.getAllEvents();
        mView.showAlarmEvents(listOfAlarmEvents);
    }

    @Override
    public void removeListener() {
        repository.setEventsListener(null);
    }

    @Override
    public void onNewEvent(List<AlarmData> updatedList) {

    }
}
