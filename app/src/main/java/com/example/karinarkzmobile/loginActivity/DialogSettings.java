package com.example.karinarkzmobile.loginActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DialogSettings extends DialogFragment implements View.OnClickListener {

    private TextInputEditText addIPAddressTextInput;
    private ISharedPreferences authRepository = ServiceLocator.getAuthRepository();
    private TextView infoAboutIPTextView;
    private ImageView imageViewDialogTitle;
    ProgressBar progressBar;
    private final String LOG_TAG = "MyLogs";
    private Call<Response> call;


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
//                    progressBar.setVisibility(View.VISIBLE);
//                    checkConnection(addIPAddressTextInput.getText().toString());
                    authRepository.saveIP(addIPAddressTextInput.getText().toString());
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
        if (call != null && !call.isCanceled()){
            call.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        if (call != null && !call.isCanceled()){
            call.cancel();
        }
        super.onDestroyView();
    }

    private void checkConnection(String ip) {

        String baseUrl = "http://" + ip + ":18001";
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .build();
        ServerConnectionAPI serverConnectionAPI = retrofit.create(ServerConnectionAPI.class);
        call = serverConnectionAPI.fetchAlarmEventList();
        call.enqueue(new Callback<Response>() {

            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, "response Successful");
                    authRepository.saveIP(addIPAddressTextInput.getText().toString());
                    progressBar.setVisibility(View.INVISIBLE);
                    dismiss();
                } else {
                    Log.d(LOG_TAG, "response NOT Successful");
                    infoAboutIPTextView.setText("No response from the server.\nPerhaps the server is not working properly");
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d(LOG_TAG, "No connection");
                authRepository.saveIP(addIPAddressTextInput.getText().toString());
                infoAboutIPTextView.setTextColor(getResources().getColor(R.color.colorRed));
                infoAboutIPTextView.setText("No connection to the server.\nVerify the correct ip address.");
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

}
