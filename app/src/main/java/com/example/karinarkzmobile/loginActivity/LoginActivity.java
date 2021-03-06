package com.example.karinarkzmobile.loginActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.karinarkzmobile.App;
import com.example.karinarkzmobile.EventService;
import com.example.karinarkzmobile.R;
import com.example.karinarkzmobile.ServiceLocator;
import com.example.karinarkzmobile.mainActivity.MainActivity;
import com.example.karinarkzmobile.mainActivity.alarmEventsFragment.IAlarmEvents;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Map;

public class LoginActivity extends AppCompatActivity implements ISettingsDialog {

    private TextInputEditText loginTextInputLayout, passwordTextInputLayout;
    private Button signInButton;
    private ImageView settingsImageView;
    private DialogSettings dialogSettings;
    private IAlarmEvents.Repository repository = ServiceLocator.getRepository();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginTextInputLayout = findViewById(R.id.loginEditText);
        passwordTextInputLayout = findViewById(R.id.passwordEditText);
        settingsImageView = findViewById(R.id.login_activity_settings_imageView);
        settingsImageView.setColorFilter(getResources().getColor(R.color.colorPrimary));

        settingsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSettings = new DialogSettings(LoginActivity.this);
                dialogSettings.show(getSupportFragmentManager(), "dialogSettings");
            }
        });

//        if (ServiceLocator.getAuthRepository().loadIP().isEmpty()) {
//            Snackbar.make(findViewById(R.id.linear_layout_login), "IP address not saved, connection to server is not possible",
//                    BaseTransientBottomBar.LENGTH_LONG).show();
//        }

        signInButton = findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = App.getInstance().getAuthMap();
                if (!ServiceLocator.getAuthRepository().loadIP().isEmpty()){

                    if (map.containsKey(loginTextInputLayout.getText().toString())
                            && map.get(loginTextInputLayout.getText().toString()).equals(passwordTextInputLayout.getText().toString())) {
                        login();
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.invalid_login_or_password, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Snackbar.make(findViewById(R.id.linear_layout_login), R.string.ip_address_not_saved,
                            BaseTransientBottomBar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        if (ServiceLocator.getAuthRepository().loadIP().isEmpty()) {

            playAnimation(settingsImageView);


            settingsImageView.setColorFilter(getResources().getColor(R.color.colorRed));
            Snackbar.make(findViewById(R.id.linear_layout_login), R.string.ip_address_not_saved,
                    BaseTransientBottomBar.LENGTH_LONG).show();
        } else {
            settingsImageView.setColorFilter(getResources().getColor(R.color.colorPrimary));
            Snackbar.make(findViewById(R.id.linear_layout_login), R.string.ip_address_saved,
                    BaseTransientBottomBar.LENGTH_LONG).show();
        }
        super.onResume();
    }

    private void playAnimation(ImageView settingsImageView) {
        YoYo.with(Techniques.Pulse)
                .duration(500)
                .repeat(5)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .playOn(settingsImageView);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void login(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        repository.updateUrl();
        if (!isMyServiceRunning(EventService.class)) {
            App.getInstance().startEventService();
            Log.d("stLog", "Сервис стартовал");
        } else
            Log.d("stLog", "Сервис уже запущен");
        finish();
    }

    @Override
    public void onSaveIPAddress(boolean successfulConnection) {
        if (!ServiceLocator.getAuthRepository().loadIP().isEmpty() && successfulConnection) {
            settingsImageView.setColorFilter(getResources().getColor(R.color.colorPrimary));
            Snackbar.make(findViewById(R.id.linear_layout_login), R.string.ip_address_saved,
                    BaseTransientBottomBar.LENGTH_LONG).show();
        } else {
            settingsImageView.setColorFilter(getResources().getColor(R.color.colorRed));
            Snackbar.make(findViewById(R.id.linear_layout_login), R.string.ip_address_not_saved,
                    BaseTransientBottomBar.LENGTH_LONG).show();
            playAnimation(settingsImageView);

        }


    }
}
