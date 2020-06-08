package com.example.karinarkzmobile.mainActivity.alarmEventsFragment;

import com.example.karinarkzmobile.data.AlarmData;

import java.util.Collection;
import java.util.List;

public interface IAlarmEvents {

    interface View{
        void showAlarmEvents(List<AlarmData> alarmData);
        void showDisconnect(int message);
    }

    interface Presenter{
        void getAlarmEvents();
        void removeListener();
        void setEventsSeenList();

    }

    interface Repository{

        void loadEventCount();
        void loadAlarmEventList();
        List<AlarmData> getAllEvents();
        void setEventsSeenList();
        void updateUrl();
    }
}
