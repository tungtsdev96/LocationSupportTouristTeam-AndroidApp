package com.svmc.android.locationsupportteam.ui.listitem.trip;

public class PresenterListTripImpl implements MVPListTrip.IPresenterListTrip {

    private MVPListTrip.IViewListTrip viewListTrip;
    private MVPListTrip.IModelListTrip modelListTrip;

    public PresenterListTripImpl(MVPListTrip.IViewListTrip iViewListTrip) {
        this.viewListTrip = iViewListTrip;
        this.viewListTrip.setPresenter(this);
        modelListTrip = new ModelListTripImpl();
    }

    @Override
    public void start() {

    }

    @Override
    public void showListTrip(int page, int type) {
        viewListTrip.showUIListTrip(modelListTrip.getListTrip(page, type), (page == 0));
    }
}
