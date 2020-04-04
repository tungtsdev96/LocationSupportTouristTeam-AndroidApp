package com.svmc.android.locationsupportteam.ui.roomlocation.detailroom;

import com.svmc.android.locationsupportteam.data.local.LocationDataLocal;
import com.svmc.android.locationsupportteam.data.local.entity.LocationCache;
import com.svmc.android.locationsupportteam.data.remote.RoomLocationRemoteDataSource;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BaseModel;

/**
 * Created by TUNGTS on 4/21/2019
 */

public class ModelDetailRoomLocationImpl extends BaseModel implements MVPDetailRoomLocation.IModelDetailRoomLocation {

    private RoomLocationRemoteDataSource roomLocationRemoteDataSource;
    private LocationDataLocal locationDataLocal;

    public ModelDetailRoomLocationImpl() {
        this.roomLocationRemoteDataSource = new RoomLocationRemoteDataSource();
        locationDataLocal = new LocationDataLocal();
    }

    @Override
    public void postAlert(ParamsPostNotification postAlert, FinishedListener<BaseResultResponse> listener, String image) {
        roomLocationRemoteDataSource.postNotification(postAlert, listener, image);
    }

    @Override
    public LocationCache getCurrentLocation() {
        return locationDataLocal.getLastLocationCache();
    }

}
