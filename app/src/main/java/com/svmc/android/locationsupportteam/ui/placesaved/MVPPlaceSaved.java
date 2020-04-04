package com.svmc.android.locationsupportteam.ui.placesaved;

import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;

import java.util.ArrayList;

/**
 * Created by TungTS on 5/14/2019
 */

public class MVPPlaceSaved {

    public interface IViewPlaceSaved {

        void showListPlace(ArrayList<Place> places);

    }

    public interface IPresenterPlaceSaved {

        void getListPlaceSaved();

    }

    public interface IModelPlaceSaved {

        ArrayList<Place> getListPlaceSaved();

    }

}
