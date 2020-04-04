package com.svmc.android.locationsupportteam.entity.googlemap.direction;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Create by TungTS on 5/11/2019
 */

public class RouteGoogle {

    @SerializedName("legs")
    private List<Leg> legs;

    @SerializedName("overview_polyline")
    private OverviewPolyline overviewPolyline;

    @SerializedName("waypoint_order")
    private List<Integer> waypointOrder;

    @SerializedName("bounds")
    private Bound bound;

    public Bound getBound() {
        return bound;
    }

    public void setBound(Bound bound) {
        this.bound = bound;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public OverviewPolyline getOverviewPolyline() {
        return overviewPolyline;
    }

    public void setOverviewPolyline(OverviewPolyline overviewPolyline) {
        this.overviewPolyline = overviewPolyline;
    }

    public List<Integer> getWaypointOrder() {
        return waypointOrder;
    }

    public void setWaypointOrder(List<Integer> waypointOrder) {
        this.waypointOrder = waypointOrder;
    }
}
