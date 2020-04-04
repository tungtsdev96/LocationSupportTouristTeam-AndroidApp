package com.svmc.android.locationsupportteam.entity.googlemap;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tungts on 3/28/2019.
 */

public class LocationPoint {

    @SerializedName("lat")
    private double lat;

    @SerializedName("lng")
    private double lng;

    private long time;

    public LocationPoint(){}

    public LocationPoint(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public LocationPoint(double lat, double lng, long time) {
        this.lat = lat;
        this.lng = lng;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String toString() {
        return lat + "," + lng;
    }

    public static LocationPoint fakeCurrent() {
        return new LocationPoint(20.995515,105.8460435);
    }

    public static List<LocationPoint> fakeList() {
        List<LocationPoint> locationPoints = new ArrayList<>();
        locationPoints.add(new LocationPoint(21.03391629999999, 105.8476679));
        locationPoints.add(new LocationPoint(21.0181814, 105.8544808));
        locationPoints.add(new LocationPoint(21.0332848, 105.8536748));
        locationPoints.add(new LocationPoint(21.026596, 105.8431384));
        locationPoints.add(new LocationPoint(21.055176, 105.821299));
        return locationPoints;
    }

}
