package com.svmc.android.locationsupportteam.data.local.entity;

import io.realm.RealmObject;

/**
 * Created by TungTS on 5/5/2019
 */

public class LocationCache extends RealmObject {

    private double lat;

    private double lng;

    private long time;

    private float accuracy;

    public LocationCache() {
    }

    public LocationCache(double lat, double lng, float accuracy, long time) {
        this.lat = lat;
        this.lng = lng;
        this.time = time;
        this.accuracy = accuracy;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }
}
