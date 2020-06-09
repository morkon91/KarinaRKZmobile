package com.example.karinarkzmobile;

import android.util.Log;

import com.example.karinarkzmobile.connectionUtils.Response;
import com.example.karinarkzmobile.connectionUtils.ServerConnectionAPI;
import com.example.karinarkzmobile.data.AlarmData;
import com.example.karinarkzmobile.mainActivity.alarmEventsFragment.IAlarmEvents;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlarmEventsRepository implements IAlarmEvents.Repository, INewEventObserved {

    private final String LOG_TAG = "myLogs";
    private List<AlarmData> alarmDataList = new ArrayList<>();
    private int newEventsCount = 0;
    private int newEventsCountBack = 0;
    private List<AlarmData> eventsSeenList = new ArrayList<>();

    private Call<Response> call;

    private LinkedHashSet<AlarmData> downloadedList = new LinkedHashSet<>();

    private List<INewEventObserver> subscribersList = new ArrayList<>();

    private ISharedPreferences authRepository = ServiceLocator.getAuthRepository();

    private String baseUrl = "https://my-json-server.typicode.com";

    private Gson gson = new GsonBuilder().create();
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .build();
    private ServerConnectionAPI serverConnectionAPI = retrofit.create(ServerConnectionAPI.class);

    @Override
    public void loadEventCount() {
        alarmDataList.clear();
    }

    @Override
    public void loadAlarmEventList() {

        call = serverConnectionAPI.fetchAlarmEventList();

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, "response.isSuccessful()");
                    downloadedList.clear();
                    downloadedList.addAll(response.body().getEvents());
                    Log.d(LOG_TAG, "Скачал новый список, количество элементов: " + response.body().getEvents().size());

                    if (downloadedList.size() > alarmDataList.size()){
                        newEventsCount++;
                    }
                    if (downloadedList.size() == 0){
                        newEventsCount = 0;
                    }

                    alarmDataList = new ArrayList<>(downloadedList);
                    Log.d(LOG_TAG, "Размер списка для отображения: " + alarmDataList.size());
                    for (int i = 0; i < alarmDataList.size(); i++) {
                        Log.d(LOG_TAG,
                                "обычная фотка элемента массива №" + i + " = " + alarmDataList.get(i).getOrdinaryPhotoURL());
                        Log.d(LOG_TAG,
                                "температурная фотка элемента массива №" + i + " = " + alarmDataList.get(i).getTemperaturePhotoURL());
                        Log.d(LOG_TAG, "int id элемента списка № " + i + " = " + alarmDataList.get(i).getEventIDint());
                    }

//                    newEvents = createNewElementList(eventsSeenList, alarmDataList);

                } else {
                    Log.d(LOG_TAG, "response NOT Successful");
                    notifyObserversAboutDisconnect(102);
                }
                notifyObservers();

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d(LOG_TAG, "onFailure: " + t.getMessage());
                newEventsCount = 0;
                notifyObserversAboutDisconnect(101);
            }
        });

    }


    private List<AlarmData> createNewElementList(List<AlarmData> eventsSeenList, List<AlarmData> alarmDataList) {
        return null;
    }

    @Override
    public List<AlarmData> getAllEvents() {
        return alarmDataList;
    }

    @Override
    public void setEventsSeenList() {
        newEventsCount = 0;
        notifyObservers();
    }

    @Override
    public void updateUrl() {
        this.baseUrl = "http://" + authRepository.loadIP() + ":18001";
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .build();
        serverConnectionAPI = retrofit.create(ServerConnectionAPI.class);
    }


    @Override
    public void addNewEventObserver(INewEventObserver observer) {
        this.subscribersList.add(observer);
    }

    @Override
    public void removeNewEventObserver(INewEventObserver observer) {
        this.subscribersList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (INewEventObserver observer : subscribersList) {
            observer.handleEvent(alarmDataList, newEventsCount);
        }
    }

    @Override
    public void notifyObserversAboutDisconnect(int code) {
        switch (code) {
            case 101:
                for (INewEventObserver observer : subscribersList) {
//                    observer.handleDisconnect("No connection to server. Please check: Is wifi enabled or does the server work.");
                    observer.handleDisconnect(101);
                }
                break;
            case 102:
                for (INewEventObserver observer : subscribersList) {
//                    observer.handleDisconnect("No response from server. Please check: Is the server working correctly.");
                    observer.handleDisconnect(102);
                }

                break;
        }

    }

}
