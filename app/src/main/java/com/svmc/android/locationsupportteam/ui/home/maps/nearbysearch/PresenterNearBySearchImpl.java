package com.svmc.android.locationsupportteam.ui.home.maps.nearbysearch;

import com.svmc.android.locationsupportteam.entity.event.EventMap;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.entity.SubPlaceType;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Create by TungTS on 5/11/2019
 */

public class PresenterNearBySearchImpl implements MVPNearBySearch.IPresenterNearBySearch {

    private MVPNearBySearch.IViewNearBySearch viewNearBySearch;
    private MVPNearBySearch.IModelNearBySearch modelNearBySearch;

    public PresenterNearBySearchImpl(MVPNearBySearch.IViewNearBySearch viewNearBySearch) {
        this.viewNearBySearch = viewNearBySearch;
        this.viewNearBySearch.setPresenter(this);
        modelNearBySearch = new ModelNearBySearchImpl();
    }

    @Override
    public void start() {

    }

    @Override
    public void showListPlaceNearBySearch(LocationPoint point, SubPlaceType placeType) {
        modelNearBySearch.getListNearBySearch(point, placeType, new FinishedListener<ResultPlace>() {
            @Override
            public void onFinished(ResultPlace result) {
                viewNearBySearch.showListPlaceNearBySearch(result.getListPlaceNearby());

                // post location to map
                ArrayList<LocationPoint> points = new ArrayList<>();
                ArrayList<String> names = new ArrayList<>();

                for (Place place: result.getListPlaceNearby()) {
                    points.add(place.getGeometry().getLocation());
                    names.add(place.getName());
                }
                EventBus.getDefault().post(new EventMap.PostLocation(points, names));
            }

            @Override
            public void onFailure(Throwable t) {
                viewNearBySearch.onErr();
            }
        });
    }

    @Override
    public void onStop() {
        modelNearBySearch.cancelRequset();
    }
}
