package com.svmc.android.locationsupportteam.data.remote;

import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.ApiFactory;
import com.svmc.android.locationsupportteam.network.BaseCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by TungTS on 4/23/2019
 */

public class UserRemoteDataSource extends BaseRemoteDataSource {

    /***
     * Search User
     */
    private Call<BaseResultResponse<ArrayList<User>>> searchUser;
    public void searchUser(final FinishedListener<BaseResultResponse<ArrayList<User>>> finishedListener, String queryText) {
        if (searchUser != null) {
            searchUser.cancel();
        }

        searchUser = ApiFactory.getApiUser().searchUser(getIdToken(), queryText);
        searchUser.enqueue(new BaseCallBack<BaseResultResponse<ArrayList<User>>>() {
            @Override
            public void onSuccess(BaseResultResponse<ArrayList<User>> result) {
                finishedListener.onFinished(result);
            }

            @Override
            public void onFailure(Throwable t) {
                finishedListener.onFailure(t);
            }
        });
    }

    /***
     * Search For Room
     */
    private Call<BaseResultResponse<ArrayList<MemberOfRoomLocation>>> searchUserForRoom;
    public void getSearchUserForRoom(final FinishedListener<BaseResultResponse<ArrayList<MemberOfRoomLocation>>> finishedListener, String queryText, String roomId) {
        if (searchUserForRoom != null) {
            searchUserForRoom.cancel();
        }

        searchUserForRoom = ApiFactory.getApiUser().searchUserForRoom(getIdToken(), queryText, roomId);
        searchUserForRoom.enqueue(new BaseCallBack<BaseResultResponse<ArrayList<MemberOfRoomLocation>>>() {
            @Override
            public void onSuccess(BaseResultResponse<ArrayList<MemberOfRoomLocation>> result) {
                finishedListener.onFinished(result);
            }

            @Override
            public void onFailure(Throwable t) {
                finishedListener.onFailure(t);
            }
        });
    }

    /**
     * get full infor user
     */
    public void getInforUser(String userId, final FinishedListener<User> listener) {
        ApiFactory.getApiUser().getInforUser(userId).enqueue(new BaseCallBack<User>() {
            @Override
            public void onSuccess(User result) {
                listener.onFinished(result);
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    /**
     * get place saved
     */
    public void getAllPlaceSaved(String uid, final FinishedListener<BaseResultResponse<ArrayList<Place>>> listener) {
        ApiFactory.getApiUser().getAllPlaceSaved(uid)
                .enqueue(new BaseCallBack<BaseResultResponse<ArrayList<Place>>>() {
                    @Override
                    public void onSuccess(BaseResultResponse<ArrayList<Place>> result) {
                        listener.onFinished(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        listener.onFailure(t);
                    }
                });
    }

}
