package com.svmc.android.locationsupportteam.ui.notification.room.shareplace;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sweetdialog.SweetAlertDialog;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.ItemNotificationAdapter;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.listeners.SweetAlertDialogListener;
import com.svmc.android.locationsupportteam.network.ApiConfig;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.auth.SignInActivity;
import com.svmc.android.locationsupportteam.ui.detailplace.PlaceActivity;
import com.svmc.android.locationsupportteam.ui.notification.room.MVPRoomNotification;
import com.svmc.android.locationsupportteam.ui.roomlocation.RoomLocationActivity;
import com.svmc.android.locationsupportteam.utils.CommonUtils;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.ErrCode;
import com.svmc.android.locationsupportteam.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TungTS on 5/14/2019
 */

public class ShareNotificationFragment extends BaseFragment implements MVPRoomNotification.IViewRoomNotification, SwipeRefreshLayout.OnRefreshListener {

    private MVPRoomNotification.IPresenterRoomNotification presenterShareNotification;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rcvNotifications;
    private List items;
    private ItemNotificationAdapter notificationAdapter;

    public static ShareNotificationFragment newInstancce() {
        ShareNotificationFragment fragment = new ShareNotificationFragment();
        fragment.setTAG(Constans.TagFragment.SHARED_NOTIFICATION_FRAGMENT);
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
        rcvNotifications.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvNotifications.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
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
                showBottomSheetControl(position);
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

    private void showBottomSheetControl(final int position) {
        final RoomLocationNotification notification = (RoomLocationNotification) items.get(position);
        BottomSheetNotifyShareFragment.newInstance(new BottomSheetNotifyShareFragment.BottomSheetCallBack() {
            @Override
            public void onShowDetailPlace() {
                Intent i = PlaceActivity.setIntentDataDetail(getActivity(), notification.getPlaceId());
                startActivity(i);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            @Override
            public void showOnRoom() {
                if (notification.getPlaceId() != null) {
                    openUIDetail(notification);
                } else {
                    Toast.makeText(getContext(), "Không tìm thấy chi tiết về địa điểm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDeleteNotify() {
                presenterShareNotification.removeNotify(position, notification.getNotifyId());
            }

            @Override
            public void onShowImage() {
                if (notification.getImage() != null) {
                    openDialogShowImage(notification);
                } else {
                    Toast.makeText(getContext(), "Không có ảnh được đính kèm", Toast.LENGTH_SHORT).show();
                }
            }
        }).show(getChildFragmentManager(), "BottomSheetNotifyShare");
    }

    private void openDialogShowImage(RoomLocationNotification notification) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Ảnh kèm theo vị trí được chia sẻ");
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.dialog_show_image, null);

        ImageView image = dialog.findViewById(R.id.img);

        GlideUtils.loadImageWrapContent(
                getContext(),
                image,
                ApiConfig.ConfigUrl.URL_NODEJS + notification.getImage(),
                CommonUtils.dpToPx(180)
        );

        builder.setView(dialog);

        builder.setNegativeButton("Trở về", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.create().show();
    }

    private void openUIDetail(RoomLocationNotification notification) {
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
        items.add("Gần đây");
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
                                    presenterShareNotification.getListNotify();
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
    public void setPresenter(MVPRoomNotification.IPresenterRoomNotification iPresenterShareNotification) {
        if (iPresenterShareNotification != null) {
            this.presenterShareNotification = iPresenterShareNotification;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenterShareNotification.start();
    }

    @Override
    public void onRefresh() {
        presenterShareNotification.getListNotify();
    }
}
