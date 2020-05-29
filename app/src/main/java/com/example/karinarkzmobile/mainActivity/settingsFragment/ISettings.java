package com.example.karinarkzmobile.mainActivity.settingsFragment;

import com.example.karinarkzmobile.data.AlarmData;

import java.util.List;

public interface ISettings {

    interface View{
        void showConnectStatus(boolean connectStatus);
    }

    interface Presenter{
        void getAlarmEvents();
        void removeListener();
        void setEventsSeenList();

    }

    interface Repository{

        int loadEventCount();
        void loadAlarmEventList();
        List<AlarmData> getAllEvents();
        void setEventsSeenList();


    }
}
