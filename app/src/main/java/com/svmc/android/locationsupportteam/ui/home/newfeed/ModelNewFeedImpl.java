package com.svmc.android.locationsupportteam.ui.home.newfeed;

import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.data.remote.RecommendDataRemote;
import com.svmc.android.locationsupportteam.entity.homescreen.HomeScreenModel;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

import java.util.ArrayList;

/**
 * Created by TUNGTS on 4/9/2019
 */

public class ModelNewFeedImpl implements MVPNewFeed.IModelNewFeed {

    private RecommendDataRemote recommendDataRemote;

    public ModelNewFeedImpl() {
        recommendDataRemote = new RecommendDataRemote();
    }

    @Override
    public void getAllRecommendPlace(String ids, String location, String queryCity, FinishedListener<BaseResultResponse<ArrayList<HomeScreenModel>>> listener) {
        recommendDataRemote.getNewFeedRecommend(ids, location, queryCity, listener);
    }

    @Override
    public void saveListPlaceTypeIds(String listIds) {
        AppPreferencens.getInstance().setListPlaceType(listIds);
    }

    @Override
    public void onStop() {
        recommendDataRemote.cancelCallNewFeed();
    }
}
