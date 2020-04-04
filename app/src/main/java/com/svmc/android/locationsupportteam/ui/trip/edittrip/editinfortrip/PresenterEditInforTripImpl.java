package com.svmc.android.locationsupportteam.ui.trip.edittrip.editinfortrip;


import com.svmc.android.locationsupportteam.entity.trip.TripInfor;
import com.svmc.android.locationsupportteam.utils.ErrCode;

/**
 * Created by TUNGTS on 3/31/2019
 */

public class PresenterEditInforTripImpl implements MVPEditInforTripFragment.IPresenterEditInforTrip {

    private MVPEditInforTripFragment.IViewEditInforTrip viewEditInforTrip;

    public PresenterEditInforTripImpl(MVPEditInforTripFragment.IViewEditInforTrip iViewEditInforTrip) {
        this.viewEditInforTrip = iViewEditInforTrip;
        viewEditInforTrip.setPresenter(this);
    }

    @Override
    public void start() {
    }

    public void validate(TripInfor tripInfor) {
        if (tripInfor.getStartPoint() == null) {
//            viewEditInforTrip.onErrValide(ErrCode.TripCode.HAVE_NO_START_POINT);
            return;
        }

        if (tripInfor.getEndPoint() == null) {
//            viewEditInforTrip.onErrValide(ErrCode.TripCode.HAVE_NO_END_POINT);
            return;
        }

        if (tripInfor.getStartDate() == null) {
//            viewEditInforTrip.onErrValide(ErrCode.TripCode.HAVE_NO_START_DATE);
            return;
        }

        if (tripInfor.getReturnDate() == null) {
//            viewEditInforTrip.onErrValide(ErrCode.TripCode.HAVE_NO_RETURN_DATE);
            return;
        }

//        viewEditInforTrip.nextStep();
    }
}
