package com.svmc.android.locationsupportteam.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.network.ApiConfig;
import com.svmc.android.locationsupportteam.ui.customviews.CircleImageView;
import com.svmc.android.locationsupportteam.utils.CommonUtils;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by TUNGTS on 4/18/2019
 */

public class ItemMemberToAddAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List items = new ArrayList();

    private RecycleViewClickListener onRecycleViewClickListener;

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public RecycleViewClickListener getOnRecycleViewClickListener() {
        return onRecycleViewClickListener;
    }

    public void setOnRecycleViewClickListener(RecycleViewClickListener onRecycleViewClickListener) {
        this.onRecycleViewClickListener = onRecycleViewClickListener;
    }

    private boolean isLoading = false;

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case Constans.AdapterItem.ITEM_VIEW_ADD_MEMBER:
                return new ItemMemberToAddViewHolder(
                        LayoutInflater.from(
                                viewGroup.getContext()
                        ).inflate(R.layout.item_member_to_add, viewGroup, false)
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
        if (viewHolder instanceof ItemMemberToAddViewHolder) {
            if (items.get(i) instanceof MemberOfRoomLocation) ((ItemMemberToAddViewHolder) viewHolder).bind((MemberOfRoomLocation) items.get(i));
            else {
                ((ItemMemberToAddViewHolder) viewHolder).bind((User) items.get(i));
            }
        } else if (viewHolder instanceof ItemTitleViewholder) {
            ((ItemTitleViewholder) viewHolder).bind((String) items.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof User || items.get(position) instanceof MemberOfRoomLocation) {
            return Constans.AdapterItem.ITEM_VIEW_ADD_MEMBER;
        } else if (items.get(position) instanceof String) {
            return Constans.AdapterItem.ITEM_TITLE;
        } else {
            return Constans.AdapterItem.ITEM_VIEW_LOAD_MORE;
        }
    }

    protected class ItemMemberToAddViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imgProfile;
        private TextView tvDisplayName;
        private TextView tvEmail;
        private TextView btnAdd;

        public ItemMemberToAddViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.img_profile);
            tvDisplayName = itemView.findViewById(R.id.tv_display_name);
            tvEmail = itemView.findViewById(R.id.tv_email);
            btnAdd = itemView.findViewById(R.id.tv_add_member);
        }

        void bind(User user) {

            // recent search
            tvDisplayName.setText(((User) user).getDisplayName());
            tvEmail.setText(((User) user).getEmail());
            GlideUtils.loadImageByPath(
                    itemView.getContext(),
                    imgProfile,
                    user.getUrlImage() != null ? ApiConfig.ConfigUrl.URL_NODEJS + user.getUrlImage() : CommonUtils.randomUrlImg(getAdapterPosition())
            );

            btnAdd.setVisibility(View.GONE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecycleViewClickListener.onClick(getAdapterPosition());
                }
            });

        }

        void bind(MemberOfRoomLocation member) {
            // result search
            GlideUtils.loadImageByPath(
                    itemView.getContext(),
                    imgProfile,
                    member.getUrlProfile() != null ? ApiConfig.ConfigUrl.URL_NODEJS + member.getUrlProfile() : CommonUtils.randomUrlImg(getAdapterPosition())
            );
            tvDisplayName.setText(member.getDisplayName());
            switch (member.getStatus()) {
                case MemberOfRoomLocation.MEMBER_LEFT:
                    tvEmail.setText(((MemberOfRoomLocation) member).getEmail());
                    btnAdd.setBackgroundResource(R.drawable.bg_btn_primary);
                    btnAdd.setTextColor(Color.WHITE);
                    btnAdd.setText("Thêm");
                    break;
                case MemberOfRoomLocation.MEMBER_INVITED:
                    btnAdd.setText("Hủy");
                    btnAdd.setTextColor(Color.BLACK);
                    btnAdd.setBackgroundResource(R.drawable.bg_remove_user_room);
                    tvEmail.setText("Đang chờ phản hồi");
                    break;
            }

            btnAdd.setVisibility(View.VISIBLE);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecycleViewClickListener != null) {
                        onRecycleViewClickListener.onClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    protected class ItemTitleViewholder extends RecyclerView.ViewHolder {

        private TextView tvTitle;

        public ItemTitleViewholder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }

        void bind(String title){
            tvTitle.setText(title);
        }
    }

    protected class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
