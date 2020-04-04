package com.svmc.android.locationsupportteam.network.api;

import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
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
 * Created by TUNGTS on 3/16/2019
 */

public interface ApiUser {

    @Multipart
    @POST("/api/user/add-user")
    Call<ResponseBody> addNewUser(@Part MultipartBody.Part image,
                                  @Part("user") User user);

    @GET("/api/user/search")
    Call<BaseResultResponse<ArrayList<User>>> searchUser(@Header("authorization") String token,
                                                         @Query("queryText") String query);

    @GET("/api/room-location/search-user")
    Call<BaseResultResponse<ArrayList<MemberOfRoomLocation>>> searchUserForRoom(@Header("authorization") String token,
                                                                                @Query("queryText") String query,
                                                                                @Query("roomId") String roomId);

    @PUT("/api/user/update-user")
    Call<ResponseBody> updateUser(@Body User user);

    @GET("/api/user/get-infor")
    Call<User> getInforUser(@Query("userId") String uid);

    @PUT("/api/user/update-on-off")
    Call<ResponseBody> updateOnOffLine(@Query("userId") String uid,
                                       @Query("isOnline") boolean isOnline);

    @POST("/api/user/syncdata")
    Call<BaseResultResponse> savePlaceSavedToServer(@Header("authorization") String token,
                                                    @Body ArrayList<Place> places);

    @GET("/api/user/get-place-saved")
    Call<BaseResultResponse<ArrayList<Place>>> getAllPlaceSaved(@Query("uid") String uid);

}
