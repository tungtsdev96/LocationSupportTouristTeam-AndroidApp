package com.svmc.android.locationsupportteam.ui.home.maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.sweetdialog.SweetAlertDialog;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.maps.android.clustering.ClusterManager;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.ItemSearchMapAdapter;
import com.svmc.android.locationsupportteam.data.local.LocationDataLocal;
import com.svmc.android.locationsupportteam.data.local.entity.LocationCache;
import com.svmc.android.locationsupportteam.entity.ItemMarker;
import com.svmc.android.locationsupportteam.entity.event.EventMap;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlaceAutocomplete;
import com.svmc.android.locationsupportteam.entity.homescreen.RecentedSearchInMap;
import com.svmc.android.locationsupportteam.entity.SubPlaceType;
import com.svmc.android.locationsupportteam.listeners.SweetAlertDialogListener;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.common.dialog.DialogShareLocation;
import com.svmc.android.locationsupportteam.ui.home.MainFragment;
import com.svmc.android.locationsupportteam.ui.home.MainActivity;
import com.svmc.android.locationsupportteam.utils.CommonUtils;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.ErrCode;
import com.svmc.android.locationsupportteam.utils.LocationProviderUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 2/26/2019
 */

public class MapFragment extends BaseFragment
        implements OnMapReadyCallback, MVPMap.IViewMaps, View.OnClickListener, TextWatcher, TextView.OnEditorActionListener, GoogleMap.OnMarkerClickListener {

    private final float zoomScale = 15.5f;

    private MVPMap.IPresenterMaps presenterMaps;
    private Handler mHandlerView = new Handler();
    private Runnable runableInnitView;
    private GoogleMap mMap;

    //// app bar
    private AppBarLayout appBarMap;
    private CardView cardToolbar;
    private RelativeLayout rltToolbar;
    private ImageView btn_left;
    private EditText edtSearch;
    private ImageView btn_search_voice;
    private ImageView btn_clear;

    private FloatingActionButton fabCurrentLocation;

    // botttom sheet
    private BottomSheetBehavior bottomSheetBehavior;
    private FrameLayout layoutBottomSheet;
    private NestedScrollView nestedScrollViewDiscover;
    private LinearLayout llDiscoverRestaurant;
    private LinearLayout llDiscoverHotel;
    private LinearLayout llDiscoverAttraction;
    private LinearLayout llDiscoverMoreTypePlace;

    private RecyclerView rcvRecentedSearch;
    private ItemSearchMapAdapter itemSearchMapAdapter;
    private List itemSearch = new ArrayList();

    private ClusterManager<ItemMarker> mClusterManager;

    /***
     * Instance this fragment
     * @return
     */
    public static MapFragment newInstance() {
        MapFragment mapFragment = new MapFragment();
        mapFragment.setTAG(Constans.TagFragment.MAP_FRAGMENT);
        return mapFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_maps;
    }

    @Override
    protected void onFragmentCreateView(View view) {
    }

    @Override
    protected void onFragmentCreated(View view) {
        presenterMaps = new PresenterMapImpl(this);
        mLocationSourceCallback = new LocationSourceCallback();
    }

    @Override
    public void innitView(final View view) {
        innitAppBarLayout(view);
        runableInnitView = new Runnable() {
            @Override
            public void run() {
                innitMap();
                innitBottomSheet(view);
                innitFabCurentLocation(view);
            }
        };
        mHandlerView.postDelayed(runableInnitView, 350);
    }

    private void innitAppBarLayout(View view) {
        appBarMap = view.findViewById(R.id.app_bar_map);
        cardToolbar = view.findViewById(R.id.card_toolbar_map);
        rltToolbar = view.findViewById(R.id.rlt_controls_toolbar_map);
        btn_left = view.findViewById(R.id.img_left);
        edtSearch = view.findViewById(R.id.edt_search);
        btn_search_voice = view.findViewById(R.id.img_right);
//        btn_search_voice.setVisibility(View.GONE);
        btn_clear = view.findViewById(R.id.btn_clear);
    }

    private void innitMap() {
        LocationCache lastLocation = new LocationDataLocal().getLastLocationCache();
        if (lastLocation != null) {
            currentLocation = new Location("LastLocation");
            currentLocation.setLatitude(lastLocation.getLat());
            currentLocation.setLongitude(lastLocation.getLng());
            currentLocation.setAccuracy(lastLocation.getAccuracy());
        }

        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.content_map, supportMapFragment).commitAllowingStateLoss();
        supportMapFragment.getMapAsync(this);
    }

    /***
     * innit view for bottom sheet discover
     */
    private float prevOffset = 0.0f;
    private float prevY;

    private void innitBottomSheet(View view) {
        layoutBottomSheet = view.findViewById(R.id.bottom_sheet_toolbar_map);
        innitViewInBottomSheetDiscover();

        bottomSheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        prevY = layoutBottomSheet.getY();
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.d(getTAG(), "Bottom Sheet STATE_COLLAPSED");
//                        fabCurrentLocation.animate().scaleX(1).scaleY(1).setDuration(100).start();
                        presenterMaps.changeUIWhenBottomSheetCollapse();
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
//                        fabCurrentLocation.animate().scaleX(0).scaleY(0).setDuration(100).start();
                        Log.d(getTAG(), "Bottom Sheet STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        presenterMaps.changeUIWhenBottomSheetExpland();
                        prevY = layoutBottomSheet.getY();
                        Log.d(getTAG(), "Bottom Sheet STATE_EXPANDED ");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        presenterMaps.changeUIWhenBottomSheetCollapse();
                        Log.d(getTAG(), "Bottom Sheet STATE_HIDDEN");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.d(getTAG(), "Bottom Sheet STATE_SETTLING");
                        break;

                }
            }

            @Override
            public void onSlide(@NonNull View view, float offset) {
//                Log.d(getTAG(), "Bottom Sheet slide " + offset + " y : " + view.getY() + " ");
                // view.getY = startY - view.getHetight * offset;

                // change fab following offside
                if (offset >= 0) {
                    if (offset <= 0.5)
                        fabCurrentLocation
                                .animate()
                                .alpha(1 - 2 * offset)
                                .scaleX(1 - 2 * offset)
                                .scaleY(1 - 2 * offset)
                                .setDuration(0)
                                .start();
                    else fabCurrentLocation
                            .animate()
                            .alpha(0)
                            .scaleX(0)
                            .scaleY(0)
                            .setDuration(0)
                            .start();
                } else if (offset < 0) {
                    if (prevOffset > 0)
                        fabCurrentLocation
                                .animate()
                                .alpha(1)
                                .scaleX(1)
                                .scaleY(1)
                                .setDuration(0)
                                .start();

                    float transitionOffset = (bottomSheetBehavior.getPeekHeight() - CommonUtils.dpToPx(56)) * Math.abs(offset);
//                    Log.d(getTAG(), "Bottom Sheet  " + fabCurrentLocation.getY() +  " " + bottomSheetBehavior.getPeekHeight() + " " + transitionOffset + " " + prevOffset + " " + offset);
                    fabCurrentLocation.animate().translationY(transitionOffset).setDuration(0).start();
                }

                if (view.getY() <= 1) {
                    presenterMaps.changeUIWhenBottomSheetExpland();
                } else {
                    presenterMaps.changeUIWhenBottomSheetCollapse();
                }

                prevY = view.getY();
                prevOffset = offset;
            }
        });
    }

    private void innitViewInBottomSheetDiscover() {
        nestedScrollViewDiscover = layoutBottomSheet.findViewById(R.id.scroll_view_discover);
        llDiscoverRestaurant = layoutBottomSheet.findViewById(R.id.ll_discover_restaurant);
        llDiscoverHotel = layoutBottomSheet.findViewById(R.id.ll_discover_hotel);
        llDiscoverAttraction = layoutBottomSheet.findViewById(R.id.ll_discover_attraction);
        llDiscoverMoreTypePlace = layoutBottomSheet.findViewById(R.id.discover_more_type_place);

        // bottom sheet discover
        llDiscoverRestaurant.setOnClickListener(this);
        llDiscoverHotel.setOnClickListener(this);
        llDiscoverAttraction.setOnClickListener(this);
        llDiscoverMoreTypePlace.setOnClickListener(this);

        innitRecycleViewRecentedSearch();
    }

    private void innitRecycleViewRecentedSearch() {
        rcvRecentedSearch = layoutBottomSheet.findViewById(R.id.rcv_recented_search);
        ViewCompat.setNestedScrollingEnabled(rcvRecentedSearch, false);
        itemSearchMapAdapter = new ItemSearchMapAdapter();
        itemSearchMapAdapter.setItems(itemSearch);
        rcvRecentedSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvRecentedSearch.setAdapter(itemSearchMapAdapter);

        itemSearchMapAdapter.setOnClickItemCallBack(new ItemSearchMapAdapter.ClickItemCallBack() {
            @Override
            public void onClick(String placeId, Object place) {
                hideKeyboard();
                presenterMaps.clickItemInListResultSearch(placeId, place);
            }
        });

        presenterMaps.openUIRecentedSearch();
    }

    /***
     * innit view for floatingbutton current Location
     * @param view
     */
    private void innitFabCurentLocation(View view) {
        fabCurrentLocation = view.findViewById(R.id.fab_current_location);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(
                (int) getContext().getResources().getDimension(R.dimen.size_16),
                (int) getContext().getResources().getDimension(R.dimen.size_16),
                (int) getContext().getResources().getDimension(R.dimen.size_16),
                bottomSheetBehavior.getPeekHeight() + (int) getContext().getResources().getDimension(R.dimen.size_16)
        );
        params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        fabCurrentLocation.setLayoutParams(params);
        fabCurrentLocation.show();
        fabCurrentLocation.setOnClickListener(this);
    }

    @Override
    public void addEvents() {
        // app bar
        btn_left.setOnClickListener(this);
        btn_search_voice.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        edtSearch.addTextChangedListener(this);
        edtSearch.setOnClickListener(this);
        edtSearch.setOnEditorActionListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.mMap = googleMap;
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                presenterMaps.changeUIOnMap();
            }
        });

        mMap.setOnMarkerClickListener(this);
        mMap.setLocationSource(mLocationSourceCallback);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                showDialogShareLocation(latLng);
            }
        });

        mClusterManager = new ClusterManager<>(getContext(), mMap);

