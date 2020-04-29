package com.example.karinarkzmobile;

import com.example.karinarkzmobile.data.AlarmData;
import com.example.karinarkzmobile.mainActivity.alarmEventsFragment.IAlarmEvents;

import java.util.Arrays;
import java.util.List;

public class AlarmEventsRepository implements IAlarmEvents.Repository {

    private List<AlarmData> alarmDataList;
    private IEventsListener eventsListener;




    @Override
    public int loadEventCount() {
        return 0;
    }

    @Override
    public void loadAlarmEventList() {
         alarmDataList = Arrays.asList(
                new AlarmData(1,
                        "12.12.2020 14:55",
                        "Teplovizor 1",
                        38, "URL",
                        "URL"),
                new AlarmData(1,
                        "12.12.2020 14:55",
                        "Teplovizor 1",
                        38, "URL",
                        "URL"),
                new AlarmData(1,
                        "12.12.2020 14:55",
                        "Teplovizor 1",
                        38, "URL",
                        "URL"));

        if (eventsListener != null) {
            eventsListener.onNewEvent(alarmDataList);
        }
    }

    @Override
    public List<AlarmData> getAllEvents() {
        loadAlarmEventList();
        return alarmDataList;
    }

    @Override
    public void setEventsListener(IEventsListener iEventsListener) {
        this.eventsListener = iEventsListener;
    }
}
