package com.svmc.android.locationsupportteam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.SubPlaceType;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by TungTS on 5/11/2019
 */

public class SubPlaceTypeAdapter extends RecyclerView.Adapter<SubPlaceTypeAdapter.SubPlaceTypeViewHolder> {

    private List<SubPlaceType> subPlaceTypes = new ArrayList<>();

    public List<SubPlaceType> getSubPlaceTypes() {
        return subPlaceTypes;
    }

    public void setSubPlaceTypes(List<SubPlaceType> subPlaceTypes) {
        this.subPlaceTypes = subPlaceTypes;
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
    public SubPlaceTypeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SubPlaceTypeViewHolder(
                LayoutInflater.from(
                        viewGroup.getContext()
                ).inflate(R.layout.item_sub_place_type, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SubPlaceTypeViewHolder viewHolder, int i) {
        viewHolder.bind(subPlaceTypes.get(i));
    }

    @Override
    public int getItemCount() {
        return subPlaceTypes.size();
    }

    protected class SubPlaceTypeViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSubPlaceType;

        public SubPlaceTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubPlaceType = itemView.findViewById(R.id.tv_sub_plae_type);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecycleViewClickListener != null) {
                        onRecycleViewClickListener.onClick(getAdapterPosition());
                    }
                }
            });
        }

        void bind(SubPlaceType placeType) {
            tvSubPlaceType.setText(placeType.getTitle());
        }
    }

}
