package com.example.karinarkzmobile.mainActivity.alarmEventsFragment.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karinarkzmobile.R;
import com.example.karinarkzmobile.data.AlarmData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AlarmEventsAdapter extends RecyclerView.Adapter<AlarmEventsAdapter.AlarmEventsHolder> {

    private List<AlarmData> alarmEventsList = new ArrayList<>();
    private OnEventClickListener onEventClickListener;

    public AlarmEventsAdapter(OnEventClickListener onEventClickListener) {
        this.onEventClickListener = onEventClickListener;
    }

    @NonNull
    @Override
    public AlarmEventsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_event_view, parent, false);
        return new AlarmEventsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmEventsHolder holder, int position) {
        holder.bind(alarmEventsList.get(position));
    }

    @Override
    public int getItemCount() {
        return alarmEventsList.size();
    }


    public void setItems(Collection<AlarmData> listOfAlarmEvents) {
        clearItems();
        alarmEventsList.addAll(listOfAlarmEvents);
        notifyDataSetChanged();
    }

    public void clearItems() {
        alarmEventsList.clear();
        notifyDataSetChanged();
    }

    class AlarmEventsHolder extends RecyclerView.ViewHolder {

        private TextView eventLocation, eventTime;
        private ImageView alarmIcon;

        public AlarmEventsHolder(@NonNull View itemView) {
            super(itemView);
            eventLocation = itemView.findViewById(R.id.alarm_event_location_recyclerView);
            eventTime = itemView.findViewById(R.id.alarm_event_time_recyclerView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlarmData alarmEvent = alarmEventsList.get(getLayoutPosition());
                    onEventClickListener.onEventClick(alarmEvent);
                }
            });
        }

        public void bind(AlarmData alarmData) {
//            eventLocation.setText("Alarm event");
            eventTime.setText(alarmData.getEventtime());
        }
    }

    public interface OnEventClickListener {
        void onEventClick(AlarmData alarmEvent);
    }
}
