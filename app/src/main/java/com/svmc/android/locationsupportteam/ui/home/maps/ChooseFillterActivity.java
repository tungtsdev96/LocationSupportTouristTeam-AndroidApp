package com.svmc.android.locationsupportteam.ui.home.maps;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.utils.Constans;

/**
 * Created by TUNGTS on 4/17/2019
 */

public class ChooseFillterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnClose;

    private TextView tvProminence;
    private TextView tvDistance;
    private boolean isRankByDefault;

    private TextView tvPriceAll;
    private TextView tvPrice01;
    private TextView tvPrice2;
    private TextView tvPrice34;
    private int currentPrice;

    private TextView tvOpenNow;
    private boolean isOpenNow;

    private TextView tvDelete;
    private TextView tvApply;

    @Override
    public int getContentView() {
        return R.layout.activity_choose_fillter;
    }

    @Override
    public void onViewCreated(View view) {
        btnClose = findViewById(R.id.img_close);
        innitRankBy();
        innitFilterPrice();
        innitFilterOpenNow();
        tvDelete = findViewById(R.id.tv_delete);
        tvApply = findViewById(R.id.tv_apply);
        addEvents();
    }

    private void innitRankBy() {
        tvProminence = findViewById(R.id.tv_prominence);
        tvDistance = findViewById(R.id.tv_distance);
        isRankByDefault = true;
    }

    private void innitFilterPrice() {
        tvPriceAll = findViewById(R.id.tv_price_all);
        tvPrice01 = findViewById(R.id.tv_price_01);
        tvPrice2 = findViewById(R.id.tv_price_2);
        tvPrice34 = findViewById(R.id.tv_price_34);
        currentPrice = 0;
    }

    private void innitFilterOpenNow() {
        tvOpenNow = findViewById(R.id.tv_open_now);
        isOpenNow = false;
    }

    private void addEvents() {
        btnClose.setOnClickListener(this);
        tvDistance.setOnClickListener(this);
        tvProminence.setOnClickListener(this);
        tvPriceAll.setOnClickListener(this);
        tvPrice01.setOnClickListener(this);
        tvPrice2.setOnClickListener(this);
        tvPrice34.setOnClickListener(this);
        tvOpenNow.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        tvApply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_close:
                onBackPressed();
                break;
            case R.id.tv_prominence:
                isRankByDefault = true;
                makeRankBy(isRankByDefault);
                break;
            case R.id.tv_distance:
                isRankByDefault = false;
                makeRankBy(isRankByDefault);
                break;
            case R.id.tv_price_all:
                currentPrice = 0;
                makePrice(currentPrice);
                break;
            case R.id.tv_price_01:
                currentPrice = 1;
                makePrice(currentPrice);
                break;
            case R.id.tv_price_2:
                currentPrice = 2;
                makePrice(currentPrice);
                break;
            case R.id.tv_price_34:
                currentPrice = 3;
                makePrice(currentPrice);
                break;
            case R.id.tv_open_now:
                isOpenNow = !isOpenNow;
                makeOpenNow(isOpenNow);
                break;
            case R.id.tv_delete:
                isRankByDefault = true;
                makeRankBy(isRankByDefault);
                currentPrice = 0;
                makePrice(0);
                isOpenNow = false;
                makeOpenNow(isOpenNow);
                break;
            case R.id.tv_apply:
                Intent i = new Intent();
                i.putExtra(Constans.KeyBundle.FILTER_PLACE_RANKBY, isRankByDefault ? "prominence" : "distance");
                i.putExtra(Constans.KeyBundle.FILTER_PLACE_PRICE, currentPrice);
                i.putExtra(Constans.KeyBundle.FILTER_PLACE_OPEN_NOW, isOpenNow);
                setResult(RESULT_OK, i);
                finish();
                break;
        }
    }

    private void makeRankBy(boolean isDefault) {
        if(isDefault) {
            tvProminence.setBackgroundResource(R.drawable.bg_btn_done_create_trip);
            tvProminence.setTextColor(getResources().getColor(R.color.green_dark));
            tvDistance.setBackgroundResource(0);
            tvDistance.setTextColor(getResources().getColor(R.color.colorBlack));
        } else {
            tvDistance.setBackgroundResource(R.drawable.bg_btn_done_create_trip);
            tvDistance.setTextColor(getResources().getColor(R.color.green_dark));
            tvProminence.setBackgroundResource(0);
            tvProminence.setTextColor(getResources().getColor(R.color.colorBlack));
        }
    }

    private void makePrice(int currentPrice) {
        switch (currentPrice) {
            case 0:
                tvPriceAll.setBackgroundResource(R.drawable.bg_btn_done_create_trip);
                tvPriceAll.setTextColor(getResources().getColor(R.color.green_dark));
                tvPrice01.setBackgroundResource(0);
                tvPrice01.setTextColor(getResources().getColor(R.color.colorBlack));
                tvPrice2.setBackgroundResource(0);
                tvPrice2.setTextColor(getResources().getColor(R.color.colorBlack));
                tvPrice34.setBackgroundResource(0);
                tvPrice34.setTextColor(getResources().getColor(R.color.colorBlack));
                break;
            case 1:
                tvPrice01.setBackgroundResource(R.drawable.bg_btn_done_create_trip);
                tvPrice01.setTextColor(getResources().getColor(R.color.green_dark));
                tvPriceAll.setBackgroundResource(0);
                tvPriceAll.setTextColor(getResources().getColor(R.color.colorBlack));
                tvPrice2.setBackgroundResource(0);
                tvPrice2.setTextColor(getResources().getColor(R.color.colorBlack));
                tvPrice34.setBackgroundResource(0);
                tvPrice34.setTextColor(getResources().getColor(R.color.colorBlack));
                break;
            case 2:
                tvPrice2.setBackgroundResource(R.drawable.bg_btn_done_create_trip);
                tvPrice2.setTextColor(getResources().getColor(R.color.green_dark));
                tvPrice01.setBackgroundResource(0);
                tvPrice01.setTextColor(getResources().getColor(R.color.colorBlack));
                tvPriceAll.setBackgroundResource(0);
                tvPriceAll.setTextColor(getResources().getColor(R.color.colorBlack));
                tvPrice34.setBackgroundResource(0);
                tvPrice34.setTextColor(getResources().getColor(R.color.colorBlack));
                break;
            case 3:
                tvPrice34.setBackgroundResource(R.drawable.bg_btn_done_create_trip);
                tvPrice34.setTextColor(getResources().getColor(R.color.green_dark));
                tvPrice01.setBackgroundResource(0);
                tvPrice01.setTextColor(getResources().getColor(R.color.colorBlack));
                tvPrice2.setBackgroundResource(0);
                tvPrice2.setTextColor(getResources().getColor(R.color.colorBlack));
                tvPriceAll.setBackgroundResource(0);
                tvPriceAll.setTextColor(getResources().getColor(R.color.colorBlack));
                break;
        }
    }

    private void makeOpenNow(boolean isOpenNow) {
        if (isOpenNow) {
            tvOpenNow.setBackgroundResource(R.drawable.bg_btn_done_create_trip);
            tvOpenNow.setTextColor(getResources().getColor(R.color.green_dark));
        } else {
            tvOpenNow.setBackgroundResource(R.drawable.bg_btn_border_gray);
            tvOpenNow.setTextColor(getResources().getColor(R.color.colorBlack));
        }
    }
}
