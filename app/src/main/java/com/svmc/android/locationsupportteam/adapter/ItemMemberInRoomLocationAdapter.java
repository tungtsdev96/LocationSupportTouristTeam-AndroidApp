package com.svmc.android.locationsupportteam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ItemMemberInRoomLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == Constans.AdapterItem.ITEM_VIEW_MEMBER_IN_GROUP) {
            return new ItemMemberInGroupViewHolder(
                    LayoutInflater.from(
                            viewGroup.getContext()
                    ).inflate(R.layout.item_member, viewGroup, false)
            );
        } else {
            return new LoadMoreViewHolder(
                    LayoutInflater.from(
                            viewGroup.getContext()
                    ).inflate(R.layout.item_load_more, viewGroup, false)
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ItemMemberInGroupViewHolder) {
            ((ItemMemberInGroupViewHolder) viewHolder).bind((MemberOfRoomLocation) items.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof MemberOfRoomLocation) {
            return Constans.AdapterItem.ITEM_VIEW_MEMBER_IN_GROUP;
        } else {
            return Constans.AdapterItem.ITEM_VIEW_LOAD_MORE;
        }
    }

    protected class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    protected class ItemMemberInGroupViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imgAvatar;
        private TextView tvDisplayName;
        private TextView tvLastTimeOnline;
        private ImageView imgMore;
        private TextView btnRemove;

        public ItemMemberInGroupViewHolder(@NonNull final View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            tvDisplayName = itemView.findViewById(R.id.tv_display_name);
            tvLastTimeOnline = itemView.findViewById(R.id.tv_last_time_online);
            imgMore = itemView.findViewById(R.id.img_more_member);
            btnRemove = itemView.findViewById(R.id.btn_remove);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onRecycleViewClickListener != null) {
//                        onRecycleViewClickListener.onClick(getAdapterPosition());
//                    }
//                }
//            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecycleViewClickListener != null) {
                        onRecycleViewClickListener.onClick(getAdapterPosition());
                    }
                }
            });

        }

        void bind(MemberOfRoomLocation member) {

            GlideUtils.loadImageByPath(
                    itemView.getContext(),
                    imgAvatar,
                    CommonUtils.randomUrlImg(getAdapterPosition())
            );

            tvDisplayName.setText(member.getDisplayName());

            if (member.getStatus() == MemberOfRoomLocation.MEMBER_INVITED) {
                tvLastTimeOnline.setVisibility(View.VISIBLE);
                tvLastTimeOnline.setText("Đang chờ phản hồi");
            } else {
                tvLastTimeOnline.setVisibility(View.GONE);
                if (member.getUserLocation() != null) {
                    tvLastTimeOnline.setText(TimeUtil.setTextTimeNew(member.getUserLocation().getLastTimeOnline()));
                }
            }
        }
    }

}
