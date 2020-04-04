package com.svmc.android.locationsupportteam.ui.home.maps;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;

import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.place.autocomplete.Prediction;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlaceAutocomplete;
import com.svmc.android.locationsupportteam.entity.homescreen.RecentedSearchInMap;
import com.svmc.android.locationsupportteam.entity.SubPlaceType;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.ErrCode;

import static android.app.Activity.RESULT_OK;

/**
 * Created by TUNGTS on 3/20/2019
 */

public class PresenterMapImpl implements MVPMap.IPresenterMaps {

    private Handler handler;
    private MVPMap.IViewMaps viewMap;
    private MVPMap.IModelMaps modelMaps;

    private boolean isChangeUIMap = true;

    private boolean isCanBack = false;

    private boolean isClickItemOnResultSearch = false;

    public void setClickItemOnResultSearch(boolean clickItemOnResultSearch) {
        isClickItemOnResultSearch = clickItemOnResultSearch;
    }

    private boolean isNearBySearch = false;

    public void setNearBySearch(boolean nearBySearch) {
        isNearBySearch = nearBySearch;
    }

    public PresenterMapImpl(MVPMap.IViewMaps viewMap) {
        this.viewMap = viewMap;
        this.modelMaps = new ModelMapImpl();
        handler = new Handler();
    }

    @Override
    public void changeUIOnMap() {
        if (isNearBySearch || isClickItemOnResultSearch) return;
        viewMap.changeUIAppBar(isChangeUIMap);
        viewMap.changeUIBottomNavigation(isChangeUIMap);
        viewMap.changeFabCurrentLocation(isChangeUIMap);
        viewMap.changeUIBottomSheetWhenTouchMap(isChangeUIMap);
        isChangeUIMap = !isChangeUIMap;
    }

    @Override
    public void changeUIWhenClickBtnLeft() {
        viewMap.onClickBtnLeft(isCanBack);
    }

    @Override
    public void changeUIWhenBottomSheetExpland() {
        if (isCanBack) return;
        isCanBack = true;
        viewMap.changeIconLeftOnActionBar(isCanBack);
        viewMap.changeUIAppBarWhenBottomSheetExplaned();
    }

    @Override
    public void changeUIWhenBottomSheetCollapse() {
        if (!isCanBack) return;
        isCanBack = false;
        viewMap.changeIconLeftOnActionBar(isCanBack);
        viewMap.changeUIAppBarWhenBottomSheetCollapse();
    }

    @Override
    public boolean onClickBackHardware() {
        if (isCanBack) {
            changeUIWhenBottomSheetCollapse();
            viewMap.setStateBottomSheet(BottomSheetBehavior.STATE_COLLAPSED);
            return false;
        }
        viewMap.changeUIBottomNavigation(false);
        return true;
    }

    @Override
    public void openUIRecentedSearch() {
        viewMap.bindUIRecectedSearchInMap(modelMaps.getListRecentedSearchInMap());
    }

    // permisson and current location
    @Override
    public void setUpPermission() {
        viewMap.checkAndTurnOnPermission();
    }

    @Override
    public void setUpCurrentLocation() {
        viewMap.checkAndTurnOnCurrentLocation();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case Constans.RequestCode.RC_CHECK_SETTINGS_CURRENT_LOCATION:
                switch (resultCode) {
                    case RESULT_OK:
                        viewMap.onSettingLocationDone();
                        break;
                    case Activity.RESULT_CANCELED:
                        viewMap.onSetUpCurrentLocationCaneled();
                        break;
                }
                break;
        }

