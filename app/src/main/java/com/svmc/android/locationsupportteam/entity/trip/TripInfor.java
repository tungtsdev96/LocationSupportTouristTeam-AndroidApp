package com.svmc.android.locationsupportteam.entity.trip;

import com.google.gson.annotations.SerializedName;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tungts on 3/28/2019.
 */

public class TripInfor {

    @SerializedName("trip_id")
    private String tripId;

    @SerializedName("title_trip")
    private String titleTrip;

    @SerializedName("start_point")
    private PlaceParam startPoint;

    @SerializedName("end_point")
    private PlaceParam endPoint;

    @SerializedName("start_date")
    private long startDate;

    @SerializedName("return_date")
    private long returnDate;

    @SerializedName("total_day")
    private int totalDay;

//    private ArrayList<TripDayInfor> tripDayInfors;

    private User userCreated;

    public TripInfor(){}

    public PlaceParam getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(PlaceParam startPoint) {
        this.startPoint = startPoint;
    }

    public PlaceParam getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(PlaceParam endPoint) {
        this.endPoint = endPoint;
    }

    public String getReturnDate() {
        return TimeUtil.convertTimeToDate(returnDate);
    }

    public void setReturnDate(long returnDate) {
        this.returnDate = returnDate;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTitleTrip() {
        return titleTrip;
    }

    public void setTitleTrip(String titleTrip) {
        this.titleTrip = titleTrip;
    }

    public String getStartDate() {
        return TimeUtil.convertTimeToDate(startDate);
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public int getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(int totalDay) {
        this.totalDay = totalDay;
    }

    public User getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(User userCreated) {
        this.userCreated = userCreated;
    }

    public static TripInfor getTripTest() {
        TripInfor tripInfor = new TripInfor();
//        tripInfor.setStartPoint(new PlaceParam());
//        tripInfor.setEndPoint(new PlaceParam());
//        tripInfor.setStartDate(Long.parseLong("1555942878912"));
//        tripInfor.setReturnDate(Long.parseLong("1555942878912"));
//        tripInfor.setTotalDay(4);
//
//        Place place = new Place();
//        place.setName("tungts");
//
//        TripDetailDay tripDetailDay = new TripDetailDay();
//        tripDetailDay.setPlace(place);
//        List<TripDetailDay> tripDetailDays = new ArrayList<>();
//        tripDetailDays.add(tripDetailDay);
//        tripDetailDays.add(tripDetailDay);
//        tripDetailDays.add(tripDetailDay);
//        tripDetailDays.add(tripDetailDay);
//        tripDetailDays.add(tripDetailDay);
//
//        TripDayInfor tripDayInfor = new TripDayInfor();
//        tripDayInfor.setDetailDays(tripDetailDays);
//        ArrayList<TripDayInfor> tripDayInfors = new ArrayList<>();
//        tripDayInfors.add(tripDayInfor);
//        tripDayInfors.add(tripDayInfor);
//        tripDayInfors.add(tripDayInfor);
//        tripDayInfors.add(tripDayInfor);
//
//        tripInfor.setTripDayInfors(tripDayInfors);
        return tripInfor;
    }
}
