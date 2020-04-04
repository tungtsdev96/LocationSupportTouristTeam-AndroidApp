package com.svmc.android.locationsupportteam.entity.event;

import com.svmc.android.locationsupportteam.entity.UserLocation;

/**
 * Created by TungTS on 4/28/2019
 */

public class EventPostUserOnOffline {

    public final UserLocation userLocation;

    public EventPostUserOnOffline(UserLocation userLocation) {
        this.userLocation = userLocation;
    }
}
