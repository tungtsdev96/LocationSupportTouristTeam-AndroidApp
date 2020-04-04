package com.svmc.android.locationsupportteam.entity.googlemap.direction;

import com.google.gson.annotations.SerializedName;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;

/**
 * Create by TungTS on 5/11/2019
 */

public class Bound {

    @SerializedName("northeast")
    private LocationPoint northeast;

    @SerializedName("southwest")
    private LocationPoint southwest;

    public LocationPoint getNortheast() {
        return northeast;
    }

    public void setNortheast(LocationPoint northeast) {
        this.northeast = northeast;
    }

    public LocationPoint getSouthwest() {
        return southwest;
    }

    public void setSouthwest(LocationPoint southwest) {
        this.southwest = southwest;
    }
}
