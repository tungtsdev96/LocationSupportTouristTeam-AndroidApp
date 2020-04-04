package com.svmc.android.locationsupportteam.ui.listitem;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.homescreen.HomeScreenModel;
import com.svmc.android.locationsupportteam.entity.SubPlaceType;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.listitem.place.ListPlaceFragment;
import com.svmc.android.locationsupportteam.ui.listitem.place.MVPListPlace;
import com.svmc.android.locationsupportteam.ui.listitem.place.PresenterListPlaceImpl;
import com.svmc.android.locationsupportteam.ui.listitem.trip.ListTripFragment;
import com.svmc.android.locationsupportteam.ui.listitem.trip.MVPListTrip;
import com.svmc.android.locationsupportteam.ui.listitem.trip.PresenterListTripImpl;
import com.svmc.android.locationsupportteam.utils.Constans;

public class ListItemActivity extends BaseActivity {

    public static Intent innitIntentFromHomeModel(Activity activity, HomeScreenModel homeScreenModel) {
        Intent intent = new Intent(activity, ListItemActivity.class);
        intent.putExtra(Constans.KeyBundle.ITEM_HOME_SCREEN_MODEL, new Gson().toJson(homeScreenModel));
        return intent;
    }

    public static Intent innitIntentFromMap(Activity activity, String location, SubPlaceType subPlaceType) {
        Intent intent = new Intent(activity, ListItemActivity.class);
        intent.putExtra(Constans.KeyBundle.CURRENT_LOCATION, location);
        intent.putExtra(Constans.KeyBundle.KEY_TYPE_PLACE, new Gson().toJson(subPlaceType));
        return intent;
    }

    private Toolbar toolbar;

    @Override
    public int getContentView() {
        return R.layout.activity_list_item;
    }

    @Override
    public void onViewCreated(View view) {
        innitView();

        String jsonModel = getIntent().getStringExtra(Constans.KeyBundle.ITEM_HOME_SCREEN_MODEL);
        HomeScreenModel model = new Gson().fromJson(jsonModel, HomeScreenModel.class);
        if (model != null) {
            getSupportActionBar().setTitle(model.getTitle());
            innitFragment(model);
            return;
        }

        String location = getIntent().getStringExtra(Constans.KeyBundle.CURRENT_LOCATION);
        String jsonSubPlaceType = getIntent().getStringExtra(Constans.KeyBundle.KEY_TYPE_PLACE);
        SubPlaceType subPlaceType = new Gson().fromJson(jsonSubPlaceType, SubPlaceType.class);
        getSupportActionBar().setTitle(subPlaceType.getTitle());
        innitListPlaceFragment(subPlaceType.getId(), location);
    }

    private void innitView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * innit fragment with home model
     * @param model
     */
    private void innitFragment(HomeScreenModel model) {
        if (model.isPlaceModel()) {
            if (model.isQueryCity()) {
                innitListPlaceFragment(model.getQueryCity(), model.isQueryCity());
                return;
            }
            innitListPlaceFragment(model.getType(), model.getLocation());
        } else {
            ListTripFragment listTripFragment = ListTripFragment.newInstance(model.getType());
            MVPListTrip.IPresenterListTrip presenterListTrip = new PresenterListTripImpl(listTripFragment);
            pushFragment(R.id.content_list, listTripFragment, false);
        }
    }

    /**
     * innit list place fragment
     * @param keyTypePlace
     * @param location
     */
    public void innitListPlaceFragment(int keyTypePlace, String location) {
        ListPlaceFragment listPlaceFragment = ListPlaceFragment.newInstance(keyTypePlace, location);
        MVPListPlace.IPresenterListPlace presenterListPlace = new PresenterListPlaceImpl(listPlaceFragment);
        pushFragment(R.id.content_list, listPlaceFragment, false);
    }

    public void innitListPlaceFragment(String queryCity, boolean isQueryCity) {
        ListPlaceFragment listPlaceFragment = ListPlaceFragment.newInstance(queryCity, isQueryCity);
        MVPListPlace.IPresenterListPlace presenterListPlace = new PresenterListPlaceImpl(listPlaceFragment);
        pushFragment(R.id.content_list, listPlaceFragment, false);
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
