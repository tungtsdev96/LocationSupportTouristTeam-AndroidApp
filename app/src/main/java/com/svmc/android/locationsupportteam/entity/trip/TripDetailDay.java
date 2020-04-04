package com.svmc.android.locationsupportteam.entity.trip;

/**
 * Created by TUNGTS on 4/1/2019
 */

public class TripDetailDay {

    private long time;

    private PlaceParam place;

    // second
    private long timeVisit;

    private String note;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public PlaceParam getPlace() {
        return place;
    }

    public void setPlace(PlaceParam place) {
        this.place = place;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getTimeVisit() {

        return timeVisit;
    }

    public void setTimeVisit(long timeVisit) {
        this.timeVisit = timeVisit;
    }
}
