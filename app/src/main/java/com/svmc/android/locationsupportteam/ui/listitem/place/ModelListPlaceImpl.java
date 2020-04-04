package com.svmc.android.locationsupportteam.ui.listitem.place;

import com.svmc.android.locationsupportteam.data.remote.RecommendDataRemote;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;

public class ModelListPlaceImpl implements MVPListPlace.IModelListPlace {

    private RecommendDataRemote recommendDataRemote;

    public ModelListPlaceImpl() {
        this.recommendDataRemote = new RecommendDataRemote();
    }

    @Override
    public void loadPlaceBytype(String location, int type, String pageToken, FinishedListener<ResultPlace> listener) {
        recommendDataRemote.getPlaceNearBySearch(location, type, pageToken, listener);
    }

    @Override
    public void loadPlaceQueryCity(String querycity, String pageToken, FinishedListener<ResultPlace> listener) {
        recommendDataRemote.getListPlaceInQueryCity(querycity, pageToken, listener);
    }
}
