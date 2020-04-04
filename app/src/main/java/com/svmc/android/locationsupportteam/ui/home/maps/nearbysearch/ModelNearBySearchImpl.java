package com.svmc.android.locationsupportteam.ui.home.maps.nearbysearch;

import com.svmc.android.locationsupportteam.data.remote.RecommendDataRemote;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.entity.SubPlaceType;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;

/**
 * Create by TungTS on 5/11/2019
 */

public class ModelNearBySearchImpl implements MVPNearBySearch.IModelNearBySearch {

    private RecommendDataRemote recommendDataRemote;

    public ModelNearBySearchImpl() {
        recommendDataRemote = new RecommendDataRemote();
    }

    @Override
    public void getListNearBySearch(LocationPoint point, SubPlaceType placeType, FinishedListener<ResultPlace> listener) {
        recommendDataRemote.getPlaceNearBySearch(point.toString(), placeType.getId(), null ,listener);
    }

    @Override
    public void cancelRequset() {
        recommendDataRemote.cancelCallNearBySearch();
    }
}
