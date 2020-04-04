package com.svmc.android.locationsupportteam.ui.home.newfeed;

import android.content.Intent;

import com.svmc.android.locationsupportteam.entity.CityProvince;
import com.svmc.android.locationsupportteam.entity.homescreen.HomeScreenModel;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

import java.util.ArrayList;

/**
 * Created by TUNGTS on 3/20/2019
 */

public class MVPNewFeed {

    public interface IViewNewFeed {

        void showViewLoading();

        void hideViewLoading();

        void lisenerforAppBar(int verticalOffset);

        void showListItemOfNewFeed(ArrayList<HomeScreenModel> homeScreenModels);

        void showSearchPlaceUI();

        void showDataToBtnPlace(String namePlace);

        void showListItemOfPlace(ArrayList<HomeScreenModel> homeScreenModels);

        void showSeeAllItems(HomeScreenModel homeScreenModel);

    }

    public interface IPresenterNewFeed {

        void result(int requestCode, int resultCode, Intent data);

        /***
         * open near by search
         */
        void openListItemOfNewFeed();

        /**
         * open by query city
         * @param cityOrProvince
         */
        void openListItemOfPlace(CityProvince cityOrProvince);

        /**
         * open ui choose city
         */
        void openSearchPlaceUI();

        void openSeeAllUI(HomeScreenModel homeScreenModel);

        /**
         * save list PlaceTypeIds
         */
        void saveListPlaceTypeIds(String listIds);
    }

    public interface IModelNewFeed {

        void getAllRecommendPlace(String ids, String location, String queryCity, FinishedListener<BaseResultResponse<ArrayList<HomeScreenModel>>> listener);

        /**
         * save list PlaceTypeIds
         */
        void saveListPlaceTypeIds(String listIds);

        void onStop();
    }

}
