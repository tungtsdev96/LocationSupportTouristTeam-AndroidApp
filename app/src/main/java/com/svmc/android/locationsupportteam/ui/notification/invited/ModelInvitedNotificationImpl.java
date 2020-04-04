package com.svmc.android.locationsupportteam.ui.notification.invited;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.svmc.android.locationsupportteam.data.remote.RoomLocationNotifyRemoteDataSource;
import com.svmc.android.locationsupportteam.data.remote.RoomLocationRemoteDataSource;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.listeners.IdTokenCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BaseModel;

import java.util.ArrayList;

/**
 * Create by TungTS on 5/2/2019
 */

public class ModelInvitedNotificationImpl extends BaseModel implements MVPInvitedNotification.IModelInvitedNotification {

    private RoomLocationNotifyRemoteDataSource roomLocationNotifyRemoteDataSource;
    private RoomLocationRemoteDataSource roomLocationRemoteDataSource;

    public ModelInvitedNotificationImpl() {
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
    public void joinRoom(final String roomId, final FinishedListener<BaseResultResponse<String>> finishedListener) {
        getIdToken(new IdTokenCallBack() {
            @Override
            public void onComplete(String idToken) {
                roomLocationRemoteDataSource.setIdToken(idToken);
                roomLocationRemoteDataSource.joinGroup(roomId, finishedListener);
            }

            @Override
            public void onFailure(Throwable e) {
                finishedListener.onFailure(e);
            }
        });
    }

    @Override
    public void removeInvitation(final String roomId, final FinishedListener<BaseResultResponse<String>> finishedListener) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        roomLocationRemoteDataSource.removeInvitation(user.getUid(), roomId, finishedListener);
    }

}
