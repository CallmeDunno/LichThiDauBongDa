package com.example.lichthidaubongda.fragment.schedule.model;

import com.google.gson.annotations.SerializedName;

public class Score {
    @SerializedName("fulltime")
    private String fullTime;

    public Score(String fullTime) {
        this.fullTime = fullTime;
    }

    public String getFullTime() {
        return fullTime;
    }

    public void setFullTime(String fullTime) {
        this.fullTime = fullTime;
    }
}
