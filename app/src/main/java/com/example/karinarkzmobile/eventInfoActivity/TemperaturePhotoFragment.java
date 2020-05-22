package com.example.karinarkzmobile.eventInfoActivity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.karinarkzmobile.R;
import com.example.karinarkzmobile.data.AlarmData;
import com.example.karinarkzmobile.mainActivity.alarmEventsFragment.AlarmEventsFragment;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemperaturePhotoFragment extends Fragment {

    private ImageView temperatureImageView;
    private AlarmData alarmEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        alarmEvent = (AlarmData) getArguments().getSerializable(AlarmEventsFragment.EXT_ALARM_DATA);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_temperature_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        temperatureImageView = view.findViewById(R.id.temperature_photo);
        Glide
                .with(getContext())
                .load(alarmEvent.getTemperaturePhotoURL())
                .into(temperatureImageView);

        temperatureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "Go to temperature photo activity", Toast.LENGTH_SHORT).show();
                List<String> list = new ArrayList<>();
                list.add(alarmEvent.getTemperaturePhotoURL());
                new ImageViewer.Builder(getContext(), list)
                        .hideStatusBar(false)
                        .setStartPosition(0)
                        .show();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
