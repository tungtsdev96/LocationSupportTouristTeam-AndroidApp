package com.svmc.android.locationsupportteam.entity.googlemap.place.autocomplete;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TUNGTS on 4/16/2019
 */

public class StructuredFormatting {

    @SerializedName("main_text")
    private String mainText;

    @SerializedName("secondary_text")
    private String secondaryText ;

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

    public static StructuredFormatting fake() {
        StructuredFormatting structuredFormatting = new StructuredFormatting();
        structuredFormatting.setMainText("aaaa");
        structuredFormatting.setSecondaryText("bbbb");
        return structuredFormatting;
    }
}
