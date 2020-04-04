package com.svmc.android.locationsupportteam.entity.googlemap.distance;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TungTS on 5/7/2019
 */

public class DisplayInfor {

    @SerializedName("text")
    private String text;

    @SerializedName("value")
    private int value;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
