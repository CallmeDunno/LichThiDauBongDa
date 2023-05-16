package com.example.lichthidaubongda.fragment.chart.model;

import com.google.gson.annotations.SerializedName;

public class Stat {
    @SerializedName("draw")
    private int draw;
    @SerializedName("lose")
    private int lose;
    @SerializedName("win")
    private int win;
    @SerializedName("goals_for")
    private int goalsFor;       //Bàn thắng
    @SerializedName("goals_against")
    private int goalsAgainst;   //Bàn thua
    @SerializedName("matchs_played")
    private int matchesPlayed;  //Số trận đã thi đấu

    public Stat(int draw, int lose, int win, int goalsFor, int goalsAgainst, int matchesPlayed) {
        this.draw = draw;
        this.lose = lose;
        this.win = win;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.matchesPlayed = matchesPlayed;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }
}
