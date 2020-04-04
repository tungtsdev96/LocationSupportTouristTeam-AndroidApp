package com.svmc.android.locationsupportteam.ui.trip.edittrip.addrecommendplace;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.ViewPagerAdapter;
import com.svmc.android.locationsupportteam.entity.trip.TripInfor;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.trip.edittrip.addrecommendplace.onmap.AddRecommendPlaceOnMap;
import com.svmc.android.locationsupportteam.utils.Constans;

/**
 * Created by TUNGTS on 4/1/2019
 */

public class AddRecommendPlaceActivity extends BaseActivity {

    private TripInfor tripInfor;

    private AppBarLayout appBar;
    private Toolbar toolbar;
    private ImageView btnBack;
    private TextView btnDone;

    private TabLayout tabPlaceType;
    private ViewPager viewPagerAddRecommendPlace;
    private ViewPagerAdapter viewPagerAdapter;

    private RelativeLayout rltShowOnMap;

    @Override
    public int getContentView() {
        return R.layout.activity_add_recommend_place;
    }

    @Override
    public void onViewCreated(View view) {
        innitView();
        addEvents();

    }

    private void innitView() {
        innitToolbar();
        tabPlaceType = findViewById(R.id.tab_add_recommend_place);
        rltShowOnMap = findViewById(R.id.rlt_show_on_map);
        inniViewPager();
    }

    private void innitToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnBack = toolbar.findViewById(R.id.btn_back);
        btnDone = toolbar.findViewById(R.id.btn_done);
    }

    private void addEvents() {

    }

    private void inniViewPager() {
        viewPagerAddRecommendPlace = findViewById(R.id.view_pager_add_recommend_place);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        String[] listType = getResources().getStringArray(R.array.type_places);
        for (String type: listType) {
            RecommendPlaceByTypeFragment recommendPlaceByTypeFragment = RecommendPlaceByTypeFragment.newInstance();
            viewPagerAdapter.addFragment(recommendPlaceByTypeFragment, type);
        }
        tabPlaceType.setupWithViewPager(viewPagerAddRecommendPlace);
        viewPagerAddRecommendPlace.setAdapter(viewPagerAdapter);
    }

    public TripInfor getTripInfor() {
        if (getIntent().getExtras() != null) {
            String jsonTripInfor = getIntent().getExtras().getString(Constans.KeyBundle.TRIP_INFOR);
            tripInfor = new Gson().fromJson(jsonTripInfor, TripInfor.class);
            return tripInfor;
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void showOnMap() {
        String jsonTripInfor = new Gson().toJson(getTripInfor());
        int currentItem = viewPagerAddRecommendPlace.getCurrentItem();
        Bundle bundle = new Bundle();
        bundle.putString(Constans.KeyBundle.TRIP_INFOR, jsonTripInfor);
        bundle.putInt(Constans.KeyBundle.CURRENT_ITEM_TYPE_PLACE, currentItem);

        Intent intent = new Intent(AddRecommendPlaceActivity.this, AddRecommendPlaceOnMap.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constans.RequestCode.RC_ADD_RECOMMEND_PLACE);
        overridePendingTransition(R.anim.slide_in, R.anim.fade_out);
    }
}
