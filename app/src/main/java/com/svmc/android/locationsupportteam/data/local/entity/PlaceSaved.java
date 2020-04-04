package com.svmc.android.locationsupportteam.data.local.entity;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by TungTS on 5/10/2019
 */

public class PlaceSaved extends RealmObject {

    @PrimaryKey
    private String placeId;

    private String jsonPlace;

    private long createdTime;

    private boolean isSynchronize;

    public PlaceSaved(){}

    public PlaceSaved(String placeId, String jsonPlace) {
        this.placeId = placeId;
        this.jsonPlace = jsonPlace;
        this.isSynchronize = false;
        this.createdTime = new Date().getTime();
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getJsonPlace() {
        return jsonPlace;
    }

    public void setJsonPlace(String jsonPlace) {
        this.jsonPlace = jsonPlace;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isSynchronize() {
        return isSynchronize;
    }

    public void setSynchronize(boolean synchronize) {
        isSynchronize = synchronize;
    }
}
