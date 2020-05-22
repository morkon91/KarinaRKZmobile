package com.example.karinarkzmobile.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class AlarmData implements Serializable {

    @SerializedName("eventid")
    @Expose
    private int eventid;
    @SerializedName("eventtime")
    @Expose
    private String eventtime;
    @SerializedName("temp")
    @Expose
    private String temp;


    private String ordinaryPhotoURL;

    private String temperaturePhotoURL;

    public AlarmData(int alarmEventID, String timeOfAlarmEvent, String temperature) {
        this.eventid = alarmEventID;
        this.eventtime = timeOfAlarmEvent;
        this.temp = temperature;
        this.ordinaryPhotoURL = "http://127.0.0.1:18001/?command=102&eventid=" + alarmEventID;
        this.temperaturePhotoURL = "http://127.0.0.1:18001/?command=103&eventid=" + alarmEventID;
//        this.ordinaryPhotoURL = "http://www.catalog-vaz.ru/images/wlp/125628-1024x768.jpg";
//        this.temperaturePhotoURL = "https://wpapers.ru/wallpapers/Holidays/New-Year/5536/PREV_Новый-год.jpg";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlarmData alarmData = (AlarmData) o;
        return eventid == alarmData.eventid &&
                Objects.equals(eventtime, alarmData.eventtime) &&
                Objects.equals(temp, alarmData.temp) &&
                Objects.equals(ordinaryPhotoURL, alarmData.ordinaryPhotoURL) &&
                Objects.equals(temperaturePhotoURL, alarmData.temperaturePhotoURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventid, eventtime, temp, ordinaryPhotoURL, temperaturePhotoURL);
    }

    public void setEventid(int eventid) {
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

    public int getEventid() {
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
