package com.svmc.android.locationsupportteam.ui.roomlocation.detailroom;

import android.content.Intent;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.svmc.android.locationsupportteam.data.local.LocationDataLocal;
import com.svmc.android.locationsupportteam.data.local.entity.LocationCache;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.ErrCode;

import static android.app.Activity.RESULT_OK;

/**
 * Created by TUNGTS on 4/21/2019
 */

public class PresenterDetailRoomLocationImpl implements MVPDetailRoomLocation.IPresenterDetailRoomLocation {

    private MVPDetailRoomLocation.IViewDetailRoomLocation viewDetailRoomLocation;
    private MVPDetailRoomLocation.IModelDetailRoomLocation modelDetailRoomLocation;
    private LocationDataLocal dataLocal;
    private boolean isFirstTouchMap = false;

    public PresenterDetailRoomLocationImpl(MVPDetailRoomLocation.IViewDetailRoomLocation viewDetailRoomLocation) {
        this.viewDetailRoomLocation = viewDetailRoomLocation;
        this.viewDetailRoomLocation.setPresenter(this);
        this.modelDetailRoomLocation = new ModelDetailRoomLocationImpl();
        dataLocal = new LocationDataLocal();

    }

    @Override
    public void start() {

    }

    @Override
    public void clickButtonLeft() {
        viewDetailRoomLocation.changeNavigation();
    }

    @Override
    public void clickButtonRight() {
        viewDetailRoomLocation.showUIAddMember();
    }

    @Override
    public void onTouchMap() {
        if (!isFirstTouchMap) {
            isFirstTouchMap = true;
            viewDetailRoomLocation.hideActionBar();
//            viewDetailRoomLocation.hideFabAlert();
            viewDetailRoomLocation.hideFabDirection();
        }  else {
            isFirstTouchMap = false;
            viewDetailRoomLocation.showActionBar();
            viewDetailRoomLocation.showFabAlert();
        }
    }

    @Override
    public LocationPoint getLastKnowLocation() {
        LocationCache locationCache = dataLocal.getLastLocationCache();
        return new LocationPoint(locationCache.getLat(), locationCache.getLng());
    }

    @Override
    public void showCurrentLocation() {
        Location location = new Location("TrackingLocation");
        location.setLatitude(dataLocal.getLastLocationCache().getLat());
        location.setLongitude(dataLocal.getLastLocationCache().getLng());
        location.setAccuracy(dataLocal.getLastLocationCache().getAccuracy());
        viewDetailRoomLocation.showCurrentLocation(location);
        viewDetailRoomLocation.moveCameraToLocation(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void showCurrentLocation(int stateView) {
        Location location = new Location("TrackingLocation");
        location.setLatitude(dataLocal.getLastLocationCache().getLat());
        location.setLongitude(dataLocal.getLastLocationCache().getLng());
        location.setAccuracy(dataLocal.getLastLocationCache().getAccuracy());
        viewDetailRoomLocation.showCurrentLocation(location);
        if (stateView == DetailRoomLocationFragment.VIEW_STATE_NORMAL) {
            viewDetailRoomLocation.moveCameraToLocation(new LatLng(location.getLatitude(), location.getLongitude()));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == Constans.RequestCode.RC_ADD_MEMBER_TO_GROUP) {
            // get list member and add Member to room
        }
    }

    @Override
    public void postAlert(RoomLocation roomLocation, String message, String type, String image) {
        User user = AppPreferencens.getInstance().getUser();
        LocationCache location = modelDetailRoomLocation.getCurrentLocation();
        ParamsPostNotification.RoomParams room = new ParamsPostNotification.RoomParams(roomLocation.getRoomId(), roomLocation.getNameRoom());
        ParamsPostNotification.SenderParams senderParams = new ParamsPostNotification.SenderParams(user.getUserId(), user.getDisplayName(), user.getPhoneNumber());
        ParamsPostNotification notification = new ParamsPostNotification(senderParams, room, message, type, new LocationPoint(location.getLat(), location.getLng()));

        modelDetailRoomLocation.postAlert(notification, new FinishedListener<BaseResultResponse>() {
            @Override
            public void onFinished(BaseResultResponse result) {
                if (result.getStatus() == ErrCode.CommonCode.RESULT_OK) {
                    viewDetailRoomLocation.onPostAlertSuccess();
                } else {
                    viewDetailRoomLocation.onPostAlertErr();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                viewDetailRoomLocation.onPostAlertErr();
            }
        }, image);

    }

}
