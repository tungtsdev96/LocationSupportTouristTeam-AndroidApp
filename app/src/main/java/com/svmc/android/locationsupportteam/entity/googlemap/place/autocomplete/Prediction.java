package com.svmc.android.locationsupportteam.entity.googlemap.place.autocomplete;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TUNGTS on 4/16/2019
 */

public class Prediction {

    @SerializedName("description")
    private String description;

    @SerializedName("place_id")
    private String placeId;

    @SerializedName("structured_formatting")
    private StructuredFormatting structuredFormatting;

    @SerializedName("types")
    private List<String> types;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public StructuredFormatting getStructuredFormatting() {
        return structuredFormatting;
    }

    public void setStructuredFormatting(StructuredFormatting structuredFormatting) {
        this.structuredFormatting = structuredFormatting;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public static Prediction fake() {
        Prediction prediction = new Prediction();
        prediction.setPlaceId("Aaaaaa");
        prediction.setStructuredFormatting(StructuredFormatting.fake());
        return prediction;
    }
}
