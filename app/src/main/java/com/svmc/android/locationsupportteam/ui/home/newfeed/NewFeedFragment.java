package com.svmc.android.locationsupportteam.ui.home.newfeed;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.HomeScreenAdapter;
import com.svmc.android.locationsupportteam.entity.homescreen.HomeScreenModel;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.common.dialog.platetype.ChoosePlaceTypeDialog;
import com.svmc.android.locationsupportteam.ui.detailplace.PlaceActivity;
import com.svmc.android.locationsupportteam.ui.home.MainActivity;
import com.svmc.android.locationsupportteam.ui.listitem.ListItemActivity;
import com.svmc.android.locationsupportteam.ui.searchplace.SearchPlaceActivity;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 2/26/2019
 */

public class NewFeedFragment extends BaseFragment
        implements View.OnClickListener, MVPNewFeed.IViewNewFeed {

    private Toolbar toolbar;
    private AppBarLayout appBar;
    private LinearLayout llPlace;
    private LinearLayout llNamePlace;
    private LinearLayout llPlaceToolbar;
    private LinearLayout llNamePlaceToolbar;

    private LinearLayout llLoading;
    private RecyclerView rcvNewFeed;
    private HomeScreenAdapter homeScreenAdapter;
    private List items;

    private FloatingActionButton fabChooseCategory;

    private PresenterNewFeedImpl presenterNewFeed;

    public static NewFeedFragment newInstance() {
        NewFeedFragment fragment = new NewFeedFragment();
        fragment.setTAG(Constans.TagFragment.NEW_FEED_FRAGMENT);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_feed;
    }

    @Override
    protected void onFragmentCreateView(View view) {

    }

    @Override
    protected void onFragmentCreated(View view) {
        presenterNewFeed = new PresenterNewFeedImpl(this);
    }

    @Override
    public void innitView(View root) {
        llLoading = root.findViewById(R.id.ll_loading);
        fabChooseCategory = root.findViewById(R.id.fab_choose_category);
        innitToolbar(root);
        innitRecycleView(root);
    }

    private void innitToolbar(View view) {
        setHasOptionsMenu(true);
        appBar = view.findViewById(R.id.app_bar_new_feed);
        toolbar = view.findViewById(R.id.toolbar_new_feed);
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).setUpDrawerLayout(toolbar);
        ((BaseActivity) getActivity()).getSupportActionBar().setTitle("");
        llPlace = view.findViewById(R.id.ll_place);
        llNamePlace = view.findViewById(R.id.ll_name_place);
        llPlaceToolbar = view.findViewById(R.id.ll_place_toolbar);
        llNamePlaceToolbar = view.findViewById(R.id.ll_name_place_toolbar);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_search:
                Toast.makeText(getContext(), "search", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogChoosePlaceType() {
        ChoosePlaceTypeDialog.newInstance(new ChoosePlaceTypeDialog.ChoosePlaceTypeCallBack() {
            @Override
            public void onChoose(String listPlaceTypeIds) {
                presenterNewFeed.saveListPlaceTypeIds(listPlaceTypeIds);
            }
        }).show(getChildFragmentManager(), "ChoosePlaceTypeDialog");
    }

    private void innitRecycleView(View root) {
        showViewLoading();
        rcvNewFeed = root.findViewById(R.id.rcv_new_feed);
        homeScreenAdapter = new HomeScreenAdapter();
        items = new ArrayList();
        homeScreenAdapter.setItems(items);
        rcvNewFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvNewFeed.setAdapter(homeScreenAdapter);
        presenterNewFeed.openListItemOfNewFeed();
    }

    @Override
    public void addEvents() {
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                lisenerforAppBar(verticalOffset);
            }
        });

        // llSeeAll Click
        homeScreenAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
            @Override
            public void onClick(int position) {
                presenterNewFeed.openSeeAllUI((HomeScreenModel) items.get(position));
            }
        });

        homeScreenAdapter.setOnClickItemPlace(new HomeScreenAdapter.ClickItemPlace() {
            @Override
            public void onItemClick(String placeId, int pos) {
                openUIDetailPlace(placeId);
            }
        });

        llNamePlace.setOnClickListener(this);
        llNamePlaceToolbar.setOnClickListener(this);
        fabChooseCategory.setOnClickListener(this);

        rcvNewFeed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0)
                    fabChooseCategory.hide();
                else if (dy < 0)
                    fabChooseCategory.show();
            }
        });
    }

    private void openUIDetailPlace(String placeId) {
        Intent i = PlaceActivity.setIntentDataDetail(getActivity(), placeId);
        getActivity().startActivity(i);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void lisenerforAppBar(int verticalOffset) {

        float x = 0.05f;
        float distance = llPlace.getY() + llPlace.getHeight();

        //Log.d("tungts", Math.abs(verticalOffset) + " " + (distance + x * appBarLayout.getHeight()));

        if (distance + x * appBar.getHeight() <= Math.abs(verticalOffset)) {
            llPlaceToolbar.setVisibility(View.VISIBLE);
            llPlace.setVisibility(View.GONE);
        } else {
            llPlaceToolbar.setVisibility(View.GONE);
            llPlace.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_name_place:
                presenterNewFeed.openSearchPlaceUI();
                break;
            case R.id.ll_name_place_toolbar:
                presenterNewFeed.openSearchPlaceUI();
                break;
            case R.id.fab_choose_category:
                showDialogChoosePlaceType();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenterNewFeed.result(requestCode, resultCode, data);
    }

    @Override
    public void showViewLoading(){
        appBar.setVisibility(View.GONE);
        llLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideViewLoading(){
        appBar.setVisibility(View.VISIBLE);
        llLoading.setVisibility(View.GONE);
    }

    @Override
    public void showListItemOfNewFeed(ArrayList<HomeScreenModel> homeScreenModels) {
        items.clear();

        if (homeScreenModels != null && homeScreenModels.size() > 0) {
            for (HomeScreenModel model : homeScreenModels) {
                if (model.getPlaces().size() > 0 ) {
                    items.add(model);
                }
            }
        }

        homeScreenAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSearchPlaceUI() {
        Intent i = new Intent(getActivity(), SearchPlaceActivity.class);
        startActivityForResult(i, Constans.RequestCode.RC_SEARCH_PLACE);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void showDataToBtnPlace(String namePlace) {
        TextView tvNamePlace = llPlace.findViewById(R.id.tv_name_place);
        tvNamePlace.setText(namePlace);
        TextView tvNamePlaceToolbar = llPlaceToolbar.findViewById(R.id.tv_name_place_toolbar);
        tvNamePlaceToolbar.setText(namePlace);
    }

    /**
     * Show Infor Place, RecenttripPlan, TopPlace, NearPlace to Recycleview
     * @param homeScreenModels
     */
    @Override
    public void showListItemOfPlace(ArrayList<HomeScreenModel> homeScreenModels) {
        items.clear();
        homeScreenAdapter.notifyDataSetChanged();

        if (homeScreenModels != null && homeScreenModels.size() > 0) {
            for (HomeScreenModel model : homeScreenModels) {
                if (model.getPlaces().size() > 0 ) {
                    items.add(model);
                }
            }
        }

        homeScreenAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSeeAllItems(HomeScreenModel homeScreenModel) {
        Intent i = ListItemActivity.innitIntentFromHomeModel(
                getActivity(),
                homeScreenModel
        );
        getActivity().startActivity(i);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}
