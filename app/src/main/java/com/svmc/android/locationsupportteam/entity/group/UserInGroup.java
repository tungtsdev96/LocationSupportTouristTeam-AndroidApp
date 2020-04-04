package com.svmc.android.locationsupportteam.entity.group;

import com.google.gson.annotations.SerializedName;

/**
 * Create by TUNGTS on 4/18/2019
 */

public class UserInGroup {

    public static final int GROUP_MEMBER = 2;
    public static final int GROUP_INVITED = 1;
    public static final int GROUP_LEAVED = 0;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("image")
    private String image;

    @SerializedName("status")
    private int status;

    @SerializedName("is_admin")
    private boolean isAdmin;

    public UserInGroup(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
