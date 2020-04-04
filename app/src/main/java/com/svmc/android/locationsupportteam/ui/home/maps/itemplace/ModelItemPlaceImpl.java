package com.svmc.android.locationsupportteam.ui.home.maps.itemplace;

import com.svmc.android.locationsupportteam.data.local.LocationDataLocal;
import com.svmc.android.locationsupportteam.data.local.PlaceSaveController;
import com.svmc.android.locationsupportteam.data.local.entity.LocationCache;
import com.svmc.android.locationsupportteam.data.remote.GoogleAPIRemoteDataSource;
import com.svmc.android.locationsupportteam.data.remote.RoomLocationRemoteDataSource;
import com.svmc.android.locationsupportteam.entity.googlemap.distance.ResultDistance;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

/**
 * Created by TungTS on 5/7/2019
 */

public class ModelItemPlaceImpl implements MVPItemPlace.IModelItemPlace {

    private GoogleAPIRemoteDataSource googleAPIRemoteDataSource;
    private LocationDataLocal locationDataLocal;
    private PlaceSaveController controller;
    private RoomLocationRemoteDataSource roomLocationRemoteDataSource;

    public ModelItemPlaceImpl() {
        this.googleAPIRemoteDataSource = new GoogleAPIRemoteDataSource();
        this.locationDataLocal = new LocationDataLocal();
        controller = new PlaceSaveController();
        roomLocationRemoteDataSource = new RoomLocationRemoteDataSource();
    }

    @Override
    public LocationCache getcurrentLocation() {
        return locationDataLocal.getLastLocationCache();
    }

    @Override
    public void getDetailPlace(String placeId, FinishedListener<ResultPlace> listener) {
        googleAPIRemoteDataSource.getSimplePlace(placeId, listener);
    }

    @Override
    public void caculDistance(double lat, double lng, String placeId, FinishedListener<ResultDistance> listener) {
        String origin = lat + "," +lng;
        String destination = "place_id:" + placeId;
        googleAPIRemoteDataSource.caculDistance(origin, destination, listener);
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

    @Override
    public void onStop() {
        googleAPIRemoteDataSource.cancelCallSimplePlace();
        googleAPIRemoteDataSource.cancelCallDistance();
    }
}
