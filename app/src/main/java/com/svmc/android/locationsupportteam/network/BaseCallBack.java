package com.svmc.android.locationsupportteam.network;

import android.content.Context;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TUNGTS on 3/16/2019
 */

public abstract class BaseCallBack<T> implements Callback<T> {

    public BaseCallBack() {
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        Log.d("BaseCallback_url", call.request().url().url().toString());
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            Log.e("BaseCallback_response", response.code() + " Message: " + response.message());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.e("BaseCallback_fail" , t.getClass().getName() + " " + t.getLocalizedMessage());
        onFailure(t);
    }

    public abstract void onSuccess(T result);

    public abstract void onFailure(Throwable t);

}
