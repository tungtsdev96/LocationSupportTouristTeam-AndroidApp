package com.svmc.android.locationsupportteam.entity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TUNGTS on 4/19/2019
 */

@IgnoreExtraProperties
public class UserLocation {

    private String userId;

    private String displayName;

    private double lat;

    private double lng;

    private long time;

    private boolean isOnline;

    private long lastTimeOnline;

    public UserLocation() {
    }

    public UserLocation(String userId, String displayName, double lat, double lng, long time, boolean isOnline) {
        this.userId = userId;
        this.displayName = displayName;
        this.lat = lat;
        this.lng = lng;
        this.time = time;
        this.isOnline = isOnline;
    }

    /***
     * Tracking location
     * @param userId
     * @param lat
     * @param lng
     * @param time
     */
    public UserLocation(String userId, double lat, double lng, long time) {
        this.userId = userId;
        this.lat = lat;
        this.lng = lng;
        this.time = time;
    }

    /**
     * Tracking online, offline
     * @param userId
     * @param isOnline
     * @param lastTimeOnline
     */
    public UserLocation(String userId, boolean isOnline, long lastTimeOnline) {
        this.userId = userId;
        this.isOnline = isOnline;
        this.lastTimeOnline = lastTimeOnline;
    }

    public long getLastTimeOnline() {
        return lastTimeOnline;
    }

    public void setLastTimeOnline(long lastTimeOnline) {
        this.lastTimeOnline = lastTimeOnline;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("displayName", displayName);
        result.put("lat", lat);
        result.put("lng", lng);
        result.put("time", time);
        result.put("isOnline", isOnline);
        result.put("lastTimeOnline", lastTimeOnline);
        return result;
    }

    private Marker marker;

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }
}
