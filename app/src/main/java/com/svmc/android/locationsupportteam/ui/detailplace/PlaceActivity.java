package com.svmc.android.locationsupportteam.ui.detailplace;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.utils.Constans;

/**
 * Created by TungTS on 5/8/2019
 */

public class PlaceActivity extends BaseActivity {

    public static final int DETAIL_PLACE = 0;

    public static Intent setIntentDataDetail(Activity activity, String placeId) {
        Intent i = new Intent(activity, PlaceActivity.class);
        i.putExtra(Constans.KeyBundle.ID_PLACE, placeId);
        i.putExtra(Constans.KeyBundle. CURRENT_FRAG_ON_PLACE, PlaceActivity.DETAIL_PLACE);
        return i;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_place;
    }

    @Override
    public void onViewCreated(View view) {
        int type = getIntent().getIntExtra(Constans.KeyBundle.CURRENT_FRAG_ON_PLACE, -1);
        switch (type) {
            case 0:
                pushDetailFragment();
                break;
        }

    }

    private void pushDetailFragment() {
        String placeId = getIntent().getStringExtra(Constans.KeyBundle.ID_PLACE);
        DetailPlaceFragment detailPlaceFragment = DetailPlaceFragment.newInstance(placeId);
        MVPDetailPlace.IPresenterDetailPlace presenterDetailPlace = new PresenterDetailPlaceImpl(detailPlaceFragment);
        pushFragment(R.id.content_place, detailPlaceFragment, false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
