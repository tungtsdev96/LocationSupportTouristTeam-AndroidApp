package com.svmc.android.locationsupportteam.ui.roomlocation.detailroom;

import android.content.Intent;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.svmc.android.locationsupportteam.data.local.entity.LocationCache;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BasePresenter;
import com.svmc.android.locationsupportteam.ui.base.BaseViewAuth;

/**
 * Created by TUNGTS on 4/21/2019
 */

public class MVPDetailRoomLocation {

    public interface IViewDetailRoomLocation extends BaseViewAuth<IPresenterDetailRoomLocation> {


        void changeNavigation();

        /***
         * show and hide fab
         */
        void showFabAlert();

        void hideFabAlert();

        void showFabDirection();

        void hideFabDirection();

        /**
         * show and hide actionbar
         */
        void showActionBar();

        void hideActionBar();

        /**
         * show UI Add and invite member
         */
        void showUIAddMember();

        void showBottomSheetCreateMessageAlert();

        /**
         * show current location source
         * @param location
         */
        void showCurrentLocation(Location location);

        /**
         * Move camera to
         * @param latLng
         */
        void moveCameraToLocation(LatLng latLng);

        void onErr();

        void onPostAlertSuccess();

        void onPostAlertErr();
    }

    public interface IPresenterDetailRoomLocation extends BasePresenter {

        void clickButtonLeft();

        void clickButtonRight();

        void onTouchMap();

        LocationPoint getLastKnowLocation();

        void showCurrentLocation();

        /**
         * show current location
         * @param stateView
         */
        void showCurrentLocation(int stateView);

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void postAlert(RoomLocation roomLocation, String message, String type, String image);

    }

    public interface IModelDetailRoomLocation {

        void postAlert(ParamsPostNotification postAlert, FinishedListener<BaseResultResponse> listener, String image);

        LocationCache getCurrentLocation();
    }

}
