package com.svmc.android.locationsupportteam.ui.trip.edittrip.editdetaildayoftrip.itemeditdetaildayoftrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.PlaceAndVehicleAdapter;
import com.svmc.android.locationsupportteam.entity.trip.TripDayInfor;
import com.svmc.android.locationsupportteam.entity.trip.TripDetailDay;
import com.svmc.android.locationsupportteam.entity.trip.TripInfor;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.trip.edittrip.EditTripActivity;
import com.svmc.android.locationsupportteam.ui.trip.edittrip.addrecommendplace.AddRecommendPlaceActivity;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/1/2019
 */

public class EditDetailDayOfTripFragment extends BaseFragment implements MVPEditDetailDayOfTrip.IViewEditDetailDayOfTrip, View.OnClickListener {

    private TripDayInfor tripDayInfor;

    private TextView tvDate;
    private TextView tvNumberOfDay;
    private TextView tvFreeDay;

    private RecyclerView rcvDetailDay;
    private PlaceAndVehicleAdapter placeAndVehicleAdapter;
    private List<TripDetailDay> detailDays;

    private TextView btnAddPlace;
    private TextView btnOptimalRoute;

    MVPEditDetailDayOfTrip.IPresenterEditDetailDayOfTrip presenter;

    public static EditDetailDayOfTripFragment newInstance(TripDayInfor tripDayInfor) {
        EditDetailDayOfTripFragment editDetailTripFragment = new EditDetailDayOfTripFragment();
        editDetailTripFragment.setTAG(Constans.TagFragment.EDIT_DETAIL_DAY_OF_TRIP_FRAGMENT);
        editDetailTripFragment.tripDayInfor = tripDayInfor;
        return editDetailTripFragment;
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
        tvDate = view.findViewById(R.id.tv_date_of_trip);
        tvNumberOfDay = view.findViewById(R.id.tv_number_day_of_trip);
        tvFreeDay = view.findViewById(R.id.tv_free_day);
        btnAddPlace = view.findViewById(R.id.btn_add_place);
        btnOptimalRoute = view.findViewById(R.id.btn_optimal_route);
        innitRcvDetailDay(view);
    }

    private void innitRcvDetailDay(View view) {
        rcvDetailDay = view.findViewById(R.id.rcv_detail_day_plan);
        placeAndVehicleAdapter = new PlaceAndVehicleAdapter();
        detailDays = tripDayInfor.getDetailDays();
        if (detailDays == null) {
            detailDays = new ArrayList<>();
        }
        placeAndVehicleAdapter.setItems(detailDays);
        rcvDetailDay.setAdapter(placeAndVehicleAdapter);
        rcvDetailDay.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void addEvents() {
        btnAddPlace.setOnClickListener(this);
        btnOptimalRoute.setOnClickListener(this);
    }

    @Override
    public void setPresenter(MVPEditDetailDayOfTrip.IPresenterEditDetailDayOfTrip iPresenterEditDayOfTripFragment) {
        if (iPresenterEditDayOfTripFragment != null) {
            presenter = iPresenterEditDayOfTripFragment;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_place:
                TripInfor tripInfor = ((EditTripActivity) getActivity()).getTripInfor();
                Intent i = new Intent(getActivity(), AddRecommendPlaceActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constans.KeyBundle.TRIP_INFOR, new Gson().toJson(tripInfor));
                i.putExtras(bundle);
                startActivityForResult(i, Constans.RequestCode.RC_ADD_RECOMMEND_PLACE);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.fade_out);
                break;
            case R.id.btn_optimal_route:
                break;
        }
    }

}
