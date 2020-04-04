package com.svmc.android.locationsupportteam.ui.home.maps.nearbysearch.item;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.PhotoAdapter;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Photo;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.customviews.CustomRatingView;
import com.svmc.android.locationsupportteam.ui.detailplace.PlaceActivity;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.ui.showdirection.ShowDirectionActivity;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/17/2019
 */

public class NearBySearchItemFragment extends BaseFragment implements View.OnClickListener, MVPItem.IViewItem {

    private MVPItem.IPresenterItem presenterItem;

    private Place place;

    private TextView tvNamePlace;
    private TextView tvAdminStrativeArea;
    private TextView tvDuration;
    private LinearLayout llPlaceInfor;
    private RelativeLayout llContent;

    private LinearLayout llRating;
    private TextView tvRatingScore;
    private CustomRatingView ratingScore;
    private TextView tvTotalRating;

    private LinearLayout llshowDirection;
    private LinearLayout llShare;
    private LinearLayout llSavePlace;
    private TextView tvSave;
    private ImageView imgSave;

    private RecyclerView rcvPhotos;
    private PhotoAdapter photoAdapter;
    private List<Photo> photos;

    public static NearBySearchItemFragment newInstance(Place place) {
        NearBySearchItemFragment nearBySearchItemFragment = new NearBySearchItemFragment();
        nearBySearchItemFragment.setTAG(Constans.TagFragment.NEAR_BY_SEARCH_ITEM_FRAGMENT);
        nearBySearchItemFragment.place = place;
        return nearBySearchItemFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_near_by_search_item;
    }

    @Override
    protected void onFragmentCreateView(View view) {
    }

    @Override
    protected void onFragmentCreated(View view) {

    }

    @Override
    protected void innitView(View view) {
//        innitRcvPhoto(view);
        llPlaceInfor = view.findViewById(R.id.ll_infor);
        llContent = view.findViewById(R.id.ll_content);
        tvNamePlace = view.findViewById(R.id.tv_name_place);
        tvNamePlace.setText(place.getName());

        tvAdminStrativeArea = view.findViewById(R.id.tv_place_type);
        tvAdminStrativeArea.setVisibility(View.VISIBLE);
        if (place.getFormattedAddress() != null ) {
            tvAdminStrativeArea.setText(
                    place.getFormattedAddress()
            );
        } else {
            tvAdminStrativeArea.setVisibility(View.GONE);
        }

        // duration
        tvDuration = view.findViewById(R.id.tv_duration);

        // rating
        llRating = view.findViewById(R.id.ll_rating);
        tvRatingScore = view.findViewById(R.id.tv_rating_score);
        ratingScore = view.findViewById(R.id.rating_score);
        tvTotalRating = view.findViewById(R.id.tv_total_rating);
        if (place.getRating() > 0) {
            llRating.setVisibility(View.VISIBLE);
            tvRatingScore.setText(String.valueOf(place.getRating()));
            ratingScore.setScore(place.getRating());
            tvTotalRating.setText("(" + place.getUserRatingTotal() + ")");
        } else {
            llRating.setVisibility(View.GONE);
        }

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
        isSave = presenterItem.checkPlaceSaveOff(place.getPlaceId());
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

        if (place.getPhotos() != null) {

            rcvPhotos.setVisibility(View.VISIBLE);
            photos.addAll(place.getPhotos());
            photoAdapter.notifyDataSetChanged();
        } else {
            rcvPhotos.setVisibility(View.GONE);
        }
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
                if (place == null) return;
                Intent i = ShowDirectionActivity.innitIntent(
                        presenterItem.getCurrentLocation(),
                        place.getGeometry().getLocation(),
                        getActivity());
                startActivity(i);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.ll_share:
                if (place != null) {
                    presenterItem.sharePlace(place);
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
            boolean isSuccess = presenterItem.savePlaceOffline(place);
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
        boolean isSuccess = presenterItem.deletePlaceOff(place.getPlaceId());
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
    public void setPresenter(MVPItem.IPresenterItem iPresenterItem) {
        if (iPresenterItem != null) {
            presenterItem = iPresenterItem;
        }
    }

    @Override
    public void showUIDetailPlace() {
        Intent i = PlaceActivity.setIntentDataDetail(getActivity(), place.getPlaceId());
        startActivity(i);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void sharePlaceSuccess() {

    }

    @Override
    public void sharePlaceErr() {

    }


}
