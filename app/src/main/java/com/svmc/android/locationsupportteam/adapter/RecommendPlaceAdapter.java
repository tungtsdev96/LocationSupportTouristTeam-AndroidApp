package com.svmc.android.locationsupportteam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.ui.customviews.CustomRatingView;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/1/2019
 */

public class RecommendPlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List items = new ArrayList();

    private RecycleViewClickListener onRecycleViewClickListener;

    public RecycleViewClickListener getOnRecycleViewClickListener() {
        return onRecycleViewClickListener;
    }

    public void setOnRecycleViewClickListener(RecycleViewClickListener onRecycleViewClickListener) {
        this.onRecycleViewClickListener = onRecycleViewClickListener;
    }

    public RecommendPlaceAdapter(){}

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == Constans.AdapterItem.ITEM_VIEW_PLACE_RECOMMEND) {
            return new ReconmmendPlaceViewHolder(
                    LayoutInflater.from(
                            viewGroup.getContext()
                    ).inflate(R.layout.item_choose_place, viewGroup, false)
            );
        } else {
            return new LoadMoreViewholder(
                    LayoutInflater.from(
                            viewGroup.getContext()
                    ).inflate(R.layout.item_load_more, viewGroup, false)
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ReconmmendPlaceViewHolder) {
            ((ReconmmendPlaceViewHolder) viewHolder).bind((Place) items.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Place) {
            return Constans.AdapterItem.ITEM_VIEW_PLACE_RECOMMEND;
        } else {
            return Constans.AdapterItem.ITEM_VIEW_LOAD_MORE;
        }
    }

    protected class LoadMoreViewholder extends RecyclerView.ViewHolder {

        public LoadMoreViewholder(@NonNull View itemView) {
            super(itemView);
        }
    }

    protected class ReconmmendPlaceViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPlace;
        private TextView tvNamePlace;
        private TextView tvRatingScore;
        private TextView tvPriceLevel;
        private ImageView btnAddPlace;
        private CustomRatingView customRatingView;

        public ReconmmendPlaceViewHolder(@NonNull final View itemView) {
            super(itemView);
            imgPlace = itemView.findViewById(R.id.img_place);
            tvNamePlace = itemView.findViewById(R.id.tv_name_place);
            tvRatingScore = itemView.findViewById(R.id.tv_rating_score);
            tvPriceLevel = itemView.findViewById(R.id.tv_price_level);
            btnAddPlace = itemView.findViewById(R.id.btn_add_place);
            customRatingView = itemView.findViewById(R.id.ratting_view_palce);

            customRatingView.setScore(4.5);

            btnAddPlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "add place", Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecycleViewClickListener != null) {
                        Toast.makeText(itemView.getContext(), "show detail place", Toast.LENGTH_SHORT).show();
                        onRecycleViewClickListener.onClick(getAdapterPosition());
                    }
                }
            });

        }

        void bind(Place place) {

        }
    }

}
