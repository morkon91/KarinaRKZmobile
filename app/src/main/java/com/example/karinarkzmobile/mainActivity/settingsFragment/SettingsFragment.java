package com.example.karinarkzmobile.mainActivity.settingsFragment;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.karinarkzmobile.App;
import com.example.karinarkzmobile.EventService;
import com.example.karinarkzmobile.ISharedPreferences;
import com.example.karinarkzmobile.R;
import com.example.karinarkzmobile.ServiceLocator;
import com.example.karinarkzmobile.connectionUtils.Response;
import com.example.karinarkzmobile.connectionUtils.ServerConnectionAPI;
import com.example.karinarkzmobile.mainActivity.alarmEventsFragment.IAlarmEvents;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    private TextInputLayout ipAddressInputLayout;
    private TextInputEditText ipAddressEditText;
    private Button disconnectFromServerButton;
    private Button connectToServerButton;
    private Button closeApplicationButton;

    private ProgressBar progressBar;

    private IAlarmEvents.Repository repository = ServiceLocator.getRepository();

    private Call<Response> call;
    private AsyncTask asyncTask;
    private ConnectionState message;
    private final String LOG_TAG = "myLogs";

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
        ipAddressInputLayout = view.findViewById(R.id.ip_address_InputLayout);
        ipAddressInputLayout.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);

        disconnectFromServerButton = view.findViewById(R.id.stop_service_SettingsFragment);
        disconnectFromServerButton.setOnClickListener(this::onClick);
        connectToServerButton = view.findViewById(R.id.connect_to_server_SettingFragment);
        connectToServerButton.setOnClickListener(this::onClick);

        closeApplicationButton = view.findViewById(R.id.close_application_button);
        closeApplicationButton.setOnClickListener(this::onClick);
        closeApplicationButton.setBackgroundColor(getResources().getColor(R.color.colorRed));

        progressBar = view.findViewById(R.id.progressBar_fragmentSettings);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.stop_service_SettingsFragment):
                if (isMyServiceRunning(EventService.class)) {
                    App.getInstance().stopEventService();
                    Snackbar.make(getView(), "You are disconnected from the server.",
                            BaseTransientBottomBar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(getView(), "You are already disconnected from the server.",
                            BaseTransientBottomBar.LENGTH_LONG).show();
                }
                break;
            case (R.id.connect_to_server_SettingFragment):
                if (!isMyServiceRunning(EventService.class)) {
                    progressBar.setVisibility(View.VISIBLE);
                    checkConnection(ipAddressEditText.getText().toString());
                } else {
                    Snackbar.make(getView(), "You are already connected to the server.",
                            BaseTransientBottomBar.LENGTH_LONG).show();
                }
                break;
            case (R.id.close_application_button):
                openQuitDialog();
                break;
        }
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(getContext());
        quitDialog.setTitle("Do you want to exit the application?");

        quitDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if (isMyServiceRunning(EventService.class)) {
                    App.getInstance().stopEventService();
                }
                authRepository.deleteToken();
                getActivity().finishAffinity();

            }
        });

        quitDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });

        quitDialog.show();
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



    @SuppressLint("StaticFieldLeak")
    private void checkConnection(String ip) {

        Log.d(LOG_TAG, "Check connection...");

        String baseUrl = "http://" + ip + ":18001";
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .build();
        ServerConnectionAPI serverConnectionAPI = retrofit.create(ServerConnectionAPI.class);

        asyncTask = new AsyncTask<Void, SettingsFragment.ConnectionState, SettingsFragment.ConnectionState>() {
            @Override
            protected SettingsFragment.ConnectionState doInBackground(Void... voids) {
                Log.d(LOG_TAG, "Doing request to server. IP Address: " + baseUrl);
                call = serverConnectionAPI.fetchAlarmEventList();
                try {
                    retrofit2.Response<Response> responseFromServer = call.execute();
                    Response response = responseFromServer.body();
                    Log.d(LOG_TAG, "response: " + response.getData());

                    if (!response.getData().isEmpty()) {
                        Log.d(LOG_TAG, "Connection Successful" + response.getData());
                        message = SettingsFragment.ConnectionState.CONNECTION_SUCCESS;
                    } else {

                        message = SettingsFragment.ConnectionState.EMPTY_RESPONSE;
                    }
                } catch (UnknownHostException e) {
                    Log.d(LOG_TAG, "Connection NOT Successful:");
                    Log.d(LOG_TAG, "Exeption: " + e);
//                    message = "Connection NOT Successful. \nInvalid ip address";
                    message = SettingsFragment.ConnectionState.INVALID_IP_ADDRESS;
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(LOG_TAG, "Connection NOT Successful:");
                    Log.d(LOG_TAG, "Exeption: " + e);
//                    message = "Connection NOT Successful. \nNo response from server";
                    message = SettingsFragment.ConnectionState.SERVER_NOT_RESPONSE;
                    e.printStackTrace();
                }
                return message;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(SettingsFragment.ConnectionState state) {
                switch (state){
                    case CONNECTION_SUCCESS:
                        ipAddressInputLayout.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_check_white_24dp));
                        authRepository.saveIP(ipAddressEditText.getText().toString());
                        repository.updateUrl();
                        App.getInstance().startEventService();
                        Snackbar.make(getView(), "Connection to server restored.",
                                BaseTransientBottomBar.LENGTH_LONG).show();
                        break;
                    case EMPTY_RESPONSE:
                        ipAddressInputLayout.setError("Connection NOT Successful: empty response from server.");
                        break;
                    case INVALID_IP_ADDRESS:
                        ipAddressInputLayout.setError("Connection NOT Successful. \nInvalid ip address.");
                        break;
                    case SERVER_NOT_RESPONSE:
                        ipAddressInputLayout.setError("Connection NOT Successful. \nNo response from server.");
                        break;
                }
                progressBar.setVisibility(View.INVISIBLE);
                super.onPostExecute(state);
            }

            @Override
            protected void onProgressUpdate(SettingsFragment.ConnectionState... values) {

                super.onProgressUpdate(values);
            }
        }.execute();
    }

    enum ConnectionState{
        CONNECTION_SUCCESS, EMPTY_RESPONSE, INVALID_IP_ADDRESS, SERVER_NOT_RESPONSE;
    }

}




