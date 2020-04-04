package com.svmc.android.locationsupportteam.ui.home.maps.nearbysearch;

import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.entity.SubPlaceType;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.ui.base.BasePresenter;
import com.svmc.android.locationsupportteam.ui.base.BaseView;

import java.util.List;

/**
 * Create by TungTS on 5/11/2019
 */

public class MVPNearBySearch {

    public interface IViewNearBySearch extends BaseView<IPresenterNearBySearch> {

        void showListPlaceNearBySearch(List<Place> places);

        void onErr();
    }

    public interface IPresenterNearBySearch extends BasePresenter {

        void showListPlaceNearBySearch(LocationPoint point, SubPlaceType placeType);

        void onStop();
    }

    public interface IModelNearBySearch {

        void getListNearBySearch(LocationPoint point, SubPlaceType placeType, FinishedListener<ResultPlace> listener);

        void cancelRequset();

    }

}
