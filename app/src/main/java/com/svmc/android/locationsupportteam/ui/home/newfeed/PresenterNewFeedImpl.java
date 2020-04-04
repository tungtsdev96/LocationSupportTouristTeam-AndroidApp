package com.svmc.android.locationsupportteam.ui.home.newfeed;

import android.content.Intent;

import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.data.local.LocationDataLocal;
import com.svmc.android.locationsupportteam.data.local.entity.LocationCache;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.entity.CityProvince;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.homescreen.HomeScreenModel;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by TUNGTS on 3/20/2019
 */

public class PresenterNewFeedImpl implements MVPNewFeed.IPresenterNewFeed {

    private MVPNewFeed.IViewNewFeed viewNewFeed;
    private MVPNewFeed.IModelNewFeed modelNewFeed;

    private boolean isQueryCity = false;
    private CityProvince oldQuery;

    public PresenterNewFeedImpl(MVPNewFeed.IViewNewFeed viewNewFeed) {
        this.viewNewFeed = viewNewFeed;
        this.modelNewFeed = new ModelNewFeedImpl();
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == Constans.RequestCode.RC_SEARCH_PLACE) {
            String jsonCityOrProvince = data.getExtras().getString(Constans.KeyBundle.SEARCH_PLACE);
            oldQuery = new Gson().fromJson(jsonCityOrProvince, CityProvince.class);
            viewNewFeed.showViewLoading();
            if (oldQuery.getId() != null) {
                isQueryCity = true;
                openListItemOfPlace(oldQuery);
                return;
            }

            viewNewFeed.showDataToBtnPlace("Gần đây");
            isQueryCity = false;
            openListItemOfNewFeed();
        }
    }

    @Override
    public void openListItemOfNewFeed() {
        String listPlaceTypesId = AppPreferencens.getInstance().getListPlaceType();

        LocationDataLocal local = new LocationDataLocal();
        LocationCache cache = local.getLastLocationCache();
        if (cache == null) {
//            String json = "\"id\": \"2\",\n" + "\"key\": \"Hà Nội\",\n" + "\"name\" : \"Thành phố Hà Nội\",\n" + "\"code\": \"HN\"";
//            openListItemOfPlace(new Gson().fromJson(json, CityProvince.class));
            return;
        }

        String location = new LocationPoint(cache.getLat(), cache.getLng()).toString();
        modelNewFeed.getAllRecommendPlace(listPlaceTypesId, location, null, new FinishedListener<BaseResultResponse<ArrayList<HomeScreenModel>>>() {
            @Override
            public void onFinished(BaseResultResponse<ArrayList<HomeScreenModel>> result) {
                viewNewFeed.showListItemOfNewFeed(result.getResult());
                viewNewFeed.hideViewLoading();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    @Override
    public void openSearchPlaceUI() {
        viewNewFeed.showSearchPlaceUI();
    }

    @Override
    public void openListItemOfPlace(CityProvince cityOrProvince) {
        String listPlaceTypesId = AppPreferencens.getInstance().getListPlaceType();
        viewNewFeed.showDataToBtnPlace(cityOrProvince.getKey());
        modelNewFeed.getAllRecommendPlace(listPlaceTypesId, null, cityOrProvince.getKey(), new FinishedListener<BaseResultResponse<ArrayList<HomeScreenModel>>>() {
            @Override
            public void onFinished(BaseResultResponse<ArrayList<HomeScreenModel>> result) {
                viewNewFeed.showListItemOfPlace(result.getResult());
                viewNewFeed.hideViewLoading();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    @Override
    public void openSeeAllUI(HomeScreenModel homeScreenModel) {
        viewNewFeed.showSeeAllItems(homeScreenModel);
    }

    @Override
    public void saveListPlaceTypeIds(String listIds) {
        viewNewFeed.showViewLoading();
        modelNewFeed.saveListPlaceTypeIds(listIds);
        if (isQueryCity) {
            openListItemOfPlace(oldQuery);
        } else {
            openListItemOfNewFeed();
        }
    }

}
