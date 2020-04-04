package com.svmc.android.locationsupportteam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Review;
import com.svmc.android.locationsupportteam.ui.customviews.CustomRatingView;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.GlideUtils;
import com.svmc.android.locationsupportteam.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TungTS on 5/8/2019
 */

public class ItemReviewPlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List items = new ArrayList();

    public void setItems(List items) {
        this.items = items;
    }

    public List getItems() {
        return items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case Constans.AdapterItem.ITEM_REVIEW_PLACE:
                return new ItemReviewPlaceViewholder(
                        LayoutInflater.from(
                                viewGroup.getContext()
                        ).inflate(R.layout.item_review_place, viewGroup, false)
                );
            case Constans.AdapterItem.ITEM_VIEW_LOAD_MORE:
                return new ItemLoadMoreViewHolder(
                        LayoutInflater.from(
                                viewGroup.getContext()
                        ).inflate(R.layout.item_load_more, viewGroup, false)
                );
            default:
                return new ItemLoadingReview(
                        LayoutInflater.from(
                                viewGroup.getContext()
                        ).inflate(R.layout.item_review_loading, viewGroup, false)
                );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ItemReviewPlaceViewholder) {
            ((ItemReviewPlaceViewholder) viewHolder).bind((Review) items.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Review) {
            return Constans.AdapterItem.ITEM_REVIEW_PLACE;
        } else if (items.get(position) == null) {
            return Constans.AdapterItem.ITEM_VIEW_LOAD_MORE;
        } else {
            return Constans.AdapterItem.ITEM_VIEW_LOADING;
        }
    }

    protected class ItemLoadingReview extends RecyclerView.ViewHolder {

        public ItemLoadingReview(@NonNull View itemView) {
            super(itemView);
        }
    }

    protected class ItemLoadMoreViewHolder extends RecyclerView.ViewHolder {

        public ItemLoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    protected class ItemReviewPlaceViewholder extends RecyclerView.ViewHolder {

        private ImageView imgAuthor;
        private TextView tvNameAuthor;
        private CustomRatingView ratingScore;
        private TextView tvLastTimeCreated;
        private TextView tvContent;

        public ItemReviewPlaceViewholder(@NonNull View itemView) {
            super(itemView);
            imgAuthor = itemView.findViewById(R.id.img_author);
            tvNameAuthor = itemView.findViewById(R.id.tv_name_author);
            ratingScore = itemView.findViewById(R.id.rating_score);
            tvLastTimeCreated = itemView.findViewById(R.id.tv_last_time_created);
            tvContent = itemView.findViewById(R.id.tv_content);
        }

        private void bind(Review review) {
            GlideUtils.loadImageByPath(
                    itemView.getContext(),
                    imgAuthor,
                    review.getProfilePhotoUrl()
            );

            tvNameAuthor.setText(review.getAuthorName());
            ratingScore.setScore(review.getRating());
            tvLastTimeCreated.setText(TimeUtil.setTextTimeNew(review.getTime()));
            tvContent.setText(review.getContentReview());
        }
    }

}
