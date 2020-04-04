package com.svmc.android.locationsupportteam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.trip.TripDayInfor;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 3/31/2019
 */

public class DayOfTripAdapter extends RecyclerView.Adapter<DayOfTripAdapter.DayOfTripViewHolder> {

    private List items = new ArrayList();
    private RecycleViewClickListener onRecycleViewClickListener;

    public DayOfTripAdapter(){};

    public RecycleViewClickListener getOnRecycleViewClickListener() {
        return onRecycleViewClickListener;
    }

    public void setOnRecycleViewClickListener(RecycleViewClickListener onRecycleViewClickListener) {
        this.onRecycleViewClickListener = onRecycleViewClickListener;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    @NonNull
    @Override
    public DayOfTripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new DayOfTripViewHolder(
                LayoutInflater.from(
                        parent.getContext()
                ).inflate(R.layout.item_day_of_trip, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DayOfTripViewHolder dayOfTripViewHolder, int position) {
        dayOfTripViewHolder.bind((TripDayInfor) items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class DayOfTripViewHolder extends RecyclerView.ViewHolder {

        public DayOfTripViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecycleViewClickListener != null) {
                        onRecycleViewClickListener.onClick(getAdapterPosition());
                    }
                }
            });
        }

        void bind(TripDayInfor tripDetail) {

        }
    }

}
