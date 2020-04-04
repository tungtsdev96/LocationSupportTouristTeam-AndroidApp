package com.svmc.android.locationsupportteam.ui.home.maps.itemplace;

import com.svmc.android.locationsupportteam.data.local.entity.LocationCache;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.distance.ResultDistance;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.utils.ErrCode;



/**
 * Created by TungTS on 5/7/2019
 */

public class PresenterItemPlaceImpl implements MVPItemPlace.IPresenterItemPlace {

    private MVPItemPlace.IViewItemPlace viewItemPlace;
    private MVPItemPlace.IModelItemPlace modelItemPlace;

    public PresenterItemPlaceImpl(MVPItemPlace.IViewItemPlace viewItemPlace) {
        this.viewItemPlace = viewItemPlace;
        this.viewItemPlace.setPresenter(this);
        modelItemPlace = new ModelItemPlaceImpl();
    }

    @Override
    public void start() {

    }


    @Override
    public void getDetailPlace(String placeId) {
        modelItemPlace.getDetailPlace(placeId, new FinishedListener<ResultPlace>() {
            @Override
            public void onFinished(ResultPlace result) {
                if (viewItemPlace == null) return;
                viewItemPlace.hideProgressBar();
                if (result.getStatus().equals(ErrCode.GGMapCode.OK)) {

                    Place place = result.getPlace();
                    // callback bind data to view
                    viewItemPlace.bindDataDetailPlace(place);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                viewItemPlace.hideProgressBar();
                viewItemPlace.onLoadDataErr();
            }
        });
    }

    @Override
    public void calDistance(String placeId) {
        LocationCache locationCache = modelItemPlace.getcurrentLocation();
        if (locationCache == null) return;

        modelItemPlace.caculDistance(locationCache.getLat(), locationCache.getLng(), placeId, new FinishedListener<ResultDistance>() {
            @Override
            public void onFinished(ResultDistance result) {
                viewItemPlace.hideProgressBar();
                if (result.getStatus().equals(ErrCode.GGMapCode.OK)) {
                    viewItemPlace.bindDataDistanceFromCurrentLocation(result);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                viewItemPlace.hideProgressBar();
                viewItemPlace.onLoadDataErr();
            }
        });
    }

    @Override
    public void sharePlace(Place place) {
        User user = AppPreferencens.getInstance().getUser();
        String roomId = AppPreferencens.getInstance().getRoomId();
        ParamsPostNotification.SenderParams sender = new ParamsPostNotification.SenderParams(user.getUserId(), user.getDisplayName(), user.getPhoneNumber());
        ParamsPostNotification.RoomParams roomParams = new ParamsPostNotification.RoomParams(roomId, null);
        ParamsPostNotification notification = new ParamsPostNotification(sender, roomParams, "", RoomLocationNotification.STATUS_SHARE_PLACE, place.getGeometry().getLocation(), place.getPlaceId());

        modelItemPlace.sharePlace(notification, new FinishedListener<BaseResultResponse>() {
            @Override
            public void onFinished(BaseResultResponse result) {
                if (result.getStatus() == ErrCode.CommonCode.RESULT_OK) {
                    viewItemPlace.sharePlaceSuccess();
                } else {
                    viewItemPlace.sharePlaceErr();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                viewItemPlace.sharePlaceErr();
            }
        });
    }

    @Override
    public boolean checkPlaceSaveOff(String placeId) {
        return modelItemPlace.checkPlaceSaveOff(placeId);
    }

    @Override
    public boolean savePlaceOffline(Place place) {
        return modelItemPlace.savePlaceOffline(place);
    }

    @Override
    public boolean deletePlaceOff(String placeId) {
        return modelItemPlace.deletePlaceOff(placeId);
    }

    @Override
    public LocationPoint getCurrentLocation() {
        LocationCache locationCache = modelItemPlace.getcurrentLocation();
        return new LocationPoint(locationCache.getLat(), locationCache.getLng());
    }

    @Override
    public void onStop() {
        modelItemPlace.onStop();
    }
}
