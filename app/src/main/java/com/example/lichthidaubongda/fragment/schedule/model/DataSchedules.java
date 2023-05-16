package com.example.lichthidaubongda.fragment.schedule.model;

import com.google.gson.annotations.SerializedName;

public class DataSchedules {
    @SerializedName("data")
    private IdSchedule dataIdSchedule;

    public DataSchedules(IdSchedule dataIdSchedule) {
        this.dataIdSchedule = dataIdSchedule;
    }

    public IdSchedule getDataIdSchedule() {
        return dataIdSchedule;
    }

    public void setDataIdSchedule(IdSchedule dataIdSchedule) {
        this.dataIdSchedule = dataIdSchedule;
    }
}
