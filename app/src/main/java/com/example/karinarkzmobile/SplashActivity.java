package com.example.karinarkzmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.karinarkzmobile.loginActivity.LoginActivity;
import com.example.karinarkzmobile.mainActivity.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private ISharedPreferences authRepository = ServiceLocator.getAuthRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (authRepository.loadToken().isEmpty()){
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            Log.d("splashLog", "Нет токена, запускаю LoginActivity");
            finish();
        } else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            Log.d("splashLog", "Есть записанный токен/авторизация, запускаю MainActivity");
            finish();
        }
    }
}
