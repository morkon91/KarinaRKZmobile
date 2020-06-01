package com.example.karinarkzmobile;

import android.content.SharedPreferences;
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
    private List<AlarmData> newEvents = new ArrayList<>();
    private List<AlarmData> eventsSeenList;

    private LinkedHashSet<AlarmData> downloadedList = new LinkedHashSet<>();

    private List<INewEventObserver> subscribersList = new ArrayList<>();

    private ISharedPreferences authRepository = ServiceLocator.getAuthRepository();

    //    private final String BASE_URL = "http://127.0.0.1:18001";
//    private String baseUrl = "https://my-json-server.typicode.com";
    private String baseUrl = "https://my-json-server.typicode.";


    private Gson gson = new GsonBuilder().create();
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .build();
    private ServerConnectionAPI serverConnectionAPI = retrofit.create(ServerConnectionAPI.class);

    @Override
    public int loadEventCount() {
        return alarmDataList.size();
    }

    @Override
    public void loadAlarmEventList() {

        Call<Response> call = serverConnectionAPI.fetchAlarmEventList();

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, "response.isSuccessful()");

                    downloadedList.addAll(response.body().getEvents());

                    Log.d(LOG_TAG, "Скачал новый список, количество элементов: " + response.body().getEvents().size());

                    alarmDataList = new ArrayList<>(downloadedList);
                    Log.d(LOG_TAG, "Размер списка для отображения: " + alarmDataList.size());
                    for (int i = 0; i < alarmDataList.size(); i++) {
                        Log.d(LOG_TAG,
                                "обычная фотка элемента массива №" + i + " = " + alarmDataList.get(i).getOrdinaryPhotoURL());
                        Log.d(LOG_TAG,
                                "температурная фотка элемента массива №" + i + " = " + alarmDataList.get(i).getTemperaturePhotoURL());
                    }
                    newEvents = createNewElementList(eventsSeenList, alarmDataList);
                    Log.d(LOG_TAG, "Количество новых элементов: " + newEvents.size());
                    Log.d(LOG_TAG, "==========================================");


                } else {
                    Log.d(LOG_TAG, "response NOT Successful");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d(LOG_TAG, "onFailure: " + t.getMessage());
            }
        });

        notifyObservers();
    }

    private List<AlarmData> createNewElementList(List<AlarmData> eventsSeenList, List<AlarmData> alarmDataList) {
        List<AlarmData> list = new ArrayList<>(alarmDataList);
        if (eventsSeenList != null)
            list.removeAll(eventsSeenList);

        return list;
    }

    @Override
    public List<AlarmData> getAllEvents() {
        return alarmDataList;
    }

    @Override
    public void setEventsSeenList() {
        List<AlarmData> list = new ArrayList<>();
        list.addAll(alarmDataList);
        eventsSeenList = list;
        newEvents = createNewElementList(eventsSeenList, alarmDataList);
        notifyObservers();
        Log.d(LOG_TAG,
                "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        Log.d(LOG_TAG,
                "Отработал метод setEventsSeenList, количество увиденных эелементов: " + eventsSeenList.size());
        Log.d(LOG_TAG,
                "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    @Override
    public void updateUrl() {
        this.baseUrl = "https://my-json-server.typicode." + authRepository.loadIP();
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
            observer.handleEvent(this.alarmDataList, this.newEvents);

        }
    }

}
