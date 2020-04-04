package com.svmc.android.locationsupportteam.listeners;

/**
 * Create by TUNGTS on 4/18/2019
 */

public interface IdTokenCallBack {

    void onComplete(String idToken);

    void onFailure(Throwable e);

}