        if (requestCode == Constans.RequestCode.RC_CHOOSE_PLACE_TYPE && resultCode == RESULT_OK) {
            String jsonData = data.getStringExtra(Constans.KeyBundle.CHOOSE_PLACE_TYPE);
            SubPlaceType subPlaceType = new Gson().fromJson(jsonData, SubPlaceType.class);
            openUINearBySearchPlace(modelMaps.getCurrentLocationCache() ,subPlaceType);
        }

    }

    @Override
    public void searchPlace(String query) {
        modelMaps.searchPlace(query, new FinishedListener<ResultPlace>() {
            @Override
            public void onFinished(ResultPlace result) {
                switch (result.getStatus()) {
                    case ErrCode.GGMapCode.OK:
                        viewMap.bindUIResultSearch(result);
                        break;
                    case ErrCode.GGMapCode.ZERO_RESULTS:
                    case ErrCode.GGMapCode.UNKNOWN_ERROR:
                        viewMap.onErr(result.getStatus());
                        break;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                viewMap.onErr(ErrCode.GGMapCode.UNKNOWN_ERROR);
            }
        });
    }

    @Override
    public void placeAutocomplete(String query, String location) {

        modelMaps.placeAutocomplete(query, location, new FinishedListener<ResultPlaceAutocomplete>() {
            @Override
            public void onFinished(ResultPlaceAutocomplete result) {
                switch (result.getStatus()) {
                    case ErrCode.GGMapCode.OK:
                        viewMap.bindUIResultAutoComplete(result);
                        break;
                    case ErrCode.GGMapCode.ZERO_RESULTS:
                    case ErrCode.GGMapCode.UNKNOWN_ERROR:
                        viewMap.onErr(result.getStatus());
                        break;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                viewMap.onErr(ErrCode.GGMapCode.UNKNOWN_ERROR);
            }
        });

    }

    @Override
    public void openUINearBySearchPlace(final LocationPoint point, final SubPlaceType placeType) {
        viewMap.setStateBottomSheet(BottomSheetBehavior.STATE_COLLAPSED);
        if (isChangeUIMap) changeUIOnMap();
        isNearBySearch = true;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewMap.showUINearBySearch(point, placeType);
            }
        }, 200);
    }

    @Override
    public void addPlaceToSearchCache(Object place) {
        if (place instanceof Place) modelMaps.addSearchCache((Place) place);
        if (place instanceof Prediction) modelMaps.addSearchCache((Prediction) place);
    }

    @Override
    public void clickItemInListResultSearch(String placeId, Object place) {
        addPlaceToSearchCache(place);
        String name = "";
        if (place instanceof Place) name = ((Place) place).getName();
        if (place instanceof Prediction)
            name = ((Prediction) place).getStructuredFormatting().getMainText();
        if (place instanceof RecentedSearchInMap)
            name = ((RecentedSearchInMap) place).getNamePlace();
        if (isChangeUIMap) changeUIOnMap();
        isClickItemOnResultSearch = true;
        openUIPlaceItem(placeId, name);
    }

    @Override
    public void openUIPlaceItem(final String placeId, final String name) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewMap.openUIPlace(placeId, name);
            }
        }, 200);
    }

    @Override
    public void shareLocation(String urlImage, LocationPoint point, String message) {
        User user = AppPreferencens.getInstance().getUser();
        String roomId = AppPreferencens.getInstance().getRoomId();
        ParamsPostNotification.SenderParams sender = new ParamsPostNotification.SenderParams(user.getUserId(), user.getDisplayName(), user.getPhoneNumber());
        ParamsPostNotification.RoomParams roomParams = new ParamsPostNotification.RoomParams(roomId, null);
        ParamsPostNotification notification = new ParamsPostNotification(sender, roomParams, message, RoomLocationNotification.STATUS_SHARE_PLACE, point, null);

        modelMaps.sharePoint(notification, new FinishedListener<BaseResultResponse>() {
            @Override
            public void onFinished(BaseResultResponse result) {
                if (result.getStatus() == ErrCode.CommonCode.RESULT_OK) {
                    viewMap.sharePointSuccess();
                } else {
                    viewMap.sharePointErr();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                viewMap.sharePointErr();
            }
        }, urlImage);
    }
}
