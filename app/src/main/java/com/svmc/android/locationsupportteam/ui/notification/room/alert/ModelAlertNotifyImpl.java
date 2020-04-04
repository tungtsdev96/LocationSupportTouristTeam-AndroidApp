package com.svmc.android.locationsupportteam.ui.notification.room.alert;

import com.svmc.android.locationsupportteam.data.remote.RoomLocationNotifyRemoteDataSource;
import com.svmc.android.locationsupportteam.data.remote.RoomLocationRemoteDataSource;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.listeners.IdTokenCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BaseModel;
import com.svmc.android.locationsupportteam.ui.notification.room.MVPRoomNotification;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Create by TungTS on 6/8/2019
 */

public class ModelAlertNotifyImpl extends BaseModel implements MVPRoomNotification.IModelShareNotification {

    private RoomLocationNotifyRemoteDataSource roomLocationNotifyRemoteDataSource;
    private RoomLocationRemoteDataSource roomLocationRemoteDataSource;

    public ModelAlertNotifyImpl() {
        this.roomLocationNotifyRemoteDataSource = new RoomLocationNotifyRemoteDataSource();
        this.roomLocationRemoteDataSource = new RoomLocationRemoteDataSource();
    }

    @Override
    public void getListNotify(final FinishedListener<BaseResultResponse<ArrayList<RoomLocationNotification>>> finishedListener) {
        getIdToken(new IdTokenCallBack() {
            @Override
            public void onComplete(String idToken) {
                roomLocationNotifyRemoteDataSource.setIdToken(idToken);
                roomLocationNotifyRemoteDataSource.getAllNotify(finishedListener);
            }

            @Override
            public void onFailure(Throwable e) {
                finishedListener.onFailure(e);
            }
        });
    }

    @Override
    public void removeNotify(String notifyId, FinishedListener<BaseResultResponse<JSONObject>> finishedListener) {
        roomLocationNotifyRemoteDataSource.removeNotify(notifyId, finishedListener);
    }

    @Override
    public void resolveAlert(String senderId, FinishedListener<BaseResultResponse<String>> finishedListener) {
        roomLocationNotifyRemoteDataSource.resolveAlert(senderId, finishedListener);
    }

}
