package com.example.lichthidaubongda.fragment.home.model;

import com.google.gson.annotations.SerializedName;

public class DataLeague {
    @SerializedName("data")
    private IdLeague dataIdLeague;

    public DataLeague(IdLeague dataIdLeague) {
        this.dataIdLeague = dataIdLeague;
    }

    public IdLeague getDataIdLeague() {
        return dataIdLeague;
    }

    public void setDataIdLeague(IdLeague dataIdLeague) {
        this.dataIdLeague = dataIdLeague;
    }
}
