package com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TUNGTS on 4/16/2019
 */

public class OpeningHours {

    @SerializedName("open_now")
    private boolean openNow;

    @SerializedName("periods")
    private List<Period> periods;

    @SerializedName("weekday_text")
    private List<String> weekdayText;

    public boolean isOpenNow() {
        return openNow;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    public List<String> getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(List<String> weekdayText) {
        this.weekdayText = weekdayText;
    }
}
