package com.svmc.android.locationsupportteam.ui.home.maps;

import android.content.Intent;
import android.location.Location;
import android.view.KeyEvent;

import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.place.autocomplete.Prediction;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlaceAutocomplete;
import com.svmc.android.locationsupportteam.entity.homescreen.RecentedSearchInMap;
import com.svmc.android.locationsupportteam.entity.SubPlaceType;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

import java.util.ArrayList;

public class MVPMap {

    public interface IViewMaps {

        /***
         * Click hardware button
         * @param keyCode
         * @param event
         * @return true -> back fragment
         *         false -> clollapse bottom sheet
         */
        boolean onKeyDown(int keyCode, KeyEvent event);

        // permission and current location
        void checkAndTurnOnPermission();

        void onPermisstionDenied();

        void checkAndTurnOnCurrentLocation();

        void onSettingLocationDone();

        void onSetUpCurrentLocationCaneled();

        void showCurrentLocation(Location location);

        void moveCameraToCurrentLocation(Location location);

        // setup ui
        void onClickBtnLeft(boolean isStateBack);

        void changeUIAppBar(boolean isHide);

        void changeUIBottomNavigation(boolean isHide);

        void changeFabCurrentLocation(boolean isHide);

        void changeUIBottomSheetWhenTouchMap(boolean isHide);

        void changeIconLeftOnActionBar(boolean isStateBack);

        void changeUIAppBarWhenBottomSheetExplaned();

        void changeUIAppBarWhenBottomSheetCollapse();

        void setStateBottomSheet(int state);

        /**
         * bind data recented search
         * @param items
         */
        void bindUIRecectedSearchInMap(ArrayList<RecentedSearchInMap> items);

        /***
         * show UI Near By Search
         */
        void showUINearBySearch(LocationPoint point, SubPlaceType placeType);

        /**
         * when close UI NearBySerchFragment or ItemPlaceFragment
         */
        void onCloseUIListPlace();

        /***
         * bind data search by text
         */
        void bindUIResultSearch(ResultPlace resultPlace);

        /**
         * bind result autocomplete
         * @param resultPlaceAutocomplete
         */
        void bindUIResultAutoComplete(ResultPlaceAutocomplete resultPlaceAutocomplete);

        /**
         * On Err
         * @param errcode
         */
        void onErr(String errcode);

        /**
         * open fragment ui content place
         * @param placeId
         */
        void openUIPlace(String placeId, String namePlace);

        /***
         * move camera to maker
         * @param point
         */
        void moveCameraToMaker(LocationPoint point);

        void moveCameraToMaker(LocationPoint point, float zoomScale);

        void sharePointSuccess();

        void sharePointErr();
    }

    public interface IPresenterMaps {

        /**
         * change UI when touch on Map,
         * when add ItemPlaceFragment and NearbySearchFragment
         */
        void changeUIOnMap();

        void changeUIWhenClickBtnLeft();

        void changeUIWhenBottomSheetExpland();

        void changeUIWhenBottomSheetCollapse();

        boolean onClickBackHardware();

        void openUIRecentedSearch();

        // permission and current location
        void setUpPermission();

        void setUpCurrentLocation();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        /***
         * Search place by query text
         * @param query
         */
        void searchPlace(String query);

        /**
         * Search place by autocomplete API near location
         * @param query
         * @param location
         */
        void placeAutocomplete(String query, String location);

        /***
         * near By search default simple
         * @param point
         * @param placeType
         */
        void openUINearBySearchPlace(LocationPoint point, SubPlaceType placeType);

        /***
         * add to search cache
         */
        void addPlaceToSearchCache(Object place);

        /**
         * click item in list result search on bottom sheet
         * @param placeId
         * @param place
         */
        void clickItemInListResultSearch(String placeId, Object place);

        /***
         * Open ItemPlaceFragment
         * @param placeId
         * @param name
         */
        void openUIPlaceItem(String placeId, String name);

        /**
         * share point
         * @param urlImage
         * @param isCurrentLocation
         */
        void shareLocation(String urlImage, LocationPoint isCurrentLocation, String message);

    }

    public interface IModelMaps {

        /***
         * get recented search map
         * @return
         */
        ArrayList<RecentedSearchInMap> getListRecentedSearchInMap();

        /***
         * API Search Place by text
         * @param query
         * @return
         */
        void searchPlace(String query, FinishedListener<ResultPlace> listener);

        /***
         * Api autocomplete
         * @param query
         * @param location
         * @return
         */
        void placeAutocomplete(String query, String location, FinishedListener<ResultPlaceAutocomplete> listener);

        void addSearchCache(Prediction prediction);

        void addSearchCache(Place place);

        LocationPoint getCurrentLocationCache();

        void sharePoint(ParamsPostNotification postNotification, FinishedListener<BaseResultResponse> listener, String image);
    }

}
