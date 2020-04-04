package com.svmc.android.locationsupportteam.ui.trip.mytrip;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.ViewPagerAdapter;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.trip.edittrip.EditTripActivity;
import com.svmc.android.locationsupportteam.ui.trip.mytrip.mytrip.MVPMyTrip;
import com.svmc.android.locationsupportteam.ui.trip.mytrip.mytrip.MyTripFragment;
import com.svmc.android.locationsupportteam.ui.trip.mytrip.mytrip.PresenterMyTripImpl;
import com.svmc.android.locationsupportteam.ui.trip.mytrip.recentedviewtrip.RecentedViewTripFragment;

public class TripActivity extends BaseActivity implements View.OnClickListener {

    private AppBarLayout appBarTrip;
    private Toolbar toolbarTrip;
    private TabLayout tabLayoutTrip;
    private FloatingActionButton btnAddTrip;

    private ViewPager viewPagerTrip;
    private ViewPagerAdapter viewPagerAdapter;

    private MyTripFragment myTripFragment;
    private MVPMyTrip.IPresenterMyTrip presenterMyTrip;

    private RecentedViewTripFragment recentedViewTripFragment;

    private void innitData() {
        myTripFragment = MyTripFragment.newInstance();
        presenterMyTrip = new PresenterMyTripImpl(myTripFragment);

        recentedViewTripFragment = RecentedViewTripFragment.newInstance();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_trip;
    }

    @Override
    public void onViewCreated(View view) {
        innitView(view);
        addEvents();
    }

    private void innitView(View view) {
        innitActionBar(view);
        tabLayoutTrip = view.findViewById(R.id.tab_layout_trip);
        viewPagerTrip = view.findViewById(R.id.view_pager_trip);
        btnAddTrip = view.findViewById(R.id.fab_add_trip);
        innitViewPager();
    }

    private void innitActionBar(View view) {
        appBarTrip = view.findViewById(R.id.app_bar_trip);
        toolbarTrip = view.findViewById(R.id.toolbar_trip);
        setSupportActionBar(toolbarTrip);
        getSupportActionBar().setTitle(getString(R.string.trip));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void innitViewPager() {
        innitData();
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(myTripFragment, getString(R.string.my_trip));
        viewPagerAdapter.addFragment(recentedViewTripFragment, getString(R.string.recented_trip));
        viewPagerTrip.setAdapter(viewPagerAdapter);
        tabLayoutTrip.setupWithViewPager(viewPagerTrip);
    }

    private void addEvents() {
        btnAddTrip.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add_trip:
                Intent i = new Intent(TripActivity.this, EditTripActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;
        }
    }
}
