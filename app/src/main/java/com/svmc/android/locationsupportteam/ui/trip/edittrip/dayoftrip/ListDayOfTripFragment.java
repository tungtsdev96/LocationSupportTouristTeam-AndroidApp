package com.svmc.android.locationsupportteam.ui.trip.edittrip.dayoftrip;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.DayOfTripAdapter;
import com.svmc.android.locationsupportteam.entity.trip.TripDayInfor;
import com.svmc.android.locationsupportteam.entity.trip.TripInfor;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.customviews.GirdSpacingItemDecoration;
import com.svmc.android.locationsupportteam.ui.trip.edittrip.EditTripActivity;
import com.svmc.android.locationsupportteam.utils.CommonUtils;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 3/31/2019
 */

public class ListDayOfTripFragment extends BaseFragment implements View.OnClickListener, MVPListDayOfTripFragment.IViewListDayOfTrip, SwipeRefreshLayout.OnRefreshListener {

    private Toolbar toolbar;
    private SwipeRefreshLayout refreshLayout;

    private RecyclerView rcvDayOfTrip;
    private DayOfTripAdapter dayOfTripAdapter;
    private List<TripDayInfor> items;

    private TextView btnNextStep;

    private MVPListDayOfTripFragment.IPresenterListDayOfTrip presenterEditDetailTrip;

    public static ListDayOfTripFragment newInstance() {
        ListDayOfTripFragment listDayOfTripFragment = new ListDayOfTripFragment();
        listDayOfTripFragment.setTAG(Constans.TagFragment.LIST_DAY_OF_TRIP_FRAGMENT);
        return listDayOfTripFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_day_of_trip;
    }

    @Override
    protected void onFragmentCreateView(View view) {

    }

    @Override
    protected void onFragmentCreated(View view) {
        innitToolbar(view);
    }

    private void innitToolbar(View view) {
        setHasOptionsMenu(true);
        toolbar = view.findViewById(R.id.toolbar);
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
        ((BaseActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getString(R.string.create_trip));
        ((BaseActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void innitView(View view) {
        refreshLayout = view.findViewById(R.id.swipe_refresh);
        btnNextStep = view.findViewById(R.id.btn_next_step);
        innitRcvDayOfTrip(view);
    }

    private void innitRcvDayOfTrip(View view) {
        TripInfor tripInfor = ((EditTripActivity) getActivity()).getTripInfor();

        rcvDayOfTrip = view.findViewById(R.id.rcv_day_of_trip);
        items = new ArrayList<>();
//        items.addAll(tripInfor.getTripDayInfors());
        dayOfTripAdapter = new DayOfTripAdapter();
        dayOfTripAdapter.setItems(items);
        rcvDayOfTrip.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rcvDayOfTrip.addItemDecoration(new GirdSpacingItemDecoration(2, CommonUtils.dpToPx(8),true));
        rcvDayOfTrip.setAdapter(dayOfTripAdapter);
    }

    @Override
    protected void addEvents() {
        btnNextStep.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        dayOfTripAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
            @Override
            public void onClick(int position) {
                ((EditTripActivity) getActivity()).setCurrentDateChoose(position);
                ((EditTripActivity) getActivity()).innitFragmentToCreateTrip(3);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next_step:
                Toast.makeText(getContext(), "Next", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void setPresenter(MVPListDayOfTripFragment.IPresenterListDayOfTrip iPresenterListDayOfTrip) {
        if (iPresenterListDayOfTrip != null) {
            this.presenterEditDetailTrip = iPresenterListDayOfTrip;
        }
    }

    @Override
    public void onRefresh() {
        if (refreshLayout.isRefreshing()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(false);
                }
            }, 1000);
        }
    }
}
