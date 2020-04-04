package com.svmc.android.locationsupportteam.data.remote;

import com.svmc.android.locationsupportteam.entity.DeviceUser;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.ApiFactory;
import com.svmc.android.locationsupportteam.network.BaseCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

/**
 * Create by TungTS on 5/2/2019
 */

public class DeviceRemoteDataSource extends BaseRemoteDataSource {

    // add to db
    public void updateDevice(DeviceUser deviceUser, final FinishedListener<BaseResultResponse<DeviceUser>> finishedListener) {

        ApiFactory.getApiDevice().updateUser(getIdToken(), deviceUser).enqueue(new BaseCallBack<BaseResultResponse<DeviceUser>>() {
            @Override
            public void onSuccess(BaseResultResponse<DeviceUser> result) {
                finishedListener.onFinished(result);
            }

            @Override
            public void onFailure(Throwable t) {
                finishedListener.onFailure(t);
            }
        });

    }

}
