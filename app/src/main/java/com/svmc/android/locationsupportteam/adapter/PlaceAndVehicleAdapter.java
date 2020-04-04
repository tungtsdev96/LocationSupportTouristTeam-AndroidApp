package com.svmc.android.locationsupportteam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.trip.TripDetailDay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/1/2019
 */

public class PlaceAndVehicleAdapter extends RecyclerView.Adapter<PlaceAndVehicleAdapter.PlaceAndVehicleViewHolder> {

    private boolean isDetail = false;

    public boolean isDetail() {
        return isDetail;
    }

    public void setDetail(boolean detail) {
        isDetail = detail;
    }

    private List items = new ArrayList();

    public PlaceAndVehicleAdapter(){}

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PlaceAndVehicleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new PlaceAndVehicleViewHolder(
                LayoutInflater.from(
                        viewGroup.getContext()
                ).inflate(R.layout.item_place_and_vehicle_of_day, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAndVehicleViewHolder placeAndVehicleViewHolder, int i) {
        placeAndVehicleViewHolder.bind((TripDetailDay) items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class PlaceAndVehicleViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llPresenterVehicle;
        LinearLayout llTimeVisit;
        LinearLayout llAddNote;
        ImageView imgRemove;
        ImageView imgVehicle;

        public PlaceAndVehicleViewHolder(@NonNull final View itemView) {
            super(itemView);
            llPresenterVehicle = itemView.findViewById(R.id.ll_present_vehicle);
            llTimeVisit = itemView.findViewById(R.id.ll_time_visit);
            llAddNote = itemView.findViewById(R.id.ll_add_note);
            imgVehicle = itemView.findViewById(R.id.img_vehicle);
            imgRemove = itemView.findViewById(R.id.img_remove);

            if (isDetail) {
                imgRemove.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "Show detail Place", Toast.LENGTH_SHORT).show();
                }
            });

            llTimeVisit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "Show dialog choose time", Toast.LENGTH_SHORT).show();
                }
            });

            llAddNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "Show activity add note", Toast.LENGTH_SHORT).show();
                }
            });

            imgRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "Remove Place", Toast.LENGTH_SHORT).show();
                }
            });
        }

        void bind(TripDetailDay tripDetailDay) {
            if (getAdapterPosition() == 0) {
                llPresenterVehicle.setVisibility(View.GONE);
            }
        }
    }

}
