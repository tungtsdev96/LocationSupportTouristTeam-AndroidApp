package com.svmc.android.locationsupportteam.ui.trip.detailtrip.detaildayoftrip;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.PlaceAndVehicleAdapter;
import com.svmc.android.locationsupportteam.entity.trip.TripDayInfor;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.utils.Constans;

/**
 * Created by TUNGTS on 4/1/2019
 */

public class DetailDayOfTripFragment extends BaseFragment {

    private TripDayInfor tripDayInfor;

    private TextView tvNumberDayOfTrip;
    private TextView tvDateOfTrip;
    private LinearLayout llEditDay;

    private RecyclerView rcvDetailDay;
    private PlaceAndVehicleAdapter placeAndVehicleAdapter;

    public static DetailDayOfTripFragment newInstance(TripDayInfor tripDayInfor) {
        DetailDayOfTripFragment detailDayOfTripFragment = new DetailDayOfTripFragment();
        detailDayOfTripFragment.tripDayInfor = tripDayInfor;
        detailDayOfTripFragment.setTAG(Constans.TagFragment.DETAIL_DAY_OF_TRIP_FRAGMENT);
        return detailDayOfTripFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_detail_day_of_trip;
    }

    @Override
    protected void onFragmentCreateView(View view) {

    }

    @Override
    protected void onFragmentCreated(View view) {

    }

    @Override
    protected void innitView(View view) {
        tvNumberDayOfTrip = view.findViewById(R.id.tv_number_day_of_trip);
        tvDateOfTrip = view.findViewById(R.id.tv_date_of_trip);
        llEditDay = view.findViewById(R.id.ll_edit_day);
        llEditDay.setVisibility(View.GONE);
        innitRcvView(view);
    }

    private void innitRcvView(View view) {
        rcvDetailDay = view.findViewById(R.id.rcv_detail_day_plan);
        placeAndVehicleAdapter = new PlaceAndVehicleAdapter();
        placeAndVehicleAdapter.setDetail(true);
        placeAndVehicleAdapter.setItems(tripDayInfor.getDetailDays());
        rcvDetailDay.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvDetailDay.setAdapter(placeAndVehicleAdapter);
    }

    @Override
    protected void addEvents() {

    }

}
