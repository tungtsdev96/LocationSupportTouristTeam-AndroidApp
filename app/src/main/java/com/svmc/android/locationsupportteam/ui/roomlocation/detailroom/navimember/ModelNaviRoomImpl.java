package com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.navimember;

import com.svmc.android.locationsupportteam.data.remote.RoomLocationRemoteDataSource;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.listeners.IdTokenCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BaseModel;

import java.util.ArrayList;

/**
 * Created by TUNGTS on 4/25/2019
 */

public class ModelNaviRoomImpl extends BaseModel implements MVPNaviRoomLocation.IModelNaviRoomLocation {

    private RoomLocationRemoteDataSource locationRemoteDataSource;

    public ModelNaviRoomImpl() {
        locationRemoteDataSource = new RoomLocationRemoteDataSource();
    }

    @Override
    public void getListMemberOfRoom(final String roomId, final FinishedListener<BaseResultResponse<ArrayList<MemberOfRoomLocation>>> finishedListener) {
        getIdToken(new IdTokenCallBack() {
            @Override
            public void onComplete(String idToken) {
                locationRemoteDataSource.setIdToken(idToken);
                locationRemoteDataSource.getALlMemberOfRoomLocation(roomId, finishedListener);
            }

            @Override
            public void onFailure(Throwable e) {
                finishedListener.onFailure(e);
            }
        });
    }

}
