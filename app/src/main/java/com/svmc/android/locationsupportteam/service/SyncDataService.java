package com.svmc.android.locationsupportteam.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.FirebaseDatabase;
import com.svmc.android.locationsupportteam.data.local.PlaceSaveController;
import com.svmc.android.locationsupportteam.entity.event.EventPostWhenStopTracking;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.network.ApiFactory;
import com.svmc.android.locationsupportteam.network.BaseCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.receivers.ConnectivityReceiver;
import com.svmc.android.locationsupportteam.utils.ErrCode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by TungTS on 5/5/2019
 */

public class SyncDataService extends Service {

    private final String TAG = "SyncDataService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG, "onStartCommand");
        getToken();
        return START_STICKY;
    }

    private void syncDataLocal(String token) {
        ArrayList<Place> places = new PlaceSaveController().getAllToSync();
        Log.d(TAG, places.size() + " place");
        if (places != null && places.size() > 0) {
            ApiFactory.getApiUser().savePlaceSavedToServer(token, places)
                    .enqueue(new BaseCallBack<BaseResultResponse>() {
                        @Override
                        public void onSuccess(BaseResultResponse result) {
                            Log.d(TAG, "sync ok");
                            stopSelf();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.d(TAG, "sync err");
                            stopSelf();
                        }
                    });
            return;
        }
        stopSelf();
    }

    public void getToken() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser == null) stop();

        mUser.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "sync get token ok");
                            String idToken = task.getResult().getToken();
                            syncDataLocal(idToken);
                        } else {
                            // Handle error -> task.getException();
                            Log.d(TAG, "sync get token err");
                            stop();
                        }
                    }
                });
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    public void stop() {
        stopSelf();
    }

}
