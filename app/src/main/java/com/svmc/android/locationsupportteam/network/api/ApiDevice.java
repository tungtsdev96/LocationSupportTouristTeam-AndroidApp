package com.svmc.android.locationsupportteam.network.api;

import com.svmc.android.locationsupportteam.entity.DeviceUser;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Create by TungTS on 5/2/2019
 */

public interface ApiDevice {

    @POST("/api/device/update")
    Call<BaseResultResponse<DeviceUser>> updateUser(@Header("authorization") String token, @Body DeviceUser deviceUser);

}
