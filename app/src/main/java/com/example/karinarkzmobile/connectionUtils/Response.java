package com.example.karinarkzmobile.connectionUtils;

import com.example.karinarkzmobile.data.AlarmData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("events")
    @Expose
    private List<AlarmData> events = null;

    public List<AlarmData> getEvents() {
        for (int i = 0; i < events.size(); i++) {
//            events.get(i).setOrdinaryPhotoURL("http://127.0.0.1:18001/?command=102&eventid=" + events.get(i).getEventid());
//            events.get(i).setTemperaturePhotoURL("http://127.0.0.1:18001/?command=103&eventid=" + events.get(i).getEventid());

            events.get(i).setOrdinaryPhotoURL("https://images.wallpaperscraft.ru/image/bmw_fary_avtomobil_124112_1024x768.jpg");
            events.get(i).setTemperaturePhotoURL("https://wpapers.ru/wallpapers/Holidays/New-Year/5536/PREV_Новый-год.jpg");
        }
        return events;
    }

    public void setEvents(List<AlarmData> events) {
        this.events = events;
    }
}
