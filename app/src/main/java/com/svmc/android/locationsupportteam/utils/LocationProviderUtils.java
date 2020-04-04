package com.svmc.android.locationsupportteam.utils;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.LocationManager;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by TUNGTS on 4/13/2019
 */

public class LocationProviderUtils {

    public static boolean checkProviderEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static void checkAndTurnOnCurrentLocation(final Activity activity, final LocationProviderCallback onLocationProviderCallback) {

        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest mLocationSettingsRequest = builder.build();

        SettingsClient mSettingsClient = LocationServices.getSettingsClient(activity);

        Task<LocationSettingsResponse> task = mSettingsClient.checkLocationSettings(mLocationSettingsRequest);
        task.addOnSuccessListener(activity, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                if (onLocationProviderCallback != null) {
                    onLocationProviderCallback.onEnable();
                }
            }
        });

        task.addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(activity,
                                Constans.RequestCode.RC_CHECK_SETTINGS_CURRENT_LOCATION);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });

    }

    public interface LocationProviderCallback {
        void onEnable();
    }

}
