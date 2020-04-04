package com.svmc.android.locationsupportteam.ui.roomlocation.createroom;

import com.svmc.android.locationsupportteam.data.local.UserLocalData;
import com.svmc.android.locationsupportteam.data.local.entity.RecentedSearchUserCache;
import com.svmc.android.locationsupportteam.data.remote.RoomLocationRemoteDataSource;
import com.svmc.android.locationsupportteam.data.remote.UserRemoteDataSource;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.listeners.IdTokenCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/20/2019
 */

public class ModelCreateRoomLocationImpl extends BaseModel
        implements MVPCreateRoomLocation.IModelCreateRoomLocation {

    private RoomLocationRemoteDataSource roomLocationRemoteDataSource;
    private UserRemoteDataSource userRemoteDataSource;
    private UserLocalData userLocalData;

    public ModelCreateRoomLocationImpl() {
        roomLocationRemoteDataSource = new RoomLocationRemoteDataSource();
        userRemoteDataSource = new UserRemoteDataSource();
        userLocalData = new UserLocalData();
    }

    @Override
    public void addUserToCache(User user) {
        userLocalData.addRecentSearch(user);
    }

    @Override
    public void addUserToCache(MemberOfRoomLocation user) {
        userLocalData.addRecentSearch(user);
    }

    @Override
    public List<RecentedSearchUserCache> getListSearchRecent() {
        return userLocalData.getUsersRescenedSearch();
    }

    @Override
    public void searchByUsernameOrEmail(int page, final String query, final FinishedListener<BaseResultResponse<ArrayList<User>>> finishedListener) {
        getIdToken(new IdTokenCallBack() {
            @Override
            public void onComplete(String idToken) {
                userRemoteDataSource.setIdToken(idToken);
                userRemoteDataSource.searchUser(finishedListener, query);
            }

            @Override
            public void onFailure(Throwable e) {
                finishedListener.onFailure(e);
            }
        });
    }

//    @Override
//    public void saveRecentedSearchToLocal(ArrayList<User> users) {
//        userLocalData.saveRecentSearch(users);
//    }

    @Override
    public void createRoomLocation(final FinishedListener<BaseResultResponse<RoomLocation>> finishedListener, final RoomLocation roomLocation) {
        getIdToken(new IdTokenCallBack() {
            @Override
            public void onComplete(String idToken) {
                roomLocationRemoteDataSource.setIdToken(idToken);
                roomLocationRemoteDataSource.createRoomLocation(finishedListener, roomLocation);
            }

            @Override
            public void onFailure(Throwable e) {
                finishedListener.onFailure(e);
            }
        });
    }

}
