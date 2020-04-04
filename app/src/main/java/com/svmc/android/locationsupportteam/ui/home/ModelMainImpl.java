package com.svmc.android.locationsupportteam.ui.home;

import com.svmc.android.locationsupportteam.data.remote.RoomLocationRemoteDataSource;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.listeners.IdTokenCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BaseModel;

import java.util.ArrayList;

/**
 * Created by TungTS on 4/23/2019
 */

public class ModelMainImpl extends BaseModel implements MVPMainFragment.IModelMain {

    private RoomLocationRemoteDataSource roomLocationRemoteDataSource;

    public ModelMainImpl() {
        this.roomLocationRemoteDataSource = new RoomLocationRemoteDataSource();
    }

    @Override
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
