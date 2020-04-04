package com.svmc.android.locationsupportteam.ui.home.maps.itemplace;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.PhotoAdapter;
import com.svmc.android.locationsupportteam.entity.event.EventMap;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.distance.DisplayInfor;
import com.svmc.android.locationsupportteam.entity.googlemap.distance.ResultDistance;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Photo;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.customviews.CustomRatingView;
import com.svmc.android.locationsupportteam.ui.detailplace.PlaceActivity;
import com.svmc.android.locationsupportteam.ui.showdirection.ShowDirectionActivity;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.utils.CommonUtils;
import com.svmc.android.locationsupportteam.utils.Constans;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TungTS on 5/7/2019
 */

public class ItemPlaceFragment extends BaseFragment implements MVPItemPlace.IViewItemPlace, View.OnClickListener {


    private TextView tvNamePlace;
    private TextView tvAdminStrativeArea;
    private TextView tvDuration;
    private LinearLayout llPlaceInfor;

    private LinearLayout llRating;
    private TextView tvRatingScore;
    private CustomRatingView ratingScore;
    private TextView tvTotalRating;

    private ProgressBar progressLoading;
    private LinearLayout llshowDirection;
    private LinearLayout llShare;
    private LinearLayout llSavePlace;
    private TextView tvSave;
    private ImageView imgSave;

    private RecyclerView rcvPhotos;
    private PhotoAdapter photoAdapter;
    private List<Photo> photos;

    private Place place;
    private String placeId;
    private String namePlace;

    private MVPItemPlace.IPresenterItemPlace presenterItemPlace;

    public static ItemPlaceFragment newInstance(String placeId, String namePlace) {
        ItemPlaceFragment fragment = new ItemPlaceFragment();
        fragment.placeId = placeId;
        fragment.namePlace = namePlace;
        fragment.setTAG(Constans.TagFragment.ITEM_PLACE_FRAGMENT_IN_MAP);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerEventBus();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenterItemPlace.onStop();
        unRegisterEventBus();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item_place;
    }

    @Override
    protected void onFragmentCreateView(View view) {

    }

    @Override
    protected void onFragmentCreated(View view) {

    }

    @Override
    protected void innitView(View view) {
        innitRcvPhoto(view);
        llPlaceInfor = view.findViewById(R.id.ll_infor);
        tvNamePlace = view.findViewById(R.id.tv_name_place);
        tvNamePlace.setText(namePlace);
        tvAdminStrativeArea = view.findViewById(R.id.tv_administrative_area_level_1);
        tvDuration = view.findViewById(R.id.tv_duration);
        progressLoading = view.findViewById(R.id.progress_loading);

        llRating = view.findViewById(R.id.ll_rating);
        tvRatingScore = view.findViewById(R.id.tv_rating_score);
        ratingScore = view.findViewById(R.id.rating_score);
        tvTotalRating = view.findViewById(R.id.tv_total_rating);

        llshowDirection = view.findViewById(R.id.ll_show_direction);
        llShare = view.findViewById(R.id.ll_share);
        if (AppPreferencens.getInstance().getRoomId() == null) llShare.setVisibility(View.GONE);

        innitControlSave(view);
    }

    private boolean isSave = false;
    private void innitControlSave(View view) {
        llSavePlace = view.findViewById(R.id.ll_save_place);
        tvSave = view.findViewById(R.id.tv_save);
        imgSave = view.findViewById(R.id.img_save);
        isSave = presenterItemPlace.checkPlaceSaveOff(placeId);
        if (isSave) {
            tvSave.setText(getString(R.string.delete));
            imgSave.setImageResource(R.drawable.ic_save_green_full);
        } else {
            tvSave.setText(getString(R.string.save));
            imgSave.setImageResource(R.drawable.ic_save_green_border);
        }
    }

    private void innitRcvPhoto(View view) {
        rcvPhotos = view.findViewById(R.id.rcv_photos);
        photos = new ArrayList<>();
        photoAdapter = new PhotoAdapter();
        photoAdapter.setPhotos(photos);
        rcvPhotos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvPhotos.setAdapter(photoAdapter);
    }

