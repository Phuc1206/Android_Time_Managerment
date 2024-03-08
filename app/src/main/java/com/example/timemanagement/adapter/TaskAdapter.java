package com.example.timemanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.timemanagement.R;
import com.example.timemanagement.database.Task;
import com.example.timemanagement.views.Timer;
import com.example.timemanagement.views.WeekViewActivity;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    public TaskAdapter(@NonNull Context context, List<Task> events)
    {
        super(context, 0, events);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Task task = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_cell, parent, false);
        CardView cardView = convertView.findViewById(R.id.layoutTask);
        TextView tvTitle = convertView.findViewById(R.id.tvtitle);
        TextView tvdes = convertView.findViewById(R.id.tvdes);
        TextView tvtime = convertView.findViewById(R.id.tvtime);
        ImageView img = convertView.findViewById(R.id.img_timer);
        TextView tvtaskpercent = convertView.findViewById(R.id.timepercent);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task selectedTask = getItem(position);  // Get the current Task object
                int time = selectedTask.getTime();
                int shortbreak = selectedTask.getShortbreak();
                int longbreak = selectedTask.getLongbreaks();
                int breaks = selectedTask.getBreaks();
                openTimerActivity(selectedTask, time, shortbreak, longbreak, breaks);

            }
        });
        tvTitle.setText(task.getTitle().toString());
        tvdes.setText(task.getDes().toString());
        tvtime.setText(task.getTimes().toString());
        String formattedPercentage = String.format("%.0f%%", task.getTaskCompletionPercentage());
        tvtaskpercent.setText(formattedPercentage);
//        if (isOvertime(task)) {
//            // Change the color of your view items
//            cardView.setBackgroundColor(Color.RED);
//        } else {
        cardView.setCardBackgroundColor(Color.parseColor(task.getColor()));
//        }

        return convertView;
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    private View getCustomView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task task = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_custom, parent, false);
        }

        TextView spinnerTitle = convertView.findViewById(R.id.spinnerTitle);
        TextView spinnerTime = convertView.findViewById(R.id.spinnerTime);

        spinnerTitle.setText(task.getTitle().toString());
        spinnerTime.setText(task.getTimes().toString());

        return convertView;
    }
    private void openTimerActivity(Task selectedTask, int time, int shortbreak, int longbreak, int breaks) {
        Intent intent = new Intent(getContext(), Timer.class);  // Replace TimerActivity with your actual activity name
        intent.putExtra(Task.TASK_EDIT_EXTRA, selectedTask.getId());
//        intent.putExtra("SELECTED_TASK", selectedTask);
//        intent.putExtra("TASK_ID", selectedTask.getId());
        intent.putExtra("TIME", time);
        intent.putExtra("SHORT_BREAK", shortbreak);
        intent.putExtra("LONG_BREAK", longbreak);
        intent.putExtra("BREAKS", breaks);
        intent.putExtra("selectedTab", "timerTab");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);

    }
    private boolean isOvertime(Task task) {
        return task.getTimes().isAfter(task.getTimes());
    }
}
