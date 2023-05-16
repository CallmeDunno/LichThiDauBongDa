package com.example.lichthidaubongda.fragment.schedule.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Schedules {
    @SerializedName("data")
    private List<Schedule> schedules;

    public Schedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
