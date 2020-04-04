package com.svmc.android.locationsupportteam.ui.trip.mytrip.recentedviewtrip;

import android.view.View;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.utils.Constans;

public class RecentedViewTripFragment extends BaseFragment {

    public static RecentedViewTripFragment newInstance() {
        RecentedViewTripFragment recentedViewTripFragment = new RecentedViewTripFragment();
        recentedViewTripFragment.setTAG(Constans.TagFragment.RECENTED_VIEW_TRIP_FRAGMENT);
        return recentedViewTripFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recented_view_trip;
    }

    @Override
    protected void onFragmentCreateView(View view) {

    }

    @Override
    protected void onFragmentCreated(View view) {

    }

    @Override
    protected void innitView(View view) {

    }

    @Override
    protected void addEvents() {

    }

}