    @Override
    protected void addEvents() {
        llshowDirection.setOnClickListener(this);
        llSavePlace.setOnClickListener(this);
        llPlaceInfor.setOnClickListener(this);
        llShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_show_direction:
//                Toast.makeText(getContext(), "show direction", Toast.LENGTH_SHORT).show();
                if (place == null) return;
                Intent i = ShowDirectionActivity.innitIntent(
                        presenterItemPlace.getCurrentLocation(),
                        place.getGeometry().getLocation(),
                        getActivity());
                startActivity(i);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.ll_share:
                if (place != null) {
                    presenterItemPlace.sharePlace(place);
                }
                break;
            case R.id.ll_save_place:
                if (isSave) {
                    deletePlaceOff();
                } else {
                    savePlaceOff();
                }
                break;
            case R.id.ll_infor:
                showUIDetailPlace();
                break;
        }
    }

    private void savePlaceOff() {
        if (place != null) {
            boolean isSuccess = presenterItemPlace.savePlaceOffline(place);
            Toast.makeText(getContext(), isSuccess ? "Lưu thành công" : "Lưu không thành công", Toast.LENGTH_SHORT).show();
            isSave = isSuccess;
            tvSave.setText(
                    isSuccess ? getString(R.string.delete) : getString(R.string.save)
            );
            imgSave.setImageResource(
                    isSuccess ? R.drawable.ic_save_green_full : R.drawable.ic_save_green_border);
        }
    }

    private void deletePlaceOff() {
        boolean isSuccess = presenterItemPlace.deletePlaceOff(placeId);
        Toast.makeText(getContext(), isSuccess ? "Xóa thành công" : "Xóa không thành công", Toast.LENGTH_SHORT).show();
        isSave = !isSuccess;
        tvSave.setText(
                isSuccess ? getString(R.string.save) : getString(R.string.delete)
        );
        imgSave.setImageResource(
                isSuccess ? R.drawable.ic_save_green_border : R.drawable.ic_save_green_full
        );
    }

    @Override
    public void showUIDetailPlace() {
        Intent i = PlaceActivity.setIntentDataDetail(getActivity(), placeId);
        startActivity(i);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (place == null) {
            progressLoading.setVisibility(View.VISIBLE);
            presenterItemPlace.getDetailPlace(placeId);
            presenterItemPlace.calDistance(placeId);
        }

        if (place != null) {
            EventBus.getDefault().post(new EventMap.PostFocusToLocation(0, place.getGeometry().getLocation()));
        }
    }

    @Override
    public void setPresenter(MVPItemPlace.IPresenterItemPlace iPresenterItemPlace) {
        if (iPresenterItemPlace != null) {
            this.presenterItemPlace = iPresenterItemPlace;
        }
    }

    @Override
    public void hideProgressBar() {
        progressLoading.setVisibility(View.GONE);
    }

    @Override
    public void bindDataDetailPlace(Place place) {
        this.place = place;

        // send data to map fragment
        ArrayList<LocationPoint> points = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        points.add(place.getGeometry().getLocation());
        names.add(place.getName());
        EventBus.getDefault().post(new EventMap.PostLocation(points, names));

        if (place.getPhotos() != null) {

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) llPlaceInfor.getLayoutParams();
            params.setMargins(0, CommonUtils.dpToPx(100), 0, 0);

            rcvPhotos.setVisibility(View.VISIBLE);
            photos.addAll(place.getPhotos());
            photoAdapter.notifyDataSetChanged();
        }

        tvNamePlace.setText(place.getName());

        tvAdminStrativeArea.setVisibility(View.VISIBLE);
        tvAdminStrativeArea.setText(
                place.getNameAdministrativeArea()
        );

        if (place.getRating() > 0) {
            llRating.setVisibility(View.VISIBLE);
            tvRatingScore.setText(String.valueOf(place.getRating()));
            ratingScore.setScore(place.getRating());
            tvTotalRating.setText("(" + place.getUserRatingTotal() + ")");
        }

    }

    @Override
    public void bindDataDistanceFromCurrentLocation(ResultDistance resultDistance) {
        tvDuration.setVisibility(View.VISIBLE);
        DisplayInfor displayInfor = resultDistance.getRows().get(0).getElements().get(0).getDuration();
        if (displayInfor != null) {
            tvDuration.setText(displayInfor.getText());
        } else {
            tvDuration.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadDataErr() {
        Log.d(getTAG(), "OnDataErr");
    }

    @Override
    public void sharePlaceSuccess() {
        Toast.makeText(getContext(), "Đã chia sẻ địa điểm vào nhóm", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sharePlaceErr() {
        Toast.makeText(getContext(), "Không thành công", Toast.LENGTH_SHORT).show();
    }

    /***
     * When user click marker on MapFragment
     * @param postClickMaker
     */
    @Subscribe
    public void onEvent(EventMap.PostClickMaker postClickMaker) {
        showUIDetailPlace();
    }
}
