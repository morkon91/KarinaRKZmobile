package com.example.karinarkzmobile.mainActivity.alarmEventsFragment;

import com.example.karinarkzmobile.data.AlarmData;

import java.util.Collection;
import java.util.List;

public interface IAlarmEvents {

    interface View{
        void showAlarmEvents(List<AlarmData> alarmData);
    }

    interface Presenter{
        void getAlarmEvents();
        void removeListener();

    }

    interface Repository{

        int loadEventCount();

        void loadAlarmEventList();

        List<AlarmData> getAllEvents();
    }
}
