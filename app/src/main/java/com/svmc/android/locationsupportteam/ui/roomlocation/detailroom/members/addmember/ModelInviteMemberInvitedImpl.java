package com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.members.addmember;

import com.svmc.android.locationsupportteam.data.local.UserLocalData;
import com.svmc.android.locationsupportteam.data.local.entity.RecentedSearchUserCache;
import com.svmc.android.locationsupportteam.data.remote.RoomLocationRemoteDataSource;
import com.svmc.android.locationsupportteam.data.remote.UserRemoteDataSource;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.listeners.IdTokenCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/9/2019
 */

public class ModelInviteMemberInvitedImpl extends BaseModel
        implements MVPInviteMember.IModelInviteMember {

    private RoomLocationRemoteDataSource roomLocationRemoteDataSource;
    private UserRemoteDataSource userRemoteDataSource;
    private UserLocalData userLocalData;

    public ModelInviteMemberInvitedImpl() {
        roomLocationRemoteDataSource = new RoomLocationRemoteDataSource();
        userRemoteDataSource = new UserRemoteDataSource();
        userLocalData = new UserLocalData();
    }

    @Override
    public void addUserToCache(User user) {
        userLocalData.addRecentSearch(user);
    }

    @Override
    public List<RecentedSearchUserCache> getListSearchRecent() {
        return userLocalData.getUsersRescenedSearch();
    }

    @Override
    public void searchByUsernameOrEmail(int page, final String query, final String roomId, final FinishedListener<BaseResultResponse<ArrayList<MemberOfRoomLocation>>> finishedListener) {
        getIdToken(new IdTokenCallBack() {
            @Override
            public void onComplete(String idToken) {
                userRemoteDataSource.setIdToken(idToken);
                userRemoteDataSource.getSearchUserForRoom(finishedListener, query, roomId);
            }

            @Override
            public void onFailure(Throwable e) {
                finishedListener.onFailure(e);
            }
        });
    }

    @Override
    public void inviteMemberToRoom(final MemberOfRoomLocation member, final String roomId, final FinishedListener<BaseResultResponse<String>> finishedListener) {
        getIdToken(new IdTokenCallBack() {
            @Override
            public void onComplete(String idToken) {
                roomLocationRemoteDataSource.setIdToken(idToken);
                ArrayList<MemberOfRoomLocation> list = new ArrayList<>();
                list.add(member);
                roomLocationRemoteDataSource.addMembersToRoomLocation(list, roomId, finishedListener);

            }
            @Override
            public void onFailure(Throwable e) {
                finishedListener.onFailure(e);
            }
        });
    }

    @Override
    public void removeInvitation(final String userId, final String roomId, final FinishedListener<BaseResultResponse<String>> finishedListener) {
        roomLocationRemoteDataSource.removeInvitation(userId, roomId, finishedListener);
    }

}
