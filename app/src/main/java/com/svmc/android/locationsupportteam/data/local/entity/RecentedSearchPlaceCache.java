package com.svmc.android.locationsupportteam.data.local.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by TungTS on 5/6/2019
 */

public class RecentedSearchPlaceCache extends RealmObject {

    @PrimaryKey
    private String placeId;

    private String mainText;

    private String secondaryText;

    private long createdTime;

    public RecentedSearchPlaceCache() {
    }

    public RecentedSearchPlaceCache(String placeId, String mainText, String secondaryText, long createdTime) {
        this.placeId = placeId;
        this.mainText = mainText;
        this.secondaryText = secondaryText;
        this.createdTime = createdTime;
    }

    public RecentedSearchPlaceCache(String mainText, String secondaryText) {
        this.mainText = mainText;
        this.secondaryText = secondaryText;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getSecondaryText() {
        return secondaryText;
    }

    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }

}
