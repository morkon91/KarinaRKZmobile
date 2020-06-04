package com.example.karinarkzmobile.mainActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.karinarkzmobile.App;
import com.example.karinarkzmobile.EventService;
import com.example.karinarkzmobile.ISharedPreferences;
import com.example.karinarkzmobile.R;
import com.example.karinarkzmobile.ServiceLocator;
import com.example.karinarkzmobile.mainActivity.alarmEventsFragment.AlarmEventsFragment;
import com.example.karinarkzmobile.mainActivity.settingsFragment.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private ISharedPreferences authRepository = ServiceLocator.getAuthRepository();

    FragmentResume fragmentOnResume = FragmentResume.ALARM_EVENTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authRepository.saveToken("PLTCNM;fjGN:sKDBNKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        //Вот тут потом разрисовать нужно разным цветом иконки путем изменения их цветов в папке drawable
        navigationView.setItemIconTintList(null);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AlarmEventsFragment()).commit();
            navigationView.setCheckedItem(R.id.alarm_events);
        }

        findViewById(R.id.menuImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.alarm_events:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AlarmEventsFragment()).commit();
                fragmentOnResume = FragmentResume.ALARM_EVENTS;
                break;
            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                fragmentOnResume = FragmentResume.SETTINGS;
                break;
            case R.id.about_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutUsFragment()).commit();
                fragmentOnResume = FragmentResume.ABOUT_US;
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        ServiceLocator.getAuthRepository().deleteToken();
        Log.d("splashLog", ServiceLocator.getAuthRepository().loadToken());
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        switch (fragmentOnResume){

            case ALARM_EVENTS:
                openQuitDialog();
                break;
            case SETTINGS:
            case ABOUT_US:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AlarmEventsFragment()).commit();
                break;
        }
    }

    enum FragmentResume {
        ALARM_EVENTS, SETTINGS, ABOUT_US;
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(this);
        quitDialog.setTitle("Do you want to exit the application?");

        quitDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if (isMyServiceRunning(EventService.class)){
                    App.getInstance().stopEventService();
                }
                authRepository.deleteToken();
                finish();
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
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