//        TextDrawable drawable = TextDrawable.builder()
//                .beginConfig()
//                    .width(60)  // width in px
//                    .height(60) // height in px
//                    .withBorder(4)
//                    .textColor(Color.WHITE)
//                .endConfig()
//                .buildRound("1", Color.RED);
//
//        mMap.addMarker(new MarkerOptions()
//                .position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
//                .icon(getMarkerIconFromDrawable(drawable)));

        presenterMaps.setUpPermission();
    }

//    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
//        Canvas canvas = new Canvas();
//        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        canvas.setBitmap(bitmap);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//        drawable.draw(canvas);
//        return BitmapDescriptorFactory.fromBitmap(bitmap);
//    }

    private void showDialogShareLocation(final LatLng latLng) {
        DialogShareLocation dialogShareLocation = DialogShareLocation.newInstance(new DialogShareLocation.DialogShareLocationCallback() {
            @Override
            public void onShare(String fileImage, boolean isCurrentLocation, String message) {
                if (isCurrentLocation) {
                    presenterMaps.shareLocation(fileImage, new LocationPoint(currentLocation.getLatitude(), currentLocation.getLongitude()), message);
                } else {
                    presenterMaps.shareLocation(fileImage, new LocationPoint(latLng.latitude, latLng.longitude), message);
                }
            }
        });
        dialogShareLocation.show(getChildFragmentManager(), "DialogShareLocation");
    }

    @Override
    public void sharePointSuccess() {
        Toast.makeText(getContext(), "Chia sẻ thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sharePointErr() {
        Toast.makeText(getContext(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        for (int i = 0; i < markers.size(); i++) {
            Marker m = markers.get(i);
            if (m.equals(marker)) {
                EventBus.getDefault().post(new EventMap.PostClickMaker(i));
            }
        }
        return true;
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
        registerEventBus();
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandlerView.removeCallbacks(runableInnitView);
        mLocationSourceCallback.onPause();
        if (locationUpdateReceiver != null) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(locationUpdateReceiver);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mLocationSourceCallback.onResume();
        presenterMaps.setUpPermission();
    }

    @Override
    public void onStop() {
        super.onStop();
        unRegisterEventBus();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenterMaps.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode != Constans.RequestCode.RC_LOCATION_PERMISSION) {
            return;
        }

        if (isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            presenterMaps.setUpCurrentLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            onPermisstionDenied();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_left:
                hideKeyboard();
                presenterMaps.changeUIWhenClickBtnLeft();
                break;
            case R.id.img_right:
                Toast.makeText(getContext(), "Speed To text", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_clear:
                edtSearch.setText("");
                break;
            case R.id.edt_search:
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) return;
                setStateBottomSheet(BottomSheetBehavior.STATE_EXPANDED);
                presenterMaps.changeUIWhenBottomSheetExpland();
                break;
            case R.id.fab_current_location:
                moveCameraToCurrentLocation(currentLocation);
                break;
            case R.id.ll_discover_restaurant:
                openUINearBySearch("{\n" +
                        "        \"id\" : 0,\n" +
                        "        \"cal_req\" : 1,\n" +
                        "        \"key\" : \"hotel\",\n" +
                        "        \"title\" : \"Khách sạn\"\n" +
                        "      }");
                break;
            case R.id.ll_discover_hotel:
                openUINearBySearch("{\n" +
                        "        \"id\" : 1,\n" +
                        "        \"cal_req\" : 1,\n" +
                        "        \"key\" : \"restaurant\",\n" +
                        "        \"title\" : \"Nhà hàng\"\n" +
                        "      }");
                break;
            case R.id.ll_discover_attraction:
                openUINearBySearch("{\n" +
                        "        \"id\" : 2,\n" +
                        "        \"cal_req\" : 1,\n" +
                        "        \"key\" : \"attractions\",\n" +
                        "        \"title\" : \"Địa danh\"\n" +
                        "      }");
                break;
            case R.id.discover_more_type_place:
                Intent i = new Intent(getActivity(), ChoosePlaceTypeActivity.class);
                startActivityForResult(i, Constans.RequestCode.RC_CHOOSE_PLACE_TYPE);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
    }

    private void openUINearBySearch(String json) {
        SubPlaceType placeType = new Gson().fromJson(json, SubPlaceType.class);
        presenterMaps.openUINearBySearchPlace(new LocationPoint(currentLocation.getLatitude(), currentLocation.getLongitude()), placeType);
    }

    //////////////////// Edittext search ///////////////
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            String query = edtSearch.getText().toString();
            if (query != null && query.length() >= 3) {
                String queryLocation = currentLocation.getLatitude() + "," + currentLocation.getLongitude();
                if (queryLocation != null) {
                    itemSearch.clear();
                    itemSearch.add(null);
                    itemSearchMapAdapter.notifyDataSetChanged();
                    presenterMaps.placeAutocomplete(query, queryLocation);
                }
            }
            btn_clear.setVisibility(View.VISIBLE);
        } else {
            // show history
            presenterMaps.openUIRecentedSearch();
            btn_clear.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            hideKeyboard();
            // show result search
            String query = edtSearch.getText().toString();
            if (query != null && query.length() > 0) {
//                presenterMaps.searchPlace(query);
            }
            return true;
        }
        return false;
    }

    /***
     * Click hardware button
     * @param keyCode
     * @param event
     * @return true -> back fragment
     *         false -> clollapse bottom sheet
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return presenterMaps.onClickBackHardware();
        }
        return true;
    }

    /***
     * When bottom navigation is hidden and user move on bottom navigation
     * @param velocityX
     * @param velocityY
     */
    public void userMoveOnBottomNavigation(float velocityX, float velocityY) {
        Log.d(getTAG(), velocityX + " " + velocityY);
        presenterMaps.changeUIWhenBottomSheetCollapse();
        setStateBottomSheet(BottomSheetBehavior.STATE_COLLAPSED);
    }

    /***
     * Check And Turn On Permisstion
     */
    @Override
    public void checkAndTurnOnPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            requestPermission(this, Constans.RequestCode.RC_LOCATION_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            presenterMaps.setUpCurrentLocation();
        }
    }

    @Override
    public void onPermisstionDenied() {
        Toast.makeText(getContext(), "Permisson Denied", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkAndTurnOnCurrentLocation() {
        LocationProviderUtils.checkAndTurnOnCurrentLocation(getActivity(), new LocationProviderUtils.LocationProviderCallback() {
            @Override
            public void onEnable() {
                onSettingLocationDone();
            }
        });
    }

    /**
     * Register tracking location
     */
    private BroadcastReceiver locationUpdateReceiver;

    @Override
    public void onSettingLocationDone() {
        if (currentLocation != null) {
            mLocationSourceCallback.setLocationSource(currentLocation);
            moveCameraToCurrentLocation(currentLocation);
        }

        ((BaseActivity) getActivity()).startTrackingLocation();
        locationUpdateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Location newLocation = intent.getParcelableExtra("location");
                currentLocation = newLocation;
                showCurrentLocation(newLocation);

            }
        };

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(
                locationUpdateReceiver,
                new IntentFilter("LocationUpdated"));
    }

    @Override
    public void onSetUpCurrentLocationCaneled() {
        Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
    }

    private Location currentLocation;
    private LocationSourceCallback mLocationSourceCallback;
    private boolean isfocus;

    @Override
    public void showCurrentLocation(final Location location) {

        Fragment fragment;
        try {
            fragment = getCurrentFragment(R.id.content_tabs);
        } catch (Exception e) {
            return;
        }

//        if (fragment instanceof NearBySearchFragment) {
//            return;
//        }

        if (location != null) {
            currentLocation = location;
            mLocationSourceCallback.setLocationSource(location);
            if (!isfocus) {
                moveCameraToCurrentLocation(location);
                isfocus = true;
            }
        }
    }

    @Override
    public void moveCameraToCurrentLocation(Location location) {
        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoomScale);
            mMap.moveCamera(cameraUpdate);
        }
    }

    /***
     * when click Button left of toolbar
     * @param isStateBack
     */
    @Override
    public void onClickBtnLeft(boolean isStateBack) {
        if (!isStateBack) {
            ((MainActivity) getActivity()).openDrawer();
            return;
        }

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        presenterMaps.changeUIWhenBottomSheetCollapse();
    }

    @Override
    public void changeUIAppBar(boolean isHide) {
        appBarMap.clearAnimation();
        appBarMap.animate().translationY(
                isHide ? -appBarMap.getHeight() : 0
        );
    }

    @Override
    public void changeUIBottomNavigation(boolean isHide) {
        ((MainFragment) getParentFragment()).changeUIBottomNavigation(isHide);
    }

    @Override
    public void changeFabCurrentLocation(boolean isHide) {
        if (isHide) fabCurrentLocation.hide();
        else fabCurrentLocation.show();
    }

    @Override
    public void changeUIBottomSheetWhenTouchMap(boolean isHide) {
        if (isHide) {
            bottomSheetBehavior.setHideable(true);
        }
        layoutBottomSheet.clearAnimation();
        layoutBottomSheet.animate().translationY(
                isHide ? bottomSheetBehavior.getPeekHeight() : 0
        );
        setStateBottomSheet(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void changeIconLeftOnActionBar(boolean isStateBack) {
        btn_left.setImageResource(
                isStateBack ? R.drawable.ic_back_black : R.drawable.ic_drawer
        );
    }

    @Override
    public void changeUIAppBarWhenBottomSheetExplaned() {
        appBarMap.setBackgroundColor(Color.WHITE);
        cardToolbar.setCardElevation(0);
        cardToolbar.setRadius(0);
        rltToolbar.setBackgroundResource(R.drawable.border_toolbar_map);
        edtSearch.setFocusable(true);
        edtSearch.setFocusableInTouchMode(true);
        edtSearch.requestFocus();
//        ((LockableBottomSheetBehavior) bottomSheetBehavior).setLocked(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showKeyboard(edtSearch);
            }
        }, 150);
    }

    @Override
    public void changeUIAppBarWhenBottomSheetCollapse() {
        hideKeyboard();
//        ((LockableBottomSheetBehavior) bottomSheetBehavior).setLocked(false);
        nestedScrollViewDiscover.scrollTo(0, 0);
        appBarMap.setBackgroundColor(Color.TRANSPARENT);
        cardToolbar.setCardElevation(getContext().getResources().getDimension(R.dimen.size_4));
        cardToolbar.setRadius(getContext().getResources().getDimension(R.dimen.size_6));
        rltToolbar.setBackgroundResource(0);
//        edtSearch.setText("");
        edtSearch.setFocusable(false);
    }

    @Override
    public void setStateBottomSheet(int state) {
        bottomSheetBehavior.setState(state);
    }

    @Override
    public void bindUIRecectedSearchInMap(ArrayList<RecentedSearchInMap> items) {
        itemSearch.clear();
        itemSearchMapAdapter.notifyDataSetChanged();
        if (items != null && items.size() > 0) {
            itemSearch.add("Tìm kiếm gần đây");
            itemSearch.addAll(items);
            itemSearchMapAdapter.notifyDataSetChanged();
            return;
        }
    }

    @Override
    public void showUINearBySearch(LocationPoint point, SubPlaceType placeType) {
        Toast.makeText(getContext(), "Tìm kiếm " + placeType.getTitle(), Toast.LENGTH_SHORT).show();
        ((MainFragment) getParentFragment()).pushNearBySearchFragment(point, placeType);
    }

    @Override
    public void onCloseUIListPlace() {
        mMap.clear();
        markers.clear();
        mClusterManager.clearItems();
        moveCameraToCurrentLocation(currentLocation);
        ((PresenterMapImpl) presenterMaps).setNearBySearch(false);
        ((PresenterMapImpl) presenterMaps).setClickItemOnResultSearch(false);
        presenterMaps.changeUIOnMap();
    }

    @Override
    public void bindUIResultSearch(ResultPlace resultPlace) {
        itemSearch.clear();
        itemSearch.addAll(resultPlace.getCandidates());
        itemSearchMapAdapter.notifyDataSetChanged();
    }

    @Override
    public void bindUIResultAutoComplete(ResultPlaceAutocomplete resultPlaceAutocomplete) {
        itemSearch.clear();
        itemSearch.addAll(resultPlaceAutocomplete.getPredictions());
        itemSearchMapAdapter.notifyDataSetChanged();
    }

    @Override
    public void onErr(String errcode) {
        switch (errcode) {
            case ErrCode.GGMapCode.ZERO_RESULTS:
                if (itemSearch.size() == 0)
                    showSweetAlert("", "Không có kết quả thỏa mãn", "Ok", SweetAlertDialog.NORMAL_TYPE, new SweetAlertDialogListener() {
                        @Override
                        public void onConfirmClicked(SweetAlertDialog dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked(SweetAlertDialog dialog) {

                        }
                    });
                break;
            case ErrCode.GGMapCode.UNKNOWN_ERROR:
//                showErrMessage("Server Lỗi");
                break;
        }
    }

    @Override
    public void openUIPlace(String placeId, String namePlace) {
        ((MainFragment) getParentFragment()).pushItemPlaceFragment(placeId, namePlace);
    }

    /***
     * Receive list location from ItemPlacePLace
     * @param postLocation
     */
    @Subscribe
    public void onEvent(EventMap.PostLocation postLocation) {
        makeListMakers(postLocation.locationPoints, postLocation.namePlaces);
    }

    /***
     * receive when request focus to location point from
     * ItemPlaceFragmenty or NearbyFragment
     * @param focusToLocation
     */
    @Subscribe
    public void onEvent(EventMap.PostFocusToLocation focusToLocation) {
        markers.get(focusToLocation.pos).showInfoWindow();
        moveCameraToMaker(focusToLocation.locationPoint);
    }

    private List<Marker> markers = new ArrayList<>();

    /***
     * Create list maker depend on points
     */
    public void makeListMakers(ArrayList<LocationPoint> points, ArrayList<String> titles) {
        mMap.clear();
        markers.clear();
        mClusterManager.clearItems();

        for (int i = 0; i < points.size(); i++) {
            LocationPoint point = points.get(i);
            MarkerOptions markerOptions =
                    new MarkerOptions()
                            .position(new LatLng(point.getLat(), point.getLng()))
                            .title(titles.get(i));
            Marker marker = mMap.addMarker(markerOptions);
            markers.add(marker);
        }

        markers.get(0).showInfoWindow();
        moveCameraToMaker(points.get(0));
    }

    /***
     * move camera to maker
     * @param point
     */
    @Override
    public void moveCameraToMaker(LocationPoint point) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(point.getLat(), point.getLng()), zoomScale));
    }

    /***
     * move camera to maker
     * @param point
     */
    @Override
    public void moveCameraToMaker(LocationPoint point, float zoomScale) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(point.getLat(), point.getLng()), zoomScale));
    }

    /**
     * A {@link LocationSource} which reports a new location whenever a user long presses the map
     * at
     * the point at which a user long pressed the map.
     */
    private class LocationSourceCallback implements LocationSource {

        private OnLocationChangedListener mListener;

        /**
         * Flag to keep track of the activity's lifecycle. This is not strictly necessary in this
         * case because onMapLongPress events don't occur while the activity containing the map is
         * paused but is included to demonstrate best practices (e.g., if a background service were
         * to be used).
         */
        private boolean mPaused;

        @Override
        public void activate(OnLocationChangedListener listener) {
            mListener = listener;
        }

        @Override
        public void deactivate() {
            mListener = null;
        }

        public void setLocationSource(Location location) {
            if (mListener != null && !mPaused) {
                mListener.onLocationChanged(location);
            }
        }

        public void onPause() {
            mPaused = true;
        }

        public void onResume() {
            mPaused = false;
        }
    }

}
