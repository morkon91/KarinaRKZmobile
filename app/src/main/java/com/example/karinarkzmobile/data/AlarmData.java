package com.example.karinarkzmobile.data;

import java.util.Objects;

public class AlarmData {

    private int alarmEventID;
    private String timeOfAlarmEvent;
    private String locationOfAlarmEvent;
    private double temperature;
    private String ordinaryPhotoURL;
    private String temperaturePhotoURL;

    public AlarmData(int alarmEventID, String timeOfAlarmEvent, String locationOfAlarmEvent, double temperature, String ordinaryPhotoURL, String temperaturePhotoURL) {
        this.alarmEventID = alarmEventID;
        this.timeOfAlarmEvent = timeOfAlarmEvent;
        this.locationOfAlarmEvent = locationOfAlarmEvent;
        this.temperature = temperature;
        this.ordinaryPhotoURL = ordinaryPhotoURL;
        this.temperaturePhotoURL = temperaturePhotoURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlarmData alarmData = (AlarmData) o;
        return alarmEventID == alarmData.alarmEventID &&
                Double.compare(alarmData.temperature, temperature) == 0 &&
                Objects.equals(timeOfAlarmEvent, alarmData.timeOfAlarmEvent) &&
                Objects.equals(locationOfAlarmEvent, alarmData.locationOfAlarmEvent) &&
                Objects.equals(ordinaryPhotoURL, alarmData.ordinaryPhotoURL) &&
                Objects.equals(temperaturePhotoURL, alarmData.temperaturePhotoURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alarmEventID, timeOfAlarmEvent, locationOfAlarmEvent, temperature, ordinaryPhotoURL, temperaturePhotoURL);
    }

    public void setAlarmEventID(int alarmEventID) {
        this.alarmEventID = alarmEventID;
    }

    public void setTimeOfAlarmEvent(String timeOfAlarmEvent) {
        this.timeOfAlarmEvent = timeOfAlarmEvent;
    }

    public void setLocationOfAlarmEvent(String locationOfAlarmEvent) {
        this.locationOfAlarmEvent = locationOfAlarmEvent;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setOrdinaryPhotoURL(String ordinaryPhotoURL) {
        this.ordinaryPhotoURL = ordinaryPhotoURL;
    }

    public void setTemperaturePhotoURL(String temperaturePhotoURL) {
        this.temperaturePhotoURL = temperaturePhotoURL;
    }

    public int getAlarmEventID() {
        return alarmEventID;
    }

    public String getTimeOfAlarmEvent() {
        return timeOfAlarmEvent;
    }

    public String getLocationOfAlarmEvent() {
        return locationOfAlarmEvent;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getOrdinaryPhotoURL() {
        return ordinaryPhotoURL;
    }

    public String getTemperaturePhotoURL() {
        return temperaturePhotoURL;
    }
}
