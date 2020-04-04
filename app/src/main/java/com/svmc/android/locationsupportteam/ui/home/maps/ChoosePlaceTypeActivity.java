package com.svmc.android.locationsupportteam.ui.home.maps;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.PlaceTypeAdapter;
import com.svmc.android.locationsupportteam.entity.PlaceType;
import com.svmc.android.locationsupportteam.entity.SubPlaceType;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.FileUtils;

import java.util.List;

/**
 * Created by TUNGTS on 5/10/2019
 */


public class ChoosePlaceTypeActivity extends BaseActivity {

    private Toolbar toolbar;
    private ImageView imgBack;
    private TextView tvTitle;

    private RecyclerView rcvPlaceType;
    private PlaceTypeAdapter typeAdapter;
    private List<PlaceType> placeTypes;

    @Override
    public int getContentView() {
        return R.layout.activity_list_recommend_place;
    }

    @Override
    public void onViewCreated(View view) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgBack = findViewById(R.id.img_back);
        tvTitle = findViewById(R.id.tv_title);

        tvTitle.setText("Danh mục");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });


        rcvPlaceType = view.findViewById(R.id.rcv_list);
        typeAdapter = new PlaceTypeAdapter();
        placeTypes = FileUtils.getPlaceTypes(this);
        typeAdapter.setPlaceTypes(placeTypes);
        rcvPlaceType.setLayoutManager(new LinearLayoutManager(this));
        rcvPlaceType.setAdapter(typeAdapter);

        typeAdapter.setOnClickSubPlaceType(new PlaceTypeAdapter.ClickSubPlaceType() {
            @Override
            public void onClick(SubPlaceType placeType) {
                choosePlaceType(placeType);
            }
        });
    }

    private void choosePlaceType(SubPlaceType placeType) {
        Intent i = new Intent();
        i.putExtra(Constans.KeyBundle.CHOOSE_PLACE_TYPE, new Gson().toJson(placeType));
        setResult(RESULT_OK, i);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}