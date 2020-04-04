package com.svmc.android.locationsupportteam.ui.detailplace;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.HomeScreenAdapter;
import com.svmc.android.locationsupportteam.adapter.ItemReviewPlaceAdapter;
import com.svmc.android.locationsupportteam.data.remote.GoogleAPIRemoteDataSource;
import com.svmc.android.locationsupportteam.entity.googlemap.distance.DisplayInfor;
import com.svmc.android.locationsupportteam.entity.googlemap.distance.ResultDistance;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.OpeningHours;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Photo;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.homescreen.HomeScreenModel;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.customviews.CustomRatingView;
import com.svmc.android.locationsupportteam.ui.listitem.ListItemActivity;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.ui.showdirection.ShowDirectionActivity;
import com.svmc.android.locationsupportteam.utils.CommonUtils;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TungTS on 5/8/2019
 */

public class DetailPlaceFragment extends BaseFragment implements MVPDetailPlace.IViewDetailPlace, View.OnClickListener {

    private MVPDetailPlace.IPresenterDetailPlace presenterDetailPlace;

    private String placeId;
    private Place place;

    private LinearLayout viewLoading;

    private LinearLayout llDetailPlace;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private View vScrim;
    private ImageView btnBack;
    private TextView tvTitle;
    private SliderLayout sliderLayout;

    private LinearLayout llInforPlace;
    private TextView tvNamePlace;

    private LinearLayout llRating;
    private TextView tvRatingScore;
    private CustomRatingView ratingScore;
    private TextView tvTotalRating;

    private TextView tvVincity;
    private TextView tvDuration;

    // ll controls
    private LinearLayout llShowDirection;
    private LinearLayout llShare;
    private LinearLayout llCall;
    private LinearLayout llSavePlace;
    private TextView tvSave;
    private ImageView imgSave;

    // ll additional infor pkace
    private boolean isShowMoreInfor = true;
    private RelativeLayout rltMoreInfor;
    private LinearLayout llMoreInfor;

    private LinearLayout llFormatAddress;
    private TextView tvFormatAddress;

    private LinearLayout llPhoneNumber;
    private TextView tvPhoneNumber;

    private LinearLayout llTimeOpen;
    private TextView tvOpenNow;
    private TextView tvTimeOpen;

    private LinearLayout llWebsite;
    private TextView tvWebsite;

    //reviews
    private LinearLayout llReviews;
    private RecyclerView rcvReviews;
    private ItemReviewPlaceAdapter reviewPlaceAdapter;
    private List itemReviews;

    //recommend nearby
    private RecyclerView rcvNearBy;
    private List itemsRecommned;
    private HomeScreenAdapter homeScreenAdapter;

    public static DetailPlaceFragment newInstance(String placeId) {
        DetailPlaceFragment detailPlaceFragment = new DetailPlaceFragment();
        detailPlaceFragment.setTAG(Constans.TagFragment.DETAIL_PLACE_FRAGMENT);
        detailPlaceFragment.placeId = placeId;
        return detailPlaceFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_place;
    }

    @Override
    protected void onFragmentCreateView(View view) {
    }


    @Override
    protected void onFragmentCreated(View view) {
        presenterDetailPlace.getDetailPlace(placeId);
        presenterDetailPlace.calDistance(placeId);
    }

