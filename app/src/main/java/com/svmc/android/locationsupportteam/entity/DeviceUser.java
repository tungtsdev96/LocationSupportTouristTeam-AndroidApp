package com.svmc.android.locationsupportteam.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Create by TungTS on 5/2/2019
 */

public class DeviceUser {

    public static final int ENABLE = 1;
    public static final int DISABLE = 1;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("player_id")
    private String playerId;

    @SerializedName("push_token")
    private String pushToken;

    @SerializedName("enable")
    private int status;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
