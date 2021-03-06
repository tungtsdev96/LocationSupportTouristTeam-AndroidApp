package com.svmc.android.locationsupportteam.ui.home.maps.nearbysearch.item;

import com.svmc.android.locationsupportteam.data.local.LocationDataLocal;
import com.svmc.android.locationsupportteam.data.local.PlaceSaveController;
import com.svmc.android.locationsupportteam.data.local.entity.LocationCache;
import com.svmc.android.locationsupportteam.data.remote.RoomLocationRemoteDataSource;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

/**
 * Create by TungTS on 5/11/2019
 */

public class ModelItemImpl implements MVPItem.IModelItem {

    private LocationDataLocal locationDataLocal;
    private PlaceSaveController controller;
    private RoomLocationRemoteDataSource roomLocationRemoteDataSource;

    public ModelItemImpl() {
        this.locationDataLocal = new LocationDataLocal();
        controller = new PlaceSaveController();
        roomLocationRemoteDataSource = new RoomLocationRemoteDataSource();
    }

    @Override
    public LocationCache getcurrentLocation() {
        return locationDataLocal.getLastLocationCache();
    }

    @Override
    public boolean checkPlaceSaveOff(String placeId) {
        return controller.checkExits(placeId);
    }

    @Override
    public boolean savePlaceOffline(Place place) {
        return controller.addPlace(place);
    }

    @Override
    public boolean deletePlaceOff(String placeId) {
        return controller.delete(placeId);
    }

    @Override
    public void sharePlace(ParamsPostNotification postNotification, FinishedListener<BaseResultResponse> listener) {
        roomLocationRemoteDataSource.postNotification(postNotification, listener, null);
    }
}
