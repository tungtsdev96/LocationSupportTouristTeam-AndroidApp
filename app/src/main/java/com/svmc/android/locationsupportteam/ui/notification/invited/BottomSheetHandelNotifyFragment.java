package com.svmc.android.locationsupportteam.ui.notification.invited;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;

/**
 * Create by TungTS on 5/2/2019
 */

public class BottomSheetHandelNotifyFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private String roomName;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    private BottomSheetNotifyCallBack bottomSheetNotifyCallBack;

    public static BottomSheetHandelNotifyFragment newInstance(String roomName, BottomSheetNotifyCallBack bottomSheetNotifyCallBack) {
        BottomSheetHandelNotifyFragment bottomSheetHandelNotifyFragment = new BottomSheetHandelNotifyFragment();
        bottomSheetHandelNotifyFragment.roomName = roomName;
        bottomSheetHandelNotifyFragment.bottomSheetNotifyCallBack = bottomSheetNotifyCallBack;
        return bottomSheetHandelNotifyFragment;
    }

    private TextView tvContent;
    private LinearLayout llJoinGroup;
    private LinearLayout llRemoveNotify;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        innitView(view);
        addEvents();
    }

    private void innitView(View view) {
        tvContent = view.findViewById(R.id.tv_content_message);
        llJoinGroup = view.findViewById(R.id.ll_join_group);
        llRemoveNotify = view.findViewById(R.id.ll_remove_notify);

        Spanned content = Html.fromHtml(
                "<html>Bạn có muốn tham gia vào nhóm <b>" + roomName + "</b> để chia sẻ vị trí ?</html>"
        );
        tvContent.setText(content);
    }

    private void addEvents() {
        llJoinGroup.setOnClickListener(this);
        llRemoveNotify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_join_group:
                if (bottomSheetNotifyCallBack != null) {
                    dismiss();
                    bottomSheetNotifyCallBack.onClickJoinGroup();
                }
                break;
            case R.id.ll_remove_notify:
                if (bottomSheetNotifyCallBack != null) {
                    dismiss();
                    bottomSheetNotifyCallBack.onClickRemoveNotify();
                }
                break;
        }
    }

    public interface BottomSheetNotifyCallBack {

        void onClickJoinGroup();

        void onClickRemoveNotify();

    }
}
