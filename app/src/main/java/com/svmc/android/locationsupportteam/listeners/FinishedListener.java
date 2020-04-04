package com.svmc.android.locationsupportteam.listeners;

/**
 * Create by TUNGTS on 4/18/2019
 */

public interface FinishedListener<T> {

    void onFinished(T result);

    void onFailure(Throwable t);

}
