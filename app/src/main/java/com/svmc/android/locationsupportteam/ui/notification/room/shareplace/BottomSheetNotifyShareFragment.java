package com.svmc.android.locationsupportteam.ui.notification.room.shareplace;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;

/**
 * Created by TungTS on 5/26/2019
 */

public class BottomSheetNotifyShareFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private TextView tvShowDetailPlace;
    private TextView tvShowOnRoom;
    private TextView tvDelete;
    private TextView imageAttach;

    private BottomSheetCallBack onBottomSheetCallBack;

    public static BottomSheetNotifyShareFragment newInstance(BottomSheetCallBack onBottomSheetCallBack) {
        BottomSheetNotifyShareFragment fragment = new BottomSheetNotifyShareFragment();
        fragment.onBottomSheetCallBack = onBottomSheetCallBack;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_share_place_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        innitView(view);
        addEvents();
    }

    private void innitView(View view) {
        tvShowDetailPlace = view.findViewById(R.id.tv_show_detail);
        tvShowOnRoom = view.findViewById(R.id.tv_show_on_room);
        tvDelete = view.findViewById(R.id.tv_delete);
        imageAttach = view.findViewById(R.id.image_attach);
    }

    private void addEvents() {
        tvShowDetailPlace.setOnClickListener(this);
        tvShowOnRoom.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        imageAttach.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_show_detail:
                if (onBottomSheetCallBack != null) {
                    onBottomSheetCallBack.onShowDetailPlace();
                }
                break;
            case R.id.tv_show_on_room:
                if (onBottomSheetCallBack != null) {
                    onBottomSheetCallBack.showOnRoom();
                }
                break;
            case R.id.tv_delete:
                if (onBottomSheetCallBack != null) {
                    onBottomSheetCallBack.onDeleteNotify();
                }
                break;
            case R.id.image_attach:
                if (onBottomSheetCallBack != null) {
                    onBottomSheetCallBack.onShowImage();
                }
                break;
        }
        dismiss();
    }

    public interface BottomSheetCallBack {

        void onShowDetailPlace();

        void showOnRoom();

        void onDeleteNotify();

        void onShowImage();

    }

}
