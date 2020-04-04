package com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TUNGTS on 4/16/2019
 */

public class DayTime {

    @SerializedName("day")
    private int day;

    @SerializedName("time")
    private String time;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getTime() {
        String tmp = time.substring(0, 2) + ":" + time.substring(2, time.length());
        return tmp;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
