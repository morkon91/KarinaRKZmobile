package com.example.karinarkzmobile.mainActivity;

import com.example.karinarkzmobile.AlarmEventsRepository;
import com.example.karinarkzmobile.data.AlarmData;

import java.util.Collection;

public class AlarmEventsPresenter implements IAlarmEvents.Presenter{

    private IAlarmEvents.View mView;
    private IAlarmEvents.Repository mRepository;

    Collection<AlarmData> collection;

    public AlarmEventsPresenter(IAlarmEvents.View mView) {
        this.mRepository = new AlarmEventsRepository();
        this.mView = mView;
    }


    @Override
    public void getAlarmEvents() {
        collection = mRepository.loadAlarmEventList();
        mView.showAlarmEvents(collection);
    }
}
