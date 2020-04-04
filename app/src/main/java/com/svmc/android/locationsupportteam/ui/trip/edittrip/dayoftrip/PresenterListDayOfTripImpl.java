package com.svmc.android.locationsupportteam.ui.trip.edittrip.dayoftrip;

/**
 * Created by TUNGTS on 3/31/2019
 */

public class PresenterListDayOfTripImpl implements MVPListDayOfTripFragment.IPresenterListDayOfTrip {

    private MVPListDayOfTripFragment.IViewListDayOfTrip viewEditDetailTrip;

    public PresenterListDayOfTripImpl(MVPListDayOfTripFragment.IViewListDayOfTrip viewEditDetailTrip) {
        this.viewEditDetailTrip = viewEditDetailTrip;
        viewEditDetailTrip.setPresenter(this);
    }

    @Override
    public void start() {

    }

}
