package com.svmc.android.locationsupportteam.data.local.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by TungTS on 4/23/2019
 */

public class RecentedSearchUserCache extends RealmObject {

    @PrimaryKey
    private String userId;

    private String email;

    private String username;

    private String displayName;

    private long time;

    public RecentedSearchUserCache() {
    }

    public RecentedSearchUserCache(String userId, String email, String username, String displayName, long time) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.displayName = displayName;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


}
