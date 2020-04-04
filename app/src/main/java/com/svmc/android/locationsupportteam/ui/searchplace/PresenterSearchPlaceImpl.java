package com.svmc.android.locationsupportteam.ui.searchplace;

public class PresenterSearchPlaceImpl implements MVPSearchPlace.IPresenterSearchPlace {

    private MVPSearchPlace.IViewSearchPlace viewSearchPlace;
    private MVPSearchPlace.IModelSearchPlace modelSearchPlace;

    public PresenterSearchPlaceImpl(MVPSearchPlace.IViewSearchPlace viewSearchPlace) {
        this.viewSearchPlace = viewSearchPlace;
        this.modelSearchPlace = new ModelSearchPlaceImpl();
    }

    @Override
    public void start() {

    }


}
