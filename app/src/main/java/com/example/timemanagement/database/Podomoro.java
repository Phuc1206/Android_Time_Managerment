package com.example.timemanagement.database;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Podomoro extends Task implements Serializable {
    private int time,shortBreak,longBreak;
    private int breaks;

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

    public int getShortBreak() {
        return shortBreak;
    }

    public void setShortBreak(int shortBreak) {
        this.shortBreak = shortBreak;
    }

    public int getLongBreak() {
        return longBreak;
    }

    public void setLongBreak(int longBreak) {
        this.longBreak = longBreak;
    }

    public Podomoro() {
    }

//    public Podomoro(int id, String title, String des, String color, Date deleted, LocalDate date, LocalTime times, LocalTime time_end, String category, int time, int shortBreak, int longBreak, int breaks) {
//        super(id, title, des, color, deleted, date, times, time_end, category);
//        this.time = time;
//        this.shortBreak = shortBreak;
//        this.longBreak = longBreak;
//        this.breaks = breaks;
//    }
}
