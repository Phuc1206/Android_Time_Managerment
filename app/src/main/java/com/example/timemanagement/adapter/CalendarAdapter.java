package com.example.timemanagement.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timemanagement.R;
import com.example.timemanagement.database.Task;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener)
    {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(days.size() > 15) //month view
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        else // week view
            layoutParams.height = (int) parent.getHeight();

        return new CalendarViewHolder(view, onItemListener, days);
    }


    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {

        final LocalDate date = days.get(position);
        if (date == null) {
            holder.dayOfMonth.setText("");
        } else {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            holder.dayOfMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            holder.dayOfMonth.setTranslationX(-10f); // Di chuyển văn bản sang phải
            holder.dayOfMonth.setTranslationY(-12f); // Di chuyển văn bản xuống dưới
            // Kiểm tra xem ngày có được chọn hay không
            if (date.equals(CalendarUtils.selectedDate)) {
                // Đặt padding để đảm bảo vị trí của văn bản không thay đổi
                holder.dayOfMonth.setPadding(
                        28,10,1,1);
                Drawable borderDrawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.day_border);
                holder.dayOfMonth.setBackground(borderDrawable);
            } else {
                holder.parentView.setBackgroundResource(0); // Đặt lại nền khi không được chọn
                // Đặt padding về 0 để đảm bảo vị trí của văn bản không thay đổi
                holder.dayOfMonth.setPadding(0, 0, 0, 0);
            }
        }
        final ArrayList<Task> tasks = Task.taskForDate(date);
        holder.dot.setVisibility(tasks.isEmpty() ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, LocalDate date);
    }
}
