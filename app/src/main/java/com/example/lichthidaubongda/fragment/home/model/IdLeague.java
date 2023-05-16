package com.example.lichthidaubongda.fragment.home.model;

import com.google.gson.annotations.SerializedName;

public class IdLeague {
    @SerializedName("4335")
    private InforLeague inforLeague;

    public IdLeague(InforLeague inforLeague) {
        this.inforLeague = inforLeague;
    }

    public InforLeague getInforLeague() {
        return inforLeague;
    }

    public void setInforLeague(InforLeague inforLeague) {
        this.inforLeague = inforLeague;
    }
}
