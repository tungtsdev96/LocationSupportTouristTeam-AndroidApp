package com.svmc.android.locationsupportteam.receivers.syncdata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.svmc.android.locationsupportteam.data.local.PlaceSaveController;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.network.ApiFactory;
import com.svmc.android.locationsupportteam.network.BaseCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

import java.util.ArrayList;

/**
 * Created by TUNGTS on 6/6/2019
 */

public class SyncDataReceiver extends BroadcastReceiver {

    public final String TAG = "SyncDataReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("SyncDataReceiver", "start sync");
        final PlaceSaveController placeSaveController = new PlaceSaveController();
        final ArrayList<Place> places = placeSaveController.getAllToSync();
        Log.d(TAG, places.size() + " place need update");
        if (places.size() > 0) {
            getToken(placeSaveController, places);
        }
    }

    private void syncDataLocal(String token, final ArrayList<Place> places, final PlaceSaveController placeSaveController) {
        ApiFactory.getApiUser().savePlaceSavedToServer(token, places)
                .enqueue(new BaseCallBack<BaseResultResponse>() {
                    @Override
                    public void onSuccess(BaseResultResponse result) {
                        Log.d(TAG, "sync ok");
                        updateSynced(places, placeSaveController);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.d(TAG, "sync err");
                    }
                });
    }

    private void updateSynced(ArrayList<Place> places, PlaceSaveController placeSaveController) {
        for (Place place: places) {
            placeSaveController.updatePlaceSynced(place.getPlaceId());
        }
    }

    public void getToken(final PlaceSaveController placeSaveController, final ArrayList<Place> places) {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser == null) return;

        mUser.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "sync get token ok");
                            String idToken = task.getResult().getToken();
                            syncDataLocal(idToken, places, placeSaveController);
                        } else {
                            // Handle error -> task.getException();
                            Log.d(TAG, "sync get token err");
                        }
                    }
                });
    }
}
