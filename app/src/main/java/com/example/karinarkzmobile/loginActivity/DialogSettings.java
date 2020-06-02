package com.example.karinarkzmobile.loginActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.example.karinarkzmobile.IShowProgressBar;
import com.example.karinarkzmobile.R;
import com.example.karinarkzmobile.ServiceLocator;
import com.google.android.material.textfield.TextInputEditText;

public class DialogSettings extends DialogFragment implements View.OnClickListener, IShowProgressBar {

    private TextInputEditText addIPAddressTextInput;
    private ISharedPreferences authRepository = ServiceLocator.getAuthRepository();
    private TextView infoAboutIPTextView;
    private ImageView imageViewDialogTitle;
    ProgressBar progressBar;

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
                    authRepository.saveIP(addIPAddressTextInput.getText().toString());
                    infoAboutIPTextView.setText("IP address " + authRepository.loadIP() + " saved");

                    dismiss();
                } else {
                    infoAboutIPTextView.setTextColor(getResources().getColor(R.color.colorRed));
                    infoAboutIPTextView.setText("Please, enter IP address");
                }
                break;
        }
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
