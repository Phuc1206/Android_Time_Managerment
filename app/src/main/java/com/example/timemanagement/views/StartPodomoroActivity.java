package com.example.timemanagement.views;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timemanagement.R;

public class StartPodomoroActivity extends AppCompatActivity {
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpodomoro);

        Button btn_start_pomodoro = findViewById(R.id.btn_start_pomodoro);
        btn_start_pomodoro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomSeekbar mySeekbarDialog = new CustomSeekbar();
                mySeekbarDialog.show(getSupportFragmentManager(), "");
            }
        });

    }
}

