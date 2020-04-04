package com.svmc.android.locationsupportteam.ui.trip.edittrip;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.entity.trip.TripInfor;
import com.svmc.android.locationsupportteam.ui.trip.edittrip.editdetaildayoftrip.EditDayOfTripFragment;
import com.svmc.android.locationsupportteam.ui.trip.edittrip.editdetaildayoftrip.MVPEditDayOfTrip;
import com.svmc.android.locationsupportteam.ui.trip.edittrip.editdetaildayoftrip.PresenterEditDayOfTripImpl;
import com.svmc.android.locationsupportteam.ui.trip.edittrip.dayoftrip.ListDayOfTripFragment;
import com.svmc.android.locationsupportteam.ui.trip.edittrip.dayoftrip.MVPListDayOfTripFragment;
import com.svmc.android.locationsupportteam.ui.trip.edittrip.dayoftrip.PresenterListDayOfTripImpl;
import com.svmc.android.locationsupportteam.ui.trip.edittrip.editinfortrip.EditInforTripFragment;
import com.svmc.android.locationsupportteam.ui.trip.edittrip.editinfortrip.MVPEditInforTripFragment;
import com.svmc.android.locationsupportteam.ui.trip.edittrip.editinfortrip.PresenterEditInforTripImpl;
import com.svmc.android.locationsupportteam.utils.Constans;

/**
 * Created by TUNGTS on 3/31/2019
 */

public class EditTripActivity extends BaseActivity {

    public static Intent innitIntent(Activity activity, TripInfor infor) {
        Intent i = new Intent(activity, EditTripActivity.class);
        i.putExtra(Constans.KeyBundle.TRIP_INFOR, new Gson().toJson(infor));
        return i;
    }

    private TripInfor tripInfor;
    private int currentDateChoose = 0;

    @Override
    public int getContentView() {
        return R.layout.activity_detail_trip;
    }

    @Override
    public void onViewCreated(View view) {
        String jsonTripInfor = getIntent().getStringExtra(Constans.KeyBundle.TRIP_INFOR);
        TripInfor infor = new Gson().fromJson(jsonTripInfor, TripInfor.class);
        if (infor != null) {
            innitEditTripInforFragment(infor);
        }
        innitFragmentToCreateTrip(1);
    }

    public void innitEditTripInforFragment(TripInfor infor) {
        EditInforTripFragment editTripInforFragment = EditInforTripFragment.newInstance(infor);
        MVPEditInforTripFragment.IPresenterEditInforTrip presenterEditInforTrip = new PresenterEditInforTripImpl(editTripInforFragment);
        pushFragment(R.id.content_step_edit_trip, editTripInforFragment,0,0, 0, 0, false);
    }

    public void innitFragmentToCreateTrip(int currentStep) {
        switch (currentStep) {
            case 1:
                EditInforTripFragment editTripInforFragment = EditInforTripFragment.newInstance();
                MVPEditInforTripFragment.IPresenterEditInforTrip presenterEditInforTrip = new PresenterEditInforTripImpl(editTripInforFragment);
                pushFragment(R.id.content_step_edit_trip, editTripInforFragment,0,0, 0, 0, false);
                break;
            case 2:
                ListDayOfTripFragment listDayOfTripFragment = ListDayOfTripFragment.newInstance();
                MVPListDayOfTripFragment.IPresenterListDayOfTrip presenterEditDetailTrip = new PresenterListDayOfTripImpl(listDayOfTripFragment);
                replaceFragment(R.id.content_step_edit_trip, listDayOfTripFragment, R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_r, R.anim.slide_out_r, true);
                break;
            case 3:
                EditDayOfTripFragment editDetailDayOfTripFragment = EditDayOfTripFragment.newInstance();
                MVPEditDayOfTrip.IPresenterEditDayOfTrip presenterAddPlaceToDetailDay = new PresenterEditDayOfTripImpl(editDetailDayOfTripFragment);
                replaceFragment(R.id.content_step_edit_trip, editDetailDayOfTripFragment, R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_r, R.anim.slide_out_r, true);
                break;
        }
    }

    //////////////// remove //////////////////
    public TripInfor getTripInfor() {
        if (tripInfor == null) {
            tripInfor = new TripInfor();
        }
        return tripInfor;
    }

    public void setTripInfor(TripInfor tripInfor) {
        this.tripInfor = tripInfor;
    }

    public void setCurrentDateChoose(int numberOfDate) {
        currentDateChoose = numberOfDate;
    }

    public int getCurrentDateChoose() {
        return currentDateChoose;
    }
    ////////////////////////////////////////////////////

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment fragment = getCurrentFragment(R.id.content_step_edit_trip);
        if (fragment instanceof EditInforTripFragment) {
            overridePendingTransition(R.anim.slide_in_r, R.anim.slide_out_r);
        }
    }
}
