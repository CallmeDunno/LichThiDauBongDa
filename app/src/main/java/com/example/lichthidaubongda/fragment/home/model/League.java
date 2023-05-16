package com.example.lichthidaubongda.fragment.home.model;

import com.google.gson.annotations.SerializedName;

public class League {
    @SerializedName("league_id")
    private int leagueID;
    @SerializedName("logo")
    private String logo;
    @SerializedName("name")
    private String name;
    @SerializedName("season")
    private int season;
    @SerializedName("season_start")
    private String seasonStart;
    @SerializedName("season_end")
    private String seasonEnd;

    public League(int leagueID, String logo, String name, int season, String seasonStart, String seasonEnd) {
        this.leagueID = leagueID;
        this.logo = logo;
        this.name = name;
        this.season = season;
        this.seasonStart = seasonStart;
        this.seasonEnd = seasonEnd;
    }

    public int getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(int leagueID) {
        this.leagueID = leagueID;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getSeasonStart() {
        return seasonStart;
    }

    public void setSeasonStart(String seasonStart) {
        this.seasonStart = seasonStart;
    }

    public String getSeasonEnd() {
        return seasonEnd;
    }

    public void setSeasonEnd(String seasonEnd) {
        this.seasonEnd = seasonEnd;
    }
}
