package com.svmc.android.locationsupportteam.ui.roomlocation.startdialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.event.EventPostLoadingData;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.network.ApiConfig;
import com.svmc.android.locationsupportteam.ui.base.BaseDialogFragment;
import com.svmc.android.locationsupportteam.ui.customviews.CircleImageView;
import com.svmc.android.locationsupportteam.ui.home.MainActivity;
import com.svmc.android.locationsupportteam.ui.roomlocation.RoomLocationActivity;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.utils.CommonUtils;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.GlideUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.Random;


/**
 * Created by TUNGTS on 4/20/2019
 */

public class RoomLocationDialog extends BaseDialogFragment implements MVPDialogRoom.IViewDialogRoom, View.OnClickListener {

    private static final String TAG = "RoomLocationDialog";

    private MVPDialogRoom.IPresenterDialogRoom presenterDialogRoom;

    private ProgressBar progressBar;
    private CardView cardContent;
    private CircleImageView avaRoom;
    private TextView tvNameRoom;
    private TextView tvTotalMember;
    private TextView tvDescriptionRoom;

    private TextView tvLeaveRoom;
    private TextView tvCreateRoom;
    private LinearLayout llShowLocationOnMap;

    public static RoomLocationDialog newInstance() {
        RoomLocationDialog fragment = new RoomLocationDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_room_location, container, false);
        return view;
    }

    @Override
    protected void setUp(View view) {
        progressBar = view.findViewById(R.id.progress_room_location);
        cardContent = view.findViewById(R.id.card_content);
        avaRoom = view.findViewById(R.id.img_room);
        tvNameRoom = view.findViewById(R.id.tv_name_room_location);
        tvTotalMember = view.findViewById(R.id.tv_total_member);
        tvDescriptionRoom = view.findViewById(R.id.tv_description_room_location);
        tvLeaveRoom = view.findViewById(R.id.btn_leave_room_location);
        tvCreateRoom = view.findViewById(R.id.btn_create_room_location);
        llShowLocationOnMap = view.findViewById(R.id.ll_show_location_on_map);
        addEvents();
        checkDataFromMain();
    }

    private void checkDataFromMain() {
        showProgress();
        hideContentView();
        String roomId = AppPreferencens.getInstance().getRoomId();

        if (roomId != null) {
            presenterDialogRoom.getInforRoom(roomId);
        } else {
            hideProgress();
            showContentView();
            innitView(null);
        }

//        MainActivity mainActivity = (MainActivity) getActivity();
//        if (mainActivity.isLoadingData()) {
//            if (mainActivity.getRoomIdJoined() != null) {
//                String roomId = mainActivity.getRoomIdJoined();
//                presenterDialogRoom.getInforRoom(roomId);
//            } else {
//                hideProgress();
//                showContentView();
//                innitView(null);
//            }
//        }
    }

    private void addEvents() {
        tvLeaveRoom.setOnClickListener(this);
        tvCreateRoom.setOnClickListener(this);
        llShowLocationOnMap.setOnClickListener(this);
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_leave_room_location:
                presenterDialogRoom.leaveRoom(roomLocation.getRoomId());
                break;
            case R.id.btn_create_room_location:
                presenterDialogRoom.openUICreateRoom();
                break;
            case R.id.ll_show_location_on_map:
                presenterDialogRoom.openUILocationOnMap();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
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

    @Subscribe
    public void onEvent(EventPostLoadingData eventPostLoadingData) {
        checkDataFromMain();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setPresenter(MVPDialogRoom.IPresenterDialogRoom iPresenterDialogRoom) {
        if (iPresenterDialogRoom != null) {
            this.presenterDialogRoom = iPresenterDialogRoom;
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showContentView() {
        Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardContent.setVisibility(View.VISIBLE);
                hideProgress();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        cardContent.startAnimation(fadeIn);
    }

    @Override
    public void hideContentView() {
        cardContent.setVisibility(View.INVISIBLE);
    }

    @Override
    public void innitView(RoomLocation roomLocation) {
        User user = AppPreferencens.getInstance().getUser();
        Glide.with(this)
                .load(ApiConfig.ConfigUrl.URL_NODEJS + user.getUrlImage())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .placeholder(R.drawable.ic_place_hoder)
                        .error(R.drawable.ic_place_hoder)
                        .skipMemoryCache(true))
                .into(avaRoom);

        if (roomLocation != null) {
            this.roomLocation = roomLocation;
            // GOTO: need img
            tvNameRoom.setText(roomLocation.getNameRoom());
            tvTotalMember.setText(roomLocation.getTotalMember() + " thành viên");
            tvDescriptionRoom.setText(getContext().getResources().getString(R.string.room_location_description_joined));
            showContentView();
            return;
        }

        tvNameRoom.setText("Chào " + user.getDisplayName());
        tvTotalMember.setVisibility(View.GONE);
        tvDescriptionRoom.setText(getContext().getResources().getString(R.string.room_location_description));
        llShowLocationOnMap.setVisibility(View.GONE);
        tvLeaveRoom.setVisibility(View.GONE);
    }

    @Override
    public void showUICreateRoom() {
        this.dismiss();
        Intent i = new Intent(getActivity(), RoomLocationActivity.class);
        i.putExtra(Constans.KeyBundle.CURRENT_FRAG_ON_ROOM_LOCATION, 0);
        startActivity(i);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private RoomLocation roomLocation;

    @Override
    public void showUILocationOnMap() {
        Intent i = new Intent(getActivity(), RoomLocationActivity.class);
        i.putExtra(Constans.KeyBundle.CURRENT_FRAG_ON_ROOM_LOCATION, 1);
        i.putExtra(Constans.KeyBundle.ROOM_LOCATION, new Gson().toJson(this.roomLocation));
        startActivity(i);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        this.dismiss();
    }

    @Override
    public void onLeaveRoom() {
        dismiss();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onInvalidToken() {
        Toast.makeText(getContext(), "Dang nhap lai", Toast.LENGTH_SHORT).show();
    }
}
