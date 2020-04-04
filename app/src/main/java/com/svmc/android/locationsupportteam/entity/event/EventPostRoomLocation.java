package com.svmc.android.locationsupportteam.entity.event;

import java.util.ArrayList;

/**
 * Created by TungTS on 4/23/2019
 * post roomlocation when start app
 */

public class EventPostRoomLocation {

    public final String roomId;

    public final int status;

    public EventPostRoomLocation(String roomId, int status) {
        this.roomId = roomId;
        this.status = status;
    }

}
