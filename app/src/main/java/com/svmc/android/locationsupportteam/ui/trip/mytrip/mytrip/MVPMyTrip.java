package com.svmc.android.locationsupportteam.ui.trip.mytrip.mytrip;

import com.svmc.android.locationsupportteam.ui.base.BasePresenter;
import com.svmc.android.locationsupportteam.ui.base.BaseView;
import com.svmc.android.locationsupportteam.entity.trip.TripInfor;

import java.util.ArrayList;

/**
 * Created by Tungts on 3/28/2019.
 */

public class MVPMyTrip {

    public interface IViewMyTrip extends BaseView<IPresenterMyTrip> {

        void isSignIn(boolean flag);

        void showMyTrip(ArrayList<TripInfor> tripInfors);

    }

    public interface IPresenterMyTrip extends BasePresenter {

        void start();

        void loadListMyTrips();

        void result(int requestCode, int resultCode);

    }

    public interface IModelMyTrip {

        ArrayList<TripInfor> getListTripInforByUserId(String userId);

    }

}
