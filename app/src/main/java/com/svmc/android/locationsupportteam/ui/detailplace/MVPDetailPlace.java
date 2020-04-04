package com.svmc.android.locationsupportteam.ui.detailplace;

import com.svmc.android.locationsupportteam.data.local.entity.LocationCache;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.distance.ResultDistance;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.entity.homescreen.HomeScreenModel;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BasePresenter;
import com.svmc.android.locationsupportteam.ui.base.BaseView;

import java.util.ArrayList;

/**
 * Created by TungTS on 5/8/2019
 */

public class MVPDetailPlace {

    public interface IViewDetailPlace extends BaseView<IPresenterDetailPlace> {

        /**
         * Bind data to
         * @param place
         */
        void bindDataDetailPlace(Place place);

        /**
         * Bind data to LLInfor
         */
        void bindDataToLLInforPlace();

        /**
         * bind distance and duration
         * @param result
         */
        void bindDataDistanceFromCurrentLocation(ResultDistance result);

        /**
         * bind Data to Rcv Reviews
         */
        void bindDataToRcvReciews();

        /***
         * bind data to RcvRecommend Place
         */
        void bindDataToRcvRecommendNearBy(ArrayList<HomeScreenModel> homeScreenModels);

        void onLoadDataErr();

        void sharePlaceSuccess();

        void sharePlaceErr();
    }

    public interface IPresenterDetailPlace extends BasePresenter {

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
         * Load data near by location
         */
        void loadNearPlace(String location);

        /***
         * Save place offline
         */
        boolean savePlaceOffline(Place place);
        boolean checkPlaceSaveOff(String placeId);
        boolean deletePlaceOff(String placeId);

        /***
         * share place
         */
        void sharePlace(Place place);

        void onStop();

        LocationPoint getCurrentLocation();
    }

    public interface IModelDetailPlace {

        LocationCache getcurrentLocation();

        void getDetailPlace(String placeId, FinishedListener<ResultPlace> listener);

        void caculDistance(double lat, double lng, String placeId, FinishedListener<ResultDistance> listener);

        void getPlaceNearby(String location, FinishedListener<BaseResultResponse<ArrayList<HomeScreenModel>>> listener);

        boolean savePlaceOffline(Place place);
        boolean checkPlaceSaveOff(String placeId);
        boolean deletePlaceOff(String placeId);

        void sharePlace(ParamsPostNotification postNotification, FinishedListener<BaseResultResponse> listener);

        void cancelRequest();

    }

}
