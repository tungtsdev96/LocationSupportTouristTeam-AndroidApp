package com.svmc.android.locationsupportteam.ui.home.maps.nearbysearch;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.sweetdialog.SweetAlertDialog;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.ViewPagerAdapter;
import com.svmc.android.locationsupportteam.entity.event.EventMap;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.SubPlaceType;
import com.svmc.android.locationsupportteam.listeners.SweetAlertDialogListener;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.customviews.WrapContentViewPager;
import com.svmc.android.locationsupportteam.ui.home.MainFragment;
import com.svmc.android.locationsupportteam.ui.home.maps.ChooseFillterActivity;
import com.svmc.android.locationsupportteam.ui.home.maps.nearbysearch.item.MVPItem;
import com.svmc.android.locationsupportteam.ui.home.maps.nearbysearch.item.NearBySearchItemFragment;
import com.svmc.android.locationsupportteam.ui.home.maps.nearbysearch.item.PresenterItemImpl;
import com.svmc.android.locationsupportteam.ui.listitem.ListItemActivity;
import com.svmc.android.locationsupportteam.utils.Constans;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by TUNGTS on 4/17/2019
 */

public class NearBySearchFragment extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener, MVPNearBySearch.IViewNearBySearch {

    MVPNearBySearch.IPresenterNearBySearch presenterNearBySearch;

    private LocationPoint point;
    private SubPlaceType placeType;
    private List<Place> places;

    private ImageView imgBack;
    private EditText edtSearch;
    private ImageView imgList;
    private ImageView imgFilter;
    private ProgressBar progressSearch;

    private WrapContentViewPager viewPagerPlace;
    private ViewPagerAdapter viewPagerAdapter;

    public static NearBySearchFragment newInstance(LocationPoint point, SubPlaceType placeType) {
        NearBySearchFragment nearBySearchFragment = new NearBySearchFragment();
        nearBySearchFragment.setTAG(Constans.TagFragment.NEAR_BY_SEARCH_FRAGMENT);
        nearBySearchFragment.point = point;
        nearBySearchFragment.placeType = placeType;
        return nearBySearchFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_near_by_search;
    }

    @Override
    protected void onFragmentCreateView(View view) {

    }

    @Override
    protected void onFragmentCreated(View view) {

    }

    @Override
    protected void innitView(View view) {
        innitToolbar(view);
        innitViewPager(view);
    }

    private void innitToolbar(View view) {
        imgBack = view.findViewById(R.id.img_left);
        imgBack.setImageResource(R.drawable.ic_back_black);
        edtSearch = view.findViewById(R.id.edt_search);
        edtSearch.setText(placeType.getTitle());
        progressSearch = view.findViewById(R.id.progress_bar_search);
        progressSearch.setVisibility(View.VISIBLE);

        imgList = view.findViewById(R.id.img_right);
        imgList.setImageResource(R.drawable.ic_list_black);
        imgList.setVisibility(View.VISIBLE);

        imgFilter = view.findViewById(R.id.btn_clear);
        imgFilter.setImageResource(R.drawable.ic_filter_black);
        imgFilter.setVisibility(View.GONE);
    }

    private void innitViewPager(View view) {
        viewPagerPlace = view.findViewById(R.id.view_pager_places);
        viewPagerPlace.setClipToPadding(false);
        viewPagerPlace.setPageMargin(16);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.setPageWidth(0.93f);
        viewPagerPlace.setAdapter(viewPagerAdapter);
        viewPagerPlace.addOnPageChangeListener(this);
        viewPagerPlace.setOffscreenPageLimit(3);
    }

    @Override
    protected void addEvents() {
        imgBack.setOnClickListener(this);
        imgList.setOnClickListener(this);
        imgFilter.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == Constans.RequestCode.RC_FILTER_PLACE) {
            String rankBy = data.getStringExtra(Constans.KeyBundle.FILTER_PLACE_RANKBY);
            int currentPrice = data.getIntExtra(Constans.KeyBundle.FILTER_PLACE_PRICE, 0);
            boolean isOpenNow = data.getBooleanExtra(Constans.KeyBundle.FILTER_PLACE_OPEN_NOW, false);
            makeReSearch(rankBy, currentPrice, isOpenNow);
        }
    }

    private void makeReSearch(String rankBy, int currentPrice, boolean isOpenNow) {
        int minPrice = 0;
        int maxPrice = 4;
        if (currentPrice == 1) {
            minPrice = 0; maxPrice = 1;
        } else if (currentPrice == 2) {
            minPrice = 2; maxPrice = 2;
        } else if (currentPrice == 3) {
            minPrice = 3; maxPrice = 4;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_left:
                ((MainFragment) getParentFragment()).popFragment(Constans.TagFragment.MAP_FRAGMENT);
                break;
            case R.id.btn_clear:
                Intent i = new Intent(getActivity(), ChooseFillterActivity.class);
                startActivityForResult(i, Constans.RequestCode.RC_FILTER_PLACE);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.img_right:
                Intent intent = ListItemActivity.innitIntentFromMap(getActivity(), point.toString(), placeType);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        EventBus.getDefault().post(new EventMap.PostFocusToLocation(i, places.get(i).getGeometry().getLocation()));
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onStart() {
        super.onStart();
        registerEventBus();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenterNearBySearch.onStop();
        unRegisterEventBus();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (places == null) {
            presenterNearBySearch.showListPlaceNearBySearch(point, placeType);
        }
    }

    @Override
    public void setPresenter(MVPNearBySearch.IPresenterNearBySearch iPresenterNearBySearch) {
        if (iPresenterNearBySearch != null) {
            this.presenterNearBySearch = iPresenterNearBySearch;
        }
    }

    @Override
    public void showListPlaceNearBySearch(List<Place> places) {
        if (places == null || places.size() == 0) {
            showSweetAlert(
                    "Thông báo",
                    "Không có kết quả thỏa mãn",
                    "Trở lại",
                    SweetAlertDialog.WARNING_TYPE,
                    new SweetAlertDialogListener() {
                        @Override
                        public void onConfirmClicked(SweetAlertDialog dialog) {
                            dialog.dismiss();
                            ((MainFragment) getParentFragment()).popFragment(Constans.TagFragment.MAP_FRAGMENT);
                        }

                        @Override
                        public void onCancelClicked(SweetAlertDialog dialog) {

                        }
                    });

            ((MainFragment) getParentFragment()).popFragment(Constans.TagFragment.MAP_FRAGMENT);
            return;
        }

        this.places = places;
        progressSearch.setVisibility(View.GONE);
//        imgFilter.setVisibility(View.VISIBLE);
        imgList.setVisibility(View.VISIBLE);

        viewPagerAdapter.clear();
        for (Place place: places) {
            NearBySearchItemFragment fragment = NearBySearchItemFragment.newInstance(place);
            MVPItem.IPresenterItem presenterItem = new PresenterItemImpl(fragment);
            viewPagerAdapter.addFragment(fragment, place.getName());
        }
        viewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onErr() {

    }

    /***
     * When user click marker on MapFragment
     * @param postClickMaker
     */
    @Subscribe
    public void onEvent(EventMap.PostClickMaker postClickMaker) {
        viewPagerPlace.setCurrentItem(postClickMaker.pos, true);
    }
}
