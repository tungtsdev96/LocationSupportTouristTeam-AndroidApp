package com.svmc.android.locationsupportteam.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.group.chat.Message;
import com.svmc.android.locationsupportteam.firebase.FirebaseUtils;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.utils.CommonUtils;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.ui.customviews.drawable.ShapeDrawableUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AdapterItem> items = new ArrayList();

    public List getItems() {
        return items;
    }

    public void addItems(List items) {
        for (int i = 0; i < items.size(); i++) {
            Object item = items.get(i);
            if (item instanceof Message) {
                addItem((Message) item);
                continue;
            }

            this.items.add(null);
        }
    }

    public void addItem(Message message) {

        int size = this.items.size();

        if (size == 0 || !message.getUserId().equals(((ItemMessage) items.get(size - 1)).getMessage().getUserId())) {
            this.items.add(new ItemMessage(0, message));
        } else {
            ItemMessage itemMessage = (ItemMessage) items.get(size - 1);

            if (message.getUserId().equals(itemMessage.getMessage().getUserId())) {
                itemMessage.setType(1);
                this.items.add(new ItemMessage(3, message));
            }

            if (size >= 2) {
                ItemMessage itemPreMessage =  (ItemMessage) items.get(size - 2);
                if (message.getUserId().equals(itemPreMessage.getMessage().getUserId())) {
                    itemMessage.setType(2);
                    ((ItemMessage) this.items.get(size)).setType(3);
                }
            }
        }

    }

    private boolean isLoading = false;

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    private RecycleViewClickListener onRecycleViewClickListener;

    public RecycleViewClickListener getOnRecycleViewClickListener() {
        return onRecycleViewClickListener;
    }

    public void setOnRecycleViewClickListener(RecycleViewClickListener onRecycleViewClickListener) {
        this.onRecycleViewClickListener = onRecycleViewClickListener;
    }

    public ItemMessageAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case Constans.AdapterItem.ITEM_RIGHT_MESSAGE:
                return new ItemMessageRightViewHolder(
                        LayoutInflater.from(
                                viewGroup.getContext()
                        ).inflate(R.layout.item_message_right, viewGroup, false)
                );
            case Constans.AdapterItem.ITEM_LEFT_MESSAGE:
                return new ItemMessageLeftViewholder(
                        LayoutInflater.from(
                                viewGroup.getContext()
                        ).inflate(R.layout.item_message_left, viewGroup, false)
                );
            case Constans.AdapterItem.ITEM_VIEW_LOAD_MORE:
                return new LoadMoreViewholder(
                        LayoutInflater.from(
                                viewGroup.getContext()
                        ).inflate(R.layout.item_load_more, viewGroup, false)
                );
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ItemMessageLeftViewholder) {
            ((ItemMessageLeftViewholder) viewHolder).bind((ItemMessage) items.get(i));
        } else if (viewHolder instanceof ItemMessageRightViewHolder) {
            ((ItemMessageRightViewHolder) viewHolder).bind((ItemMessage) items.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (items.get(position) instanceof ItemMessage) {
            Message message = ((ItemMessage) items.get(position)).getMessage();
            if (message.getUserId().equals(FirebaseUtils.getFirebaseAuth().getUid())) {
                return Constans.AdapterItem.ITEM_RIGHT_MESSAGE;
            } else {
                return Constans.AdapterItem.ITEM_LEFT_MESSAGE;
            }
        } else {
            return Constans.AdapterItem.ITEM_VIEW_LOAD_MORE;
        }
    }

    protected class ItemMessage implements AdapterItem {

        private int type;

        private Message message;

        public ItemMessage(int type, Message message) {
            this.type = type;
            this.message = message;

        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }

    protected class ItemMessageRightViewHolder extends RecyclerView.ViewHolder {

        private TextView tvContentMessage;
        private RelativeLayout rltMessage;

        public ItemMessageRightViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContentMessage = itemView.findViewById(R.id.tv_content_message);
            rltMessage = itemView.findViewById(R.id.rlt_message_right);
        }

        void bind(ItemMessage itemMessage) {
            Drawable drawable = null;
            switch (itemMessage.getType()) {
                case 0:
                    drawable = ShapeDrawableUtils.getStateListDrawable(
                                CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                                CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                                CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                                CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                                itemView.getContext().getResources().getColor(R.color.green_500),
                                0);
                    tvContentMessage.setBackgroundDrawable(drawable);
                    setPadding(
                            0,
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_4)),
                            0,
                            0

                    );
                    break;
                case 1:
                    drawable = ShapeDrawableUtils.getStateListDrawable(
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_6)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            itemView.getContext().getResources().getColor(R.color.green_500),
                            0);
                    tvContentMessage.setBackgroundDrawable(drawable);
                    setPadding(
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_0_5)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_4)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_0_5)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_0_5))

                    );
                    break;
                case 2:
                    drawable = ShapeDrawableUtils.getStateListDrawable(
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_6)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_6)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            itemView.getContext().getResources().getColor(R.color.green_500),
                            0);
                    tvContentMessage.setBackgroundDrawable(drawable);
                    setPadding(
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_0_5)),
                            0,
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_0_5)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_0_5))

                    );
                    break;
                case 3:
                    drawable = ShapeDrawableUtils.getStateListDrawable(
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_6)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            itemView.getContext().getResources().getColor(R.color.green_500),
                            0);
                    tvContentMessage.setBackgroundDrawable(drawable);
                    setPadding(
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_0_5)),
                            0,
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_0_5)),
                            0

                    );
                    break;
            }
        }

        private void setPadding(int left, int top, int right, int bottom) {
            rltMessage.setPadding(left, top, right, bottom);
            rltMessage.requestLayout();
        }
    }

    protected class ItemMessageLeftViewholder extends RecyclerView.ViewHolder {

        private TextView tvContentMessage;
        private ImageView imgAvatar;
        private RelativeLayout rltMessage;

        public ItemMessageLeftViewholder(@NonNull View itemView) {
            super(itemView);
            tvContentMessage = itemView.findViewById(R.id.tv_content_message);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            rltMessage = itemView.findViewById(R.id.rlt_message_left);
        }

        void bind(ItemMessage itemMessage) {
            Drawable drawable = null;
            switch (itemMessage.getType()) {
                case 0:
                    drawable = ShapeDrawableUtils.getStateListDrawable(
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            itemView.getContext().getResources().getColor(R.color.colorGrayE6),
                            0);
                    tvContentMessage.setBackgroundDrawable(drawable);
                    imgAvatar.setVisibility(View.VISIBLE);
                    setPadding(
                            0,
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_4)),
                            0,
                            0

                    );
                    break;
                case 1:
                    drawable = ShapeDrawableUtils.getStateListDrawable(
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_6)),
                            itemView.getContext().getResources().getColor(R.color.colorGrayE6),
                            0);
                    tvContentMessage.setBackgroundDrawable(drawable);
                    imgAvatar.setVisibility(View.VISIBLE);
                    setPadding(
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_0_5)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_4)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_0_5)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_0_5))

                    );
                    break;
                case 2:
                    drawable = ShapeDrawableUtils.getStateListDrawable(
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_6)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_6)),
                            itemView.getContext().getResources().getColor(R.color.colorGrayE6),
                            0);
                    tvContentMessage.setBackgroundDrawable(drawable);
                    imgAvatar.setVisibility(View.INVISIBLE);
                    setPadding(
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_0_5)),
                            0,
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_0_5)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_0_5))

                    );
                    break;
                case 3:
                    drawable = ShapeDrawableUtils.getStateListDrawable(
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_6)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_radius_message)),
                            itemView.getContext().getResources().getColor(R.color.colorGrayE6),
                            0);
                    tvContentMessage.setBackgroundDrawable(drawable);
                    imgAvatar.setVisibility(View.INVISIBLE);
                    setPadding(
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_0_5)),
                            0,
                            CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_0_5)),
                            0

                    );
                    break;
            }
        }

        private void setPadding(int left, int top, int right, int bottom) {
            if (getAdapterPosition() == items.size() - 1) {
                bottom = CommonUtils.dpToPx((int) itemView.getContext().getResources().getDimension(R.dimen.size_4));
            }
            rltMessage.setPadding(left, top, right, bottom);
            rltMessage.requestLayout();

        }

    }

    protected class LoadMoreViewholder extends RecyclerView.ViewHolder {

        public LoadMoreViewholder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
