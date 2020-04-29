package com.example.karinarkzmobile;

import com.example.karinarkzmobile.mainActivity.alarmEventsFragment.IAlarmEvents;

public class ServiceLocator {

    private static IAlarmEvents.Repository repository;

    public static void init() {
        repository = new AlarmEventsRepository();
    }

    public static IAlarmEvents.Repository getRepository() {
        if (repository == null) {
            repository = new AlarmEventsRepository();
        }
        return repository;
    }
}
