package com.svmc.android.locationsupportteam.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.SubPlaceType;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.base.BaseMapFragment;
import com.svmc.android.locationsupportteam.ui.customviews.CustomBottomNavigationView;
import com.svmc.android.locationsupportteam.ui.home.maps.MapFragment;
import com.svmc.android.locationsupportteam.ui.home.maps.itemplace.ItemPlaceFragment;
import com.svmc.android.locationsupportteam.ui.home.maps.itemplace.MVPItemPlace;
import com.svmc.android.locationsupportteam.ui.home.maps.itemplace.PresenterItemPlaceImpl;
import com.svmc.android.locationsupportteam.ui.home.maps.nearbysearch.MVPNearBySearch;
import com.svmc.android.locationsupportteam.ui.home.maps.nearbysearch.NearBySearchFragment;
import com.svmc.android.locationsupportteam.ui.home.maps.nearbysearch.PresenterNearBySearchImpl;
import com.svmc.android.locationsupportteam.ui.home.newfeed.NewFeedFragment;
import com.svmc.android.locationsupportteam.utils.Constans;

/**
 * Created by TUNGTS on 2/24/2019
 */

public class MainFragment extends BaseMapFragment
        implements BottomNavigationView.OnNavigationItemSelectedListener,
                    FragmentManager.OnBackStackChangedListener,
                    NavigationView.OnNavigationItemSelectedListener,
                    CustomBottomNavigationView.MovementCallBack, MVPMainFragment.IViewMain, View.OnClickListener {

//    private FloatingActionButton fabActionAlert;
    private CustomBottomNavigationView navigationView;
    private boolean isHideNavigation = false;

    FragmentManager fragmentManager;
    private int currentIndexTabs = 0;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        fragment.setTAG(Constans.TagFragment.HOME_FRAGMENT);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onFragmentCreateView(View view) {
        fragmentManager = getChildFragmentManager();
        innitFragment();
    }

    public void innitFragment() {
        BaseFragment fragment = getFragment();
        pushFragment(R.id.content_tabs, fragment);
    }

    @Override
    protected void onFragmentCreated(View view) {
        innitView(view);
        addEvents();
    }

    @Override
    public void innitView(View root) {
//        fabActionAlert = root.findViewById(R.id.fab_action_room);
        innitBottomNavigation(root);
    }

    private void innitBottomNavigation(View view) {
        navigationView = view.findViewById(R.id.bottom_menu_home);
        navigationView.setItemIconTintList(null);
    }

    @Override
    public void addEvents() {
//        fabActionAlert.setOnClickListener(this);
        navigationView.setOnNavigationItemSelectedListener(this);
        navigationView.setOnMovementCallBack(this);
        fragmentManager.addOnBackStackChangedListener(this);
    }

    public void changeUIBottomNavigation(boolean isHide) {
        navigationView.clearAnimation();
        navigationView.animate().translationY(
                isHide ? navigationView.getHeight() : 0
        );
        isHideNavigation = false;
    }

    public BaseFragment getFragment() {
        switch (currentIndexTabs) {
            case 0:
                return NewFeedFragment.newInstance();
            case 1:
                return MapFragment.newInstance();
            default:
                return NewFeedFragment.newInstance();
        }
    }

    /***
     * When click search near by current location
     * @param locationPoint
     * @param placeType
     */
    public void pushNearBySearchFragment(LocationPoint locationPoint, SubPlaceType placeType) {
        NearBySearchFragment fragment = NearBySearchFragment.newInstance(locationPoint, placeType);
        MVPNearBySearch.IPresenterNearBySearch presenterNearBySearch = new PresenterNearBySearchImpl(fragment);
        pushFragment(R.id.content_tabs, fragment);
    }

    /**
     * ItemPlace Fragment when search
     * @param placeId
     */
    public void pushItemPlaceFragment(String placeId, String namePlace) {
        ItemPlaceFragment fragment = ItemPlaceFragment.newInstance(placeId, namePlace);
        MVPItemPlace.IPresenterItemPlace presenterItemPlace = new PresenterItemPlaceImpl(fragment);
        pushFragment(R.id.content_tabs, fragment);
    }

    public void popFragment(String backStackName) {
        boolean isBackStack = getChildFragmentManager().popBackStackImmediate(backStackName, 0);
        if (isBackStack && backStackName.equals(Constans.TagFragment.MAP_FRAGMENT)) {
            Fragment fragment = getCurrentFragment(R.id.content_tabs);
            ((MapFragment) fragment).onCloseUIListPlace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Fragment fragment = getCurrentFragment(R.id.content_tabs);
            if (fragment instanceof MapFragment) {
               boolean flag = ((MapFragment) fragment).onKeyDown(keyCode, event);
               if (flag) {
                   currentIndexTabs = 0;
                   innitFragment();
                   navigationView.getMenu().findItem(R.id.tabs_map).setChecked(false);
                   navigationView.getMenu().findItem(R.id.tabs_home).setChecked(true);
               }
               return false;
            } else if (fragment instanceof NearBySearchFragment || fragment instanceof ItemPlaceFragment) {
                popFragment(Constans.TagFragment.MAP_FRAGMENT);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.tabs_map:
                currentIndexTabs = 1;
                innitFragment();
                break;
            case R.id.tabs_home:
                currentIndexTabs = 0;
                innitFragment();
                break;
        }
        return true;
    }

    @Override
    public void onBackStackChanged() {}

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (fabActionAlert != null) {
//            fabActionAlert.hide();
//        }
        presenterMain.start();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onPermisstionLocationGranted() {
        enableLocationProvider();
    }

    @Override
    protected void onPermisstionLocationDenied() {
        Log.d(getTAG(), "onPermisstionLocationDenied");
    }

    @Override
    protected void onLocationProvideEnabled() {
        Log.d(getTAG(), "onLocationProvideEnabled - startTrackingLocation");
        startTrackingLocation();
    }

    @Override
    protected void onLocationProvideCancel() {
        Log.d(getTAG(), "onLocationProvideCancel");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment currentFragment = getCurrentFragment(R.id.content_tabs);
        if (currentFragment instanceof MapFragment) {
            ((MapFragment) currentFragment).onActivityResult(requestCode, resultCode, data);
        } else if (currentFragment instanceof NearBySearchFragment) {
            ((NearBySearchFragment) currentFragment).onActivityResult(requestCode, resultCode, data);
        }
    }

    /* [START WHEN TOUCH OR MOVE BOTTOM NAVIGATION] */
    @Override
    public void onMoveLeft(View view, float velocityX, float velocityY) {

    }

    @Override
    public void onMoveRight(View view, float velocityX, float velocityY) {

    }

    @Override
    public void onMoveTop(View view, float velocityX, float velocityY) {
        Fragment currentFragment = getCurrentFragment(R.id.content_tabs);
        if (currentFragment instanceof MapFragment) {
            ((MapFragment) currentFragment).userMoveOnBottomNavigation(velocityX, velocityY);
        }
    }

    @Override
    public void onMoveBottom(View view, float velocityX, float velocityY) {}
    /* [STOP WHEN TOUCH OR MOVE BOTTOM NAVIGATION] */

    private MVPMainFragment.IPresenterMain presenterMain;
    @Override
    public void setPresenter(MVPMainFragment.IPresenterMain iPresenterMain) {
        if (iPresenterMain != null) {
            presenterMain = iPresenterMain;
        }
    }

    /***
     * start tracking location
     */
    @Override
    public void ennaleLocationProvider() {
        checkPermisstionLocation();
    }

    @Override
    public void startTrackingLocation() {
//        fabActionAlert.show();
        ((BaseActivity) getActivity()).startTrackingLocation();
        showNotificationSOS();
    }

    /***
     * show NotficationSOS
     */
    @Override
    public void showNotificationSOS() {

    }

    @Override
    public void onFirstStartApp() {
    }

    @Override
    public void onInvalidToken() {
        signIn();
    }

    @Override
    public void onClick(View v) {
    }
}
