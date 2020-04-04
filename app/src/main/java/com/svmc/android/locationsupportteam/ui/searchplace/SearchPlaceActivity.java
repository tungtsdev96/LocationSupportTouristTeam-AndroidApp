package com.svmc.android.locationsupportteam.ui.searchplace;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.ItemSearchCityAndProvinceAdapter;
import com.svmc.android.locationsupportteam.entity.CityProvince;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.ConvertUnsigned;
import com.svmc.android.locationsupportteam.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/9/2019
 */

public class SearchPlaceActivity extends BaseActivity
        implements View.OnClickListener, TextWatcher, MVPSearchPlace.IViewSearchPlace {

    private MVPSearchPlace.IPresenterSearchPlace presenterSearchPlace;

    private ImageView imgClose;

    private EditText edtSearch;
    private TextView btnClear;

    private RecyclerView rcvRecentedSearch;
    private ItemSearchCityAndProvinceAdapter itemSearchAdapter;
    private List items;
    private List<CityProvince> coppyItems = new ArrayList();

    @Override
    public int getContentView() {
        return R.layout.activity_search_place;
    }

    @Override
    public void onViewCreated(View view) {
        presenterSearchPlace = new PresenterSearchPlaceImpl(this);
        innitView();
        addEvents();
    }

    private void innitView() {
        imgClose = findViewById(R.id.img_close);
        edtSearch = findViewById(R.id.edt_search);
        btnClear = findViewById(R.id.btn_clear);
        innitRcv();
    }

    private void innitRcv() {
        rcvRecentedSearch = findViewById(R.id.rcv_recented_search);
        items = new ArrayList();
        items.add(null);
        itemSearchAdapter = new ItemSearchCityAndProvinceAdapter();
        itemSearchAdapter.setItems(items);
        rcvRecentedSearch.setLayoutManager(new LinearLayoutManager(this));
        rcvRecentedSearch.setAdapter(itemSearchAdapter);
        innitDataToRcv();
    }

    private void innitDataToRcv() {
        final List<CityProvince> cityProvinces = FileUtils.getListCityAndProvince(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                items.remove(0);
                itemSearchAdapter.notifyItemRemoved(0);
                CityProvince province = new CityProvince();
                province.setName("Gần đây");
                province.setKey("Gần đây");
                items.add(province);
                items.addAll(cityProvinces);

                coppyItems.addAll(items);
                itemSearchAdapter.notifyDataSetChanged();
            }
        }, 500);
    }

    private void addEvents() {
        imgClose.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        edtSearch.addTextChangedListener(this);
        itemSearchAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
            @Override
            public void onClick(int position) {
                Intent data = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(Constans.KeyBundle.SEARCH_PLACE, new Gson().toJson(items.get(position)));
                data.putExtras(bundle);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_close:
                onBackPressed();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.btn_clear:
                edtSearch.setText("");
                btnClear.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        filter(s.toString());
        if (s.length() > 0) {
            btnClear.setVisibility(View.VISIBLE);
        } else {
            btnClear.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void filter(String query) {
        items.clear();
        if (query.length() == 0) {
            items.addAll(coppyItems);
            itemSearchAdapter.notifyDataSetChanged();
            return;
        }

        ConvertUnsigned convertUnsigned = new ConvertUnsigned();
        for (CityProvince cityProvince : coppyItems) {
            String tmp = convertUnsigned.convertString(query);
            String name = cityProvince.getName().toLowerCase();
            String key = cityProvince.getKey().toLowerCase();
            if (convertUnsigned.convertString(name).contains(tmp) || convertUnsigned.convertString(key).contains(tmp)) {
                items.add(cityProvince);
            }
        }
        itemSearchAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(MVPSearchPlace.IPresenterSearchPlace iPresenterSearchPlace) {
        if (iPresenterSearchPlace != null) {
            presenterSearchPlace = iPresenterSearchPlace;
        }
    }

}
