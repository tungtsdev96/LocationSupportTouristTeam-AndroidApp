package com.svmc.android.locationsupportteam.ui.trip.edittrip.addrecommendplace.onmap;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.customviews.CustomRatingView;
import com.svmc.android.locationsupportteam.utils.Constans;

/**
 * Created by TUNGTS on 4/1/2019
 */

public class ChoosePlaceOnMapFragment extends BaseFragment implements View.OnClickListener {

    private Place place;

    private ImageView imgPlace;
    private ImageView btnAddPlace;
    private TextView tvNamePlace;
    private TextView tvRatingScore;
    private CustomRatingView ratingView;
    private TextView tvPriceLevel;
    private View itemChoosePlaceOnMap;

    public static ChoosePlaceOnMapFragment newInstance(Place place) {
        ChoosePlaceOnMapFragment choosePlaceOnMapFragment = new ChoosePlaceOnMapFragment();
        choosePlaceOnMapFragment.setTAG(Constans.TagFragment.CHOOSE_RECOMMEND_PLACE_BY_TYPE_FRAGMENT);
        choosePlaceOnMapFragment.place = place;
        return choosePlaceOnMapFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frgament_choose_place_on_map;
    }

    @Override
    protected void onFragmentCreateView(View view) {

    }

    @Override
    protected void onFragmentCreated(View view) {

    }

    @Override
    protected void innitView(View view) {
        imgPlace = view.findViewById(R.id.img_place);
        btnAddPlace = view.findViewById(R.id.btn_add_place);
        tvNamePlace = view.findViewById(R.id.tv_name_place);
        tvRatingScore = view.findViewById(R.id.tv_rating_score);
        ratingView = view.findViewById(R.id.ratting_view_palce);
        tvPriceLevel = view.findViewById(R.id.tv_price_level);
        itemChoosePlaceOnMap = view.findViewById(R.id.item_choose_place_on_map);
    }

    @Override
    protected void addEvents() {
        btnAddPlace.setOnClickListener(this);
        itemChoosePlaceOnMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_place:
                Toast.makeText(getContext(), "add Place", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_choose_place_on_map:
                Toast.makeText(getContext(), "detail place", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
