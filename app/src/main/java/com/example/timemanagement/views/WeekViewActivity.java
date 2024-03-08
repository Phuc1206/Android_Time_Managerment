package com.example.timemanagement.views;

import static com.example.timemanagement.adapter.CalendarUtils.daysInWeekArray;
import static com.example.timemanagement.adapter.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.timemanagement.adapter.CalendarAdapter;
import com.example.timemanagement.adapter.CalendarUtils;
import com.example.timemanagement.R;
import com.example.timemanagement.database.SQLiteManager;
import com.example.timemanagement.database.Task;
import com.example.timemanagement.adapter.TaskAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.util.ArrayList;


public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView taskListView;
    private TaskAdapter taskAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        CalendarUtils.selectedDate = LocalDate.now();
        initWidgets();

        setWeekView();
        loadFromDBToMemory();
        setEvent();
        BottomNavigationView bottomNavigationView= findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                return true;

            } else if (itemId == R.id.thongke) {
                Intent intent = new Intent(WeekViewActivity.this, ChartActivity.class);
                intent.putExtra("selectedTab", "charTab"); // "settingTab" là một ví dụ, bạn có thể chọn giá trị khác
                startActivity(intent);
                finish();
            } else if(itemId == R.id.pomodoro){
                Intent intent = new Intent(WeekViewActivity.this, Timer.class);
                intent.putExtra("selectedTab", "timerTab"); // "settingTab" là một ví dụ, bạn có thể chọn giá trị khác
                startActivity(intent);
                finish();
            }
            else if(itemId == R.id.caidat){
                Intent intent = new Intent(WeekViewActivity.this, SettingActivity.class);
                intent.putExtra("selectedTab", "settingTab"); // "settingTab" là một ví dụ, bạn có thể chọn giá trị khác
                startActivity(intent);
                finish();
            }
            return true;
        });
        NotificationManager manager= (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        manager.cancelAll();

    }

    private void setEvent() {
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task selectedTask = (Task) taskListView.getItemAtPosition(position);
                Intent editTaskIntent = new Intent(getApplicationContext(), DetailTaskActivity.class);
                editTaskIntent.putExtra(Task.TASK_EDIT_EXTRA, selectedTask.getId());
                startActivity(editTaskIntent);
            }
        });
    }

    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateNoteListArray();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        taskListView = findViewById(R.id.taskListView);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setTaskAdpater();
    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onStart();
        setTaskAdpater();
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (taskAdapter != null) {
//            taskListView.setAdapter(null); // Detach the adapter from the ListView
//            taskAdapter.clear(); // Clear the adapter's data
//            taskAdapter = null;}
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (taskAdapter != null) {
//            taskListView.setAdapter(null); // Detach the adapter from the ListView
//            taskAdapter.clear(); // Clear the adapter's data
//            taskAdapter = null;}
//    }


    private void setTaskAdpater()
    {
        ArrayList<Task> dailyTasks = Task.taskForDate(CalendarUtils.selectedDate);
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        taskAdapter = new TaskAdapter(getApplicationContext(), dailyTasks);
        taskListView.setAdapter(taskAdapter);
    }

    public void newTaskAction(View view)
    {
        startActivity(new Intent(this, TaskEditActivity.class));
    }
}