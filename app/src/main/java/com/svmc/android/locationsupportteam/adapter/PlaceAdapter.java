package com.svmc.android.locationsupportteam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.data.remote.GoogleAPIRemoteDataSource;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.ui.customviews.CustomRatingView;
import com.svmc.android.locationsupportteam.utils.CommonUtils;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        if (viewtype == Constans.AdapterItem.ITEM_VIEW_PLACE) {
            return new PlaceViewholder(
                    LayoutInflater.from(
                            viewGroup.getContext()
                    ).inflate(R.layout.item_place, viewGroup, false)
            );
        } else if (viewtype == Constans.AdapterItem.ITEM_VIEW_LOAD_MORE) {
            return new LoadMoreViewholder(
                    LayoutInflater.from(
                            viewGroup.getContext()
                    ).inflate(R.layout.item_load_more, viewGroup, false)
            );
        } else {
            return new LoadingViewHolder(
                    LayoutInflater.from(
                            viewGroup.getContext()
                    ).inflate(R.layout.item_loading_trip, viewGroup, false)
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof PlaceViewholder) {
            ((PlaceViewholder) viewHolder).bind((Place) items.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Place) {
            return Constans.AdapterItem.ITEM_VIEW_PLACE;
        } else if (items.get(position) == null) {
            return Constans.AdapterItem.ITEM_VIEW_LOAD_MORE;
        } else {
            return Constans.AdapterItem.ITEM_VIEW_LOADING;
        }
    }

    protected class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    protected class LoadMoreViewholder extends RecyclerView.ViewHolder {

        public LoadMoreViewholder(@NonNull View itemView) {
            super(itemView);
        }
    }

    protected class PlaceViewholder extends RecyclerView.ViewHolder {

        private ImageView imgPlace;
        private TextView tvNamePlace;
        private CustomRatingView ratingView;
        private TextView tvNumberReview;
        private LinearLayout llRating;

        public PlaceViewholder(@NonNull View itemView) {
            super(itemView);
            imgPlace = itemView.findViewById(R.id.img_place);
            tvNamePlace = itemView.findViewById(R.id.tv_name_place);
            ratingView = itemView.findViewById(R.id.rating_score);
            tvNumberReview = itemView.findViewById(R.id.tv_number_reviewed);
            llRating = itemView.findViewById(R.id.ll_rating);
        }

        void bind(Place place) {
            if (place.getPhotos() != null && place.getPhotos().size() > 0) {
                String url = GoogleAPIRemoteDataSource.getUrlPhoto(
                        CommonUtils.dpToPx(180), true, place.getPhotos().get(0).getPhotoReference()
                );
                GlideUtils.loadImageWrapContent(
                        itemView.getContext(),
                        imgPlace,
                        url,
                        CommonUtils.dpToPx(180)
                );
            }

            tvNamePlace.setText(place.getName());

            if (place.getRating() > 0) {
                ratingView.setScore(place.getRating());
                tvNumberReview.setText(" - " + place.getUserRatingTotal() + " đánh giá");
            } else {
                llRating.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecycleViewClickListener != null) {
                        onRecycleViewClickListener.onClick(getAdapterPosition());
                    }
                }
            });
        }

    }

}
