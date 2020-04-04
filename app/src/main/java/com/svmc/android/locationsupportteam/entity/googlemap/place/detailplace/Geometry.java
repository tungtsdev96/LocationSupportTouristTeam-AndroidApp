package com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace;

import com.google.gson.annotations.SerializedName;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;

/**
 * Created by TUNGTS on 4/16/2019
 */

public class Geometry {

    @SerializedName("location")
    private LocationPoint location;

    @SerializedName("viewport")
    private Viewport viewport;

    public LocationPoint getLocation() {
        return location;
    }

    public void setLocation(LocationPoint location) {
        this.location = location;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

}
