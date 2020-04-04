package com.svmc.android.locationsupportteam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.network.ApiConfig;
import com.svmc.android.locationsupportteam.ui.customviews.CircleImageView;
import com.svmc.android.locationsupportteam.ui.customviews.drawable.ShapeDrawableUtils;
import com.svmc.android.locationsupportteam.utils.CommonUtils;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.GlideUtils;
import com.svmc.android.locationsupportteam.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by TungTS on 5/2/2019
 */

public class ItemNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecycleViewClickListener onRecycleViewClickListener;

    public void setOnRecycleViewClickListener(RecycleViewClickListener onRecycleViewClickListener) {
        this.onRecycleViewClickListener = onRecycleViewClickListener;
    }

    private List items = new ArrayList();

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case Constans.AdapterItem.ITEM_TITLE:
                return new ItemTitleViewHolder(
                        LayoutInflater.from(
                                viewGroup.getContext()
                        ).inflate(R.layout.item_text_title, viewGroup, false)
                );
            case Constans.AdapterItem.ITEM_VIEW_LOAD_MORE:
                return new LoadMoreViewHolder(
                        LayoutInflater.from(
                                viewGroup.getContext()
                        ).inflate(R.layout.item_load_more, viewGroup, false)
                );
            case Constans.AdapterItem.ITEM_VIEW_ROOM_NOTIFY:
                return new ItemNotificationViewHolder(
                        LayoutInflater.from(
                                viewGroup.getContext()
                        ).inflate(R.layout.item_notification, viewGroup, false)
                );
            case Constans.AdapterItem.ITEM_VIEW_ROOM_NOTIFY_SHARE:
                return new ItemNotificationShareViewHolder(
                        LayoutInflater.from(
                                viewGroup.getContext()
                        ).inflate(R.layout.item_notification_share, viewGroup, false)
                );
            case Constans.AdapterItem.ITEM_VIEW_ROOM_NOTIFY_ALERT:
                return new ItemNotificationAlertViewHolder(
                        LayoutInflater.from(
                                viewGroup.getContext()
                        ).inflate(R.layout.item_notification_alert, viewGroup, false)
                );
            default:
                return new ItemLoadingNotification(
                        LayoutInflater.from(
                                viewGroup.getContext()
                        ).inflate(R.layout.item_notification_loading, viewGroup, false)
                );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ItemNotificationViewHolder) {
            ((ItemNotificationViewHolder) viewHolder).bind((RoomLocationNotification) items.get(i));
        } else if (viewHolder instanceof ItemTitleViewHolder) {
            ((ItemTitleViewHolder) viewHolder).bind((String) items.get(i));
        } else if (viewHolder instanceof ItemNotificationAlertViewHolder) {
            ((ItemNotificationAlertViewHolder) viewHolder).bind((RoomLocationNotification) items.get(i));
        } else if (viewHolder instanceof  ItemNotificationShareViewHolder) {
            ((ItemNotificationShareViewHolder) viewHolder).bind((RoomLocationNotification) items.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) == null) {
            return Constans.AdapterItem.ITEM_VIEW_LOAD_MORE;
        } else if (items.get(position) instanceof String) {
            return Constans.AdapterItem.ITEM_TITLE;
        } else if (items.get(position) instanceof RoomLocationNotification) {
            if (((RoomLocationNotification) items.get(position)).getType().equals(RoomLocationNotification.STATUS_ALERT)
                    || ((RoomLocationNotification) items.get(position)).getType().equals(RoomLocationNotification.STATUS_SOS)) {
                return Constans.AdapterItem.ITEM_VIEW_ROOM_NOTIFY_ALERT;
            } else if (((RoomLocationNotification) items.get(position)).getType().equals(RoomLocationNotification.STATUS_SHARE_PLACE)) {
                return Constans.AdapterItem.ITEM_VIEW_ROOM_NOTIFY_SHARE;
            }
            return Constans.AdapterItem.ITEM_VIEW_ROOM_NOTIFY;
        }
        return Constans.AdapterItem.ITEM_VIEW_LOADING;
    }

    protected class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    protected class ItemLoadingNotification extends RecyclerView.ViewHolder {

        public ItemLoadingNotification(@NonNull View itemView) {
            super(itemView);
        }
    }

    protected class ItemTitleViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;

        public ItemTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTitle.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    itemView.getContext().getResources().getDimension(R.dimen.text_size_title)
            );
        }

        void bind(String title) {
            tvTitle.setText(title);
        }
    }

    protected class ItemNotificationViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView civProfile;
        private TextView tvContent;
        private TextView tvLatsTimeCreated;
        private ImageView btnMore;

        public ItemNotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            civProfile = itemView.findViewById(R.id.civ_profile);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvLatsTimeCreated = itemView.findViewById(R.id.tv_last_time_created);
            btnMore = itemView.findViewById(R.id.btn_more);
        }

        void bind(RoomLocationNotification notification) {

            String urlImage;
            urlImage = CommonUtils.randomUrlImg(getAdapterPosition());
            GlideUtils.loadImageByPath(
                    itemView.getContext(),
                    civProfile,
                    urlImage
            );

            String nameSender = notification.getSenderUser().getDisplayName();
            String nameGroup = notification.getRoom().getName();
            Spanned content = null;

            switch (notification.getType()) {
                case RoomLocationNotification.STATUS_INVITED:
                    content = Html.fromHtml(
                            "<html><b>" + nameSender + "</b> đã đã mời bạn tham gia vào nhóm " +
                                    "<b>" + nameGroup + "</b> để chia sẻ vị trí </html>"
                    );
                    btnMore.setImageResource(R.drawable.ic_more_black);

                    btnMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onRecycleViewClickListener != null) {
                                onRecycleViewClickListener.onClick(getAdapterPosition());
                            }
                        }
                    });

                    break;
                case RoomLocationNotification.STATUS_SHARE_PLACE:
                    content = Html.fromHtml(
                            "<html><b>" + nameSender + "</b> đã chia sẻ một điểm trong nhóm </html>"
                    );
                    btnMore.setImageResource(R.drawable.ic_more_black);

                    btnMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onClickBtnRight != null) {
                                onClickBtnRight.onClick(getAdapterPosition());
                            }
                        }
                    });

                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onRecycleViewClickListener != null) {
                                onRecycleViewClickListener.onClick(getAdapterPosition());
                            }
                        }
                    });
                    break;
            }

            tvContent.setText(content);
            tvLatsTimeCreated.setText(TimeUtil.setTextTimeNew(notification.getCreatedTime()));
            tvLatsTimeCreated.setTextColor(
                    notification.getReceiverUser().isRead()
                            ? itemView.getContext().getResources().getColor(R.color.text_color_default)
                            : itemView.getContext().getResources().getColor(R.color.color_primary_dark_blue)
            );
        }
    }

    protected class ItemNotificationShareViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView civProfile;
        private TextView tvContent;
        private TextView tvLatsTimeCreated;
        private ImageView btnMore;

        private TextView tvMessage;
        private ImageView imgAttach;

        public ItemNotificationShareViewHolder(@NonNull View itemView) {
            super(itemView);

            civProfile = itemView.findViewById(R.id.civ_profile);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvLatsTimeCreated = itemView.findViewById(R.id.tv_last_time_created);
            btnMore = itemView.findViewById(R.id.btn_more);

            tvMessage = itemView.findViewById(R.id.tv_mesage);
            imgAttach = itemView.findViewById(R.id.img_attach);
        }

        void bind(RoomLocationNotification notification) {

            String urlImage;
            urlImage = CommonUtils.randomUrlImg(getAdapterPosition());
            GlideUtils.loadImageByPath(
                    itemView.getContext(),
                    civProfile,
                    urlImage
            );

            String nameSender = notification.getSenderUser().getDisplayName();
            String nameGroup = notification.getRoom().getName();
            Spanned content = null;


            content = Html.fromHtml(
                    "<html><b>" + nameSender + "</b> đã chia sẻ một điểm trong nhóm </html>"
            );
            btnMore.setImageResource(R.drawable.ic_more_black);

            btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickBtnRight != null) {
                        onClickBtnRight.onClick(getAdapterPosition());
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecycleViewClickListener != null) {
                        onRecycleViewClickListener.onClick(getAdapterPosition());
                    }
                }
            });

            tvContent.setText(content);
            tvLatsTimeCreated.setText(TimeUtil.setTextTimeNew(notification.getCreatedTime()));
            tvLatsTimeCreated.setTextColor(
                    notification.getReceiverUser().isRead()
                            ? itemView.getContext().getResources().getColor(R.color.text_color_default)
                            : itemView.getContext().getResources().getColor(R.color.color_primary_dark_blue)
            );

            if (notification.getMessage() != null) {
                tvMessage.setVisibility(View.VISIBLE);
                tvMessage.setText(
                        Html.fromHtml("<html><b>Tin nhắn: </b>" + notification.getMessage() + "</html>")
                );
            } else {
                tvMessage.setVisibility(View.GONE);
            }

            if (notification.getImage() != null) {
                imgAttach.setVisibility(View.VISIBLE);
                GlideUtils.loadImageWrapContent(
                        itemView.getContext(),
                        imgAttach,
                        ApiConfig.ConfigUrl.URL_NODEJS + notification.getImage(),
                        CommonUtils.dpToPx(180)
                );
            } else {
                tvMessage.setVisibility(View.GONE);
                imgAttach.setVisibility(View.GONE);
            }
        }
    }

    protected class ItemNotificationAlertViewHolder extends RecyclerView.ViewHolder {

        private TextView tvStatus;
        private TextView tvContent;
        private TextView tvCreatedTime;
        private ImageView img;
        private ImageView imgMore;

        public ItemNotificationAlertViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvContent = itemView.findViewById(R.id.tv_content_message);
            img = itemView.findViewById(R.id.img_point);
            imgMore = itemView.findViewById(R.id.img_more);
            tvCreatedTime = itemView.findViewById(R.id.tv_last_time_created);
        }

        void bind(RoomLocationNotification notification) {
            tvCreatedTime.setText(TimeUtil.setTextTimeNew(notification.getCreatedTime()));
            String urlImage = notification.getImage();
            img.setVisibility(
                    urlImage != null ? View.VISIBLE : View.GONE
            );
            if (urlImage != null) {
                GlideUtils.loadImageWrapContent(
                        itemView.getContext(),
                        img,
                        ApiConfig.ConfigUrl.URL_NODEJS + urlImage,
                        CommonUtils.dpToPx(180)
                );
            }

            String nameSender = notification.getSenderUser().getDisplayName();
            Spanned content = null;

            tvStatus.setVisibility(View.VISIBLE);

            content = Html.fromHtml(
                    "<html><b>" + nameSender + "</b> " + notification.getMessage() + " </html>"
            );

            tvContent.setText(content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecycleViewClickListener != null) {
                        onRecycleViewClickListener.onClick(getAdapterPosition());
                    }
                }
            });

            tvStatus.setText(
                    notification.isResolve() ? "Đã xử lý" : "Chưa được xử lý"
            );

            tvStatus.setBackgroundResource(
                    notification.isResolve() ? R.drawable.bg_btn_primary : R.drawable.bg_leave_group
            );

            imgMore.setImageResource(
                    notification.isResolve() ? R.drawable.ic_delete_black : R.drawable.ic_more_black
            );

            imgMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickBtnRight != null) {
                        onClickBtnRight.onClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    private ClickBtnRight onClickBtnRight;

    public void setOnClickBtnRight(ClickBtnRight onClickBtnRight) {
        this.onClickBtnRight = onClickBtnRight;
    }

    public interface ClickBtnRight {
        void onClick(int pos);
    }

}
