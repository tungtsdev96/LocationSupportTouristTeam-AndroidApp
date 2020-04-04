package com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TUNGTS on 4/16/2019
 */

public class Period {

    @SerializedName("open")
    private DayTime open;

    @SerializedName("close")
    private DayTime close;

    public DayTime getOpen() {
        return open;
    }

    public void setOpen(DayTime open) {
        this.open = open;
    }

    public DayTime getClose() {
        return close;
    }

    public void setClose(DayTime close) {
        this.close = close;
    }
}
