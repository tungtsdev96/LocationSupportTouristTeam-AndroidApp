package com.svmc.android.locationsupportteam.entity.homescreen;

import com.google.gson.annotations.SerializedName;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.trip.TripInfor;

import java.util.ArrayList;

/**
 * Created by TUNGTS on 4/9/2019
 */

public class HomeScreenModel {

    @SerializedName("type")
    private int type;

    @SerializedName("location")
    private String location;

    @SerializedName("title")
    private String title;

    @SerializedName("trips")
    private ArrayList<TripInfor> tripInfors;

    @SerializedName("places")
    private ArrayList<Place> places;

    @SerializedName("is_query_city")
    private boolean isQueryCity;

    @SerializedName("query_city")
    private String queryCity;

    public String getQueryCity() {
        return queryCity;
    }

    public void setQueryCity(String queryCity) {
        this.queryCity = queryCity;
    }

    public boolean isQueryCity() {
        return isQueryCity;
    }

    public void setQueryCity(boolean queryCity) {
        isQueryCity = queryCity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<TripInfor> getTripInfors() {
        return tripInfors;
    }

    public void setTripInfors(ArrayList<TripInfor> tripInfors) {
        this.tripInfors = tripInfors;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isPlaceModel() {
        return places != null && places.size() > 0;
    }

    public static HomeScreenModel fake1() {
        HomeScreenModel homeScreenModel = new HomeScreenModel();
        homeScreenModel.setTitle("Place");
//        homeScreenModel.setType(5);

        ArrayList<Place> places = new ArrayList<>();
        places.add(Place.fake());
        places.add(Place.fake());
        places.add(Place.fake());
        places.add(Place.fake());
        places.add(Place.fake());
        places.add(Place.fake());
        places.add(Place.fake());
        places.add(Place.fake());

        homeScreenModel.setPlaces(places);

        return homeScreenModel;
    }

//    public static HomeScreenModel fake2() {
//        HomeScreenModel homeScreenModel = new HomeScreenModel();
//        homeScreenModel.setTitle("trip");
////        homeScreenModel.setType(1);
//
//        ArrayList<TripInfor> tripInfors = new ArrayList<>();
//        tripInfors.add(TripInfor.getTripTest());
//        tripInfors.add(TripInfor.getTripTest());
//        tripInfors.add(TripInfor.getTripTest());
//        tripInfors.add(TripInfor.getTripTest());
//        tripInfors.add(TripInfor.getTripTest());
//        tripInfors.add(TripInfor.getTripTest());
//        tripInfors.add(TripInfor.getTripTest());
//
//        homeScreenModel.setTripInfors(tripInfors);
//
//        return homeScreenModel;
//    }
}
