package com.svmc.android.locationsupportteam.firebase.auth;


/**
 * Created by TUNGTS on 3/17/2019
 */

public abstract class BaseAuthenticated<T> {

    public abstract void fireBaseAuth(T t);

    public abstract void signOut();

}
