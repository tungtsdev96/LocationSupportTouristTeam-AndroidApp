package com.svmc.android.locationsupportteam.entity.event;

import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;

import java.util.ArrayList;

/**
 * Created by TungTS on 4/27/2019
 * Post list user location from NaviRoomDetailFrgament -> DetailRoomLocationFragment
 */

public class EventPostListUserLocation {

    public final ArrayList<MemberOfRoomLocation> members;

    public EventPostListUserLocation(ArrayList<MemberOfRoomLocation> members) {
        this.members = members;
    }
}
