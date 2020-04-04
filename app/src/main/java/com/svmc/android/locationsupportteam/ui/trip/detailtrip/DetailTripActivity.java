package com.svmc.android.locationsupportteam.ui.trip.detailtrip;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.DetailDayOfTripAdapter;
import com.svmc.android.locationsupportteam.entity.trip.TripInfor;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.trip.detailtrip.detaildayoftrip.InforDayOfTripFragment;
import com.svmc.android.locationsupportteam.utils.Constans;

/**
 * Created by TUNGTS on 4/1/2019
 */

public class DetailTripActivity extends BaseActivity {

    private TripInfor tripInfor;

    public TripInfor getTripInfor() {
        if (getIntent().getExtras() != null) {
            String jsonTripInfor = getIntent().getExtras().getString(Constans.KeyBundle.TRIP_INFOR);
            tripInfor = new Gson().fromJson(jsonTripInfor, TripInfor.class);
            return tripInfor;
        }
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_detail_trip;
    }

    @Override
    public void onViewCreated(View view) {
        innitView();
        addEvents();
    }

    private void innitView() {
        innitFragment(1);
    }

    private void addEvents() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void innitFragment(int step) {
        switch (step){
            case 1:
                DetailTripFragment detailTripFragment = DetailTripFragment.newInstance();
                pushFragment(R.id.content_step_edit_trip, detailTripFragment, false);
                break;
            case 2:
                InforDayOfTripFragment inforDayOfTripFragment = InforDayOfTripFragment.newInstance();
                replaceFragment(R.id.content_step_edit_trip, inforDayOfTripFragment, 0, 0, 0, 0, true);
                break;
        }
    }

}
