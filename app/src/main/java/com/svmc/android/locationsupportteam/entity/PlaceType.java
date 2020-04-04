package com.svmc.android.locationsupportteam.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Create by TungTS on 5/11/2019
 */

public class PlaceType {

    @SerializedName("sub_type")
    private List<SubPlaceType> subPlaceTypes;

    @SerializedName("icon")
    private String icon;

    @SerializedName("title")
    private String title;

    @SerializedName("id")
    private int id;

    public List<SubPlaceType> getSubPlaceTypes() {
        return subPlaceTypes;
    }

    public void setSubPlaceTypes(List<SubPlaceType> subPlaceTypes) {
        this.subPlaceTypes = subPlaceTypes;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
