package com.svmc.android.locationsupportteam.ui.roomlocation.detailroom;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.UserLocation;
import com.svmc.android.locationsupportteam.entity.event.EventPostFocusCameraMapTo;
import com.svmc.android.locationsupportteam.entity.event.EventPostListUserLocation;
import com.svmc.android.locationsupportteam.entity.event.EventPostUpdateStatusUser;
import com.svmc.android.locationsupportteam.entity.event.EventPostUserOnOffline;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.listeners.LocationSourceCallback;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.base.BaseMapFragment;
import com.svmc.android.locationsupportteam.ui.common.dialog.BottomSheetAlertRoomDialog;
import com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.members.MemberActivity;
import com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.navimember.MVPNaviRoomLocation;
import com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.navimember.NaviRoomLocationMemberFragment;
import com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.navimember.PresenterNaviRoomLocationImpl;
import com.svmc.android.locationsupportteam.ui.showdirection.ShowDirectionActivity;
import com.svmc.android.locationsupportteam.utils.CommonUtils;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.TimeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TUNGTS on 4/21/2019
 */

public class DetailRoomLocationFragment extends BaseMapFragment implements MVPDetailRoomLocation.IViewDetailRoomLocation, OnMapReadyCallback, View.OnClickListener {

    public static final int VIEW_STATE_NORMAL = 0;
    public static final int VIEW_STATE_SHARE = 1;
    public static final int VIEW_STATE_ALERT = 2;

    /**
     * declare for share location or alert
     * 0 - normal
     * 1 - Share
     * 2 - alert
     */
    private String userSenderId;
    private int stateView = 0;
    private LocationPoint pointFocusFirst;
    private Marker markerFocusFirst;
    private MarkerOptions optionsPlace;
    private String urlImage;

    /**
     * declare for view
     */
    private MVPDetailRoomLocation.IPresenterDetailRoomLocation presenterDetailRoomLocation;
    private RoomLocation roomLocation;
    private Location currentLocation;

    private DrawerLayout drawerLayout;
    private AppBarLayout appBarLayout;
    private ImageView imgLeft;
    private TextView tvTitle;
    private ImageView imgRight;
    private ImageView imgCurrentLocation;

    private FloatingActionButton fabPushAlert;
    private FloatingActionButton fabShowDirection;

    private GoogleMap mMap;

    public static DetailRoomLocationFragment newInstance(RoomLocation roomLocation) {
        DetailRoomLocationFragment detailRoomLocationFragment = new DetailRoomLocationFragment();
        detailRoomLocationFragment.setTAG(Constans.TagFragment.DETAIL_ROOM_LOCATION_FRAGMENT);
        detailRoomLocationFragment.roomLocation = roomLocation;
        return detailRoomLocationFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b == null) {
            stateView = VIEW_STATE_NORMAL;
            return;
        }

        userSenderId = b.getString(Constans.KeyBundle.ROOM_USER_SENDER);
        String jsonPointShare = b.getString(Constans.KeyBundle.ROOM_LOCATION_SHARE_POINT);
        String jsonPointAlert = b.getString(Constans.KeyBundle.ROOM_ALERT_DATA);

        urlImage = b.getString(Constans.KeyBundle.ROOM_NOTIFY_URL_IMAGE);

        if (jsonPointAlert != null) {
            stateView = VIEW_STATE_ALERT;
            pointFocusFirst = new Gson().fromJson(jsonPointAlert, LocationPoint.class);
            return;
        }

        if (jsonPointShare != null) {
            stateView = VIEW_STATE_SHARE;
            pointFocusFirst = new Gson().fromJson(jsonPointShare, LocationPoint.class);
            return;
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_room_location;
    }

    @Override
    protected void onFragmentCreateView(View view) {
        ((BaseActivity) getActivity()).setStatusBarTranslucent();
        mLocationSourceCallback = new LocationSourceCallback();
    }

    @Override
    protected void onFragmentCreated(View view) {

    }

    @Override
    protected void innitView(View view) {
        innitAppBar(view);
        innitFragmentNaviMember();
        innitMap();
    }

    private void innitAppBar(View view) {
        appBarLayout = view.findViewById(R.id.app_bar_map);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        imgLeft = view.findViewById(R.id.img_left);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(
                roomLocation.getNameRoom() != null ? roomLocation.getNameRoom() : "Nhóm"
        );
        imgCurrentLocation = view.findViewById(R.id.img_current_location);
        imgRight = view.findViewById(R.id.img_right);
        fabPushAlert = view.findViewById(R.id.fab_push_alert);
        fabShowDirection = view.findViewById(R.id.fab_show_direction);
    }

