package com.svmc.android.locationsupportteam.ui.home.maps.nearbysearch.item;

import com.svmc.android.locationsupportteam.data.local.entity.LocationCache;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BasePresenter;
import com.svmc.android.locationsupportteam.ui.base.BaseView;

/**
 * Create by TungTS on 5/11/2019
 */

public class MVPItem {

    public interface IViewItem extends BaseView<IPresenterItem> {

        void showUIDetailPlace();

        void sharePlaceSuccess();

        void sharePlaceErr();

    }

    public interface IPresenterItem extends BasePresenter {

        /**
         * Place Offline
         * @param placeId
         * @return
         */
        boolean checkPlaceSaveOff(String placeId);

        boolean savePlaceOffline(Place place);

        boolean deletePlaceOff(String placeId);

        /**
         * share place
         */
        void sharePlace(Place place);

        LocationPoint getCurrentLocation();
    }

    public interface IModelItem {

        LocationCache getcurrentLocation();

        boolean checkPlaceSaveOff(String placeId);

        boolean savePlaceOffline(Place place);

        boolean deletePlaceOff(String placeId);

        void sharePlace(ParamsPostNotification postNotification, FinishedListener<BaseResultResponse> listener);

    }

}
