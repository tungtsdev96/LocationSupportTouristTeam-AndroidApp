package com.svmc.android.locationsupportteam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.trip.TripInfor;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tungts on 3/28/2019.
 */

public class TripAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List items = new ArrayList<>();

    public TripAdapter(){}

    private RecycleViewClickListener onRecycleViewClickListener;

    public void setOnRecycleViewClickListener(RecycleViewClickListener onRecycleViewClickListener) {
        this.onRecycleViewClickListener = onRecycleViewClickListener;
    }

    public void setItems(List items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        if (viewtype == Constans.AdapterItem.ITEM_VIEW_TRIP) {
            return new TripViewHolder(
                    LayoutInflater.from(
                            viewGroup.getContext()
                    ).inflate(R.layout.item_trip, viewGroup, false)
            );
        } else if (viewtype == Constans.AdapterItem.ITEM_VIEW_LOAD_MORE) {
            return new LoadMoreViewHolder(
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof TripViewHolder) {
            ((TripViewHolder) viewHolder).bind((TripInfor) items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("wsss", items.get(position).getClass().getName());
        if (items.get(position) instanceof TripInfor) {
            Log.d("trip", "trip");
            return Constans.AdapterItem.ITEM_VIEW_TRIP;
        } else if (items.get(position) == null) {
            Log.d("trip", "load more");
            return Constans.AdapterItem.ITEM_VIEW_LOAD_MORE;
        } else {
            Log.d("trip", "loading");
            return Constans.AdapterItem.ITEM_VIEW_LOADING;
        }
    }

    protected class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }

    protected class TripViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgTrip;
        private TextView tvLongTrip;
        private TextView tvTitleTrip;
        private TextView tvUserCreated;
        private TextView tvLastTimeCreated;
        private View itemTrip;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTrip = itemView.findViewById(R.id.img_trip);
            tvLongTrip = itemView.findViewById(R.id.tv_long_trip);
            tvTitleTrip = itemView.findViewById(R.id.tv_title_trip);
            tvUserCreated = itemView.findViewById(R.id.tv_user_created);
            tvLastTimeCreated = itemView.findViewById(R.id.tv_last_time_created);
            itemTrip = itemView.findViewById(R.id.item_trip);

            itemTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecycleViewClickListener != null) {
                        onRecycleViewClickListener.onClick(getAdapterPosition());
                    }
                }
            });
        }

        void bind(TripInfor infor) {

        }
    }

    protected class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
