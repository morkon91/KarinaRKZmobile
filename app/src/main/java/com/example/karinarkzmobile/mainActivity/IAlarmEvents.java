package com.example.karinarkzmobile.mainActivity;

import com.example.karinarkzmobile.data.AlarmData;

import java.util.Collection;
import java.util.List;

public interface IAlarmEvents {

    interface View{
        void showAlarmEvents(Collection<AlarmData> alarmData);
    }

    interface Presenter{
        void getAlarmEvents();

    }

    interface Repository{
        int loadEventCount();
        List<AlarmData> loadAlarmEventList();
    }
}
