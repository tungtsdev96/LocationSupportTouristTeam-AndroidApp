package com.svmc.android.locationsupportteam.ui.trip.edittrip.search;

import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlaceAutocomplete;

/**
 * Created by TUNGTS on 5/22/2019
 */

public class MVPSearchPoint {

    public interface IViewSearchPoint {

        void bindResultUI(ResultPlaceAutocomplete resultPlaceAutocomplete);

    }

    public interface IPresenterSearchPoint {


    }

    public interface IModelSearchPoint {

    }

}
