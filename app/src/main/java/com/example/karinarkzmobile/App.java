package com.example.karinarkzmobile;

import android.app.Application;
import android.content.Intent;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceLocator.init();
        startService(new Intent(this, EventService.class));
    }
}
