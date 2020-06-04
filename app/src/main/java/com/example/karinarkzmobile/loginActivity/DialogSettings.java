package com.example.karinarkzmobile.loginActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.karinarkzmobile.ISharedPreferences;
import com.example.karinarkzmobile.R;
import com.example.karinarkzmobile.ServiceLocator;
import com.example.karinarkzmobile.connectionUtils.Response;
import com.example.karinarkzmobile.connectionUtils.ServerConnectionAPI;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DialogSettings extends DialogFragment implements View.OnClickListener{

    private TextInputEditText addIPAddressTextInput;
    private ISharedPreferences authRepository = ServiceLocator.getAuthRepository();
    private TextView infoAboutIPTextView;
    private ImageView imageViewDialogTitle;
    ProgressBar progressBar;
    private final String LOG_TAG = "myLogs";
    private Call<Response> call;

    private AsyncTask asyncTask;
    private ConnectionState message;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.dialog_add_ip_address, null);

        view.findViewById(R.id.confirm_ip_button_dialogLoginActivity).setOnClickListener(this::onClick);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        addIPAddressTextInput = view.findViewById(R.id.ip_address_editText);
        addIPAddressTextInput.setText(authRepository.loadIP());

        infoAboutIPTextView = view.findViewById(R.id.info_about_ip_dialog_login);
        infoAboutIPTextView.setText("");

        imageViewDialogTitle = view.findViewById(R.id.imageView_dialog_title);
        imageViewDialogTitle.setColorFilter(getResources().getColor(R.color.colorAccent));

        progressBar = view.findViewById(R.id.progressBar_dialogLogin);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.confirm_ip_button_dialogLoginActivity):
                if (!addIPAddressTextInput.getText().toString().isEmpty()) {

                    checkConnection(addIPAddressTextInput.getText().toString());
//                    authRepository.saveIP(addIPAddressTextInput.getText().toString());

                    infoAboutIPTextView.setText("");
                } else {
                    infoAboutIPTextView.setTextColor(getResources().getColor(R.color.colorRed));
                    infoAboutIPTextView.setText("Please, enter IP address");
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        cancelTask();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        cancelTask();
        super.onDestroyView();
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

        asyncTask = new AsyncTask<Void, ConnectionState, ConnectionState>() {
            @Override
            protected ConnectionState doInBackground(Void... voids) {
                Log.d(LOG_TAG, "Doing request to server. IP Address: " + baseUrl);
                call = serverConnectionAPI.fetchAlarmEventList();
                try {
                    retrofit2.Response<Response> responseFromServer = call.execute();
                    Response response = responseFromServer.body();
                    Log.d(LOG_TAG, "response: " + response.getData());

                    if (!response.getData().isEmpty()) {
                        Log.d(LOG_TAG, "Connection Successful" + response.getData());
                        message = ConnectionState.CONNECTION_SUCCESS;
                    } else {

                        message = ConnectionState.EMPTY_RESPONSE;
                    }
                } catch (UnknownHostException e) {
                    Log.d(LOG_TAG, "Connection NOT Successful:");
                    Log.d(LOG_TAG, "Exeption: " + e);
//                    message = "Connection NOT Successful. \nInvalid ip address";
                    message = ConnectionState.INVALID_IP_ADDRESS;
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(LOG_TAG, "Connection NOT Successful:");
                    Log.d(LOG_TAG, "Exeption: " + e);
//                    message = "Connection NOT Successful. \nNo response from server";
                    message = ConnectionState.SERVER_NOT_RESPONSE;
                    e.printStackTrace();
                }
                return message;
            }

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(ConnectionState state) {
                switch (state){
                    case CONNECTION_SUCCESS:
                        infoAboutIPTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
                        infoAboutIPTextView.setText("Connection Successful");
                        authRepository.saveIP(addIPAddressTextInput.getText().toString());
                        dismiss();
                        break;
                    case EMPTY_RESPONSE:
                        Log.d(LOG_TAG, "Connection NOT Successful: empty response from server.");
                        infoAboutIPTextView.setTextColor(getResources().getColor(R.color.colorRed));
                        infoAboutIPTextView.setText("Connection NOT Successful: empty response from server.");
                        authRepository.saveIP(addIPAddressTextInput.getText().toString());
                        break;
                    case INVALID_IP_ADDRESS:
                        infoAboutIPTextView.setTextColor(getResources().getColor(R.color.colorRed));
                        infoAboutIPTextView.setText("Connection NOT Successful. \nInvalid ip address");
                        break;
                    case SERVER_NOT_RESPONSE:
                        infoAboutIPTextView.setTextColor(getResources().getColor(R.color.colorRed));
                        infoAboutIPTextView.setText("Connection NOT Successful. \nNo response from server");
                        authRepository.saveIP(addIPAddressTextInput.getText().toString());
                        break;
                }

                progressBar.setVisibility(View.INVISIBLE);
                super.onPostExecute(state);
            }

            @Override
            protected void onProgressUpdate(ConnectionState... values) {

                super.onProgressUpdate(values);
            }
        }.execute();
    }


    public void cancelTask() {
        if (asyncTask != null){
            asyncTask.cancel(true);
            call.cancel();
        }
    }

    enum ConnectionState{
        CONNECTION_SUCCESS, EMPTY_RESPONSE, INVALID_IP_ADDRESS, SERVER_NOT_RESPONSE;

    }
}
