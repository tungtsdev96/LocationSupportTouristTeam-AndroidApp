package com.svmc.android.locationsupportteam.ui.trip.edittrip.addrecommendplace;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.RecommendPlaceAdapter;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/1/2019
 */

public class RecommendPlaceByTypeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private SwipeRefreshLayout swipe_refresh_place_recommend;

    private RecyclerView rcvAddPlace;
    private RecommendPlaceAdapter recommendPlaceAdapter;
    private List items = new ArrayList();

    private EditText edtSearch;
    private ImageView btnFilter;
    private ImageView btnShowOnMap;

    public static RecommendPlaceByTypeFragment newInstance() {
        RecommendPlaceByTypeFragment recommendPlaceByTypeFragment = new RecommendPlaceByTypeFragment();
        recommendPlaceByTypeFragment.setTAG(Constans.TagFragment.ADD_RECOMMEND_PLACE_BY_TYPE_FRAGMENT);
        return recommendPlaceByTypeFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_recommend_place_by_type;
    }

    @Override
    protected void onFragmentCreateView(View view) {

    }

    @Override
    protected void onFragmentCreated(View view) {

    }

    @Override
    protected void innitView(View view) {
        swipe_refresh_place_recommend = view.findViewById(R.id.swipe_refresh_place_recommend);
        edtSearch = view.findViewById(R.id.edt_search);
        btnFilter = view.findViewById(R.id.btn_filter);
        btnShowOnMap = view.findViewById(R.id.btn_show_on_map);
        innitRcvAddPlace(view);
    }

    private void innitRcvAddPlace(View view) {
        rcvAddPlace = view.findViewById(R.id.rcv_add_place);
        recommendPlaceAdapter = new RecommendPlaceAdapter();
        items.add(new Place());
        items.add(new Place());
        items.add(new Place());
        items.add(new Place());
        items.add(new Place());
        recommendPlaceAdapter.setItems(items);
        rcvAddPlace.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rcvAddPlace.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvAddPlace.setAdapter(recommendPlaceAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        swipe_refresh_place_recommend.setRefreshing(false);
    }

    @Override
    protected void addEvents() {
        swipe_refresh_place_recommend.setOnRefreshListener(this);
        btnFilter.setOnClickListener(this);
        btnShowOnMap.setOnClickListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipe_refresh_place_recommend.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_filter:
                Toast.makeText(getContext(), "fillter ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_show_on_map:
                ((AddRecommendPlaceActivity) getActivity()).showOnMap();
                break;
        }
    }
}
