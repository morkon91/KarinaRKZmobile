package com.example.karinarkzmobile.data;

import java.util.Objects;

public class AlarmData {

    private String timeOfAlarmEvent;
    private String locationOfAlarmEvent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlarmData alarmData = (AlarmData) o;
        return temperature == alarmData.temperature &&
                Objects.equals(timeOfAlarmEvent, alarmData.timeOfAlarmEvent) &&
                Objects.equals(locationOfAlarmEvent, alarmData.locationOfAlarmEvent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeOfAlarmEvent, locationOfAlarmEvent, temperature);
    }

    public String getTimeOfAlarmEvent() {
        return timeOfAlarmEvent;
    }

    public void setTimeOfAlarmEvent(String timeOfAlarmEvent) {
        this.timeOfAlarmEvent = timeOfAlarmEvent;
    }

    public String getLocationOfAlarmEvent() {
        return locationOfAlarmEvent;
    }

    public void setLocationOfAlarmEvent(String locationOfAlarmEvent) {
        this.locationOfAlarmEvent = locationOfAlarmEvent;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public AlarmData(String locationOfAlarmEvent,String timeOfAlarmEvent, int temperature) {
        this.timeOfAlarmEvent = timeOfAlarmEvent;
        this.locationOfAlarmEvent = locationOfAlarmEvent;
        this.temperature = temperature;
    }

    private int temperature;



}
