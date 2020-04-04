package com.svmc.android.locationsupportteam.entity.googlemap.place.response;

import com.google.gson.annotations.SerializedName;
import com.svmc.android.locationsupportteam.entity.googlemap.BaseResponseGoogle;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/16/2019
 */

public class ResultPlace extends BaseResponseGoogle {

    /// for place detail
    @SerializedName("result")
    private Place place;

    /***
     * get Place for api place detail
     * @return
     */
    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    /// Find Place responses
    @SerializedName("candidates")
    private List<Place> candidates;

    /***
     * get List candidates for api find place
     * @return
     */
    public List<Place> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Place> candidates) {
        this.candidates = candidates;
    }

    /// Nearby Search and Text Search responses
    @SerializedName("results")
    private List<Place> listPlaceNearby;

    /***
     * Get list place for api nearby search
     * @return
     */
    public List<Place> getListPlaceNearby() {
        return listPlaceNearby;
    }

    public void setListPlaceNearby(List<Place> listPlaceNearby) {
        this.listPlaceNearby = listPlaceNearby;
    }

    public static ResultPlace fake(){
        ResultPlace resultPlace = new ResultPlace();
        resultPlace.setPlace(Place.fake());

        List<Place> places = new ArrayList<>();
        double x = Math.random();

        for (int i = 0; i < x * 10; i++) {
            places.add(Place.fake());
        }

        resultPlace.setCandidates(places);
        resultPlace.setListPlaceNearby(places);
        return resultPlace;
    }

}
