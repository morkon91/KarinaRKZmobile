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
        return events;
    }

    public void setEvents(List<AlarmData> events) {
        this.events = events;
    }
}
