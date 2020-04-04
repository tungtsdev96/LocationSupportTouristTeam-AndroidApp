package com.svmc.android.locationsupportteam.ui.showdirection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sweetdialog.SweetAlertDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.maps.android.PolyUtil;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.direction.Bound;
import com.svmc.android.locationsupportteam.entity.googlemap.direction.Leg;
import com.svmc.android.locationsupportteam.entity.googlemap.direction.ResultPathGoogle;
import com.svmc.android.locationsupportteam.listeners.SweetAlertDialogListener;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.List;


/**
 * Create by TungTS on 5/11/2019
 */

public class ShowDirectionActivity extends BaseActivity implements MVPShowDirection.IViewShowDirection, View.OnClickListener, OnMapReadyCallback {

    public static Intent innitIntent(LocationPoint origin, LocationPoint destination, Activity activity) {
        Intent i = new Intent(activity, ShowDirectionActivity.class);
        i.putExtra(Constans.KeyBundle.SHOW_DIRECTION_ORIGIN, new Gson().toJson(origin));
        i.putExtra(Constans.KeyBundle.SHOW_DIRECTION_DESTINATION, new Gson().toJson(destination));
        return i;
    }

    private MVPShowDirection.IPresenterShowDirection presenterShowDirection;

    private LocationPoint origin, destination;
    private String mode = "driving";
    private Marker originMarker, destinationMarker;

    private Toolbar toolbar;
    private ImageView imgBack;
    private ImageView imgFocusCurrentLocation;

    private FrameLayout frDriving;
    private ImageView imgDriving;
    private ResultPathGoogle resultDriving;
    private List<LatLng> listDriving;
    final String TYPE_DRIVING = "driving";

    private FrameLayout frBicycling;
    private ImageView imgBicycling;
    private List<LatLng> listBicycling;
    private ResultPathGoogle resultBicycling;
    final String TYPE_BICYCLING = "bicycling";

    private FrameLayout frWalking;
    private ImageView imgWalking;
    private List<LatLng> listWalking;
    private ResultPathGoogle resultWalking;
    final String TYPE_WALKING= "walking";

    private ProgressBar progressBar;
    private LinearLayout llInfor;
    private TextView tvDistanceDuration;
    private TextView tvEndLocation;
    private TextView tvLoading;

    private GoogleMap mMap;

    @Override
    public int getContentView() {
        return R.layout.activity_show_direction;
    }

    @Override
    public void onViewCreated(View view) {
        String jsonOrigin = getIntent().getStringExtra(Constans.KeyBundle.SHOW_DIRECTION_ORIGIN);
        String jsonDestination = getIntent().getStringExtra(Constans.KeyBundle.SHOW_DIRECTION_DESTINATION);
        origin = new Gson().fromJson(jsonOrigin, LocationPoint.class);
        destination = new Gson().fromJson(jsonDestination, LocationPoint.class);

        presenterShowDirection = new PresenterShowDirectionImpl(this);

        toolbar = findViewById(R.id.toolbar);
        imgBack = findViewById(R.id.img_back);
        imgFocusCurrentLocation = findViewById(R.id.img_current_location);
        setSupportActionBar(toolbar);

        frDriving = findViewById(R.id.fr_driving);
        imgDriving = findViewById(R.id.img_driving);

        frBicycling = findViewById(R.id.fr_bicycling);
        imgBicycling = findViewById(R.id.img_bicycling);

        frWalking = findViewById(R.id.fr_walking);
        imgWalking = findViewById(R.id.img_walking);

        progressBar = findViewById(R.id.progress_bar);
        llInfor = findViewById(R.id.ll_infor);
        tvDistanceDuration = findViewById(R.id.tv_distance_duration);
        tvEndLocation = findViewById(R.id.tv_end_location);
        tvLoading = findViewById(R.id.tv_loading);

        innitMap();
        addEvents();
    }

