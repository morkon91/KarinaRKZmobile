package com.example.karinarkzmobile.mainActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.karinarkzmobile.eventInfoActivity.EventInfoActivity;
import com.example.karinarkzmobile.R;
import com.example.karinarkzmobile.adapter.AlarmEventsAdapter;
import com.example.karinarkzmobile.data.AlarmData;

import java.util.Arrays;
import java.util.Collection;


public class AlarmEventsFragment extends Fragment implements IAlarmEvents.View{

    private RecyclerView alarmEventsRecyclerView;
    private AlarmEventsAdapter adapter;

    private IAlarmEvents.Presenter mPresenter;


    public AlarmEventsFragment() {
        // Required empty public constructor
    }


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

        AlarmEventsAdapter.OnEventClickListener onEventClickListener = new AlarmEventsAdapter.OnEventClickListener() {
            @Override
            public void onEventClick(AlarmData alarmEvent) {
                Intent intent = new Intent(getContext(), EventInfoActivity.class);
                startActivity(intent);
            }
        };
        adapter = new AlarmEventsAdapter(onEventClickListener);
        alarmEventsRecyclerView.setAdapter(adapter);

        mPresenter.getAlarmEvents();

        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void showAlarmEvents(Collection<AlarmData> alarmData) {
        Collection<AlarmData> collection = alarmData;
        adapter.setItems(collection);
    }
}
