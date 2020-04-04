package com.svmc.android.locationsupportteam.network.api;

import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Create by TungTS on 5/2/2019
 */

public interface ApiNotification {

    @GET("/api/notification/get-all")
    Call<BaseResultResponse<ArrayList<RoomLocationNotification>>> getAllNotify(@Header("authorization") String idToken);

    @DELETE("/api/notification/delete")
    Call<BaseResultResponse<JSONObject>> removeNotify(@Header("authorization") String idToken,
                                                      @Query("notificationId") String notifyId);

    @PUT("/api/notification/resolve-alert")
    Call<BaseResultResponse<String>> resolveAlert(@Query("userId") String senderId);

}
