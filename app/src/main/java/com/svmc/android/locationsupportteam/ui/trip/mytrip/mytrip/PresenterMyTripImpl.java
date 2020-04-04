package com.svmc.android.locationsupportteam.ui.trip.mytrip.mytrip;

import com.google.firebase.auth.FirebaseUser;
import com.svmc.android.locationsupportteam.firebase.FirebaseUtils;

/**
 * Created by Tungts on 3/28/2019.
 */

public class PresenterMyTripImpl implements MVPMyTrip.IPresenterMyTrip {

    MVPMyTrip.IViewMyTrip viewMyTrip;
    MVPMyTrip.IModelMyTrip modelMyTrip;


    public PresenterMyTripImpl(MVPMyTrip.IViewMyTrip viewMyTrip) {
        this.viewMyTrip = viewMyTrip;
        viewMyTrip.setPresenter(this);
        modelMyTrip = new ModelMyTripImpl();
    }

    @Override
    public void start() {
        loadListMyTrips();
    }

    @Override
    public void loadListMyTrips() {
        FirebaseUser user = FirebaseUtils.getFirebaseAuth().getCurrentUser();
        if (user != null) {
            viewMyTrip.isSignIn(true);
            viewMyTrip.showMyTrip(modelMyTrip.getListTripInforByUserId(user.getUid()));
            return;
        }
        viewMyTrip.isSignIn(false);
        viewMyTrip.showMyTrip(null);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

}
