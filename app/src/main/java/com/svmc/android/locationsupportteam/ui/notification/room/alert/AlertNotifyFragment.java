package com.svmc.android.locationsupportteam.ui.notification.room.alert;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.sweetdialog.SweetAlertDialog;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.ItemNotificationAdapter;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.listeners.SweetAlertDialogListener;
import com.svmc.android.locationsupportteam.ui.auth.SignInActivity;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.notification.room.MVPRoomNotification;
import com.svmc.android.locationsupportteam.ui.roomlocation.RoomLocationActivity;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.ErrCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by TungTS on 6/8/2019
 */

public class AlertNotifyFragment extends BaseFragment implements MVPRoomNotification.IViewRoomNotification, SwipeRefreshLayout.OnRefreshListener {

    private MVPRoomNotification.IPresenterRoomNotification presenterAlertNotification;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rcvNotifications;
    private List items;
    private ItemNotificationAdapter notificationAdapter;

    public static AlertNotifyFragment newInstancce() {
        AlertNotifyFragment fragment = new AlertNotifyFragment();
        fragment.setTAG(Constans.TagFragment.ALERT_NOTIFICATION_FRAGMENT);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_notification;
    }

    @Override
    protected void onFragmentCreateView(View view) {

    }

    @Override
    protected void onFragmentCreated(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_notification);
        swipeRefreshLayout.setRefreshing(true);

        rcvNotifications = view.findViewById(R.id.rcv_notifications);
        items = new ArrayList();
        for (int i = 0; i < 10; i++) {
            items.add(i);
        }
        notificationAdapter = new ItemNotificationAdapter();
        notificationAdapter.setItems(items);
        rcvNotifications.setEnabled(true);
        rcvNotifications.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rcvNotifications.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvNotifications.setAdapter(notificationAdapter);
    }

    @Override
    protected void innitView(View view) {
    }

    @Override
    protected void addEvents() {
        swipeRefreshLayout.setOnRefreshListener(this);
        notificationAdapter.setOnClickBtnRight(new ItemNotificationAdapter.ClickBtnRight() {
            @Override
            public void onClick(int position) {
                RoomLocationNotification notification = (RoomLocationNotification) items.get(position);
                if (notification.isResolve()) {
                    items.remove(position);
                    notificationAdapter.notifyItemRemoved(position);
                    Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
                } else {
                    showBottomSheet(position);
                }
            }
        });

        notificationAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
            @Override
            public void onClick(int position) {
                RoomLocationNotification notification = (RoomLocationNotification) items.get(position);
                openUIDetail(notification);
            }
        });
    }

    private void showBottomSheet(final int position) {
        BottomSheetAlertNotify bottomSheetAlertNotify = BottomSheetAlertNotify.newInstance(new BottomSheetAlertNotify.BottomSheetCallBack() {
            @Override
            public void onClickSolve() {
                String senderId = ((RoomLocationNotification) items.get(position)).getSenderUser().getUserId();
                presenterAlertNotification.resolveAlert(senderId);
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        bottomSheetAlertNotify.show(getFragmentManager(), "AlertNotify");
    }

    private void openUIDetail(RoomLocationNotification notification) {
        if (notification.isResolve()) return;
        Intent i = RoomLocationActivity.innitIntent(
                getActivity(),
                RoomLocationActivity.DETAIL_ROOM,
                new RoomLocation(notification.getRoom().getRoomId(), notification.getRoom().getName()),
                notification.getType(),
                notification.getPoint(),
                notification.getSenderUser().getUserId(),
                notification.getImage()
        );
        getActivity().startActivity(i);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onLoadNotifyDone(ArrayList<RoomLocationNotification> notifications) {
        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
        items.clear();
        notificationAdapter.notifyDataSetChanged();
        if (notifications == null || notifications.isEmpty()) return;
        items.addAll(notifications);
        notificationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRemoveDone(int pos) {
        items.remove(pos);
        notificationAdapter.notifyItemRemoved(pos);
        if (items.size() == 1) {
            items.clear();
            notificationAdapter.notifyDataSetChanged();
        }
        Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErr(int errCode) {
        try {
            switch (errCode) {
                case ErrCode.CommonCode.ERR_SERVER:
                    showSweetAlert(
                            "Thông báo",
                            "Đã có lỗi xảy ra. Xin hãy thử lại",
                            null,
                            "Ok",
                            SweetAlertDialog.WARNING_TYPE,
                            new SweetAlertDialogListener() {
                                @Override
                                public void onConfirmClicked(SweetAlertDialog dialog) {
                                    dialog.dismiss();
                                    presenterAlertNotification.getListNotify();
                                }

                                @Override
                                public void onCancelClicked(SweetAlertDialog dialog) {

                                }
                            });
                    break;
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onInvalidToken() {
        showSweetAlert(
                "Thông báo",
                "Đã có lỗi xảy ra hãy đăng nhập lại",
                null,
                "Ok",
                SweetAlertDialog.WARNING_TYPE,
                new SweetAlertDialogListener() {
                    @Override
                    public void onConfirmClicked(SweetAlertDialog dialog) {
                        startActivity(new Intent(getContext(), SignInActivity.class));
                        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }

                    @Override
                    public void onCancelClicked(SweetAlertDialog dialog) {

                    }
                });
    }

    @Override
    public void setPresenter(MVPRoomNotification.IPresenterRoomNotification iPresenterRoomNotification) {
        if (iPresenterRoomNotification != null) {
            this.presenterAlertNotification = iPresenterRoomNotification;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenterAlertNotification.start();
    }

    @Override
    public void onRefresh() {
        presenterAlertNotification.getListNotify();
    }

}
