package com.example.karinarkzmobile.mainActivity.settingsFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.karinarkzmobile.R;
import com.google.android.material.textfield.TextInputEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    TextInputEditText ipAddresseditText;
    Button closeApplicationButton;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ipAddresseditText = view.findViewById(R.id.ip_address_editText);
        closeApplicationButton = view.findViewById(R.id.close_application_button);
        closeApplicationButton.setBackgroundColor(getResources().getColor(R.color.colorRed));


        
        super.onViewCreated(view, savedInstanceState);
    }
    
}




