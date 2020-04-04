package com.svmc.android.locationsupportteam.ui.trip.mytrip.mytrip;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.TripAdapter;
import com.svmc.android.locationsupportteam.firebase.FirebaseUtils;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.entity.trip.TripInfor;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.ui.auth.SignInActivity;
import com.svmc.android.locationsupportteam.ui.trip.detailtrip.DetailTripActivity;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.ArrayList;

public class MyTripFragment extends BaseFragment implements MVPMyTrip.IViewMyTrip, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private LinearLayout llUserNotSignIn;
    private TextView btnSignIn;

    private MVPMyTrip.IPresenterMyTrip presenterMyTrip;

    private SwipeRefreshLayout swipeRefreshMyTrip;
    private RecyclerView rcvMyTrip;
    private ArrayList items;
    private TripAdapter tripAdapter;


    public static MyTripFragment newInstance() {
        MyTripFragment myTripFragment = new MyTripFragment();
        myTripFragment.setTAG(Constans.TagFragment.MY_TRIP_FRAGMENT);
        return myTripFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_trip;
    }

    @Override
    protected void onFragmentCreateView(View view) {

    }

    @Override
    protected void onFragmentCreated(View view) {

    }

    private void innitRecycleView(View view) {
        swipeRefreshMyTrip = view.findViewById(R.id.swipe_refresh_my_trip);
        rcvMyTrip = view.findViewById(R.id.rcv_my_trip);
        tripAdapter = new TripAdapter();
        items = new ArrayList();
        items.add("tungts");
        items.add("tungts");
        items.add("tungts");
        items.add("tungts");
        items.add("tungts");
        items.add("tungts");
        tripAdapter.setItems(items);
        rcvMyTrip.setAdapter(tripAdapter);
        rcvMyTrip.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void innitView(View view) {
        innitRecycleView(view);
        llUserNotSignIn = view.findViewById(R.id.rlt_user_not_sign_in);
        btnSignIn = view.findViewById(R.id.btn_start);
    }

    @Override
    protected void addEvents() {
        btnSignIn.setOnClickListener(this);
        swipeRefreshMyTrip.setOnRefreshListener(this);
        tripAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
            @Override
            public void onClick(int position) {
                TripInfor tripInfor = (TripInfor) items.get(position);
                Bundle bundle = new Bundle();
                bundle.putString(Constans.KeyBundle.TRIP_INFOR, new Gson().toJson(tripInfor));
                Intent i = new Intent(getActivity(), DetailTripActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (FirebaseUtils.getFirebaseAuth() != null) {
            presenterMyTrip.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (swipeRefreshMyTrip.isRefreshing()) {
            swipeRefreshMyTrip.setRefreshing(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenterMyTrip.result(requestCode, resultCode);
    }

    @Override
    public void isSignIn(boolean flag) {
        if (flag) {
            llUserNotSignIn.setVisibility(View.GONE);
            rcvMyTrip.setVisibility(View.VISIBLE);
            return;
        }
        llUserNotSignIn.setVisibility(View.VISIBLE);
        rcvMyTrip.setVisibility(View.GONE);
        items.clear();
        tripAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMyTrip(ArrayList<TripInfor> tripInfors) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                items.clear();
//                items.add(TripInfor.getTripTest());
//                items.add(TripInfor.getTripTest());
//                items.add(TripInfor.getTripTest());
//                items.add(TripInfor.getTripTest());
//                items.add(TripInfor.getTripTest());
                tripAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }

    @Override
    public void setPresenter(MVPMyTrip.IPresenterMyTrip iPresenterMyTrip) {
        if (iPresenterMyTrip != null) {
            presenterMyTrip = iPresenterMyTrip;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                Intent i = new Intent(getActivity(), SignInActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshMyTrip.setRefreshing(false);
            }
        }, 1000);
    }
}
