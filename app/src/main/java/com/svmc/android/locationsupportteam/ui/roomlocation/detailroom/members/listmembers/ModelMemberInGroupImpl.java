package com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.members.listmembers;

import com.svmc.android.locationsupportteam.data.remote.RoomLocationRemoteDataSource;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.listeners.IdTokenCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BaseModel;

import java.util.ArrayList;

/**
 * Created by TUNGTS on 4/9/2019
 */

public class ModelMemberInGroupImpl extends BaseModel implements MVPMemberInRoomLocation.IModelMemberInRoomLocation {

    private RoomLocationRemoteDataSource roomLocationRemoteDataSource;

    public ModelMemberInGroupImpl() {
        this.roomLocationRemoteDataSource = new RoomLocationRemoteDataSource();
    }

    @Override
    public void getAllMemberInRoom(final String roomId, final FinishedListener<BaseResultResponse<ArrayList<MemberOfRoomLocation>>> finishedListener) {
        getIdToken(new IdTokenCallBack() {
            @Override
            public void onComplete(String idToken) {
                roomLocationRemoteDataSource.setIdToken(idToken);
                roomLocationRemoteDataSource.getALlMemberOfRoomLocation(roomId, finishedListener);
            }

            @Override
            public void onFailure(Throwable e) {
                finishedListener.onFailure(e);
            }
        });
    }

    @Override
    public void removeMember(String roomId, String memberId, FinishedListener<BaseResultResponse<String>> finishedListener) {
        roomLocationRemoteDataSource.updateStatusUserInRoom(memberId, roomId, MemberOfRoomLocation.MEMBER_LEFT, finishedListener);
    }

    @Override
    public void removeInvitation(final String userId, final String roomId, final FinishedListener<BaseResultResponse<String>> finishedListener) {
        roomLocationRemoteDataSource.removeInvitation(userId, roomId,  finishedListener);
    }
}
