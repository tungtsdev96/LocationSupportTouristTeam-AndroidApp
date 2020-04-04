package com.svmc.android.locationsupportteam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.ui.customviews.CircleImageView;
import com.svmc.android.locationsupportteam.utils.CommonUtils;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.GlideUtils;
import com.svmc.android.locationsupportteam.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/21/2019
 */

public class ItemMemberOnOffAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List items = new ArrayList();

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    private RecycleViewClickListener onRecycleViewClickListener;

    public RecycleViewClickListener getOnRecycleViewClickListener() {
        return onRecycleViewClickListener;
    }

    public void setOnRecycleViewClickListener(RecycleViewClickListener onRecycleViewClickListener) {
        this.onRecycleViewClickListener = onRecycleViewClickListener;
    }

    private boolean isAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case Constans.AdapterItem.ITEM_VIEW_MEMBER_ON_OFF:
                return new ItemMeberOnOffViewHolder(
                        LayoutInflater.from(
                                viewGroup.getContext()
                        ).inflate(R.layout.item_member_online_offline, viewGroup, false)
                );
            case Constans.AdapterItem.ITEM_TITLE:
                return new ItemTitleViewholder(
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
            default:
                return new LoadMoreViewHolder(
                        LayoutInflater.from(
                                viewGroup.getContext()
                        ).inflate(R.layout.item_load_more, viewGroup, false)
                );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ItemMeberOnOffViewHolder) {
            ((ItemMeberOnOffViewHolder) viewHolder).bind((MemberOfRoomLocation) items.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof MemberOfRoomLocation) {
            return Constans.AdapterItem.ITEM_VIEW_MEMBER_ON_OFF;
        } else if (items.get(position) instanceof String) {
            return Constans.AdapterItem.ITEM_TITLE;
        } else {
            return Constans.AdapterItem.ITEM_VIEW_LOAD_MORE;
        }
    }

    protected class ItemTitleViewholder extends RecyclerView.ViewHolder {

        public ItemTitleViewholder(@NonNull View itemView) {
            super(itemView);
        }
    }

    protected class ItemMeberOnOffViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imgAvatar;
        private View vStatus;
        private FrameLayout frStatus;
        private TextView tvDisplayName;
        private TextView tvLastTimeOnLine;
        private ImageView imgMore;
        private ImageView imgCall;

        public ItemMeberOnOffViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.img_avatar);
            vStatus = itemView.findViewById(R.id.v_user_status);
            frStatus = itemView.findViewById(R.id.fr_status);
            tvDisplayName = itemView.findViewById(R.id.tv_display_name);
            tvLastTimeOnLine = itemView.findViewById(R.id.tv_last_time_online);
            imgMore = itemView.findViewById(R.id.img_more);
            imgCall = itemView.findViewById(R.id.img_call);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecycleViewClickListener != null) {
                        onRecycleViewClickListener.onClick(getAdapterPosition());
                    }
                }
            });

            imgCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickCallAction != null) {
                        onClickCallAction.clickCall(getAdapterPosition());
                    }
                }
            });
        }

        void bind(MemberOfRoomLocation memberOfRoomLocation) {

            GlideUtils.loadImageByPath(
                    itemView.getContext(),
                    imgAvatar,
                    CommonUtils.randomUrlImg(getAdapterPosition())
            );
            tvDisplayName.setText(memberOfRoomLocation.getDisplayName());

            if (memberOfRoomLocation.getAction().equals("true")) {
                imgMore.setVisibility(View.VISIBLE);
                imgMore.setImageResource(R.drawable.ic_warning);
            } else {
                imgMore.setImageDrawable(null);
                imgMore.setVisibility(View.GONE);
            }

            switch (memberOfRoomLocation.getStatus()) {
                case MemberOfRoomLocation.MEMBER_INVITED:
                    imgCall.setVisibility(View.GONE);
                    vStatus.setVisibility(View.GONE);
                    frStatus.setVisibility(View.GONE);
                    tvLastTimeOnLine.setVisibility(View.VISIBLE);
                    tvLastTimeOnLine.setText("Đang chờ phản hồi");
                    break;
                case MemberOfRoomLocation.MEMBER_JOINED:
                    vStatus.setVisibility(View.VISIBLE);
                    vStatus.setBackgroundResource(R.drawable.bg_user_offline);
                    frStatus.setVisibility(View.VISIBLE);
                    if (memberOfRoomLocation.getUserLocation() != null) {
                        if (memberOfRoomLocation.getUserLocation().isOnline()) {
                            vStatus.setBackgroundResource(R.drawable.bg_user_online);
                            tvLastTimeOnLine.setVisibility(View.GONE);
                        } else {
                            vStatus.setBackgroundResource(R.drawable.bg_user_offline);
                            tvLastTimeOnLine.setVisibility(View.VISIBLE);
                            tvLastTimeOnLine.setText(
                                    TimeUtil.setTextTimeNew(memberOfRoomLocation.getUserLocation().getLastTimeOnline())
                            );
                        }
                    }
                    break;
            }

        }
    }

    protected class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public ClickCallAction getOnClickCallAction() {
        return onClickCallAction;
    }

    public void setOnClickCallAction(ClickCallAction onClickCallAction) {
        this.onClickCallAction = onClickCallAction;
    }

    private ClickCallAction onClickCallAction;

    public interface ClickCallAction {
        void clickCall(int pos);
    }

}
