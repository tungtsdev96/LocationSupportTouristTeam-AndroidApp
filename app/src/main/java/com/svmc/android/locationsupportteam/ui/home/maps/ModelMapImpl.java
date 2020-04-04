package com.svmc.android.locationsupportteam.ui.home.maps;

import com.svmc.android.locationsupportteam.data.local.LocationDataLocal;
import com.svmc.android.locationsupportteam.data.local.RecentSearchPlaceLocalData;
import com.svmc.android.locationsupportteam.data.local.entity.LocationCache;
import com.svmc.android.locationsupportteam.data.local.entity.RecentedSearchPlaceCache;
import com.svmc.android.locationsupportteam.data.remote.GoogleAPIRemoteDataSource;
import com.svmc.android.locationsupportteam.data.remote.RoomLocationRemoteDataSource;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.place.autocomplete.Prediction;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlaceAutocomplete;
import com.svmc.android.locationsupportteam.entity.homescreen.RecentedSearchInMap;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

import java.util.ArrayList;

public class ModelMapImpl implements MVPMap.IModelMaps {

    private GoogleAPIRemoteDataSource googleAPIRemoteDataSource;
    private RecentSearchPlaceLocalData recentSearchPlaceLocalData;
    private LocationDataLocal locationDataLocal;

    private RoomLocationRemoteDataSource roomLocationRemoteDataSource;


    public ModelMapImpl() {
        googleAPIRemoteDataSource = new GoogleAPIRemoteDataSource();
        recentSearchPlaceLocalData = new RecentSearchPlaceLocalData();
        locationDataLocal = new LocationDataLocal();
        roomLocationRemoteDataSource = new RoomLocationRemoteDataSource();
    }

    @Override
    public ArrayList<RecentedSearchInMap> getListRecentedSearchInMap() {
        ArrayList<RecentedSearchInMap> recentedSearchInMaps = new ArrayList<>();
        for (RecentedSearchPlaceCache placeCache: recentSearchPlaceLocalData.getPlacesRescenedSearch()) {
            recentedSearchInMaps.add(new RecentedSearchInMap(placeCache.getPlaceId(), placeCache.getMainText(), placeCache.getSecondaryText()));
        }
        return recentedSearchInMaps;
    }

    @Override
    public void searchPlace(String query, FinishedListener<ResultPlace> listener) {
        googleAPIRemoteDataSource.searchPlace(query, listener);
    }

    @Override
    public void placeAutocomplete(String query, String location, FinishedListener<ResultPlaceAutocomplete> listener) {
        googleAPIRemoteDataSource.placeAutocomplete(query, location, listener);
    }

    @Override
    public void addSearchCache(Prediction prediction){
        recentSearchPlaceLocalData.addRecenSearchPlace(prediction);
    }

    @Override
    public void addSearchCache(Place place){
        recentSearchPlaceLocalData.addRecenSearchPlace(place);
    }

    @Override
    public LocationPoint getCurrentLocationCache() {
        LocationCache locationCache = locationDataLocal.getLastLocationCache();
        return new LocationPoint(locationCache.getLat(), locationCache.getLng());
    }

    @Override
    public void sharePoint(ParamsPostNotification postNotification, FinishedListener<BaseResultResponse> listener, String image) {
        roomLocationRemoteDataSource.postNotification(postNotification, listener, image);
    }

}
