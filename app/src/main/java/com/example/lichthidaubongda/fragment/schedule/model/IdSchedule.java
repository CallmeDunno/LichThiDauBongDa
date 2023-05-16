package com.example.lichthidaubongda.fragment.schedule.model;

import com.google.gson.annotations.SerializedName;

public class IdSchedule {
    @SerializedName("4335")
    private Schedules idSchedules;

    public IdSchedule(Schedules idSchedules) {
        this.idSchedules = idSchedules;
    }

    public Schedules getIdSchedules() {
        return idSchedules;
    }

    public void setIdSchedules(Schedules idSchedules) {
        this.idSchedules = idSchedules;
    }
}
