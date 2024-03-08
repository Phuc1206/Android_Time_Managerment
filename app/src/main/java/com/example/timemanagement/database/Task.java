package com.example.timemanagement.database;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Task implements Serializable {
    public static ArrayList<Task> tasksList = new ArrayList<>();
    public static String TASK_EDIT_EXTRA = "taskEdit";

    public Task() {
    }

    public static ArrayList<Task> taskForDate(LocalDate date)
    {
        ArrayList<Task> tasks = new ArrayList<>();

        for(Task task : tasksList)
        {
            if(task.getDate().equals(date) && task.getDeleted()==null)
                tasks.add(task);
        }

        return tasks;
    }
    public static ArrayList<Task> getTasksForWeek(){
        LocalDate today =LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);
        ArrayList<Task> tasksThisWeek = tasksList.stream().filter(task -> !task.getDate().isBefore(startOfWeek) && !task.getDate().isAfter(endOfWeek) && task.getDeleted()==null)
                .collect(Collectors.toCollection(ArrayList::new));
        return tasksThisWeek;
    }



    public static Map<Task, Float> getTaskCompletionStatisticsForMonth(LocalDate selectedDate) {
//        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = selectedDate.withDayOfMonth(1);
        LocalDate endOfMonth = selectedDate.withDayOfMonth(selectedDate.lengthOfMonth());

        ArrayList<Task> tasksThisMonth = tasksList.stream()
                .filter(task -> !task.getDate().isBefore(startOfMonth) && !task.getDate().isAfter(endOfMonth) && task.getDeleted() == null)
                .collect(Collectors.toCollection(ArrayList::new));

        Map<Task, Float> taskCompletionStatistics = new HashMap<>();
        for (Task task : tasksThisMonth) {
            taskCompletionStatistics.put(task, task.getTaskCompletionPercentage());
        }

        return taskCompletionStatistics;
    }
    public static ArrayList<Task> getTasksForMonth(){
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        ArrayList<Task> tasksThisMonth = tasksList.stream()
                .filter(task -> !task.getDate().isBefore(startOfMonth) && !task.getDate().isAfter(endOfMonth) && task.getDeleted()==null)
                .collect(Collectors.toCollection(ArrayList::new));
        return tasksThisMonth;
    }
    private float taskCompletionPercentage;
    private int time,breaks,longbreaks,shortbreak;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getBreaks() {
        return breaks;
    }

    public void setBreaks(int breaks) {
        this.breaks = breaks;
    }

    public int getLongbreaks() {
        return longbreaks;
    }

    public void setLongbreaks(int longbreaks) {
        this.longbreaks = longbreaks;
    }

    public int getShortbreak() {
        return shortbreak;
    }

    public float getTaskCompletionPercentage() {
        return taskCompletionPercentage;
    }

    public void setTaskCompletionPercentage(float taskCompletionPercentage) {
        this.taskCompletionPercentage = taskCompletionPercentage;
    }
    public void setShortbreak(int shortbreak) {
        this.shortbreak = shortbreak;
    }

    public void setTimes(LocalTime times) {
        this.times = times;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private int id;

    private String title,des,color,category;
    private Date deleted;
    public String getDes() {
        return des;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDes(String des) {
        this.des = des;
    }

    private LocalDate date;
    private LocalTime times,time_end;

    public LocalTime getTime_end() {
        return time_end;
    }

    public void setTime_end(LocalTime time_end) {
        this.time_end = time_end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }
    public static Task getTaskForID(int passedTaskID)
    {
        for(Task task: tasksList){
            if (task.getId()== passedTaskID)
                return task;
        }
        return null;
    }


    public Task(int id, String title, String des,String color, Date deleted, LocalDate date, LocalTime times,LocalTime time_end, String category,int time,int shortbreak,int longbreaks, int breaks , float taskCompletionPercentage) {
        this.id = id;
        this.title = title;
        this.des = des;
        this.color = color;
        this.deleted = deleted;
        this.date = date;
        this.times = times;
        this.time_end = time_end;
        this.category = category;
        this.time = time;
        this.shortbreak =shortbreak;
        this.longbreaks =longbreaks;
        this.breaks = breaks;
        this.taskCompletionPercentage =taskCompletionPercentage;
    }

    public Task(int id, String title, String des,String color, LocalDate date, LocalTime times,LocalTime time_end,String category,int time,int shortbreak,int longbreaks, int breaks,float taskCompletionPercentage)
    {
        this.id = id;
        this.title = title;
        this.des =des;
        this.color = color;
        this.date = date;
        this.times = times;
        this.time_end = time_end;
        this.category = category;
        deleted = null;
        this.time = time;
        this.shortbreak =shortbreak;
        this.longbreaks =longbreaks;
        this.breaks = breaks;
        this.taskCompletionPercentage =taskCompletionPercentage;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public LocalTime getTimes()
    {
        return times;
    }



}

