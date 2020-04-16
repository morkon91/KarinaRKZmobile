package com.example.karinarkzmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText loginTextInputLayout, passwordTextInputLayout;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginTextInputLayout = findViewById(R.id.loginEditText);
        passwordTextInputLayout = findViewById(R.id.passwordEditText);

        signInButton = findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginTextInputLayout.getText().toString().equals("")
                        && passwordTextInputLayout.getText().toString().equals("")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this, "Не верный логин или пароль",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
