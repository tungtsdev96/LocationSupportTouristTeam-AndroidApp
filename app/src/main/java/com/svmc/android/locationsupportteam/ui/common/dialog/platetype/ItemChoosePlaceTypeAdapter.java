package com.svmc.android.locationsupportteam.ui.common.dialog.platetype;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.SubPlaceType;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;

import java.util.List;

/**
 * Created by TUNGTS on 5/29/2019
 */

public class ItemChoosePlaceTypeAdapter extends RecyclerView.Adapter<ItemChoosePlaceTypeAdapter.PlaceTypeViewholder> {

    private List<SubPlaceType> subPlaceTypes;
    private String oldPlateTypes;

    boolean[] check;

    public void setSubPlaceTypes(List<SubPlaceType> subPlaceTypes, String oldPlateTypes) {
        check = new boolean[subPlaceTypes.size() + 1];
        this.subPlaceTypes = subPlaceTypes;
        this.oldPlateTypes = oldPlateTypes;

        String[] ids = oldPlateTypes.split(",");
        for (String id: ids) {
            check[Integer.parseInt(id)] = true;
        }
    }

    private RecycleViewClickListener onRecycleViewClickListener;

    public void setOnRecycleViewClickListener(RecycleViewClickListener onRecycleViewClickListener) {
        this.onRecycleViewClickListener = onRecycleViewClickListener;
    }

    @NonNull
    @Override
    public PlaceTypeViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PlaceTypeViewholder(
                LayoutInflater.from(
                        viewGroup.getContext()
                ).inflate(R.layout.item_place_type_choose, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceTypeViewholder placeTypeViewholder, int i) {
        placeTypeViewholder.bind(subPlaceTypes.get(i));
    }

    @Override
    public int getItemCount() {
        return subPlaceTypes.size();
    }

    public String getListIdChoose() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < subPlaceTypes.size(); i++) {
            if (check[i]) {
                if (s.length() == 0) s.append(i);
                else s.append("," + i);
            }
        }
        return s.toString();
    }

    protected class PlaceTypeViewholder extends RecyclerView.ViewHolder {

        private ImageView imgChoose;
        private TextView tvName;

        public PlaceTypeViewholder(@NonNull View itemView) {
            super(itemView);
            imgChoose = itemView.findViewById(R.id.img_choose);
            tvName = itemView.findViewById(R.id.tv_title);
        }

        void bind(SubPlaceType placeType) {
            tvName.setText(placeType.getTitle());
            imgChoose.setVisibility(
                    check[getAdapterPosition()] ? View.VISIBLE : View.INVISIBLE
            );

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check[getAdapterPosition()] = !check[getAdapterPosition()];
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }

    }

}
