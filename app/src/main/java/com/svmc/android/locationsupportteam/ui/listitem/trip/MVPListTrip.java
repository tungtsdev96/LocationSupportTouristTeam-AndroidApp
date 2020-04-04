package com.svmc.android.locationsupportteam.ui.listitem.trip;

import com.svmc.android.locationsupportteam.entity.trip.TripInfor;
import com.svmc.android.locationsupportteam.ui.base.BasePresenter;
import com.svmc.android.locationsupportteam.ui.base.BaseView;

import java.util.ArrayList;

public class MVPListTrip {

    public interface IViewListTrip extends BaseView<IPresenterListTrip> {

        void showUIListTrip(ArrayList<TripInfor> tripInfors, boolean isFirstLoad);

    }

    public interface IPresenterListTrip extends BasePresenter {

        void showListTrip(int page, int type);

    }

    public interface IModelListTrip {

        ArrayList<TripInfor> getListTrip(int page, int type);

    }

}
