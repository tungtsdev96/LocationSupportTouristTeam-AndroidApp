package com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace;

import com.google.gson.annotations.SerializedName;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;

/**
 * Created by TUNGTS on 4/16/2019
 */

public class Viewport {

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
