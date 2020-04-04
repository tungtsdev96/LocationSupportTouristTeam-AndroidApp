package com.svmc.android.locationsupportteam.network.api;

import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by TungTS on 4/23/2019
 */

public interface ApiRoomLocation {

    @POST("/api/room-location/create")
    Call<BaseResultResponse<RoomLocation>> createRoomLocation(@Header("authorization") String token, @Body RoomLocation roomLocation);

    @GET("/api/room-location/get-by-user")
    Call<BaseResultResponse<String>> getRoomLocationByUser(@Header("authorization") String token);

    @GET("/api/room-location/get-infor")
    Call<BaseResultResponse<RoomLocation>> getInforRoombyId(@Query("roomId") String roomId);

    @GET("/api/room-location/get-member")
    Call<BaseResultResponse<ArrayList<MemberOfRoomLocation>>> getAllMember(@Header("authorization") String token,
                                                                           @Query("roomId") String roomId);

    @POST("/api/room-location/add-members")
    Call<BaseResultResponse<String>> addMembers(@Header("authorization") String token,
                                                @Body ArrayList<MemberOfRoomLocation> memberOfRoomLocation,
                                                @Query("roomId") String roomId);

    @GET("/api/room-location/update-status")
    Call<BaseResultResponse<String>> updateStatusUserInRoom(@Query("userId") String userId,
                                                            @Query("roomId") String roomId,
                                                            @Query("status") int status);

    @PUT("/api/room-location/join-room")
    Call<BaseResultResponse<String>> joinRoom(@Header("authorization") String idToken,
                                              @Query("roomId") String roomId);

    @PUT("/api/room-location/remove-invitation")
    Call<BaseResultResponse<String>> removeInvitation(@Query("userId") String userId,
                                                      @Query("roomId") String roomId);

    @Multipart
    @POST("/api/room-location/post-alert")
    Call<BaseResultResponse> postAlert(@Part MultipartBody.Part image,
                                       @Part("notify") ParamsPostNotification postNotification);

}
