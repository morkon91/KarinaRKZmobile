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

import com.example.karinarkzmobile.App;
import com.example.karinarkzmobile.EventService;
import com.example.karinarkzmobile.R;
import com.example.karinarkzmobile.ServiceLocator;
import com.example.karinarkzmobile.mainActivity.MainActivity;
import com.example.karinarkzmobile.mainActivity.alarmEventsFragment.IAlarmEvents;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

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
                dialogSettings = new DialogSettings();
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
                if (!ServiceLocator.getAuthRepository().loadIP().isEmpty()){
                    if (loginTextInputLayout.getText().toString().equals("")
                            && passwordTextInputLayout.getText().toString().equals("")) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        repository.updateUrl();
                        if (!isMyServiceRunning(EventService.class)) {
                            App.getInstance().startEventService();
                            Log.d("stLog", "Сервис стартовал");
                        } else
                            Log.d("stLog", "Сервис уже запущен");
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Не верный логин или пароль", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Snackbar.make(findViewById(R.id.linear_layout_login), "IP address not saved, connection to server" +
                                    " is not possible. Please, enter IP address.",
                            BaseTransientBottomBar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        if (ServiceLocator.getAuthRepository().loadIP().isEmpty()) {
            settingsImageView.setColorFilter(getResources().getColor(R.color.colorPrimary));
            Snackbar.make(findViewById(R.id.linear_layout_login), "IP address not saved, connection to server is not possible",
                    BaseTransientBottomBar.LENGTH_LONG).show();
        } else {
            settingsImageView.setColorFilter(getResources().getColor(R.color.colorPrimary));
            Snackbar.make(findViewById(R.id.linear_layout_login), "IP address saved, you can connect to server",
                    BaseTransientBottomBar.LENGTH_LONG).show();
        }
        super.onResume();
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
}
