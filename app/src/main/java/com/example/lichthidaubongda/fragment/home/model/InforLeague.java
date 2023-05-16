package com.example.lichthidaubongda.fragment.home.model;

import com.example.lichthidaubongda.fragment.chart.model.Chart;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InforLeague {
    @SerializedName("league_detail")
    private League league;
    @SerializedName("data")
    private List<Chart> charts;

    public InforLeague(League league, List<Chart> charts) {
        this.league = league;
        this.charts = charts;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public List<Chart> getCharts() {
        return charts;
    }

    public void setCharts(List<Chart> charts) {
        this.charts = charts;
    }
}
