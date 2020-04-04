package com.svmc.android.locationsupportteam.ui.base;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.LocationProviderUtils;

/**
 * Created by TungTS on 4/23/2019
 */

public abstract class BaseMapFragment extends BaseFragment {

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode != Constans.RequestCode.RC_LOCATION_PERMISSION) {
            return;
        }

        if (isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            onPermisstionLocationGranted();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            onPermisstionLocationDenied();
        }
    }

    /***
     * Check permisstion Location
     */
    public void checkPermisstionLocation() {
        try {
            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission to access the location is missing.
                requestPermission(this, Constans.RequestCode.RC_LOCATION_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION, true);
            } else {
                onPermisstionLocationGranted();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void onPermisstionLocationGranted();

    protected abstract void onPermisstionLocationDenied();

    /***
     * Enable location provider
     */
    public void enableLocationProvider() {
        LocationProviderUtils.checkAndTurnOnCurrentLocation(getActivity(), new LocationProviderUtils.LocationProviderCallback() {
            @Override
            public void onEnable() {
                onLocationProvideEnabled();
            }
        });
    }

    protected abstract void onLocationProvideEnabled();

    protected abstract void onLocationProvideCancel();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case Constans.RequestCode.RC_CHECK_SETTINGS_CURRENT_LOCATION:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        onLocationProvideEnabled();
                        break;
                    case Activity.RESULT_CANCELED:
                        onLocationProvideCancel();
                        break;
                }
                break;
        }
    }



}
