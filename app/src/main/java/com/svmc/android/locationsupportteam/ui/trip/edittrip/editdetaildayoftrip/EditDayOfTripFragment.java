package com.svmc.android.locationsupportteam.ui.trip.edittrip.editdetaildayoftrip;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.ViewPagerAdapter;
import com.svmc.android.locationsupportteam.entity.trip.TripInfor;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.trip.edittrip.EditTripActivity;
import com.svmc.android.locationsupportteam.ui.trip.edittrip.editdetaildayoftrip.itemeditdetaildayoftrip.MVPEditDetailDayOfTrip;
import com.svmc.android.locationsupportteam.ui.trip.edittrip.editdetaildayoftrip.itemeditdetaildayoftrip.PresenterEditDetailDayOfTripImpl;
import com.svmc.android.locationsupportteam.utils.Constans;

/**
 * Created by TUNGTS on 4/1/2019
 */

public class EditDayOfTripFragment extends BaseFragment implements ViewPager.OnPageChangeListener, View.OnClickListener, MVPEditDayOfTrip.IViewEditDayOfTrip {

    private Toolbar toolbar;
    private ImageView btnBack;
    private TextView tvTitle;
    private TextView btnDone;

    private ViewPager viewPagerDayOfTrip;
    private ViewPagerAdapter viewPagerAdapter;

    private MVPEditDayOfTrip.IPresenterEditDayOfTrip presenterEditDayOfTrip;

    public static EditDayOfTripFragment newInstance() {
        EditDayOfTripFragment editDetailDayOfTripFragment = new EditDayOfTripFragment();
        editDetailDayOfTripFragment.setTAG(Constans.TagFragment.EDIT_DAY_OF_TRIP_FRAGMENT);
        return editDetailDayOfTripFragment;
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
        toolbar = view.findViewById(R.id.toolbar);
        btnBack = view.findViewById(R.id.btn_back);
        tvTitle = view.findViewById(R.id.tv_title);
        btnDone = view.findViewById(R.id.btn_done);
        innitViewPager(view);
    }

    private void innitViewPager(View view) {
//        TripInfor tripInfor = ((EditTripActivity)getActivity()).getTripInfor();
//        viewPagerDayOfTrip = view.findViewById(R.id.view_pager_day_of_trip);
//        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
//        for (int i = 0; i < tripInfor.getTotalDay(); i++) {
//            com.svmc.android.locationsupportteam.ui.trip.edittrip.editdetaildayoftrip.itemeditdetaildayoftrip.EditDetailDayOfTripFragment editDetailDayOfTripFragment = com.svmc.android.locationsupportteam.ui.trip.edittrip.editdetaildayoftrip.itemeditdetaildayoftrip.EditDetailDayOfTripFragment.newInstance(tripInfor.getTripDayInfors().get(0));
//            MVPEditDetailDayOfTrip.IPresenterEditDetailDayOfTrip presenterEditDayOfTripFragment = new PresenterEditDetailDayOfTripImpl(editDetailDayOfTripFragment);
//            viewPagerAdapter.addFragment(editDetailDayOfTripFragment, "Day " + (i + 1));
//        }
//        viewPagerDayOfTrip.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void addEvents() {
        viewPagerDayOfTrip.addOnPageChangeListener(this);
        btnBack.setOnClickListener(this);
        btnDone.setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                getActivity().onBackPressed();
                break;
            case R.id.btn_done:
                getActivity().onBackPressed();
                break;
        }
    }

    @Override
    public void setPresenter(MVPEditDayOfTrip.IPresenterEditDayOfTrip iPresenterEditDayOfTrip) {
        if (iPresenterEditDayOfTrip != null) {
            this.presenterEditDayOfTrip = iPresenterEditDayOfTrip;
        }
    }
}
