package com.svmc.android.locationsupportteam.ui.placesaved;

import com.svmc.android.locationsupportteam.data.local.PlaceSaveController;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;

import java.util.ArrayList;

/**
 * Created by TungTS on 5/14/2019
 */

public class ModelPlaceSavedImpl implements MVPPlaceSaved.IModelPlaceSaved {

    private PlaceSaveController controller;

    public ModelPlaceSavedImpl() {
        controller = new PlaceSaveController();
    }

    @Override
    public ArrayList<Place> getListPlaceSaved() {
        return controller.getAllPlaceSaved();
    }

}
