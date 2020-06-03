package com.example.karinarkzmobile.mainActivity.settingsFragment;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.karinarkzmobile.App;
import com.example.karinarkzmobile.EventService;
import com.example.karinarkzmobile.ISharedPreferences;
import com.example.karinarkzmobile.R;
import com.example.karinarkzmobile.ServiceLocator;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    TextInputEditText ipAddressEditText;
    Button disconnectFromServerButton;
    Button connectToServerButton;
    Button closeApplicationButton;
    LinearLayout linearLayout;

    private ISharedPreferences authRepository = ServiceLocator.getAuthRepository();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ipAddressEditText = view.findViewById(R.id.ip_address_editText);
        ipAddressEditText.setText(authRepository.loadIP());

        disconnectFromServerButton = view.findViewById(R.id.stop_service_SettingsFragment);
        disconnectFromServerButton.setOnClickListener(this::onClick);
        connectToServerButton = view.findViewById(R.id.connect_to_server_SettingFragment);
        connectToServerButton.setOnClickListener(this::onClick);

        linearLayout = view.findViewById(R.id.fragment_alarm_events);

        closeApplicationButton = view.findViewById(R.id.close_application_button);
        closeApplicationButton.setOnClickListener(this::onClick);
        closeApplicationButton.setBackgroundColor(getResources().getColor(R.color.colorRed));

//        closeApplicationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isMyServiceRunning(EventService.class)){
//                    App.getInstance().stopEventService();
//                }
//                authRepository.deleteToken();
//                getActivity().finishAffinity();
//            }
//        });

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case (R.id.stop_service_SettingsFragment):
                if (isMyServiceRunning(EventService.class)){
                    App.getInstance().stopEventService();
                    Snackbar.make(getView(), "You are disconnected from the server.",
                            BaseTransientBottomBar.LENGTH_LONG).show();
                }else {
                    Snackbar.make(getView(), "You are already disconnected from the server.",
                            BaseTransientBottomBar.LENGTH_LONG).show();
                }
                break;
            case (R.id.connect_to_server_SettingFragment):
                if (!isMyServiceRunning(EventService.class)){
                    App.getInstance().startEventService();
                    Snackbar.make(getView(), "Connection to server restored",
                            BaseTransientBottomBar.LENGTH_LONG).show();
                }else {
                    Snackbar.make(getView(), "You are already connected from the server.",
                            BaseTransientBottomBar.LENGTH_LONG).show();
                }
                break;
            case (R.id.close_application_button):
                if (isMyServiceRunning(EventService.class)){
                    App.getInstance().stopEventService();
                }
                authRepository.deleteToken();
                getActivity().finishAffinity();
                break;
        }
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




