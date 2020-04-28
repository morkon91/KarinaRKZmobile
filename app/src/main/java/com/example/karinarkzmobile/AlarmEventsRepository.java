package com.example.karinarkzmobile;

import com.example.karinarkzmobile.data.AlarmData;
import com.example.karinarkzmobile.mainActivity.IAlarmEvents;

import java.util.Arrays;
import java.util.List;

public class AlarmEventsRepository implements IAlarmEvents.Repository {

    @Override
    public int loadEventCount() {
        return 0;
    }

    @Override
    public List<AlarmData> loadAlarmEventList() {
        return Arrays.asList(
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
    }
}
