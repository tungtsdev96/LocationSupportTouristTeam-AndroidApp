package com.svmc.android.locationsupportteam.entity.event;

import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;

import java.util.ArrayList;

/**
 * Created by TungTS on 5/7/2019
 */

public class EventMap {

    public static class PostLocation {

        public final ArrayList<LocationPoint> locationPoints;

        public final ArrayList<String> namePlaces;

        public PostLocation(ArrayList<LocationPoint> locationPoints, ArrayList<String> namePlaces) {
            this.locationPoints = locationPoints;
            this.namePlaces = namePlaces;
        }

    }

    public static class PostClickMaker {

        public final int pos;

        public PostClickMaker(int pos) {
            this.pos = pos;
        }
    }

    public static class PostFocusToLocation {

        public final int pos;

        public final LocationPoint locationPoint;

        public PostFocusToLocation(int pos, LocationPoint locationPoint) {
            this.pos = pos;
            this.locationPoint = locationPoint;
        }
    }


}
