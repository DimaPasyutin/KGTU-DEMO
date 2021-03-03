package com.example.kgtu.data.pojo;

import androidx.annotation.NonNull;

import rx.Single;

public class DaysBeforeExams {

    private Integer time;
    private  String wish;

    public DaysBeforeExams() {
    }

    public DaysBeforeExams(Integer time, String wish) {
        this.time = time;
        this.wish = wish;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
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
