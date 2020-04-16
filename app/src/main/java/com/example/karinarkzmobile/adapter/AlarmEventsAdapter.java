package com.example.karinarkzmobile.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karinarkzmobile.R;

import java.util.List;

public class AlarmEventsAdapter extends RecyclerView.Adapter<AlarmEventsAdapter.AlarmEventsHolder>{


    @NonNull
    @Override
    public AlarmEventsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmEventsHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class AlarmEventsHolder extends RecyclerView.ViewHolder{

        private TextView eventLocation, eventTime;
        private ImageView alarmIcon;

        public AlarmEventsHolder(@NonNull View itemView) {
            super(itemView);
            eventLocation = itemView.findViewById(R.id.alarm_event_location_recyclerView);
            eventTime = itemView.findViewById(R.id.alarm_event_time_recyclerView);
            alarmIcon = itemView.findViewById(R.id.alarm_event_icon_recyclerView);
        }
    }
}
