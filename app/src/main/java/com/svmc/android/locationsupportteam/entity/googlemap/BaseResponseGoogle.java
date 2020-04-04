package com.svmc.android.locationsupportteam.entity.googlemap;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TUNGTS on 4/16/2019
 */

public abstract class BaseResponseGoogle {

    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("next_page_token")
    private String pageToken;

    public String getPageToken() {
        return pageToken;
    }

    public void setPageToken(String pageToken) {
        this.pageToken = pageToken;
    }
}
