package com.example.lichthidaubongda.object;

import androidx.annotation.NonNull;

public class DateTime implements Comparable<DateTime> {
    private static final int[] DAYS = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private final int month;   // month (between 1 and 12)
    private final int day;     // day   (between 1 and DAYS[month]
    private final int year;    // year
    private final int hour;
    private final int minute;

    public DateTime(String date, String time) {
        String[] fields = date.split("-");
        if (fields.length != 3) {
            throw new IllegalArgumentException("Invalid date");
        }
        day = Integer.parseInt(fields[2]);
        month = Integer.parseInt(fields[1]);
        year = Integer.parseInt(fields[0]);
        if (!isValidDate(day, month, year)) throw new IllegalArgumentException("Invalid date");

        String[] timeFields = time.split(":");
        if (timeFields.length != 2) {
            throw new IllegalArgumentException("Invalid time");
        }
        hour = Integer.parseInt(timeFields[0]);
        minute = Integer.parseInt(timeFields[1]);
        if (!isValidTime(hour, minute)) {
            throw new IllegalArgumentException("Invalid time");
        }
    }

    public int month() {
        return month;
    }

    public int day() {
        return day;
    }

    public int year() {
        return year;
    }

    public int hour() {
        return hour;
    }

    public int minute() {
        return minute;
    }


    // is the given date valid?
    private static boolean isValidDate(int d, int m, int y) {
        if (m < 1 || m > 12) return false;
        if (d < 1 || d > DAYS[m]) return false;
        return m != 2 || d != 29 || isLeapYear(y);
    }

    private static boolean isValidTime(int h, int m) {
        if (h < 0 || h > 24) {
            return false;
        }
        return m >= 0 && m <= 59;
    }

    // is y a leap year?
    private static boolean isLeapYear(int y) {
        if (y % 400 == 0) return true;
        if (y % 100 == 0) return false;
        return y % 4 == 0;
    }

    @Override
    public int compareTo(DateTime that) {
        if (this.year < that.year) return -1;
        if (this.year > that.year) return +1;
        if (this.month < that.month) return -1;
        if (this.month > that.month) return +1;
        if (this.day < that.day) return -1;
        if (this.day > that.day) return +1;
        if (this.hour < that.hour) return -1;
        if (this.hour > that.hour) return +1;
        if (this.minute < that.minute) return -1;
        if (this.minute > that.minute) return +1;
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        if (hour < 10 && minute < 10) {
            return day + "-" + month + "-" + year + " 0" + hour + ":" + minute + "0";
        } else {
            if (hour < 10 && minute > 10) {
                return day + "-" + month + "-" + year + " 0" + hour + ":" + minute;
            }
            if (minute < 10) {
                return day + "-" + month + "-" + year + " " + hour + ":" + minute + "0";
            }
        }
        return day + "-" + month + "-" + year + " " + hour + ":" + minute;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        DateTime that = (DateTime) other;
        return (this.month == that.month) && (this.day == that.day) && (this.year == that.year) && (this.hour == that.hour) && (this.minute == that.minute);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + month;
        hash = 31 * hash + day;
        hash = 31 * hash + year;
        hash = 31 * hash + hour;
        hash = 31 * hash + minute;
        return hash;
    }

}