    @Override
    protected void innitView(View view) {
        viewLoading = view.findViewById(R.id.view_loading);

        appBarLayout = view.findViewById(R.id.app_bar_layout);
        vScrim = view.findViewById(R.id.v_scrim);
        toolbar = view.findViewById(R.id.toolbar);
        btnBack = view.findViewById(R.id.img_back);
        tvTitle = view.findViewById(R.id.tv_title);

        llDetailPlace = view.findViewById(R.id.ll_detail_place);

        tvNamePlace = view.findViewById(R.id.tv_name_place);
        llRating = view.findViewById(R.id.ll_rating);
        tvRatingScore = view.findViewById(R.id.tv_rating_score);
        ratingScore = view.findViewById(R.id.rating_score);
        tvTotalRating = view.findViewById(R.id.tv_total_rating);

        tvVincity = view.findViewById(R.id.tv_vincity);
        tvDuration = view.findViewById(R.id.tv_duration);

        sliderLayout = view.findViewById(R.id.slide_layout);

        // ll controls
        llShowDirection = view.findViewById(R.id.ll_show_direction);
        llCall = view.findViewById(R.id.ll_call);
        llShare = view.findViewById(R.id.ll_share);
        if (AppPreferencens.getInstance().getRoomId() == null) llShare.setVisibility(View.GONE);
        innitControlsSave(view);

        // ll additional infor pkace
        rltMoreInfor = view.findViewById(R.id.rlt_more_infor);
        llMoreInfor = view.findViewById(R.id.ll_more_infor);

        llFormatAddress = view.findViewById(R.id.ll_format_address);
        tvFormatAddress = view.findViewById(R.id.tv_fomart_address);

        llPhoneNumber = view.findViewById(R.id.ll_phone_number);
        tvPhoneNumber = view.findViewById(R.id.tv_phone_number);

        llTimeOpen = view.findViewById(R.id.ll_time_open);
        tvTimeOpen = view.findViewById(R.id.tv_time_open);
        tvOpenNow = view.findViewById(R.id.tv_open_now);

        llWebsite = view.findViewById(R.id.ll_website);
        tvWebsite = view.findViewById(R.id.tv_website);

        innitRcvReviews(view);
        innitRcvRecommnedNearBy(view);
    }

    private boolean isSave = false;
    private void innitControlsSave(View view) {
        llSavePlace = view.findViewById(R.id.ll_save_place);
        tvSave = view.findViewById(R.id.tv_save);
        imgSave = view.findViewById(R.id.img_save);
        isSave = presenterDetailPlace.checkPlaceSaveOff(placeId);
        if (isSave) {
            tvSave.setText(getString(R.string.delete));
            imgSave.setImageResource(R.drawable.ic_save_blue_full);
        } else {
            tvSave.setText(getString(R.string.save));
            imgSave.setImageResource(R.drawable.ic_save_blue_border);
        }
    }

