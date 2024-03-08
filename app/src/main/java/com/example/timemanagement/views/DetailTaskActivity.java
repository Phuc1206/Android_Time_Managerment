package com.example.timemanagement.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.timemanagement.R;
import com.example.timemanagement.database.SQLiteManager;
import com.example.timemanagement.database.Task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class DetailTaskActivity extends AppCompatActivity {
    TextView tvTitle,tvDate,tvTime,tvTimeEnd,tvtaskpercent;
    Button btnchange;
    Button btnSave,btndel;
    Task selectedTask;
    EditText edtTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);
        setControl();
        checkForEditTask();
        setEvent();
    }

    private void setEvent() {
        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedTask.setDeleted(new Date());
                SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(DetailTaskActivity.this);
                sqLiteManager.updateNoteInDB(selectedTask);
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteManager sqLiteManager =SQLiteManager.instanceOfDatabase(DetailTaskActivity.this);
                String title = String.valueOf(edtTitle.getText());
                String time = String.valueOf(tvTime.getText());
                String date = String.valueOf(tvDate.getText());
                selectedTask.setTitle(title);
                selectedTask.setTimes(LocalTime.parse(time));
                selectedTask.setDate(LocalDate.parse(date));
                sqLiteManager.updateNoteInDB(selectedTask);
                finish();
            }
        });
        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTitle.setVisibility(View.GONE);
                edtTitle.setVisibility(View.VISIBLE);
                edtTitle.setText(tvTitle.getText());
            }
        });

    }

    private void checkForEditTask() {
        Intent previousIntent = getIntent();
        int passedTaskID = previousIntent.getIntExtra(Task.TASK_EDIT_EXTRA,-1);
        selectedTask = Task.getTaskForID(passedTaskID);
        if (selectedTask != null){
            tvTitle.setText(selectedTask.getTitle());
            tvTime.setText(selectedTask.getTimes().toString());
            tvDate.setText(selectedTask.getDate().toString());
            tvTimeEnd.setText(selectedTask.getTime_end().toString());
        }
    }

    private void setControl() {
        tvTitle = findViewById(R.id.tvDetailTitle);
        tvDate = findViewById(R.id.tvDetailDate);
        tvTime = findViewById(R.id.tvDetailTime);
        btnchange = findViewById(R.id.btnchange);
        btndel =findViewById(R.id.btnDeleteTask);
        btnSave = findViewById(R.id.btnComplete);
        edtTitle = findViewById(R.id.edtDetailTitle);
        tvTimeEnd = findViewById(R.id.tvDetailTimeEmd);

    }
}