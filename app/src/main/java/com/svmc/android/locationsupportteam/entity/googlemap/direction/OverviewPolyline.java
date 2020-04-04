package com.svmc.android.locationsupportteam.entity.googlemap.direction;

import com.google.gson.annotations.SerializedName;

/**
 * Create by TungTS on 5/11/2019
 */

public class OverviewPolyline {

    @SerializedName("points")
    private String points;

    public String getPoints() {
        return points;
    }
}
