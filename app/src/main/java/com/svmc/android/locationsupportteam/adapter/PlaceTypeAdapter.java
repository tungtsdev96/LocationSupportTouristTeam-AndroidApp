package com.svmc.android.locationsupportteam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.PlaceType;
import com.svmc.android.locationsupportteam.entity.SubPlaceType;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by TungTS on 5/11/2019
 */

public class PlaceTypeAdapter extends RecyclerView.Adapter<PlaceTypeAdapter.PlaceTypeViewHolder> {

    private List<PlaceType> placeTypes = new ArrayList<>();

    public List<PlaceType> getPlaceTypes() {
        return placeTypes;
    }

    public void setPlaceTypes(List<PlaceType> placeTypes) {
        this.placeTypes = placeTypes;
    }

    @NonNull
    @Override
    public PlaceTypeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PlaceTypeViewHolder(
                LayoutInflater.from(
                        viewGroup.getContext()
                ).inflate(R.layout.item_place_type, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceTypeViewHolder placeTypeViewHolder, int i) {
        placeTypeViewHolder.bind(placeTypes.get(i));
    }

    @Override
    public int getItemCount() {
        return placeTypes.size();
    }

    protected class PlaceTypeViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private ImageView imgPlaceType;

        private RecyclerView rcvSubTypes;
        private List<SubPlaceType> subPlaceTypes;
        private SubPlaceTypeAdapter subPlaceTypeAdapter;

        public PlaceTypeViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_name_place_type);
            imgPlaceType = itemView.findViewById(R.id.img_place_type);

            rcvSubTypes = itemView.findViewById(R.id.rcv_sub_place_type);
            subPlaceTypes = new ArrayList<>();
            subPlaceTypeAdapter = new SubPlaceTypeAdapter();
            subPlaceTypeAdapter.setSubPlaceTypes(subPlaceTypes);
            rcvSubTypes.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            rcvSubTypes.setAdapter(subPlaceTypeAdapter);

            subPlaceTypeAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
                @Override
                public void onClick(int position) {
                    if (onClickSubPlaceType != null) {
                        onClickSubPlaceType.onClick(subPlaceTypes.get(position));
                    }
                }
            });
        }

        void bind(PlaceType placeType) {
            tvTitle.setText(placeType.getTitle());
            imgPlaceType.setImageResource(
                    CommonUtils.getDrawableResourceFromString(itemView.getContext(), placeType.getIcon())
            );

            subPlaceTypes.addAll(placeType.getSubPlaceTypes());
            subPlaceTypeAdapter.notifyDataSetChanged();
        }
    }

    private ClickSubPlaceType onClickSubPlaceType;

    public void setOnClickSubPlaceType(ClickSubPlaceType onClickSubPlaceType) {
        this.onClickSubPlaceType = onClickSubPlaceType;
    }

    public interface ClickSubPlaceType {

        void onClick(SubPlaceType placeType);

    }

}
