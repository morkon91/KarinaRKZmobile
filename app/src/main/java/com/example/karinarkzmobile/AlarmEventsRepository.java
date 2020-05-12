package com.example.karinarkzmobile;

import android.content.Intent;
import android.util.Log;

import com.example.karinarkzmobile.data.AlarmData;
import com.example.karinarkzmobile.mainActivity.alarmEventsFragment.IAlarmEvents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlarmEventsRepository implements IAlarmEvents.Repository {

    private List<AlarmData> alarmDataList = new ArrayList<>();
    private IEventsListener eventsListener;

    @Override
    public int loadEventCount() {
        return 0;
    }

    @Override
    public void loadAlarmEventList() {
        Log.d("myLogs", String.valueOf(alarmDataList.size()));
         alarmDataList = Arrays.asList(
                new AlarmData(1,
                        "12.12.2020 14:55",
                        "Teplovizor 1",
                        "38", "https://lh3.googleusercontent.com/proxy/15qLskn21PMedhWsIrMWiwpfexR96Ok7oV2v2LDlgrHIluyqHtzCubWGzjyccxGjJwsllg1qLVsPSwL_S1ODr_4Jo7-OVnpzNKmYT68QGJP5jdxI87WnYW_3nmTnxjvW55YsLpAm",
                        "https://ru-minecraft.ru/uploads/posts/2013-09/thumbs/1378393378_520278-1024x768.jpg"),
                 new AlarmData(2,
                         "12.12.2020 14:55",
                         "Teplovizor 2",
                         "37,5", "https://lh3.googleusercontent.com/proxy/Ou5HUBRzq8pys3rZ7sThl1hTuJXTpFA8fOObuf6l" +
                         "-9lK9TBFQWYEEPIE9rfY2EJ1gBrpj8ZSXnuN0R7tHs8C0Oam58Ng-SHlzuqTzy9-dqbhCYXBYoi2Ec6okV3X0iLhcKghRf2DwTZwg70OEQCJ6pc",
                         "https://oboi.ws/wallpapers/12_12101_oboi_tonushhaja_planeta_1024x768.jpg"),
                 new AlarmData(3,
                         "12.12.2020 14:55",
                         "Teplovizor 3",
                         "38,2", "https://lh3.googleusercontent.com/proxy/Ou5HUBRzq8pys3rZ7sThl1hTuJXTpFA8fOObuf6l" +
                         "-9lK9TBFQWYEEPIE9rfY2EJ1gBrpj8ZSXnuN0R7tHs8C0Oam58Ng-SHlzuqTzy9-dqbhCYXBYoi2Ec6okV3X0iLhcKghRf2DwTZwg70OEQCJ6pc",
                         "https://wpapers.ru/wallpapers/All/9582/1024x768_Череп.jpg"));





        if (eventsListener != null) {
            eventsListener.onNewEvent(alarmDataList);
        }
    }

    @Override
    public List<AlarmData> getAllEvents() {
        loadAlarmEventList();
        return alarmDataList;
    }

    @Override
    public void setEventsListener(IEventsListener iEventsListener) {
        this.eventsListener = iEventsListener;
    }
}