    private void innitFragmentNaviMember() {
        NaviRoomLocationMemberFragment naviRoomLocationMemberFragment = NaviRoomLocationMemberFragment.newInstance(roomLocation);
        MVPNaviRoomLocation.IPresenterNaviRoomLocation presenterNaviRoomLocation = new PresenterNaviRoomLocationImpl(naviRoomLocationMemberFragment);
        pushFragment(R.id.content_navi, naviRoomLocationMemberFragment);
        drawerLayout.openDrawer(Gravity.START);
    }

    private void innitMap() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;

        this.mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                presenterDetailRoomLocation.onTouchMap();
            }
        });

        this.mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                currentLatlngFocus = marker.getPosition();
                showFabDirection();
                marker.showInfoWindow();
                return false;
            }
        });

        mMap.setLocationSource(mLocationSourceCallback);
        checkPermisstionLocation();

        if (stateView != VIEW_STATE_NORMAL) {
            LatLng latLng = new LatLng(pointFocusFirst.getLat(), pointFocusFirst.getLng());
            MarkerOptions markerOptions =
                    new MarkerOptions()
                            .position(latLng);

            if (stateView == VIEW_STATE_ALERT) {
                if (urlImage != null) {

                }
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_warning));
            } else if (stateView == VIEW_STATE_SHARE) {
                if (urlImage != null) {

                }
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(CommonUtils.getMarkerColor(0)));
                markerOptions.title("Địa điểm được chia sẻ");
            }

            moveCameraToLocation(latLng);
            optionsPlace = markerOptions;
            markerFocusFirst = mMap.addMarker(markerOptions);
        }
    }

    @Override
    protected void addEvents() {
        imgLeft.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        imgCurrentLocation.setOnClickListener(this);
        fabPushAlert.setOnClickListener(this);
        fabShowDirection.setOnClickListener(this);

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(@NonNull View view) {
                hideKeyboard();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_left:
                presenterDetailRoomLocation.clickButtonLeft();
                break;
            case R.id.img_right:
                presenterDetailRoomLocation.clickButtonRight();
                break;
            case R.id.img_current_location:
                presenterDetailRoomLocation.showCurrentLocation();
                break;
            case R.id.fab_push_alert:
                showBottomSheetCreateMessageAlert();
                break;
            case R.id.fab_show_direction:
                Intent i = ShowDirectionActivity.innitIntent(
                        presenterDetailRoomLocation.getLastKnowLocation(),
                        new LocationPoint(currentLatlngFocus.latitude, currentLatlngFocus.longitude),
                        getActivity());
                startActivity(i);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
    }

    @Override
    public void showBottomSheetCreateMessageAlert() {
        final BottomSheetAlertRoomDialog bottomSheetFragment = new BottomSheetAlertRoomDialog();
        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
        bottomSheetFragment.setOnBottomSheetCallBack(new BottomSheetAlertRoomDialog.BottomSheetCallBack() {
            @Override
            public void onCreatedMessage(String message, String alertType, String image) {
                presenterDetailRoomLocation.postAlert(roomLocation, message, alertType, image);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        registerEventBus();
    }

    @Override
    public void onStop() {
        super.onStop();
        unRegisterEventBus();
    }

    @Override
    public void onPause() {
        super.onPause();
        mLocationSourceCallback.onPause();
        if (locationUpdateReceiver != null) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(locationUpdateReceiver);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mLocationSourceCallback.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenterDetailRoomLocation.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setPresenter(MVPDetailRoomLocation.IPresenterDetailRoomLocation iPresenterDetailRoomLocation) {
        if (iPresenterDetailRoomLocation != null) {
            presenterDetailRoomLocation = iPresenterDetailRoomLocation;
        }
    }

    private Marker prevMarkerFocus;
    public void createMarker(LatLng latLng, String title, String snipet) {
        if (prevMarkerFocus == null) {
            MarkerOptions markerOptions =
                    new MarkerOptions()
                            .position(latLng)
                            .title(title)
                            .snippet(snipet);
            prevMarkerFocus = mMap.addMarker(markerOptions);
        } else {
            prevMarkerFocus.setPosition(latLng);
            prevMarkerFocus.setTitle(title);
            prevMarkerFocus.setSnippet(snipet);
        }
        prevMarkerFocus.showInfoWindow();
    }

    /***
     * when click item member in naviRoomLocation
     * @param eventPostFocusCameraMapTo
     */
    @Subscribe
    public void onEventFocusCamera(EventPostFocusCameraMapTo eventPostFocusCameraMapTo) {
        changeNavigation();
        String userId = eventPostFocusCameraMapTo.id;

        for (UserLocation location : listUserLocation) {
            if (location.getUserId().equals(userId)) {
                LatLng latLng = new LatLng(location.getLat(), location.getLng());

                location.getMarker().setPosition(latLng);
                location.getMarker().setTitle(location.getDisplayName());
                location.getMarker().setSnippet(TimeUtil.setTextTimeNew(location.getTime()));
//                location.getMarker().setIcon(null);
                location.getMarker().showInfoWindow();

                moveCameraToLocation(latLng);
                showFabDirection();

                if (stateView == VIEW_STATE_ALERT && location.getUserId().equals(userSenderId)) {
                    markerFocusFirst.setTitle(location.getDisplayName());
                }

                break;
            }
        }
    }

    /**
     * Receive list member (include location) from NaviRoomFragment
     */
    private ArrayList<UserLocation> listUserLocation = new ArrayList<>();
    @Subscribe
    public void onEventReceiveListMember(EventPostListUserLocation eventPostListUserLocation) {
        mMap.clear();
        if (optionsPlace != null) {
            markerFocusFirst = mMap.addMarker(optionsPlace);
            markerFocusFirst.showInfoWindow();
        }
        listValueEventStatusInRoomChange.clear();
        listUserLocation.clear();

        ArrayList<MemberOfRoomLocation> members = eventPostListUserLocation.members;
        posRandomMarker = 1;
        for (int i = 0; i < members.size() ; i++) {
            MemberOfRoomLocation member = members.get(i);
            UserLocation userLocation = member.getUserLocation();

            if (userLocation == null) {
                userLocation = new UserLocation();
            } else if (member.getStatus() == MemberOfRoomLocation.MEMBER_JOINED) {
//                Log.d(getTAG(), "pos random" + posRandomMarker);

                MarkerOptions markerOptions =
                        new MarkerOptions()
                                .position(new LatLng(userLocation.getLat(), userLocation.getLng()))
                                .title(member.getDisplayName())
                                .snippet(TimeUtil.setTextTimeNew(userLocation.getTime()))
                                .icon(BitmapDescriptorFactory.defaultMarker(CommonUtils.getMarkerColor(posRandomMarker++)));

                if (stateView == VIEW_STATE_ALERT && member.getUserId().equals(userSenderId)) {
                    markerFocusFirst.setTitle(member.getDisplayName());
                    markerFocusFirst.showInfoWindow();
                    userLocation.setMarker(markerFocusFirst);
                } else {
                    Marker marker = mMap.addMarker(markerOptions);
                    userLocation.setMarker(marker);
                }
                listUserLocation.add(userLocation);
            }

            userLocation.setUserId(member.getUserId());
            userLocation.setDisplayName(member.getDisplayName());
            updateStatusInRoom(userLocation, member.getStatus());
        }
    }

    private HashMap<String, ValueEventListener> listValueEventStatusInRoomChange = new HashMap<>();
    /***
     * update response user status in room change
     * invited -> joined or joined -> left
     * @param userLocation
     */
    private void updateStatusInRoom(final UserLocation userLocation, final int prevStatus) {

        if (listValueEventStatusInRoomChange.containsKey(userLocation.getUserId())) return;

        ValueEventListener statusChangeListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(getTAG(), String.valueOf(dataSnapshot.child("status").getValue()));
                if (dataSnapshot.getValue() != null) {
                    long status = (long) dataSnapshot.child("status").getValue();
                    if (status == MemberOfRoomLocation.MEMBER_JOINED) {
                        updateRealtime(userLocation);
                        if (prevStatus == MemberOfRoomLocation.MEMBER_INVITED) {
                            /// send notify -> Navi rooom
                            EventBus.getDefault().post(new EventPostUpdateStatusUser(userLocation.getUserId(), MemberOfRoomLocation.MEMBER_JOINED));
                        }
                    }
                } else {
                    if (userLocation.getMarker() != null) {
                        userLocation.getMarker().remove();
                        /// send notify -> Navi rooom
                        EventBus.getDefault().post(new EventPostUpdateStatusUser(userLocation.getUserId(), MemberOfRoomLocation.MEMBER_LEFT));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        FirebaseDatabase.getInstance().getReference()
                .child("user")
                .child(userLocation.getUserId())
                .child("room_location")
                .child(roomLocation.getRoomId())
                .addValueEventListener(statusChangeListener);

        listValueEventStatusInRoomChange.put(userLocation.getUserId(), statusChangeListener);
    }

    private HashMap<String, ValueEventListener> listValueEventLocationRealTime = new HashMap<>();
    /**
     * update location realtime
     * @param userLocation
     */
    private int posRandomMarker = 1;
    private void updateRealtime(UserLocation userLocation) {

        Log.d(getTAG(), "update real time " + listValueEventLocationRealTime.containsKey(userLocation.getUserId()));
        if (listValueEventLocationRealTime.containsKey(userLocation.getUserId())) return;

        if (userLocation.getMarker() == null) {
            MarkerOptions markerOptions =
                    new MarkerOptions()
                            .position(new LatLng(userLocation.getLat(), userLocation.getLng()))
                            .title(userLocation.getDisplayName())
                            .snippet(TimeUtil.setTextTimeNew(userLocation.getTime()))
                            .icon(BitmapDescriptorFactory.defaultMarker(CommonUtils.getMarkerColor(posRandomMarker++)));
            Marker marker = mMap.addMarker(markerOptions);
            userLocation.setMarker(marker);
            listUserLocation.add(userLocation);
        }

        FirebaseDatabase.getInstance().getReference()
                .child("location_user")
                .child(userLocation.getUserId())
                .addValueEventListener(locationChangeListener);

        listValueEventLocationRealTime.put(userLocation.getUserId(), locationChangeListener);
    }

    ValueEventListener locationChangeListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            UserLocation v = dataSnapshot.getValue(UserLocation.class);
            Log.d(getTAG(), new Gson().toJson(dataSnapshot.getValue()));
            if (v != null) {

                for (UserLocation userLocation : listUserLocation) {
                    if (userLocation.getUserId().equals(v.getUserId())) {

                        userLocation.setLat(v.getLat());
                        userLocation.setLng(v.getLng());
                        userLocation.getMarker().setPosition(new LatLng(v.getLat(), v.getLng()));
                        userLocation.getMarker().setTitle(userLocation.getDisplayName());
                        userLocation.getMarker().setSnippet(TimeUtil.setTextTimeNew(v.getTime()) + " " + v.getLat() + "," + v.getLng());
                        userLocation.setTime(v.getTime());

                        boolean isOnline = Boolean.parseBoolean((String) dataSnapshot.child("isOnline").getValue());

                        if (userLocation.isOnline() != isOnline) {
                            userLocation.setOnline(isOnline);
                            userLocation.setLastTimeOnline((Long) dataSnapshot.child("lastTimeOnline").getValue());
                            // post on, off line
                            EventBus.getDefault().post(new EventPostUserOnOffline(userLocation));
                        }

                        return;
                    }
                }

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.d(getTAG(), "loadPost:onCancelled", databaseError.toException());
        }
    };

    @SuppressLint("MissingPermission")
    @Override
    protected void onPermisstionLocationGranted() {
        enableLocationProvider();
        this.mMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onPermisstionLocationDenied() {
    }

    /**
     * Register tracking location
     */
    private BroadcastReceiver locationUpdateReceiver;
    @Override
    protected void onLocationProvideEnabled() {
        // start tracking
        ((BaseActivity) getActivity()).startTrackingLocation();

        presenterDetailRoomLocation.showCurrentLocation(stateView);

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

    /***
     * set current location for view
     */
    private LocationSourceCallback mLocationSourceCallback;
    @Override
    public void showCurrentLocation(Location location) {
        mLocationSourceCallback.setLocationSource(location);
    }

    @Override
    protected void onLocationProvideCancel() {

    }

    @Override
    public void changeNavigation() {
        if (drawerLayout != null && !drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.openDrawer(Gravity.START);
        } else if (drawerLayout != null && drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
        }
    }

    @Override
    public void showFabAlert() {
        fabPushAlert.show();
    }

    @Override
    public void hideFabAlert() {
        fabPushAlert.hide();
    }

    @Override
    public void showActionBar() {
        appBarLayout.clearAnimation();
        appBarLayout.animate().alpha(1);
        appBarLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideActionBar() {
        appBarLayout.clearAnimation();
        appBarLayout.animate().alpha(0);
        appBarLayout.setVisibility(View.GONE);
    }

    @Override
    public void showFabDirection() {
        fabShowDirection.show();
    }

    @Override
    public void hideFabDirection() {
        fabShowDirection.hide();
    }

    @Override
    public void showUIAddMember() {
        Intent i = new Intent(getActivity(), MemberActivity.class);
        i.putExtra(Constans.KeyBundle.ROOM_LOCATION, new Gson().toJson(roomLocation));
        startActivityForResult(i, Constans.RequestCode.RC_ADD_MEMBER_TO_GROUP);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private LatLng currentLatlngFocus;
    @Override
    public void moveCameraToLocation(LatLng latLng) {
        currentLatlngFocus = latLng;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18f);
        mMap.moveCamera(cameraUpdate);
    }

    @Override
    public void onErr() {
        hideSweetProgressDialog(false, "Đã xảy ra lỗi");
    }

    @Override
    public void onPostAlertSuccess() {
        Toast.makeText(getContext(), "Đã gửi thông báo cho nhóm", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostAlertErr() {
        Toast.makeText(getContext(), "Không thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInvalidToken() {}

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        }
        return true;
    }

    public void setMapType(int postion) {
        switch (postion) {
            case 0:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case 1:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case 2:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }
    }

    class InfoWindowImageAdapter implements GoogleMap.InfoWindowAdapter {

        private String image;

        public InfoWindowImageAdapter(String urlImage) {
            this.image = urlImage;
        }

        @Override
        public View getInfoWindow(Marker marker) {

            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }

}
