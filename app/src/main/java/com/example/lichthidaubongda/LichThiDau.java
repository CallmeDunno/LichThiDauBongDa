package com.example.lichthidaubongda;

import java.util.Comparator;

public class LichThiDau{
    private String id;
    private String team_1;
    private String logo_1;
    private String team_2;
    private String logo_2;
    private DateTime dateTime;
    private String round;
    private String more;

    public LichThiDau(String id, String team_1, String logo_1, String team_2, String logo_2, String time, String date, String round, String more) {
        this.id = id;
        this.team_1 = team_1;
        this.logo_1 = logo_1;
        this.team_2 = team_2;
        this.logo_2 = logo_2;
        this.dateTime = new DateTime(date, time);
        this.round = round;
        this.more = more;
    }

    public LichThiDau(String team_1, String logo_1, String team_2, String logo_2, String time, String date, String round, String more) {
        this.team_1 = team_1;
        this.logo_1 = logo_1;
        this.team_2 = team_2;
        this.logo_2 = logo_2;
        this.dateTime = new DateTime(date, time);
        this.round = round;
        this.more = more;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTeam_1() {
        return team_1;
    }

    public void setTeam_1(String team_1) {
        this.team_1 = team_1;
    }

    public String getLogo_1() {
        return logo_1;
    }

    public void setLogo_1(String logo_1) {
        this.logo_1 = logo_1;
    }

    public String getTeam_2() {
        return team_2;
    }

    public void setTeam_2(String team_2) {
        this.team_2 = team_2;
    }

    public String getLogo_2() {
        return logo_2;
    }

    public void setLogo_2(String logo_2) {
        this.logo_2 = logo_2;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public static class DateOrder implements Comparator<LichThiDau>{

        @Override
        public int compare(LichThiDau a, LichThiDau b) {
            return a.dateTime.compareTo(b.dateTime);
        }
    }
}
