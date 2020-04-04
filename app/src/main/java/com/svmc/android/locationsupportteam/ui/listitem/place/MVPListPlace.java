package com.svmc.android.locationsupportteam.ui.listitem.place;

import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.ui.base.BasePresenter;
import com.svmc.android.locationsupportteam.ui.base.BaseView;

import java.util.List;


public class MVPListPlace {

    public interface IViewListPlace extends BaseView<IPresenterListPlace> {

        void showUIListPlace(ResultPlace resultPlace, boolean isFirstLoad);

        void showListPlaceWhenFilter(List items);

        void onLoadDataErr();

    }

    public interface IPresenterListPlace extends BasePresenter {

        void setFirstLoad();

        /**
         * get all place by type
         * @param location
         * @param pageToken
         * @param type
         */
        void showListPlace(String location, String pageToken, int type);

        void showListPlace(String qeryCity, String pagetoken);

        /**
         * filter Items
         * @param coppysItems
         * @param isOpenNow
         * @param filterType
         */
        void filterBy(List coppysItems, boolean isOpenNow, int filterType);
    }

    public interface IModelListPlace {

        void loadPlaceBytype(String location, int type, String pageToken, FinishedListener<ResultPlace> listener);

        void loadPlaceQueryCity(String querycity, String pageToken, FinishedListener<ResultPlace> listene);

    }

}
