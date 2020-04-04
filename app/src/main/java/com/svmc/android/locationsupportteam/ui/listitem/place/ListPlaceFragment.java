package com.svmc.android.locationsupportteam.ui.listitem.place;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.PlaceAdapter;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.listeners.EndlessRecyclerViewScrollListener;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.detailplace.PlaceActivity;
import com.svmc.android.locationsupportteam.ui.common.dialog.FilterDialog;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.ArrayList;
import java.util.List;

public class ListPlaceFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, MVPListPlace.IViewListPlace {

    private String queryCity;
    private boolean isQueryCity;

    private int queryType;
    private String location;
    private String nextPageToken;

    private MVPListPlace.IPresenterListPlace presenterListPlace;

    private SwipeRefreshLayout swipeRefreshList;

    private EndlessRecyclerViewScrollListener scrollListener;
    private RecyclerView rcvPlaces;
    private PlaceAdapter placeAdapter;
    private List items;
    private List coppysItems;

    private boolean isFilter = false;

    public static ListPlaceFragment newInstance(int queryType, String location) {
        ListPlaceFragment listPlaceFragment = new ListPlaceFragment();
        listPlaceFragment.setTAG(Constans.TagFragment.LIST_PLACE_FRAGMENT);
        listPlaceFragment.queryType = queryType;
        listPlaceFragment.location = location;
        return listPlaceFragment;
    }

    public static ListPlaceFragment newInstance(String queryCity, boolean isQueryCity) {
        ListPlaceFragment listPlaceFragment = new ListPlaceFragment();
        listPlaceFragment.setTAG(Constans.TagFragment.LIST_PLACE_FRAGMENT);
        listPlaceFragment.queryCity = queryCity;
        listPlaceFragment.isQueryCity = isQueryCity;
        return listPlaceFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_item;
    }

    @Override
    protected void onFragmentCreateView(View view) {

    }

    @Override
    protected void onFragmentCreated(View view) {

    }

    @Override
    protected void innitView(View view) {
        setHasOptionsMenu(true);
        swipeRefreshList = view.findViewById(R.id.swipe_refresh_list);
        swipeRefreshList.setRefreshing(true);
        innitRcv(view);
    }

    private void innitRcv(View view) {
        rcvPlaces = view.findViewById(R.id.rcv_list);
        items = new ArrayList();
        coppysItems = new ArrayList();
        items.add("Loading");
        items.add("Loading");
        items.add("Loading");
        items.add("Loading");
        placeAdapter = new PlaceAdapter();
        placeAdapter.setItems(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvPlaces.setLayoutManager(linearLayoutManager);
        rcvPlaces.setAdapter(placeAdapter);
        rcvPlaces.setEnabled(false);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (nextPageToken != null && !isFilter) {
                    items.add(null);
                    placeAdapter.notifyItemInserted(items.size());
                    if (isQueryCity) {
                        presenterListPlace.showListPlace(queryCity, nextPageToken);
                        return;
                    }
                    presenterListPlace.showListPlace(location, nextPageToken, queryType);
                }
            }
        };
        rcvPlaces.addOnScrollListener(scrollListener);
        loadData();
    }

    private void loadData() {
        isFilter = false;
        presenterListPlace.setFirstLoad();
        if (isQueryCity) {
            presenterListPlace.showListPlace(queryCity, "");
            return;
        }
        presenterListPlace.showListPlace(location, "", queryType);
    }

    @Override
    protected void addEvents() {
        swipeRefreshList.setOnRefreshListener(this);
        placeAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
            @Override
            public void onClick(int position) {
                Intent i = PlaceActivity.setIntentDataDetail(getActivity(), ((Place)items.get(position)).getPlaceId());
                startActivity(i);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    public void onRefresh() {
        isFilter = false;
        presenterListPlace.setFirstLoad();
        if (isQueryCity) {
            presenterListPlace.showListPlace(queryCity, "");
            return;
        }
        presenterListPlace.showListPlace(location, "", queryType);
    }

    @Override
    public void onPause() {
        super.onPause();
        swipeRefreshList.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.action_filter, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                openFilterDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFilterDialog() {
        FilterDialog.newInstance(new FilterDialog.FilterCallback() {
            @Override
            public void onFilterPlace(boolean isOpenNow, int filterType) {
                isFilter = true;
                presenterListPlace.filterBy(coppysItems, isOpenNow, filterType);
            }
        }).show(getChildFragmentManager(), "DialogFilter");

    }

    @Override
    public void setPresenter(MVPListPlace.IPresenterListPlace iPresenterListTrip) {
        if (iPresenterListTrip != null) {
            this.presenterListPlace = iPresenterListTrip;
        }
    }

    @Override
    public void showUIListPlace(ResultPlace resultPlace, boolean isFirstLoad) {
        nextPageToken = resultPlace.getPageToken();
        if (isFirstLoad) {
            items.clear();
            coppysItems.clear();
            placeAdapter.notifyDataSetChanged();
            rcvPlaces.setEnabled(true);
            swipeRefreshList.setRefreshing(false);
        } else {
            items.remove(items.size() - 1);
            placeAdapter.notifyItemRemoved(items.size() - 1);
        }

        items.addAll(resultPlace.getListPlaceNearby());
        coppysItems.addAll(resultPlace.getListPlaceNearby());
        placeAdapter.notifyDataSetChanged();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (nextPageToken != null) {
                    scrollListener.resetState();
                }
            }
        }, 500);
    }

    @Override
    public void showListPlaceWhenFilter(List itemFilters) {
        items.clear();
        items.addAll(itemFilters);
        placeAdapter.notifyDataSetChanged();
        rcvPlaces.scrollToPosition(0);
    }

    @Override
    public void onLoadDataErr() {
    }
}
