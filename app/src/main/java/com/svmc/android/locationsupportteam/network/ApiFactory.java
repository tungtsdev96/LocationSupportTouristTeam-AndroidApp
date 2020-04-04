package com.svmc.android.locationsupportteam.network;

import android.support.annotation.NonNull;

import com.svmc.android.locationsupportteam.network.api.ApiDevice;
import com.svmc.android.locationsupportteam.network.api.ApiGoogle;
import com.svmc.android.locationsupportteam.network.api.ApiNotification;
import com.svmc.android.locationsupportteam.network.api.ApiRecommend;
import com.svmc.android.locationsupportteam.network.api.ApiRoomLocation;
import com.svmc.android.locationsupportteam.network.api.ApiUser;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TUNGTS on 3/16/2019
 */

public class ApiFactory {

    private static Retrofit retrofitNodeJs;
    private static Retrofit retrofitGoogleMapsApi;
    private static Retrofit retrofitOneSignal;

    public static ApiGoogle getApiGoogle() {
        return getRetrofitGoogleMapsApi().create(ApiGoogle.class);
    }

    public static ApiDevice getApiDevice() {
        return getRetrofitNodeJs().create(ApiDevice.class);
    }

    public static ApiUser getApiUser() {
        return getRetrofitNodeJs().create(ApiUser.class);
    }

    public static ApiRoomLocation getApiRoomLocation() {
        return getRetrofitNodeJs().create(ApiRoomLocation.class);
    }

    public static ApiNotification getApiNotification() {
        return getRetrofitNodeJs().create(ApiNotification.class);
    }

    public static ApiRecommend getApiRecommend() {
        return getRetrofitNodeJs().create(ApiRecommend.class);
    }

    private static Retrofit getRetrofitGoogleMapsApi() {
        if (retrofitGoogleMapsApi == null) {
            synchronized (ApiFactory.class) {
                retrofitGoogleMapsApi = new Retrofit.Builder()
                        .baseUrl(ApiConfig.ConfigUrl.URL_GOOGLEAPI)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient())
                        .build();
            }
        }
        return retrofitGoogleMapsApi;
    }

    private static Retrofit getRetrofitNodeJs() {
        if (retrofitNodeJs == null) {
            synchronized (ApiFactory.class) {
                retrofitNodeJs = new Retrofit.Builder()
                        .baseUrl(ApiConfig.ConfigUrl.URL_NODEJS)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient())
                        .build();
            }
        }
        return retrofitNodeJs;
    }

    private static Retrofit getRetrofitOneSignal() {
        if (retrofitOneSignal == null) {
            synchronized (ApiFactory.class) {
                retrofitOneSignal = new Retrofit.Builder()
                        .baseUrl(ApiConfig.ConfigUrl.URL_ONE_SIGNAL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient())
                        .build();
            }
        }
        return retrofitOneSignal;
    }

    @NonNull
    private static OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(ApiConfig.ConfigTime.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(ApiConfig.ConfigTime.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(ApiConfig.ConfigTime.WRITE_TIMEOUT, TimeUnit.SECONDS);
        return builder.build();
    }

}
