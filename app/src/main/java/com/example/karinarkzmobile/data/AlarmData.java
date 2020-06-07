package com.example.karinarkzmobile.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class AlarmData implements Serializable {

    private int eventIDint;
    @SerializedName("eventid")
    @Expose
    private String eventid;
    @SerializedName("eventtime")
    @Expose
    private String eventtime;
    @SerializedName("temp")
    @Expose
    private String temp;

    private String ordinaryPhotoURL;

    private String temperaturePhotoURL;

    public AlarmData(String alarmEventID, String timeOfAlarmEvent, String temperature) {
        this.eventid = alarmEventID;
        this.eventtime = timeOfAlarmEvent;
        this.temp = temperature;
    }

    @Override
    public String toString() {
        return "AlarmData{" +
                "eventid='" + eventid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlarmData alarmData = (AlarmData) o;
        return eventIDint == alarmData.eventIDint &&
                Objects.equals(eventid, alarmData.eventid) &&
                Objects.equals(eventtime, alarmData.eventtime) &&
                Objects.equals(temp, alarmData.temp) &&
                Objects.equals(ordinaryPhotoURL, alarmData.ordinaryPhotoURL) &&
                Objects.equals(temperaturePhotoURL, alarmData.temperaturePhotoURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventIDint, eventid, eventtime, temp, ordinaryPhotoURL, temperaturePhotoURL);
    }

    public int getEventIDint() {
        return eventIDint;
    }

    public void setEventIDint(int eventIDint) {
        this.eventIDint = eventIDint;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public void setEventtime(String eventtime) {
        this.eventtime = eventtime;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setOrdinaryPhotoURL(String ordinaryPhotoURL) {
        this.ordinaryPhotoURL = ordinaryPhotoURL;
    }

    public void setTemperaturePhotoURL(String temperaturePhotoURL) {
        this.temperaturePhotoURL = temperaturePhotoURL;
    }

    public String getEventid() {
        return eventid;
    }

    public String getEventtime() {
        return eventtime;
    }

    public String getTemp() {
        return temp;
    }

    public String getOrdinaryPhotoURL() {
        return ordinaryPhotoURL;
    }

    public String getTemperaturePhotoURL() {
        return temperaturePhotoURL;
    }
}
