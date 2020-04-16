package com.example.karinarkzmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);

        findViewById(R.id.menuImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);

                //Вот тут потом разрисовать нужно разным цветом иконки путем изменения их цветов в папке drawable
                NavigationView navigationView = findViewById(R.id.navigationView);
                navigationView.setItemIconTintList(null);
            }
        });
    }
}
