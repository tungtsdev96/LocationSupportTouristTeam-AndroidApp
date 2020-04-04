package com.svmc.android.locationsupportteam.ui.placesaved;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.PlaceAdapter;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.detailplace.PlaceActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TungTS on 5/14/2019
 */

public class PlaceSavedActivity extends BaseActivity implements MVPPlaceSaved.IViewPlaceSaved, SwipeRefreshLayout.OnRefreshListener {

    private MVPPlaceSaved.IPresenterPlaceSaved presenterPlaceSaved;

    private Toolbar toolbar;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rcvPlaces;
    private PlaceAdapter placeAdapter;
    private List places;

    @Override
    public int getContentView() {
        return R.layout.activity_place_saved;
    }

    @Override
    public void onViewCreated(View view) {
        presenterPlaceSaved = new PresenterPlaceSavedImpl(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đã lưu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        refreshLayout = findViewById(R.id.swipe_refresh);

        innitRecycleViewPlace();
        addEvents();
    }

    private void innitRecycleViewPlace() {
        rcvPlaces = findViewById(R.id.rcv_place);
        placeAdapter = new PlaceAdapter();
        places = new ArrayList();
        placeAdapter.setItems(places);
        rcvPlaces.setLayoutManager(new LinearLayoutManager(this));
        rcvPlaces.setAdapter(placeAdapter);
        presenterPlaceSaved.getListPlaceSaved();
    }

    private void addEvents() {
        refreshLayout.setOnRefreshListener(this);

        placeAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
            @Override
            public void onClick(int position) {
                Place place = (Place) places.get(position);
                Intent i = PlaceActivity.setIntentDataDetail(PlaceSavedActivity.this, place.getPlaceId());
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 1500);
    }

    @Override
    public void showListPlace(ArrayList<Place> places) {
        this.places.addAll(places);
        placeAdapter.notifyDataSetChanged();
    }


}
