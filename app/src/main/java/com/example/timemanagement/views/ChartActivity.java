package com.example.timemanagement.views;

import static com.example.timemanagement.views.Timer.totalTimeSpent;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.timemanagement.R;
import com.example.timemanagement.database.Task;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ChartActivity extends AppCompatActivity {
    Button btntoday,btnweekly,btnmonthly;
    EditText edtdate;
    private BarChart barChart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        PieChart pieChart = findViewById(R.id.chart);
        barChart = findViewById(R.id.chart2);
        btntoday = findViewById(R.id.today);
        btnweekly =findViewById(R.id.weekly);
        btnmonthly =findViewById(R.id.monthly);
        edtdate = findViewById(R.id.edtdate);
        edtdate.setInputType(InputType.TYPE_NULL);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        edtdate.setText(String.format("%02d", currentMonth + 1));
        LocalDate selectedDate = LocalDate.now();
        updateBarChartForMonth(barChart,selectedDate);
        edtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a Calendar instance for the current date
                Calendar calendar = Calendar.getInstance();

                // Create a month picker dialog using the Calendar instance
                DatePickerDialog dialog = new DatePickerDialog(ChartActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Extract the selected month (0-based)
                                int selectedMonth = month;
                                LocalDate selectedDate =LocalDate.of(year,month+1, 1);

                                // Update the EditText with the selected month (formatted)
                                edtdate.setText(String.format("%02d", selectedMonth + 1)); // Add 1 to make it user-friendly

                                // Update the BarChart data based on the selected month
                                updateBarChartForMonth(barChart, selectedDate);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));


                dialog.show();
            }
        });
        bottomNavigationView.setBackground(null);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedTab")) {
            String selectedTab = intent.getStringExtra("selectedTab");
            // Chọn chỉ mục tương ứng trong BottomNavigationView
            if ("charTab".equals(selectedTab)) {
                bottomNavigationView.setSelectedItemId(R.id.thongke2); // R.id.settingMenuItem là ID của mục Setting trong menu
            }
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home2) {
                startActivity(new Intent(ChartActivity.this, WeekViewActivity.class));
                finish();
            } else if (itemId == R.id.thongke2) {
                return true;
            } else if (itemId == R.id.pomodoro2) {
                Intent charIntent = new Intent(ChartActivity.this, Timer.class);
                charIntent.putExtra("selectedTab", "timerTab");
                startActivity(charIntent);
                finish();
            }else if (itemId == R.id.caidat2) {
                Intent charIntent = new Intent(ChartActivity.this, SettingActivity.class);
                charIntent.putExtra("selectedTab", "settingTab");
                startActivity(charIntent);
                finish();
            }
            return true;
        });
        btntoday.setOnClickListener(v -> updateChart(pieChart, Task.taskForDate(LocalDate.now())));
        btnweekly.setOnClickListener(v -> updateChart(pieChart, Task.getTasksForWeek()));
        btnmonthly.setOnClickListener(v -> updateChart(pieChart, Task.getTasksForMonth()));
        updateChart(pieChart, Task.taskForDate(LocalDate.now()));


    }
    private void updateBarChartForMonth(BarChart barChart,LocalDate month){
        Map<Task, Float> taskCompletionStatistics = Task.getTaskCompletionStatisticsForMonth(month);
        // Prepare the data for the BarChart
        ArrayList<BarEntry> entries = new ArrayList<>();
        int index = 0;
        for (Map.Entry<Task, Float> entry : taskCompletionStatistics.entrySet()) {
            float completionPercentage = entry.getValue();
            entries.add(new BarEntry(index++, completionPercentage));
        }
        ArrayList<Integer> colors = new ArrayList<>();
        for (Task task : taskCompletionStatistics.keySet()) {
            int colorInt;
            try {
                // Assuming color is stored as a hex string (e.g., "#FF0000")
                colorInt = Color.parseColor(task.getColor());
            } catch (IllegalArgumentException e) {
                // Handle invalid color values (e.g., set a default color)
                colorInt = Color.GRAY;
            }
            colors.add(colorInt);
        }
        BarDataSet dataSet = new BarDataSet(entries, "Task Completion Percentages");
        dataSet.setColors(colors);

        ArrayList<String> labels = new ArrayList<>();
        for (Task task : taskCompletionStatistics.keySet()) {
            LocalDate taskDate = task.getDate();
            int taskMonth = taskDate.getMonthValue();
            if (taskMonth == month.getMonthValue()) {
                String label = String.format("%02d-%02d", taskDate.getDayOfMonth(),taskDate.getMonthValue());
                labels.add(label);
            }
        }
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        barChart.invalidate();
    }
    private void updateChart(PieChart pieChart, ArrayList<Task> tasks){
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        // Tạo một HashMap để lưu trữ số lượng công việc theo từng danh mục
        HashMap<String, Integer> categoryCounts = new HashMap<>();

        // Lấy danh sách công việc


        // Đếm số lượng công việc theo từng danh mục
        for (Task task : tasks) {
            String category = task.getCategory();
            if (categoryCounts.containsKey(category)) {
                categoryCounts.put(category, categoryCounts.get(category) + 1);
            } else {
                categoryCounts.put(category, 1);
            }
        }

        // Tạo một danh sách các PieEntry
        ArrayList<PieEntry> entries = new ArrayList<>();

        // Thêm dữ liệu vào danh sách PieEntry
        for (Map.Entry<String, Integer> entry : categoryCounts.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        // Tạo PieDataSet
        PieDataSet dataSet = new PieDataSet(entries, "Task Categories");

        dataSet.setColors(colors);
        PieData pieData = new PieData(dataSet);


        pieChart.setData(pieData);
        pieChart.invalidate(); // Làm mới biểu đồ
    }
}