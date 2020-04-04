package com.svmc.android.locationsupportteam.entity.trip;

import java.util.List;

/**
 * Created by Tungts on 3/28/2019.
 */

public class TripDayInfor {

    private String time;

    private List<TripDetailDay> detailDays;

    public TripDayInfor() {}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<TripDetailDay> getDetailDays() {
        return detailDays;
    }

    public void setDetailDays(List<TripDetailDay> detailDays) {
        this.detailDays = detailDays;
    }
}
