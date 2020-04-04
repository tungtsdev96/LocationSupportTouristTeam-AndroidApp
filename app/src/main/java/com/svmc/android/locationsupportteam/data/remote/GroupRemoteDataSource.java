package com.svmc.android.locationsupportteam.data.remote;

import com.svmc.android.locationsupportteam.entity.group.Group;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.ApiFactory;
import com.svmc.android.locationsupportteam.network.BaseCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Create by TUNGTS on 4/18/2019
 */

public class GroupRemoteDataSource extends BaseRemoteDataSource{

    public void addGroup(final FinishedListener<JSONObject> onFinishedListener, Group group) {

    }

}
