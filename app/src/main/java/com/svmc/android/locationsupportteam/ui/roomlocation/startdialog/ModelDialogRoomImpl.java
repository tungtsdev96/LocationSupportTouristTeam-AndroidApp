package com.svmc.android.locationsupportteam.ui.roomlocation.startdialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.svmc.android.locationsupportteam.data.remote.RoomLocationRemoteDataSource;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.listeners.IdTokenCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BaseModel;


/**
 * Created by TungTS on 4/25/2019
 */

public class ModelDialogRoomImpl extends BaseModel implements MVPDialogRoom.IModelDialogRoom {

    private RoomLocationRemoteDataSource roomLocationRemoteDataSource;

    public ModelDialogRoomImpl() {
        this.roomLocationRemoteDataSource = new RoomLocationRemoteDataSource();
    }

    @Override
    public void leaveRoom(final String roomId, final FinishedListener<BaseResultResponse<String>> finishedListener) {
        getIdToken(new IdTokenCallBack() {
            @Override
            public void onComplete(String idToken) {
                roomLocationRemoteDataSource.setIdToken(idToken);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                roomLocationRemoteDataSource.updateStatusUserInRoom(user.getUid(), roomId, MemberOfRoomLocation.MEMBER_LEFT, finishedListener);
            }

            @Override
            public void onFailure(Throwable e) {
                finishedListener.onFailure(e);
            }
        });
    }

    @Override
    public void getInforRoom(String roomId, FinishedListener<BaseResultResponse<RoomLocation>> finishedListener) {
        roomLocationRemoteDataSource.getInforRoomById(roomId, finishedListener);
    }

}
