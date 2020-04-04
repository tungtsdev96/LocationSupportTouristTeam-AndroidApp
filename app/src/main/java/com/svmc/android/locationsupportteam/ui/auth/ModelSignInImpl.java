package com.svmc.android.locationsupportteam.ui.auth;

import com.svmc.android.locationsupportteam.data.remote.DeviceRemoteDataSource;
import com.svmc.android.locationsupportteam.data.remote.UserRemoteDataSource;
import com.svmc.android.locationsupportteam.entity.DeviceUser;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.listeners.IdTokenCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BaseModel;

import java.util.ArrayList;

/**
 * Created by TUNGTS on 3/19/2019
 */

public class ModelSignInImpl extends BaseModel implements MVPSignIn.IModelSignIn {

    private DeviceRemoteDataSource deviceRemoteDataSource;
    private UserRemoteDataSource userRemoteDataSource;

    public ModelSignInImpl() {
        this.deviceRemoteDataSource = new DeviceRemoteDataSource();
        this.userRemoteDataSource = new UserRemoteDataSource();
    }

    @Override
    public void addDeviceToDatabase(final DeviceUser deviceUser, final FinishedListener<BaseResultResponse<DeviceUser>> finishedListener) {
        getIdToken(new IdTokenCallBack() {
            @Override
            public void onComplete(String idToken) {
                deviceRemoteDataSource.setIdToken(idToken);
                deviceRemoteDataSource.updateDevice(deviceUser, finishedListener);
            }

            @Override
            public void onFailure(Throwable e) {
                finishedListener.onFailure(e);
            }
        });
    }

    @Override
    public void getInforUserById(String useId, FinishedListener<User> listener) {
        userRemoteDataSource.getInforUser(useId, listener);
    }

    @Override
    public void getPlaceSaved(String uid, FinishedListener<BaseResultResponse<ArrayList<Place>>> listener) {
        userRemoteDataSource.getAllPlaceSaved(uid, listener);
    }


}
