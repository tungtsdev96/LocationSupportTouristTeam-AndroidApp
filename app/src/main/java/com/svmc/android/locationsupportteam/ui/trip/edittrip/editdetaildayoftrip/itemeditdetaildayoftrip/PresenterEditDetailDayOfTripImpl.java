package com.svmc.android.locationsupportteam.ui.trip.edittrip.editdetaildayoftrip.itemeditdetaildayoftrip;

/**
 * Created by TUNGTS on 4/1/2019
 */

public class PresenterEditDetailDayOfTripImpl implements MVPEditDetailDayOfTrip.IPresenterEditDetailDayOfTrip {

    MVPEditDetailDayOfTrip.IViewEditDetailDayOfTrip viewEditDayOfTripFragment;

    public PresenterEditDetailDayOfTripImpl(MVPEditDetailDayOfTrip.IViewEditDetailDayOfTrip viewEditDayOfTripFragment) {
        this.viewEditDayOfTripFragment = viewEditDayOfTripFragment;
        viewEditDayOfTripFragment.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
