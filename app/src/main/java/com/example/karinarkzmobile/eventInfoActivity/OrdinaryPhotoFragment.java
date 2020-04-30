package com.example.karinarkzmobile.eventInfoActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.karinarkzmobile.R;
import com.example.karinarkzmobile.data.AlarmData;
import com.example.karinarkzmobile.mainActivity.alarmEventsFragment.AlarmEventsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdinaryPhotoFragment extends Fragment {

    private ImageView ordinaryImageView;
    private AlarmData alarmEvent;

    public OrdinaryPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        alarmEvent = (AlarmData) getArguments().getSerializable(AlarmEventsFragment.EXT_ALARM_DATA);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ordinary_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ordinaryImageView = view.findViewById(R.id.ordinary_photo);
        Glide
                .with(getContext())
                .load(alarmEvent.getOrdinaryPhotoURL())
                .into(ordinaryImageView);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
