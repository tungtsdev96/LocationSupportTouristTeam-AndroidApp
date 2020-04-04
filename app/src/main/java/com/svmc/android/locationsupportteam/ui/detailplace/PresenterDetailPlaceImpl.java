package com.svmc.android.locationsupportteam.ui.detailplace;

import com.svmc.android.locationsupportteam.data.local.entity.LocationCache;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.distance.ResultDistance;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.entity.homescreen.HomeScreenModel;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.utils.ErrCode;

import java.util.ArrayList;

/**
 * Created by TungTS on 5/8/2019
 */

public class PresenterDetailPlaceImpl implements MVPDetailPlace.IPresenterDetailPlace {

    private MVPDetailPlace.IViewDetailPlace viewDetailPlace;
    private MVPDetailPlace.IModelDetailPlace modelDetailPlace;

    public PresenterDetailPlaceImpl(MVPDetailPlace.IViewDetailPlace viewDetailPlace) {
        this.viewDetailPlace = viewDetailPlace;
        this.viewDetailPlace.setPresenter(this);
        modelDetailPlace = new ModelDetailPlaceImpl();
    }

    @Override
    public void start() {

    }

    @Override
    public void getDetailPlace(String placeId) {
        modelDetailPlace.getDetailPlace(placeId, new FinishedListener<ResultPlace>() {
            @Override
            public void onFinished(ResultPlace result) {
                if (result.getStatus().equals(ErrCode.GGMapCode.OK)) {
                    Place place = result.getPlace();
                    viewDetailPlace.bindDataDetailPlace(place);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                viewDetailPlace.onLoadDataErr();
            }
        });
    }

    @Override
    public void calDistance(String placeId) {
        LocationCache locationCache = modelDetailPlace.getcurrentLocation();
        if (locationCache == null) return;

        modelDetailPlace.caculDistance(locationCache.getLat(), locationCache.getLng(), placeId, new FinishedListener<ResultDistance>() {
            @Override
            public void onFinished(ResultDistance result) {
                if (result.getStatus().equals(ErrCode.GGMapCode.OK)) {
                    viewDetailPlace.bindDataDistanceFromCurrentLocation(result);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                viewDetailPlace.onLoadDataErr();
            }
        });
    }

    @Override
    public void loadNearPlace(String location) {
        modelDetailPlace.getPlaceNearby(location, new FinishedListener<BaseResultResponse<ArrayList<HomeScreenModel>>>() {
            @Override
            public void onFinished(BaseResultResponse<ArrayList<HomeScreenModel>> result) {
                if (result.getStatus() == ErrCode.CommonCode.RESULT_OK) {
                    viewDetailPlace.bindDataToRcvRecommendNearBy(result.getResult());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                viewDetailPlace.onLoadDataErr();
            }
        });
    }

    @Override
    public boolean savePlaceOffline(Place place) {
        return modelDetailPlace.savePlaceOffline(place);
    }

    @Override
    public boolean checkPlaceSaveOff(String placeId) {
        return modelDetailPlace.checkPlaceSaveOff(placeId);
    }

    @Override
    public boolean deletePlaceOff(String placeId) {
        return modelDetailPlace.deletePlaceOff(placeId);
    }

    @Override
    public void sharePlace(Place place) {
        User user = AppPreferencens.getInstance().getUser();
        String roomId = AppPreferencens.getInstance().getRoomId();
        ParamsPostNotification.SenderParams sender = new ParamsPostNotification.SenderParams(user.getUserId(), user.getDisplayName(), user.getPhoneNumber());
        ParamsPostNotification.RoomParams roomParams = new ParamsPostNotification.RoomParams(roomId, null);
        ParamsPostNotification notification = new ParamsPostNotification(sender, roomParams, "", RoomLocationNotification.STATUS_SHARE_PLACE, place.getGeometry().getLocation(), place.getPlaceId());

        modelDetailPlace.sharePlace(notification, new FinishedListener<BaseResultResponse>() {
            @Override
            public void onFinished(BaseResultResponse result) {
                if (result.getStatus() == ErrCode.CommonCode.RESULT_OK) {
                    viewDetailPlace.sharePlaceSuccess();
                } else {
                    viewDetailPlace.sharePlaceErr();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                viewDetailPlace.sharePlaceErr();
            }
        });
    }

    @Override
    public void onStop() {
        modelDetailPlace.cancelRequest();
    }

    @Override
    public LocationPoint getCurrentLocation() {
        LocationCache locationCache = modelDetailPlace.getcurrentLocation();
        return new LocationPoint(locationCache.getLat(), locationCache.getLng());
    }
}
