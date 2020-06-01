package com.example.karinarkzmobile;

import com.example.karinarkzmobile.mainActivity.alarmEventsFragment.IAlarmEvents;

public class ServiceLocator {

    private static IAlarmEvents.Repository repository;
    private static ISharedPreferences authRepository;

    public static void init() {
        authRepository = new AuthRepository();
        repository = new AlarmEventsRepository();

    }

    public static IAlarmEvents.Repository getRepository() {
        if (repository == null) {
            repository = new AlarmEventsRepository();
        }
        return repository;
    }

    public static ISharedPreferences getAuthRepository(){
        if (authRepository == null) {
            authRepository = new AuthRepository();
        }
        return authRepository;
    }
}
