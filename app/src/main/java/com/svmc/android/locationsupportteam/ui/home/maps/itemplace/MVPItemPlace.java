package com.svmc.android.locationsupportteam.ui.home.maps.itemplace;


import com.svmc.android.locationsupportteam.data.local.entity.LocationCache;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.distance.ResultDistance;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BasePresenter;
import com.svmc.android.locationsupportteam.ui.base.BaseView;

/**
 * Created by TungTS on 5/7/2019
 */

public class MVPItemPlace  {

    public interface IViewItemPlace extends BaseView<IPresenterItemPlace> {

        void showUIDetailPlace();

        void hideProgressBar();

        void bindDataDetailPlace(Place place);

        void bindDataDistanceFromCurrentLocation(ResultDistance resultDistance);

        void onLoadDataErr();

        void sharePlaceSuccess();

        void sharePlaceErr();

    }

    public interface IPresenterItemPlace extends BasePresenter {

        /***
         * get all detail place by Id
         * @param placeId
         */
        void getDetailPlace(String placeId);

        /***
         * Cacute distance from current locatiton to place
         * @param placeId
         */
        void calDistance(String placeId);

        /**
         * share place
         */
        void sharePlace(Place place);

        /**
         * Place Offline
         * @param placeId
         * @return
         */
        boolean checkPlaceSaveOff(String placeId);

        boolean savePlaceOffline(Place place);

        boolean deletePlaceOff(String placeId);

        LocationPoint getCurrentLocation();

        void onStop();
    }

    public interface IModelItemPlace {

        LocationCache getcurrentLocation();

        void getDetailPlace(String placeId, FinishedListener<ResultPlace> listener);

        void caculDistance(double lat, double lng, String placeId, FinishedListener<ResultDistance> listener);

        boolean checkPlaceSaveOff(String placeId);

        boolean savePlaceOffline(Place place);

        boolean deletePlaceOff(String placeId);

        void sharePlace(ParamsPostNotification postNotification, FinishedListener<BaseResultResponse> listener);

        void onStop();
    }

}
