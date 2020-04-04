package com.svmc.android.locationsupportteam.ui.trip.edittrip.addrecommendplace.onmap;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.TypePlaceRecommendAdapter;
import com.svmc.android.locationsupportteam.adapter.ViewPagerAdapter;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.trip.TripInfor;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.customviews.WrapContentViewPager;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/1/2019
 */

public class AddRecommendPlaceOnMap extends BaseActivity implements View.OnClickListener {

    private TripInfor tripInfor;

    public TripInfor getTripInfor() {
        if (getIntent().getExtras() != null) {
            String jsonTripInfor = getIntent().getExtras().getString(Constans.KeyBundle.TRIP_INFOR);
            tripInfor = new Gson().fromJson(jsonTripInfor, TripInfor.class);
            return tripInfor;
        }
        return null;
    }

    private int currentTypePlaceSearch = 0;

    public int getCurrentTypePlaceSearch() {
        if (getIntent().getExtras() != null) {
            currentTypePlaceSearch = getIntent().getExtras().getInt(Constans.KeyBundle.CURRENT_ITEM_TYPE_PLACE, 0);
            return currentTypePlaceSearch;
        }
        return 0;
    }

    private ImageView btnFilter;

    private RecyclerView rcvTypePlace;
    private TypePlaceRecommendAdapter typePlaceRecommendAdapter;
    private List items = new ArrayList();

    private Toolbar toolbar;
    private ImageView btnBack;
    private TextView btnDone;

    private WrapContentViewPager viewPagerChoosePlaceOnMap;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Place> places;

    @Override
    public int getContentView() {
        return R.layout.activity_add_recommend_on_map;
    }

    @Override
    public void onViewCreated(View view) {
        innitView();
        addEvents();
    }

    private void innitView() {
        btnFilter = findViewById(R.id.btn_filter);
        innitToolbar();
        innitRcvTypePlace();
        innitviewPager();
    }

    private void innitToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnBack = toolbar.findViewById(R.id.btn_back);
        btnDone = toolbar.findViewById(R.id.btn_done);
    }

    private void innitRcvTypePlace() {
        rcvTypePlace = findViewById(R.id.rcv_type_place);

        String[] typePlaces = getResources().getStringArray(R.array.type_places);
        for (String name: typePlaces) {
            items.add(name);
        }

        typePlaceRecommendAdapter = new TypePlaceRecommendAdapter();
        typePlaceRecommendAdapter.setCurrentTypePlace(getCurrentTypePlaceSearch());
        rcvTypePlace.setAdapter(typePlaceRecommendAdapter);
        rcvTypePlace.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        typePlaceRecommendAdapter.setItems(items);
    }

    private void innitviewPager() {
        viewPagerChoosePlaceOnMap = findViewById(R.id.view_pager_choose_palce);
        places = new ArrayList<>();
        places.add(new Place());
        places.add(new Place());
        places.add(new Place());
        places.add(new Place());
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (Place place: places) {
            viewPagerAdapter.addFragment(ChoosePlaceOnMapFragment.newInstance(place), "a");
        }
        viewPagerChoosePlaceOnMap.setAdapter(viewPagerAdapter);
    }

    private void addEvents() {
        btnFilter.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_filter:
                Toast.makeText(this, "filter", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_done:
                onBackPressed();
                break;
        }
    }
}