    private void innitRcvRecommnedNearBy(View view) {
        rcvNearBy = view.findViewById(R.id.rcv_recommend_place);
        ViewCompat.setNestedScrollingEnabled(rcvNearBy, false);
        homeScreenAdapter = new HomeScreenAdapter();
        itemsRecommned = new ArrayList();
        homeScreenAdapter.setItems(itemsRecommned);
        rcvNearBy.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvNearBy.setAdapter(homeScreenAdapter);

        homeScreenAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
            @Override
            public void onClick(int position) {
                Intent i = ListItemActivity.innitIntentFromHomeModel(getActivity(), (HomeScreenModel) itemsRecommned.get(position));
                startActivity(i);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        homeScreenAdapter.setOnClickItemPlace(new HomeScreenAdapter.ClickItemPlace() {
            @Override
            public void onItemClick(String placeId, int pos) {
                Intent i = PlaceActivity.setIntentDataDetail(getActivity(), placeId);
                startActivity(i);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    private void innitRcvReviews(View view) {
        llReviews = view.findViewById(R.id.ll_reviews);
        rcvReviews = view.findViewById(R.id.rcv_reviews);
        ViewCompat.setNestedScrollingEnabled(rcvReviews, false);
        reviewPlaceAdapter = new ItemReviewPlaceAdapter();
        itemReviews = new ArrayList();
        reviewPlaceAdapter.setItems(itemReviews);
        rcvReviews.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvReviews.setAdapter(reviewPlaceAdapter);
    }

    @Override
    protected void addEvents() {
        btnBack.setOnClickListener(this);
        llShowDirection.setOnClickListener(this);
        llCall.setOnClickListener(this);
        llShare.setOnClickListener(this);
        llSavePlace.setOnClickListener(this);
        rltMoreInfor.setOnClickListener(this);
        llPhoneNumber.setOnClickListener(this);
        llWebsite.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                getActivity().onBackPressed();
                break;
            case R.id.ll_show_direction:
                if (place == null) return;
                Intent i = ShowDirectionActivity.innitIntent(
                        presenterDetailPlace.getCurrentLocation(),
                        place.getGeometry().getLocation(),
                        getActivity());
                startActivity(i);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.ll_share:
                if (place != null) {
                    presenterDetailPlace.sharePlace(place);
                }
                break;
            case R.id.ll_call:
                if (place.getFormattedPhoneNumber() != null) {
                    callTo(place.getFormattedPhoneNumber());
                }
                break;
            case R.id.ll_phone_number:
                if (place.getFormattedPhoneNumber() != null) {
                    callTo(place.getFormattedPhoneNumber());
                }
                break;
            case R.id.ll_website:
                if (place.getWebsite() != null) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(place.getWebsite()));
                    startActivity(browserIntent);
                }
                break;
            case R.id.ll_save_place:
                if (isSave) {
                    deletePlaceOff();
                } else {
                    savePlaceOff();
                }
                break;
            case R.id.rlt_more_infor:
                if (!isShowMoreInfor) {
                    llMoreInfor.setVisibility(View.VISIBLE);
                    isShowMoreInfor = true;
                } else {
                    isShowMoreInfor = false;
                    llMoreInfor.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void callTo(String formattedPhoneNumber) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            requestPermission(this, Constans.RequestCode.RC_CALL_PHONE, Manifest.permission.CALL_PHONE, true);
        } else {
            String dial = "tel:" + formattedPhoneNumber;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (isPermissionGranted(permissions, grantResults, Manifest.permission.CALL_PHONE)) {
            callTo(place.getFormattedPhoneNumber());
        }
    }

    private void savePlaceOff() {
        if (place != null) {
            boolean isSuccess = presenterDetailPlace.savePlaceOffline(place);
            Toast.makeText(getContext(), isSuccess ? "Lưu thành công" : "Lưu không thành công", Toast.LENGTH_SHORT).show();
            isSave = isSuccess;
            tvSave.setText(
                    isSuccess ? getString(R.string.delete) : getString(R.string.save)
            );
            imgSave.setImageResource(
                    isSuccess ? R.drawable.ic_save_blue_full : R.drawable.ic_save_blue_border);
        }
    }

    private void deletePlaceOff() {
        boolean isSuccess = presenterDetailPlace.deletePlaceOff(placeId);
        Toast.makeText(getContext(), isSuccess ? "Xóa thành công" : "Xóa không thành công", Toast.LENGTH_SHORT).show();
        isSave = !isSuccess;
        tvSave.setText(
                isSuccess ? getString(R.string.save) : getString(R.string.delete)
        );
        imgSave.setImageResource(
                isSuccess ? R.drawable.ic_save_blue_border : R.drawable.ic_save_blue_full
        );
    }

    private void innitViewSlider(List<Photo> photos) {
        if (photos == null || photos.size() == 0) {
            sliderLayout.setVisibility(View.GONE);
            vScrim.setVisibility(View.GONE);
            return;
        }

        ((BaseActivity) getActivity()).setStatusBarTranslucent();
        sliderLayout.removeAllSliders();
        for (Photo photo: photos) {
            String url = GoogleAPIRemoteDataSource.getUrlPhoto(
                    CommonUtils.dpToPx(200),
                    true,
                    photo.getPhotoReference()
            );

            DefaultSliderView sliderView = new DefaultSliderView(getContext());
            sliderView
                    .image(url)
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);

            sliderLayout.addSlider(sliderView);
        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
    }

    @Override
    public void onStop() {
        super.onStop();
        sliderLayout.stopAutoCycle();
        presenterDetailPlace.onStop();
    }

    @Override
    public void bindDataDetailPlace(Place place) {
        this.place = place;
        viewLoading.setVisibility(View.GONE);
        appBarLayout.setVisibility(View.VISIBLE);
        llDetailPlace.setVisibility(View.VISIBLE);

        innitViewSlider(place.getPhotos());
        bindDataToLLInforPlace();
        bindDataToMoreInfor();
        bindDataToRcvReciews();

        itemsRecommned.add(null);
        homeScreenAdapter.setLocading(true);
        homeScreenAdapter.notifyDataSetChanged();
        String location = place.getGeometry().getLocation().toString();
        presenterDetailPlace.loadNearPlace(location);
    }

    private void bindDataToMoreInfor() {

        if (place.getFormattedAddress() != null) {
            tvFormatAddress.setText(place.getFormattedAddress());
        } else {
            llFormatAddress.setVisibility(View.GONE);
        }

        if (place.getFormattedPhoneNumber() != null) {
            tvPhoneNumber.setText(place.getFormattedPhoneNumber());
        } else {
            llPhoneNumber.setVisibility(View.GONE);
            llCall.setVisibility(View.GONE);
        }

        if (place.getWebsite() != null) {
            tvWebsite.setText(place.getWebsite());
        } else {
            llWebsite.setVisibility(View.GONE);
        }

        if (place.getOpeningHours() != null) {
            tvOpenNow.setText(
                    place.getOpeningHours().isOpenNow() ? "Đang mở" : "Đã đóng"
            );

            tvOpenNow.setTextColor(
                    place.getOpeningHours().isOpenNow()
                            ? getResources().getColor(R.color.material_blue_700)
                            : getResources().getColor(R.color.material_red_500)
            );

            try {

                int day = new Date().getDay();
                if (day == 6) {
                    day = -1;
                }

                OpeningHours openingHours = place.getOpeningHours();
                Log.d(getTAG(), openingHours.getPeriods().get(0).getOpen().getDay() + " " + openingHours.getPeriods().get(0).getOpen().getTime());
                Log.d(getTAG(), new Gson().toJson(openingHours));
                if (openingHours.getPeriods().get(0).getOpen().getDay() == 0 &&  openingHours.getPeriods().get(0).getOpen().getTime().equals("00:00")) tvTimeOpen.setText("Luôn mở");
                else
                    tvTimeOpen.setText(
                            "Mở cửa lúc " + openingHours.getPeriods().get(day + 1).getOpen().getTime()
                    );

            } catch (Exception e) {
                tvTimeOpen.setVisibility(View.GONE);
            }

        } else {
            llTimeOpen.setVisibility(View.GONE);
        }
    }

    @Override
    public void bindDataToLLInforPlace() {
        tvTitle.setText(place.getName());
        tvNamePlace.setText(place.getName());

        if (place.getRating() > 0) {
            llRating.setVisibility(View.VISIBLE);
            tvRatingScore.setText(String.valueOf(place.getRating()));
            ratingScore.setScore(place.getRating());
            tvTotalRating.setText("(" + place.getUserRatingTotal() + ")");
        }

        tvVincity.setText(
                place.getVicinity() != null ? place.getVicinity() : place.getNameAdministrativeArea()
        );
    }

    @Override
    public void bindDataDistanceFromCurrentLocation(ResultDistance result) {
        tvDuration.setVisibility(View.VISIBLE);
        DisplayInfor displayInfor = result.getRows().get(0).getElements().get(0).getDuration();
        if (displayInfor != null) {
            tvDuration.setText(displayInfor.getText());
        } else {
            tvDuration.setVisibility(View.GONE);
        }
    }

    @Override
    public void bindDataToRcvReciews() {
        if (place.getReviews() == null || place.getReviews().size() == 0) {
            llReviews.setVisibility(View.GONE);
            return;
        }

        rcvReviews.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        itemReviews.addAll(place.getReviews());
        reviewPlaceAdapter.notifyDataSetChanged();
    }

    @Override
    public void bindDataToRcvRecommendNearBy(ArrayList<HomeScreenModel> homeScreenModels) {
        itemsRecommned.clear();
        homeScreenAdapter.notifyDataSetChanged();
        homeScreenAdapter.setLocading(false);

        if (homeScreenModels != null && homeScreenModels.size() > 0) {
            for (HomeScreenModel model : homeScreenModels) {
                if (model.getPlaces().size() > 0 ) {
                    itemsRecommned.add(model);
                }
            }
            homeScreenAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoadDataErr() {
        if (homeScreenAdapter.isLocading()) {
            itemsRecommned.clear();
            homeScreenAdapter.setLocading(false);
            homeScreenAdapter.notifyDataSetChanged();
        }
//        showErrMessage("Đã có lỗi xảy ra");
    }

    @Override
    public void sharePlaceSuccess() {
        Toast.makeText(getContext(), "Đã chia sẻ vào nhóm", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sharePlaceErr() {
        Toast.makeText(getContext(), "Không thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(MVPDetailPlace.IPresenterDetailPlace iPresenterDetailPlace) {
        if (iPresenterDetailPlace != null) {
            presenterDetailPlace = iPresenterDetailPlace;
        }
    }

}
