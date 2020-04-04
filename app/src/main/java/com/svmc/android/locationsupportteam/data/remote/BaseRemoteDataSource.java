package com.svmc.android.locationsupportteam.data.remote;

/**
 * Created by TUNGTS on 4/21/2019
 */

public abstract class BaseRemoteDataSource {

    private String idToken;

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getIdToken() {
        return idToken;
    }
}
