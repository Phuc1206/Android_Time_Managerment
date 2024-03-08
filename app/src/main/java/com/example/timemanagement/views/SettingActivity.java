package com.example.timemanagement.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.timemanagement.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        BottomNavigationView bottomNavigationView= findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        Intent intent = getIntent();
        Intent timerIntent = getIntent();
        Intent charIntent = getIntent();
        if (intent != null && intent.hasExtra("selectedTab")) {
            String selectedTab = intent.getStringExtra("selectedTab");
            // Chọn chỉ mục tương ứng trong BottomNavigationView
            if ("settingTab".equals(selectedTab)) {
                bottomNavigationView.setSelectedItemId(R.id.caidat2); // R.id.settingMenuItem là ID của mục Setting trong menu
            }
        }
        if (charIntent != null && charIntent.hasExtra("selectedTab")) {
            String selectedTab = charIntent.getStringExtra("selectedTab");
            // Chọn chỉ mục tương ứng trong BottomNavigationView
            if ("settingTab".equals(selectedTab)) {
                bottomNavigationView.setSelectedItemId(R.id.caidat2); // R.id.settingMenuItem là ID của mục Setting trong menu
            }
        }
        if (timerIntent != null && timerIntent.hasExtra("selectedTab")) {
            String selectedTab = timerIntent.getStringExtra("selectedTab");
            // Chọn chỉ mục tương ứng trong BottomNavigationView
            if ("settingTab".equals(selectedTab)) {
                bottomNavigationView.setSelectedItemId(R.id.caidat2); // R.id.settingMenuItem là ID của mục Setting trong menu
            }
        }
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home2) {
                Intent setIntent = new Intent(SettingActivity.this, WeekViewActivity.class);
                setIntent.putExtra("selectedTab", "homeTab");
                startActivity(setIntent);
                finish();
            } else if (itemId == R.id.thongke2) {
                Intent setIntent = new Intent(SettingActivity.this, ChartActivity.class);
                setIntent.putExtra("selectedTab", "charTab");
                startActivity(setIntent);
                finish();
            } else if (itemId == R.id.pomodoro2) {
                Intent setIntent = new Intent(SettingActivity.this, Timer.class);
                setIntent.putExtra("selectedTab", "timerTab");
                startActivity(setIntent);
                finish();
            }else if (itemId == R.id.caidat2){
                return true;
            }

            return true;
        });
    }
}