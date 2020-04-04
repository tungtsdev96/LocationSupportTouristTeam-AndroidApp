package com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TUNGTS on 4/16/2019
 */

public class Photo {

    @SerializedName("width")
    private int width;

    @SerializedName("height")
    private int height;

    @SerializedName("html_attributions")
    private List<String> htmlAttributions;

    @SerializedName("photo_reference")
    private String photoReference;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }
}
