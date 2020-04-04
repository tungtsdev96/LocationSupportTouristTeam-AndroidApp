package com.svmc.android.locationsupportteam.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Create by TungTS on 5/11/2019
 */

public class SubPlaceType {

    @SerializedName("title")
    private String title;

    @SerializedName("key")
    private String key;

    @SerializedName("cal_req")
    private int calReq;

    @SerializedName("id")
    private int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getCalReq() {
        return calReq;
    }

    public void setCalReq(int calReq) {
        this.calReq = calReq;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
