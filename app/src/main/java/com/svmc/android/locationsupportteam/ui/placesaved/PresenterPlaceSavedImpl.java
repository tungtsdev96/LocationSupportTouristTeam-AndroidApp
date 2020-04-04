package com.svmc.android.locationsupportteam.ui.placesaved;

/**
 * Created by TungTS on 5/14/2019
 */

public class PresenterPlaceSavedImpl implements MVPPlaceSaved.IPresenterPlaceSaved {

    private MVPPlaceSaved.IViewPlaceSaved viewPlaceSaved;
    private MVPPlaceSaved.IModelPlaceSaved modelPlaceSaved;

    public PresenterPlaceSavedImpl(MVPPlaceSaved.IViewPlaceSaved viewPlaceSaved) {
        this.viewPlaceSaved = viewPlaceSaved;
        this.modelPlaceSaved = new ModelPlaceSavedImpl();
    }

    @Override
    public void getListPlaceSaved() {
        viewPlaceSaved.showListPlace(modelPlaceSaved.getListPlaceSaved());
    }
}
