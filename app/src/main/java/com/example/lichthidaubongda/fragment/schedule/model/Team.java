package com.example.lichthidaubongda.fragment.schedule.model;

import com.google.gson.annotations.SerializedName;

public class Team {
    @SerializedName("team_name")
    private String teamName;
    @SerializedName("team_name_full")
    private String teamNameFull;
    @SerializedName("logo")
    private String logo;

    public Team(String teamName, String teamNameFull, String logo) {
        this.teamName = teamName;
        this.teamNameFull = teamNameFull;
        this.logo = logo;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamNameFull() {
        return teamNameFull;
    }

    public void setTeamNameFull(String teamNameFull) {
        this.teamNameFull = teamNameFull;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
