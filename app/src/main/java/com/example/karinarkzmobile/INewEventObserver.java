package com.example.karinarkzmobile;

import com.example.karinarkzmobile.data.AlarmData;

import java.util.List;

public interface INewEventObserver {
    void handleEvent(List<AlarmData> updatedList, int newEventsCount);
    void handleDisconnect(int message);
}
