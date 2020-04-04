package com.svmc.android.locationsupportteam.entity.googlemap.direction;

import com.google.gson.annotations.SerializedName;
import com.svmc.android.locationsupportteam.entity.googlemap.BaseResponseGoogle;

import java.util.ArrayList;

/**
 * Create by TungTS on 5/11/2019
 */

public class ResultPathGoogle extends BaseResponseGoogle {

    @SerializedName("routes")
    private ArrayList<RouteGoogle> routes;

    public ArrayList<RouteGoogle> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<RouteGoogle> routes) {
        this.routes = routes;
    }

}
