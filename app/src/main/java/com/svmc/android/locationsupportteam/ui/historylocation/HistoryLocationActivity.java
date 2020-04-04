package com.svmc.android.locationsupportteam.ui.historylocation;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.data.local.LocationDataLocal;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.utils.TimeUtil;

import java.util.ArrayList;

public class HistoryLocationActivity extends BaseActivity implements OnMapReadyCallback {

    private Toolbar toolbar;
    private GoogleMap mMap;
    private LocationDataLocal dataLocal;
    private ArrayList<LocationPoint> points;

    private ArrayList<Marker> markers = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.activity_history_location;
    }

    @Override
    public void onViewCreated(View view) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lịch sử");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

    }

    private void innitData() {
        dataLocal = new LocationDataLocal();
        points = dataLocal.getLastPointCache();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_map_type, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_map_type:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(R.array.map_type, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {
                        // The 'which' argument contains the index position
                        // of the selected item
                       switch (position) {
                           case 0:
                               mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                               break;
                           case 1:
                               mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                               break;
                           case 2:
                               mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                               break;
                       }
                    }
                });
                builder.create().show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return false;
            }
        });

        mMap.setMyLocationEnabled(true);

        innitData();

        for (int i = 0; i < points.size(); i++) {
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .width(60)  // width in px
                    .height(60) // height in px
                    .withBorder(4)
                    .textColor(Color.WHITE)
                    .endConfig()
                    .buildRound(String.valueOf(points.size() - i), Color.RED);

            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(points.get(i).getLat(), points.get(i).getLng()))
                    .title(TimeUtil.setTextTimeNew(points.get(i).getTime()))
                    .icon(getMarkerIconFromDrawable(drawable)));

            markers.add(marker);
        }

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(points.get(0).getLat(), points.get(0).getLng()), 12f);
        mMap.moveCamera(cameraUpdate);
    }

    private BitmapDescriptor getMarkerIconFromDrawable(TextDrawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
