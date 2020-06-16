package com.example.karinarkzmobile.mainActivity.alarmEventsFragment;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.karinarkzmobile.App;
import com.example.karinarkzmobile.EventService;
import com.example.karinarkzmobile.INewEventObserver;
import com.example.karinarkzmobile.ServiceLocator;
import com.example.karinarkzmobile.eventInfoActivity.EventInfoActivity;
import com.example.karinarkzmobile.R;
import com.example.karinarkzmobile.mainActivity.alarmEventsFragment.adapter.AlarmEventsAdapter;
import com.example.karinarkzmobile.data.AlarmData;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class AlarmEventsFragment extends Fragment implements IAlarmEvents.View, SwipeRefreshLayout.OnRefreshListener {

    public static final String EXT_ALARM_DATA = AlarmData.class.getSimpleName();

    private RecyclerView alarmEventsRecyclerView;
    private AlarmEventsAdapter adapter;

    private IAlarmEvents.Presenter mPresenter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mPresenter = new AlarmEventsPresenter(this);

        alarmEventsRecyclerView = view.findViewById(R.id.alarm_events_recyclerView);
        alarmEventsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout_Fragment_alarm_events);
        swipeRefreshLayout.setOnRefreshListener(this::onRefresh);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorRed));

        AlarmEventsAdapter.OnEventClickListener onEventClickListener = new AlarmEventsAdapter.OnEventClickListener() {
            @Override
            public void onEventClick(AlarmData alarmEvent) {
                Intent intent = new Intent(getContext(), EventInfoActivity.class);
                intent.putExtra(EXT_ALARM_DATA, alarmEvent);
                startActivity(intent);
            }
        };
        adapter = new AlarmEventsAdapter(onEventClickListener);
        alarmEventsRecyclerView.setAdapter(adapter);

        mPresenter.getAlarmEvents();

        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void showAlarmEvents(List<AlarmData> listOfAlarmEvents) {
        getView().post(new Runnable() {
            @Override
            public void run() {
                List<AlarmData> collection = listOfAlarmEvents;
                adapter.setItems(collection);
            }
        });
    }

    @Override
    public void showDisconnect(int messageOfDisconnect) {
        switch (messageOfDisconnect) {
            case 101:
                Snackbar.make(getView(),getString(R.string.no_connection_to_server_Please_check) + "\n" + getString(R.string.is_wifi_enabled),
                        BaseTransientBottomBar.LENGTH_LONG).show();
//                    observer.handleDisconnect("No connection to server. Please check: Is wifi enabled or does the server work.");
                break;
            case 102:
                Snackbar.make(getView(), getString(R.string.no_response_from_server_Please_check) + "\n" + getString(R.string.is_the_server_working_correctly),
                        BaseTransientBottomBar.LENGTH_LONG).show();
//                    observer.handleDisconnect("No response from server. Please check: Is the server working correctly.");
                break;
        }

    }

    @Override
    public void onDestroyView() {
        mPresenter.removeListener();
        super.onDestroyView();
    }

    @Override
    public void onResume() {

        if (isMyServiceRunning(EventService.class))
        mPresenter.setEventsSeenList();

        if (!isMyServiceRunning(EventService.class))
            mPresenter.getAlarmEvents();

        super.onResume();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        if (!isMyServiceRunning(EventService.class)){
            App.getInstance().startEventService();
            Snackbar.make(getView(), R.string.permanent_server_connection_resumed,
                    BaseTransientBottomBar.LENGTH_LONG).show();
            swipeRefreshLayout.setRefreshing(false);
        }else {
            Snackbar.make(getView(), R.string.permanent_connection_to_the_server_is_already_active,
                    BaseTransientBottomBar.LENGTH_LONG).show();
            swipeRefreshLayout.setRefreshing(false);
        }
        mPresenter.getAlarmEvents();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
