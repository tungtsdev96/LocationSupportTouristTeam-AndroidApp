package com.svmc.android.locationsupportteam.entity.googlemap.distance;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TungTS on 5/7/2019
 */

public class Row {

    @SerializedName("elements")
    private List<Element> elements;

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }
}