    private void innitMap() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    private void addEvents() {
        imgBack.setOnClickListener(this);
        imgFocusCurrentLocation.setOnClickListener(this);
        imgDriving.setOnClickListener(this);
        imgBicycling.setOnClickListener(this);
        imgWalking.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;

        MarkerOptions desOptions =
                new MarkerOptions()
                        .position(new LatLng(destination.getLat(), destination.getLng()));
        destinationMarker = mMap.addMarker(desOptions);

        MarkerOptions originOptions =
                new MarkerOptions()
                        .position(new LatLng(origin.getLat(), origin.getLng()))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location));
        originMarker = mMap.addMarker(originOptions);

        presenterShowDirection.getPath(origin.toString(), destination.toString(), null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.img_current_location:
                moveCameraTo(origin);
                break;
            case R.id.img_driving:
                chooseDriving();
                break;
            case R.id.img_bicycling:
                chooseBicycling();
                break;
            case R.id.img_walking:
                chooseWalking();
                break;
        }
    }

    @Override
    public void chooseDriving() {
        mode = TYPE_DRIVING;
        imgDriving.setImageResource(R.drawable.ic_car_green_dark);
        imgBicycling.setImageResource(R.drawable.ic_bicycling_gray);
        imgWalking.setImageResource(R.drawable.ic_walk_gray);

        if (resultDriving == null)  {
            presenterShowDirection.getPath(origin.toString(), destination.toString(), TYPE_DRIVING);
        } else {
            showInforDes();
            drawPath(listDriving);
        }
    }

    @Override
    public void chooseBicycling() {
        mode = TYPE_BICYCLING;
        imgDriving.setImageResource(R.drawable.ic_car_gray);
        imgBicycling.setImageResource(R.drawable.ic_bicycling_green);
        imgWalking.setImageResource(R.drawable.ic_walk_gray);

        if (resultBicycling == null)  {
            presenterShowDirection.getPath(origin.toString(), destination.toString(), TYPE_BICYCLING);
        } else {
            showInforDes();
            drawPath(listBicycling);
        }
    }

    @Override
    public void chooseWalking() {
        mode = TYPE_WALKING;
        imgDriving.setImageResource(R.drawable.ic_car_gray);
        imgBicycling.setImageResource(R.drawable.ic_bicycling_gray);
        imgWalking.setImageResource(R.drawable.ic_walk_green_dark);

        if (resultWalking == null)  {
            presenterShowDirection.getPath(origin.toString(), destination.toString(), TYPE_WALKING);
        } else {
            showInforDes();
            drawPath(listWalking);
        }
    }

    @Override
    public void showDirection(ResultPathGoogle resultPathGoogle) {
        if (resultPathGoogle.getRoutes() == null || resultPathGoogle.getRoutes().size() == 0) {
            onNoResult();
            return;
        }

        switch (mode) {
            case TYPE_DRIVING:
                listDriving = PolyUtil.decode(resultPathGoogle.getRoutes().get(0).getOverviewPolyline().getPoints());
                this.resultDriving = resultPathGoogle;
                drawPath(listDriving);
                break;
            case TYPE_BICYCLING:
                listBicycling = PolyUtil.decode(resultPathGoogle.getRoutes().get(0).getOverviewPolyline().getPoints());
                this.resultBicycling = resultPathGoogle;
                drawPath(listDriving);
                break;
            case TYPE_WALKING:
                listWalking = PolyUtil.decode(resultPathGoogle.getRoutes().get(0).getOverviewPolyline().getPoints());
                this.resultWalking = resultPathGoogle;
                drawPath(listDriving);
                break;
        }

        moveCameraTo(resultPathGoogle.getRoutes().get(0).getBound());

    }

    private Polyline path;
    @Override
    public void drawPath(List<LatLng> listDriving) {
        if (path != null) {
            path.remove();
        }

        PolylineOptions polylineOptions = new PolylineOptions().
                geodesic(true).
                color(Color.BLUE).
                width(10);

        path = mMap.addPolyline(polylineOptions.addAll(listDriving));
        onFindDone();
    }

    @Override
    public void onNoResult() {
        showSweetAlert(
                "Thông báo",
                "Không tìm thấy",
                "Ok",
                SweetAlertDialog.WARNING_TYPE,
                new SweetAlertDialogListener() {
                    @Override
                    public void onConfirmClicked(SweetAlertDialog dialog) {
                        dialog.hide();
                    }

                    @Override
                    public void onCancelClicked(SweetAlertDialog dialog) {

                    }
                });
    }

    @Override
    public void moveCameraTo(Bound bound) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(bound.getNortheast().getLat(), bound.getNortheast().getLng()));
        builder.include(new LatLng(bound.getSouthwest().getLat(), bound.getSouthwest().getLng()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 6));
    }

    @Override
    public void moveCameraTo(LocationPoint point) {
        mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(new LatLng(point.getLat(), point.getLng()), 10f)
        );
    }

    @Override
    public void onErr() {
        Log.d(this.getClass().getName(), "Err");
        onFindDone();
    }

    @Override
    public void setLoading() {
        llInfor.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        tvLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFindDone() {
        llInfor.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        tvLoading.setVisibility(View.GONE);
    }

    @Override
    public void showInforDes() {
        Leg leg = null;
        switch (mode) {
            case TYPE_DRIVING:
                leg = resultDriving.getRoutes().get(0).getLegs().get(0);
                break;
            case TYPE_BICYCLING:
                leg = resultBicycling.getRoutes().get(0).getLegs().get(0);
                break;
            case TYPE_WALKING:
                leg = resultWalking.getRoutes().get(0).getLegs().get(0);
                break;
        }

        if (leg == null) return;
        onFindDone();

        String distance = leg.getDuration().getText();
        String duration = leg.getDistance().getText();

        Spanned tmp = Html.fromHtml(
                "<html><b>" + duration + "</b> (" + distance + ")</html>"
        );

        tvDistanceDuration.setText(
                tmp
        );
        tvEndLocation.setText(Html.fromHtml(
                "<html><b>Điểm đến: </b>" + leg.getEndAddress() + "</html>"
        ));
    }
}

