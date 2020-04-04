package com.svmc.android.locationsupportteam.ui.trip.edittrip.search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.ItemSearchCityAndProvinceAdapter;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 5/22/2019
 */

public class SearchPointActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private ImageView imgClose;

    private EditText edtSearch;
    private TextView btnClear;

    private RecyclerView rcvRecentedSearch;
    private ItemSearchCityAndProvinceAdapter itemSearchAdapter;
    private List items;

    @Override
    public int getContentView() {
        return R.layout.activity_search_place;
    }

    @Override
    public void onViewCreated(View view) {
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
        itemSearchAdapter = new ItemSearchCityAndProvinceAdapter();
        itemSearchAdapter.setItems(items);
        rcvRecentedSearch.setLayoutManager(new LinearLayoutManager(this));
        rcvRecentedSearch.setAdapter(itemSearchAdapter);
    }

    private void addEvents() {
        imgClose.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        edtSearch.addTextChangedListener(this);
        itemSearchAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
            @Override
            public void onClick(int position) {

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
        if (s.length() > 0) {
            btnClear.setVisibility(View.VISIBLE);
        } else {
            btnClear.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
