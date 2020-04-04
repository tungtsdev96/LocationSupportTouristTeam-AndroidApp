package com.svmc.android.locationsupportteam.entity.event;

/**
 * Created by TungTS on 5/6/2019
 * Post to update user to offline
 */

public class EventPostWhenStopTracking {

    public final boolean isOffline;

    public EventPostWhenStopTracking(boolean isOffline) {
        this.isOffline = isOffline;
    }
}
