package com.svmc.android.locationsupportteam.data.remote;

import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.ApiFactory;
import com.svmc.android.locationsupportteam.network.BaseCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.network.api.ApiRoomLocation;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Part;

/**
 * Created by TUNGTS on 4/21/2019
 */

public class RoomLocationRemoteDataSource extends BaseRemoteDataSource {

    public void createRoomLocation(final FinishedListener<BaseResultResponse<RoomLocation>> finishedListener, RoomLocation roomLocation) {
        ApiFactory
                .getApiRoomLocation()
                .createRoomLocation(getIdToken(), roomLocation)
                .enqueue(new BaseCallBack<BaseResultResponse<RoomLocation>>() {
                    @Override
                    public void onSuccess(BaseResultResponse<RoomLocation> result) {
                        finishedListener.onFinished(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        finishedListener.onFailure(t);
                    }
                });

    }

    public void getRoomLocationByUser(final FinishedListener<BaseResultResponse<String>> finishedListener) {
        ApiFactory
                .getApiRoomLocation()
                .getRoomLocationByUser(getIdToken())
                .enqueue(new BaseCallBack<BaseResultResponse<String>>() {
                    @Override
                    public void onSuccess(BaseResultResponse<String> result) {
                        finishedListener.onFinished(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        finishedListener.onFailure(t);
                    }
                });
    }

    public void getInforRoomById(String roomId, final FinishedListener<BaseResultResponse<RoomLocation>> finishedListener) {
        ApiFactory
                .getApiRoomLocation()
                .getInforRoombyId(roomId)
                .enqueue(new BaseCallBack<BaseResultResponse<RoomLocation>>() {
                    @Override
                    public void onSuccess(BaseResultResponse<RoomLocation> result) {
                        finishedListener.onFinished(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        finishedListener.onFailure(t);
                    }
                });
    }

    public void getALlMemberOfRoomLocation(String roomId, final FinishedListener<BaseResultResponse<ArrayList<MemberOfRoomLocation>>> finishedListener) {
        ApiFactory
                .getApiRoomLocation()
                .getAllMember(getIdToken(), roomId)
                .enqueue(new BaseCallBack<BaseResultResponse<ArrayList<MemberOfRoomLocation>>>() {
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

    // add and invited member to room
    public void addMembersToRoomLocation(ArrayList<MemberOfRoomLocation> members, String roomId , final FinishedListener<BaseResultResponse<String>> finishedListener) {
        ApiFactory
                .getApiRoomLocation()
                .addMembers(getIdToken(), members, roomId)
                .enqueue(new BaseCallBack<BaseResultResponse<String>>() {
                    @Override
                    public void onSuccess(BaseResultResponse<String> result) {
                        finishedListener.onFinished(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        finishedListener.onFailure(t);
                    }
                });
    }

    public void updateStatusUserInRoom(String userId, String roomId, int status, final FinishedListener<BaseResultResponse<String>> finishedListener) {
        ApiFactory
                .getApiRoomLocation()
                .updateStatusUserInRoom(userId, roomId, status)
                .enqueue(new BaseCallBack<BaseResultResponse<String>>() {
                    @Override
                    public void onSuccess(BaseResultResponse<String> result) {
                        finishedListener.onFinished(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        finishedListener.onFailure(t);
                    }
                });
    }

    public void joinGroup(String roomId, final FinishedListener<BaseResultResponse<String>> finishedListener) {
        ApiFactory
                .getApiRoomLocation()
                .joinRoom(getIdToken(), roomId)
                .enqueue(new BaseCallBack<BaseResultResponse<String>>() {
                    @Override
                    public void onSuccess(BaseResultResponse<String> result) {
                        finishedListener.onFinished(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        finishedListener.onFailure(t);
                    }
                });
    }

    public void removeInvitation(String userId, String roomId, final FinishedListener<BaseResultResponse<String>> finishedListener) {
        ApiFactory
                .getApiRoomLocation()
                .removeInvitation(userId, roomId)
                .enqueue(new BaseCallBack<BaseResultResponse<String>>() {
                    @Override
                    public void onSuccess(BaseResultResponse<String> result) {
                        finishedListener.onFinished(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        finishedListener.onFailure(t);
                    }
                });
    }

    public void postNotification(ParamsPostNotification postNotification, final FinishedListener<BaseResultResponse> finishedListener, String image) {
        MultipartBody.Part part = null;
        if (image != null) {
            File fileImage = new File(image);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileImage);
            part = MultipartBody.Part.createFormData("uploaded_file", fileImage.getName(), requestBody);
        }

        ApiFactory
                .getApiRoomLocation()
                .postAlert(part, postNotification)
                .enqueue(new BaseCallBack<BaseResultResponse>() {
                    @Override
                    public void onSuccess(BaseResultResponse result) {
                        finishedListener.onFinished(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        finishedListener.onFailure(t);
                    }
                });
    }

}
