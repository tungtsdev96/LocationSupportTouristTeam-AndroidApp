package com.svmc.android.locationsupportteam.ui.trip.detailtrip;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.DetailDayOfTripAdapter;
import com.svmc.android.locationsupportteam.entity.trip.TripInfor;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/1/2019
 */

public class DetailTripFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshDetailTrip;

    // user create
    private ImageView imgProfile;
    private TextView tvNameUserCreated;

    // start, end point
    private TextView tvLongTrip;
    private TextView tvStartDate;
    private TextView tvStartPoint;
    private TextView tvEndPoint;

    // recycle view
    private RecyclerView rcvDetailDayPlanTrip;
    private DetailDayOfTripAdapter detailDayOfTripAdapter;
    private ArrayList items;

    public static DetailTripFragment newInstance() {
        DetailTripFragment detailTripFragment = new DetailTripFragment();
        detailTripFragment.setTAG(Constans.TagFragment.DETAIL_TRIP_FRAGMENT);
        return detailTripFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_trip;
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
        swipeRefreshDetailTrip = view.findViewById(R.id.swipe_refresh_detail_trip);
        imgProfile = view.findViewById(R.id.img_profile);
        tvNameUserCreated = view.findViewById(R.id.tv_display_name);
        tvLongTrip = view.findViewById(R.id.tv_long_trip);
        tvStartPoint = view.findViewById(R.id.tv_start_point);
        tvEndPoint = view.findViewById(R.id.tv_end_point);
        tvStartDate = view.findViewById(R.id.tv_start_date);
        innitRcvDetailDayPlan(view);
    }

    private void innitToolbar(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        ((BaseActivity)getActivity()).setSupportActionBar(toolbar);
        ((BaseActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void innitRcvDetailDayPlan(View view) {
        TripInfor tripInfor = ((DetailTripActivity) getActivity()).getTripInfor();
        rcvDetailDayPlanTrip = view.findViewById(R.id.rcv_detail_day_plan);
        detailDayOfTripAdapter = new DetailDayOfTripAdapter();
//        items = tripInfor.getTripDayInfors();
        detailDayOfTripAdapter.setItems(items);
        rcvDetailDayPlanTrip.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvDetailDayPlanTrip.setAdapter(detailDayOfTripAdapter);
    }

    @Override
    protected void addEvents() {
        swipeRefreshDetailTrip.setOnRefreshListener(this);
        detailDayOfTripAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
            @Override
            public void onClick(int position) {
                ((DetailTripActivity) getActivity()).innitFragment(2);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        swipeRefreshDetailTrip.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshDetailTrip.setRefreshing(false);
            }
        }, 1000);
    }
}
