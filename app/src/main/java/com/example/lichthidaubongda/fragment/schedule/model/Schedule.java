package com.example.lichthidaubongda.fragment.schedule.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.lichthidaubongda.object.DateTime;
import com.google.gson.annotations.SerializedName;

public class Schedule {
    @SerializedName("home_team")
    private Team homeTeam;
    @SerializedName("away_team")
    private Team awayTeam;
    @SerializedName("status_short")
    private String status;
    @SerializedName("event_date")
    private String dateTime;
    @SerializedName("round_int")
    private int round;

    @SerializedName("score")
    private Score score;

    public Schedule(Team homeTeam, Team awayTeam, String status, String dateTime, int round, Score score) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.status = status;
        this.dateTime = dateTime;
        this.round = round;
        this.score = score;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public static final DiffUtil.ItemCallback<Schedule> SCHEDULE_DIFF_UTIL = new DiffUtil.ItemCallback<Schedule>() {
        @Override
        public boolean areItemsTheSame(@NonNull Schedule oldItem, @NonNull Schedule newItem) {
            return oldItem.getRound() == newItem.getRound() &&
                    oldItem.getStatus().equals(newItem.getStatus());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Schedule oldItem, @NonNull Schedule newItem) {
            return oldItem.getHomeTeam().getTeamName().equals(newItem.getHomeTeam().getTeamName()) &&
                    oldItem.getHomeTeam().getLogo().equals(newItem.getHomeTeam().getLogo()) &&
                    oldItem.getAwayTeam().getTeamName().equals(newItem.getHomeTeam().getTeamName()) &&
                    oldItem.getAwayTeam().getLogo().equals(newItem.getAwayTeam().getLogo()) &&
                    oldItem.getRound() == newItem.getRound() &&
                    oldItem.getDateTime().equals(newItem.getDateTime()) &&
                    oldItem.getStatus().equals(newItem.getStatus());
        }
    };
}
