package com.svmc.android.locationsupportteam.ui.detailplace;

import com.svmc.android.locationsupportteam.data.local.LocationDataLocal;
import com.svmc.android.locationsupportteam.data.local.PlaceSaveController;
import com.svmc.android.locationsupportteam.data.local.entity.LocationCache;
import com.svmc.android.locationsupportteam.data.remote.GoogleAPIRemoteDataSource;
import com.svmc.android.locationsupportteam.data.remote.RecommendDataRemote;
import com.svmc.android.locationsupportteam.data.remote.RoomLocationRemoteDataSource;
import com.svmc.android.locationsupportteam.entity.googlemap.distance.ResultDistance;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.entity.homescreen.HomeScreenModel;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

import java.util.ArrayList;

/**
 * Created by TungTS on 5/8/2019
 */

public class ModelDetailPlaceImpl implements MVPDetailPlace.IModelDetailPlace {

    private GoogleAPIRemoteDataSource googleAPIRemoteDataSource;
    private LocationDataLocal locationDataLocal;
    private RecommendDataRemote recommendDataRemote;
    private PlaceSaveController placeSaveController;
    private RoomLocationRemoteDataSource roomLocationRemoteDataSource;

    public ModelDetailPlaceImpl() {
        this.googleAPIRemoteDataSource = new GoogleAPIRemoteDataSource();
        this.locationDataLocal = new LocationDataLocal();
        this.recommendDataRemote = new RecommendDataRemote();
        this.placeSaveController = new PlaceSaveController();
        this.roomLocationRemoteDataSource = new RoomLocationRemoteDataSource();
    }

    @Override
    public LocationCache getcurrentLocation() {
        return locationDataLocal.getLastLocationCache();
    }

    @Override
    public void getDetailPlace(String placeId, FinishedListener<ResultPlace> listener) {
        googleAPIRemoteDataSource.getPlaceDetail(placeId, listener);
    }

    @Override
    public void caculDistance(double lat, double lng, String placeId, FinishedListener<ResultDistance> listener) {
        String origin = lat + "," +lng;
        String destination = "place_id:" + placeId;
        googleAPIRemoteDataSource.caculDistance(origin, destination, listener);
    }

    @Override
    public void getPlaceNearby(String location, FinishedListener<BaseResultResponse<ArrayList<HomeScreenModel>>> listener) {
        recommendDataRemote.getRecommendInPlace(location, listener);
    }

    @Override
    public boolean savePlaceOffline(Place place) {
        return placeSaveController.addPlace(place);
    }

    @Override
    public boolean checkPlaceSaveOff(String placeId) {
        return placeSaveController.checkExits(placeId);
    }

    @Override
    public boolean deletePlaceOff(String placeId) {
        return placeSaveController.delete(placeId);
    }

    @Override
    public void sharePlace(ParamsPostNotification postNotification, FinishedListener<BaseResultResponse> listener) {
        roomLocationRemoteDataSource.postNotification(postNotification, listener, null);
    }

    @Override
    public void cancelRequest() {
        googleAPIRemoteDataSource.cancelCallDetailPlace();
        googleAPIRemoteDataSource.cancelCallDistance();
    }

}
