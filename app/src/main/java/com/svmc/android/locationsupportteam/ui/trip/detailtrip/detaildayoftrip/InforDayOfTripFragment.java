package com.svmc.android.locationsupportteam.ui.trip.detailtrip.detaildayoftrip;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.ViewPagerAdapter;
import com.svmc.android.locationsupportteam.entity.trip.TripDayInfor;
import com.svmc.android.locationsupportteam.entity.trip.TripInfor;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.trip.detailtrip.DetailTripActivity;
import com.svmc.android.locationsupportteam.utils.Constans;

/**
 * Created by TUNGTS on 4/1/2019
 */

public class InforDayOfTripFragment extends BaseFragment {

    private Toolbar toolbar;
    private ImageView btnBack;
    private TextView btnDone;
    private TextView tvTitle;
    private TabLayout tabLayoutAddPlace;

    private ViewPager viewPagerDayOfTrip;
    private ViewPagerAdapter viewPagerAdapter;

    public static InforDayOfTripFragment newInstance() {
        InforDayOfTripFragment inforDayOfTripFragment = new InforDayOfTripFragment();
        inforDayOfTripFragment.setTAG(Constans.TagFragment.INFOR_DAY_OF_TRIP_FRAGMENT);
        return inforDayOfTripFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_place_to_detail_day;
    }

    @Override
    protected void onFragmentCreateView(View view) {

    }

    @Override
    protected void onFragmentCreated(View view) {

    }

    @Override
    protected void innitView(View view) {
        innitToolbar(view);
        innitViewPager(view);
    }

    private void innitToolbar(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        btnBack = toolbar.findViewById(R.id.btn_back);
        tvTitle = toolbar.findViewById(R.id.tv_title); tvTitle.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        btnDone = toolbar.findViewById(R.id.btn_done); btnDone.setVisibility(View.GONE);
        tabLayoutAddPlace = view.findViewById(R.id.tab_layout_add_place_to_detail_day);
        tabLayoutAddPlace.setVisibility(View.VISIBLE);
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void innitViewPager(View view) {
//        TripInfor tripInfor = ((DetailTripActivity) getActivity()).getTripInfor();
//        viewPagerDayOfTrip = view.findViewById(R.id.view_pager_day_of_trip);
//        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
//        int i = 1;
//        for (TripDayInfor tripDayInfor: tripInfor.getTripDayInfors()) {
//            viewPagerAdapter.addFragment(DetailDayOfTripFragment.newInstance(tripDayInfor), "Day " + i++);
//        }
//        tabLayoutAddPlace.setupWithViewPager(viewPagerDayOfTrip);
//        viewPagerDayOfTrip.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void addEvents() {

    }

}
