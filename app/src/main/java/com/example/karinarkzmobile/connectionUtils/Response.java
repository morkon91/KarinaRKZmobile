package com.example.karinarkzmobile.connectionUtils;

import com.example.karinarkzmobile.ISharedPreferences;
import com.example.karinarkzmobile.ServiceLocator;
import com.example.karinarkzmobile.data.AlarmData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    private ISharedPreferences authRepository = ServiceLocator.getAuthRepository();
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("events")
    @Expose
    private List<AlarmData> events = null;

    public List<AlarmData> getEvents() {
        for (int i = 0; i < events.size(); i++) {
            events.get(i).setOrdinaryPhotoURL("http://" + authRepository.loadIP() + ":18001/?command=102&eventid=" + events.get(i).getEventid());
            events.get(i).setTemperaturePhotoURL("http://" + authRepository.loadIP() + ":18001/?command=103&eventid=" + events.get(i).getEventid());
            events.get(i).setEventIDint(Integer.parseInt(events.get(i).getEventid()));
        }
        return events;
    }

    public void setEvents
            (List<AlarmData> events) {
        this.events = events;
    }

    public String getData() {
        return data;
    }
}
