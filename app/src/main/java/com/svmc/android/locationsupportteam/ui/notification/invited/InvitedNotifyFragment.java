package com.svmc.android.locationsupportteam.ui.notification.invited;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.sweetdialog.SweetAlertDialog;
import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.ItemNotificationAdapter;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.listeners.SweetAlertDialogListener;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.auth.SignInActivity;
import com.svmc.android.locationsupportteam.ui.roomlocation.RoomLocationActivity;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.ErrCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TungTS on 5/14/2019
 */

public class InvitedNotifyFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, MVPInvitedNotification.IViewInvitedNotification {

    private MVPInvitedNotification.IPresenterInvitedNotification presenterNotification;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rcvNotifications;
    private List items;
    private ItemNotificationAdapter notificationAdapter;

    public static InvitedNotifyFragment newInstancce() {
        InvitedNotifyFragment invitedNotifyFragment = new InvitedNotifyFragment();
        invitedNotifyFragment.setTAG(Constans.TagFragment.INVITED_NOTIFICATION_FRAGMENT);
        return invitedNotifyFragment;
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
    }

    @Override
    protected void innitView(View view) {
        innitRecycleView(view);
    }

    @Override
    protected void addEvents() {
        swipeRefreshLayout.setOnRefreshListener(this);
        notificationAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
            @Override
            public void onClick(int position) {
                if (items.get(position) instanceof String) return;
                RoomLocationNotification notification = (RoomLocationNotification) items.get(position);
                presenterNotification.handleClickNotify(position);
            }
        });
    }

    private void innitRecycleView(View view) {
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
        rcvNotifications.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvNotifications.setAdapter(notificationAdapter);
    }

    @Override
    public void onRefresh() {
        presenterNotification.getListNotify();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenterNotification.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setPresenter(MVPInvitedNotification.IPresenterInvitedNotification iPresenterInvitedNotification) {
        if (iPresenterInvitedNotification != null) {
            this.presenterNotification = iPresenterInvitedNotification;
        }
    }

    @Override
    public void onLoadNotifyDone(ArrayList<RoomLocationNotification> notifications) {
        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
        items.clear();
        notificationAdapter.notifyDataSetChanged();
        if (notifications == null || notifications.isEmpty()) return;

        for (RoomLocationNotification notify: notifications) {
            String roomId = AppPreferencens.getInstance().getRoomId();
            if (!notify.getRoom().getRoomId().equals(roomId)) {
                items.add(notify);
            }
        }

        if (items.size() > 0) items.add(0, "Gần đây");
        notificationAdapter.notifyDataSetChanged();
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
                                    presenterNotification.getListNotify();
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
    public void showBottomSheet(final int pos) {
        final RoomLocationNotification notification = (RoomLocationNotification) items.get(pos);
        BottomSheetHandelNotifyFragment.newInstance(notification.getRoom().getName(), new BottomSheetHandelNotifyFragment.BottomSheetNotifyCallBack() {
            @Override
            public void onClickJoinGroup() {
                showSweetProgressDialog("Đang lưu");
                presenterNotification.joinRoom(pos, notification);
            }

            @Override
            public void onClickRemoveNotify() {
                showSweetProgressDialog("Đang xóa");
                presenterNotification.removeInvitation(pos, notification);
            }
        }).show(getChildFragmentManager(), "BottomSheet");
    }

    @Override
    public void onRemoveSuccess(int pos) {
        items.remove(pos);
        notificationAdapter.notifyItemRemoved(pos);
        if (items.size() == 1) {
            items.clear();
            notificationAdapter.notifyDataSetChanged();
        }
        hideSweetProgressDialog(true, "Đã xóa");
    }

    @Override
    public void onJoinRoomSuccess(int pos) {
        RoomLocationNotification notification = (RoomLocationNotification) items.get(pos);
        final RoomLocation roomLocation = new RoomLocation();
        roomLocation.setNameRoom(notification.getRoom().getName());
        roomLocation.setRoomId(notification.getRoom().getRoomId());
        AppPreferencens.getInstance().setRoomId(notification.getRoom().getRoomId());

        items.remove(pos);
        notificationAdapter.notifyItemRemoved(pos);

        hideSweetProgressDialog(true, "Xong", new SweetAlertDialogListener() {
            @Override
            public void onConfirmClicked(SweetAlertDialog dialog) {
                dialog.dismiss();
                Intent i = new Intent(getContext(), RoomLocationActivity.class);
                i.putExtra(Constans.KeyBundle.CURRENT_FRAG_ON_ROOM_LOCATION, 1);
                i.putExtra(Constans.KeyBundle.ROOM_LOCATION, new Gson().toJson(roomLocation));
                startActivity(i);
                getActivity().finish();
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            @Override
            public void onCancelClicked(SweetAlertDialog dialog) {

            }
        });
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
}
