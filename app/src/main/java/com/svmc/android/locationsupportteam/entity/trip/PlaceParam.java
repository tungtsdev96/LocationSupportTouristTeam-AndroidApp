package com.svmc.android.locationsupportteam.entity.trip;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TUNGTS on 5/22/2019
 */

public class PlaceParam {

    @SerializedName("place_id")
    private String placeId;

    @SerializedName("name")
    private String name;

    @SerializedName("url_image")
    private String urlImage;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
