package com.example.karinarkzmobile.mainActivity.alarmEventsFragment;

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
        listOfAlarmEvents = repository.getAllEvents();
        mView.showAlarmEvents(listOfAlarmEvents);
    }

    @Override
    public void removeListener() {
        if (repository instanceof INewEventObserved) {
            ((INewEventObserved) repository).removeNewEventObserver(this);}
    }

    @Override
    public void handleEvent(List<AlarmData> updatedList, List<AlarmData> newEventList) {
        mView.showAlarmEvents(updatedList);
    }
}
