package com.svmc.android.locationsupportteam.entity.event;

/**
 * Created by TungTS on 4/27/2019
 * Post user on, off, status to
 */

public class EventPostUpdateStatusUser {

    public final int status;

    public final String userId;

    public EventPostUpdateStatusUser(String userId, int status) {
        this.status = status;
        this.userId = userId;
    }
}
