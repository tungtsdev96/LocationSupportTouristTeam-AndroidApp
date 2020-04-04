package com.svmc.android.locationsupportteam.ui.home.maps.nearbysearch.item;

import com.svmc.android.locationsupportteam.data.local.entity.LocationCache;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.utils.ErrCode;

/**
 * Create by TungTS on 5/11/2019
 */

public class PresenterItemImpl implements MVPItem.IPresenterItem {

    private MVPItem.IViewItem viewItem;
    private MVPItem.IModelItem modelItem;

    public PresenterItemImpl(MVPItem.IViewItem viewItemPlace) {
        this.viewItem = viewItemPlace;
        this.viewItem.setPresenter(this);
        modelItem = new ModelItemImpl();
    }

    @Override
    public void start() {

    }

    @Override
    public boolean checkPlaceSaveOff(String placeId) {
        return modelItem.checkPlaceSaveOff(placeId);
    }

    @Override
    public boolean savePlaceOffline(Place place) {
        return modelItem.savePlaceOffline(place);
    }

    @Override
    public boolean deletePlaceOff(String placeId) {
        return modelItem.deletePlaceOff(placeId);
    }

    @Override
    public void sharePlace(Place place) {
        User user = AppPreferencens.getInstance().getUser();
        String roomId = AppPreferencens.getInstance().getRoomId();
        ParamsPostNotification.SenderParams sender = new ParamsPostNotification.SenderParams(user.getUserId(), user.getDisplayName(), user.getPhoneNumber());
        ParamsPostNotification.RoomParams roomParams = new ParamsPostNotification.RoomParams(roomId, null);
        ParamsPostNotification notification = new ParamsPostNotification(sender, roomParams, "", RoomLocationNotification.STATUS_SHARE_PLACE, place.getGeometry().getLocation(), place.getPlaceId());

        modelItem.sharePlace(notification, new FinishedListener<BaseResultResponse>() {
            @Override
            public void onFinished(BaseResultResponse result) {
                if (result.getStatus() == ErrCode.CommonCode.RESULT_OK) {
                    viewItem.sharePlaceSuccess();
                } else {
                    viewItem.sharePlaceErr();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                viewItem.sharePlaceErr();
            }
        });
    }

    @Override
    public LocationPoint getCurrentLocation() {
        LocationCache locationCache = modelItem.getcurrentLocation();
        return new LocationPoint(locationCache.getLat(), locationCache.getLng());
    }

}

