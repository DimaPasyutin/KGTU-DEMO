package com.example.kgtu.data.pojo;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

public class DaysBeforeExams {

    private Timestamp time;
    private  String wish;

    public DaysBeforeExams() {
    }

    public DaysBeforeExams(Timestamp time, String wish) {
        this.time = time;
        this.wish = wish;
    }

    public Timestamp getTime() {
        return new Timestamp(time.getSeconds() - System.currentTimeMillis() / 1000, 0);
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }

    @NonNull
    @Override
    public String toString() {
        return wish;
    }
}
