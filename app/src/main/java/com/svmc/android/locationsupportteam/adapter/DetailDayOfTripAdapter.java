package com.svmc.android.locationsupportteam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.trip.TripDayInfor;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/1/2019
 */

public class DetailDayOfTripAdapter extends RecyclerView.Adapter<DetailDayOfTripAdapter.DetailDayOfTripViewHolder> {

    private List items = new ArrayList();

    public DetailDayOfTripAdapter(){}

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

    @NonNull
    @Override
    public DetailDayOfTripViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DetailDayOfTripViewHolder(
                LayoutInflater.from(
                        viewGroup.getContext()
                ).inflate(R.layout.item_detail_day_of_trip, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DetailDayOfTripViewHolder detailDayOfTripViewHolder, int i) {
        detailDayOfTripViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class DetailDayOfTripViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNumberOfDay;
        private TextView tvDateOfTrip;
        private TextView tvLongDistance;
        private TextView tvNumberPlaces;

        private RecyclerView rcvPlaceOfDay;
        private PlaceOfDayAdapter placeOfDayAdapter;


        public DetailDayOfTripViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumberOfDay = itemView.findViewById(R.id.tv_number_day_of_trip);
            tvDateOfTrip = itemView.findViewById(R.id.tv_date_of_trip);
            tvLongDistance = itemView.findViewById(R.id.tv_long_distance);
            tvNumberPlaces = itemView.findViewById(R.id.tv_number_places);

            rcvPlaceOfDay = itemView.findViewById(R.id.rcv_place_of_day);
            placeOfDayAdapter = new PlaceOfDayAdapter();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecycleViewClickListener != null) {
                        onRecycleViewClickListener.onClick(getAdapterPosition());
                    }
                }
            });

            placeOfDayAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
                @Override
                public void onClick(int position) {
                    if (onRecycleViewClickListener != null) {
                        onRecycleViewClickListener.onClick(getAdapterPosition());
                    }
                }
            });
        }

        void bind(int pos) {
            placeOfDayAdapter.setItems(((TripDayInfor) items.get(pos)).getDetailDays(), pos);
            rcvPlaceOfDay.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            rcvPlaceOfDay.setAdapter(placeOfDayAdapter);
        }
    }

}
