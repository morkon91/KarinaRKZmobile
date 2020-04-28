package com.example.karinarkzmobile.eventInfoActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.karinarkzmobile.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemperaturePhotoFragment extends Fragment {

    public TemperaturePhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_temperature_photo, container, false);
    }
}
