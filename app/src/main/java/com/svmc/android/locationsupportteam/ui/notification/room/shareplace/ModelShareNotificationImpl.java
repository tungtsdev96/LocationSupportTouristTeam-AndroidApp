package com.svmc.android.locationsupportteam.ui.notification.room.shareplace;

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
 * Create by TungTS on 5/2/2019
 */

public class ModelShareNotificationImpl extends BaseModel implements MVPRoomNotification.IModelShareNotification {

    private RoomLocationNotifyRemoteDataSource roomLocationNotifyRemoteDataSource;
    private RoomLocationRemoteDataSource roomLocationRemoteDataSource;

    public ModelShareNotificationImpl() {
        this.roomLocationNotifyRemoteDataSource = new RoomLocationNotifyRemoteDataSource();
        this.roomLocationRemoteDataSource = new RoomLocationRemoteDataSource();
    }

    /**
     * get all notify of user
     */
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
    public void removeNotify(final String notifyId, final FinishedListener<BaseResultResponse<JSONObject>> finishedListener) {
        roomLocationNotifyRemoteDataSource.removeNotify(notifyId, finishedListener);
    }

    @Override
    public void resolveAlert(String senderId, FinishedListener<BaseResultResponse<String>> finishedListener) {

    }
}
