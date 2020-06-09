package com.example.karinarkzmobile.eventInfoActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.karinarkzmobile.R;
import com.example.karinarkzmobile.data.AlarmData;
import com.example.karinarkzmobile.mainActivity.alarmEventsFragment.AlarmEventsFragment;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class EventInfoActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private OrdinaryPhotoFragment ordinaryPhotoFragment;
    private TemperaturePhotoFragment temperaturePhotoFragment;

    private TextView locationEventInfo, timeOfAlarmEventInfo, detectedTemperature;

//    private TextView tempPhoto, ordPhoto;

    private AlarmData alarmEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        locationEventInfo = findViewById(R.id.location_event_info_textView);

        timeOfAlarmEventInfo = findViewById(R.id.time_of_alarm_event_textView);
        detectedTemperature = findViewById(R.id.detected_temperature_textView);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null){
            alarmEvent = (AlarmData) arguments.getSerializable(AlarmEventsFragment.EXT_ALARM_DATA);
        }
        timeOfAlarmEventInfo.setText(alarmEvent.getEventtime());
        detectedTemperature.setText(alarmEvent.getTemp());

        locationEventInfo.setText(R.string.alert_event_information);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

//        tempPhoto = findViewById(R.id.temp_photo);
//        ordPhoto = findViewById(R.id.ord_photo);
//        tempPhoto.setText(alarmEvent.getTemperaturePhotoURL());
//        ordPhoto.setText(alarmEvent.getOrdinaryPhotoURL());

        ordinaryPhotoFragment = new OrdinaryPhotoFragment();
        ordinaryPhotoFragment.setArguments(getIntent().getExtras());

        temperaturePhotoFragment = new TemperaturePhotoFragment();
        temperaturePhotoFragment.setArguments(getIntent().getExtras());

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(temperaturePhotoFragment, getString(R.string.temperature_photo));
        viewPagerAdapter.addFragment(ordinaryPhotoFragment, getString(R.string.ordinary_photo));

        viewPager.setAdapter(viewPagerAdapter);

        findViewById(R.id.arrowBackImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();


        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }

    @Override
    protected void onDestroy() {
        Glide.get(this).clearMemory();
        Fresco.getImagePipeline().clearCaches();
        super.onDestroy();
    }
}
