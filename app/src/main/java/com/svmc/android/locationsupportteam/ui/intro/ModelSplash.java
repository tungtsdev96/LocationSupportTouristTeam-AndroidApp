package com.svmc.android.locationsupportteam.ui.intro;

import com.svmc.android.locationsupportteam.data.remote.RoomLocationRemoteDataSource;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.listeners.IdTokenCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BaseModel;


/**
 * Created by TungTS on 5/13/2019
 */

public class ModelSplash extends BaseModel {

    private RoomLocationRemoteDataSource roomLocationRemoteDataSource;

    public ModelSplash() {
        this.roomLocationRemoteDataSource = new RoomLocationRemoteDataSource();
    }

    public void getRoomLocation(final FinishedListener<BaseResultResponse<String>> finishedListener) {
        getIdToken(new IdTokenCallBack() {
            @Override
            public void onComplete(String idToken) {
                roomLocationRemoteDataSource.setIdToken(idToken);
                roomLocationRemoteDataSource.getRoomLocationByUser(finishedListener);
            }

            @Override
            public void onFailure(Throwable e) {
                finishedListener.onFailure(e);
            }
        });
    }


}
