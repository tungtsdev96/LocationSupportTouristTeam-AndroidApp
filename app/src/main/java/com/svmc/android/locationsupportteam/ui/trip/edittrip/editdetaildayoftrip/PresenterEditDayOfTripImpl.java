package com.svmc.android.locationsupportteam.ui.trip.edittrip.editdetaildayoftrip;

/**
 * Created by TUNGTS on 4/1/2019
 */

public class PresenterEditDayOfTripImpl implements MVPEditDayOfTrip.IPresenterEditDayOfTrip {

    private MVPEditDayOfTrip.IViewEditDayOfTrip viewAddPlaceToDetailDay;

    public PresenterEditDayOfTripImpl(MVPEditDayOfTrip.IViewEditDayOfTrip viewAddPlaceToDetailDay) {
        this.viewAddPlaceToDetailDay = viewAddPlaceToDetailDay;
        viewAddPlaceToDetailDay.setPresenter(this);
    }

    @Override
    public void start() {

    }

}
