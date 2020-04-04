package com.svmc.android.locationsupportteam.ui.listitem.trip;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.TripAdapter;
import com.svmc.android.locationsupportteam.entity.trip.TripInfor;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.ArrayList;

public class ListTripFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, MVPListTrip.IViewListTrip {

    private int type;
    private MVPListTrip.IPresenterListTrip presenterListTrip;

    private SwipeRefreshLayout swipeRefreshList;

    private RecyclerView rcvTrips;
    private TripAdapter tripAdapter;
    private ArrayList items;

    public static ListTripFragment newInstance(int type) {
        ListTripFragment listTripFragment = new ListTripFragment();
        listTripFragment.setTAG(Constans.TagFragment.LIST_TRIP_FRAGMENT);
        listTripFragment.type = type;
        return listTripFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_item;
    }

    @Override
    protected void onFragmentCreateView(View view) {

    }

    @Override
    protected void onFragmentCreated(View view) {

    }

    @Override
    protected void innitView(View view) {
        swipeRefreshList = view.findViewById(R.id.swipe_refresh_list);
        innitRcv(view);
    }

    private void innitRcv(View view) {
        rcvTrips = view.findViewById(R.id.rcv_list);
        tripAdapter = new TripAdapter();
        items = new ArrayList();
        items.add("Loading");
        items.add("Loading");
        items.add("Loading");
        items.add("Loading");
        items.add("Loading");
        tripAdapter.setItems(items);
        rcvTrips.setAdapter(tripAdapter);
        rcvTrips.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void addEvents() {
        swipeRefreshList.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshList.setRefreshing(false);
            }
        }, 350);
    }

    @Override
    public void onPause() {
        super.onPause();
        swipeRefreshList.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenterListTrip.showListTrip(0, type);
    }

    @Override
    public void setPresenter(MVPListTrip.IPresenterListTrip iPresenterListTrip) {
        if (iPresenterListTrip != null) {
            this.presenterListTrip = iPresenterListTrip;
        }
    }

    @Override
    public void showUIListTrip(final ArrayList<TripInfor> tripInfors, final boolean isFirstLoad) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFirstLoad) {
                    items.clear();
                }
                items.addAll(tripInfors);
                tripAdapter.notifyDataSetChanged();
            }
        }, 1500);
    }

}
