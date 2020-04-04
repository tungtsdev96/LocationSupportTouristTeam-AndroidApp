package com.svmc.android.locationsupportteam.entity.googlemap.distance;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TungTS on 5/7/2019
 */

public class Element {

    @SerializedName("duration")
    private DisplayInfor duration;

    @SerializedName("distance")
    private DisplayInfor distance;

    @SerializedName("status")
    private String status;

    public DisplayInfor getDuration() {
        return duration;
    }

    public void setDuration(DisplayInfor duration) {
        this.duration = duration;
    }

    public DisplayInfor getDistance() {
        return distance;
    }

    public void setDistance(DisplayInfor distance) {
        this.distance = distance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
