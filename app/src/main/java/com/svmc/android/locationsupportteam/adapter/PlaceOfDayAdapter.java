package com.svmc.android.locationsupportteam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.trip.TripDetailDay;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/1/2019
 */

public class PlaceOfDayAdapter extends RecyclerView.Adapter<PlaceOfDayAdapter.PlaceOfDayViewholder> {

    private List items = new ArrayList();

    private RecycleViewClickListener onRecycleViewClickListener;

    public PlaceOfDayAdapter(){}

    public List getItems() {
        return items;
    }

    public void setItems(List items, int currentParent) {
        for (int i = 0; i < items.size(); i++) {
            this.items.add(new PlaceOfDayItem(currentParent, (TripDetailDay) items.get(i)));
        }
    }

    public RecycleViewClickListener getOnRecycleViewClickListener() {
        return onRecycleViewClickListener;
    }

    public void setOnRecycleViewClickListener(RecycleViewClickListener onRecycleViewClickListener) {
        this.onRecycleViewClickListener = onRecycleViewClickListener;
    }

    @NonNull
    @Override
    public PlaceOfDayViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new PlaceOfDayViewholder(
                LayoutInflater.from(
                        viewGroup.getContext()
                ).inflate(R.layout.item_place_of_day_of_trip, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceOfDayViewholder placeOfDayViewholder, int position) {
        placeOfDayViewholder.bind((PlaceOfDayItem) items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class PlaceOfDayViewholder extends RecyclerView.ViewHolder {

        public PlaceOfDayViewholder(@NonNull View itemView) {
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

        void bind(PlaceOfDayItem placeOfDayItem) {

        }
    }

    protected class PlaceOfDayItem implements AdapterItem {

        private int currentParent;

        private TripDetailDay tripDetailDay;

        public PlaceOfDayItem(int currentParent, TripDetailDay tripDetailDay) {
            this.currentParent = currentParent;
            this.tripDetailDay = tripDetailDay;
        }

        public int getCurrentParent() {
            return currentParent;
        }

        public void setCurrentParent(int currentParent) {
            this.currentParent = currentParent;
        }

        public TripDetailDay getTripDetailDay() {
            return tripDetailDay;
        }

        public void setTripDetailDay(TripDetailDay tripDetailDay) {
            this.tripDetailDay = tripDetailDay;
        }
    }

}
