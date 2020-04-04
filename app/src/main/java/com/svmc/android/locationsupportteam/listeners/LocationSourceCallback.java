package com.svmc.android.locationsupportteam.listeners;

import android.location.Location;

import com.google.android.gms.maps.LocationSource;

/**
 * A {@link LocationSource} which reports a new location whenever a user long presses the map
 * at
 * the point at which a user long pressed the map.
 */

public class LocationSourceCallback implements LocationSource {

    private LocationSource.OnLocationChangedListener mListener;

    /**
     * Flag to keep track of the activity's lifecycle. This is not strictly necessary in this
     * case because onMapLongPress events don't occur while the activity containing the map is
     * paused but is included to demonstrate best practices (e.g., if a background service were
     * to be used).
     */
    private boolean mPaused;

    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    public void setLocationSource(Location location) {
        if (mListener != null && !mPaused) {
            mListener.onLocationChanged(location);
        }
    }

    public void onPause() {
        mPaused = true;
    }

    public void onResume() {
        mPaused = false;
    }
}