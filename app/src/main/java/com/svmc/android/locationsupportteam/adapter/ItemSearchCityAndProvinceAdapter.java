package com.svmc.android.locationsupportteam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.CityProvince;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/10/2019
 */

public class ItemSearchCityAndProvinceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

    public ItemSearchCityAndProvinceAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == Constans.AdapterItem.ITEM_VIEW_LOAD_MORE) {
            return new LoadMoreViewHolder(
                    LayoutInflater.from(
                            viewGroup.getContext()
                    ).inflate(R.layout.item_load_more, viewGroup, false)
            );
        }

        return new ItemCityProvinceSearchViewHolder(
                LayoutInflater.from(
                        viewGroup.getContext()
                ).inflate(R.layout.item_search_place, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ItemCityProvinceSearchViewHolder) {
            ((ItemCityProvinceSearchViewHolder) viewHolder).bind((CityProvince) items.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof String || items.get(position) instanceof CityProvince) {
            return Constans.AdapterItem.ITEM_SEARCH_CITY_PROVINCE;
        } else {
            return Constans.AdapterItem.ITEM_VIEW_LOAD_MORE;
        }
    }

    protected class ItemTextTitle extends RecyclerView.ViewHolder {

        private TextView tvTitle;

        public ItemTextTitle(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);

        }
    }

    protected class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    protected class ItemCityProvinceSearchViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgIcon;
        private TextView tvNameProvinceCity;
        private TextView tvNameParent;

        public ItemCityProvinceSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.img_icon);
            tvNameProvinceCity = itemView.findViewById(R.id.tv_name_place);
            tvNameParent = itemView.findViewById(R.id.tv_place_parent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecycleViewClickListener != null) {
                        onRecycleViewClickListener.onClick(getAdapterPosition());
                    }
                }
            });
        }

        void bind(CityProvince cityProvince) {
            if (cityProvince.getId() == null) {
                imgIcon.setImageResource(R.drawable.ic_near_me_green);
                tvNameProvinceCity.setText(itemView.getContext().getString(R.string.near_me));
                tvNameParent.setVisibility(View.GONE);
                return;
            }

            imgIcon.setImageResource(R.drawable.ic_location_green);
            tvNameProvinceCity.setText(
                    cityProvince.getName() != null ? cityProvince.getName() : ""
            );
            tvNameParent.setVisibility(View.GONE);
//                if (cityProvince.getNameParent() == null) {
//                    tvNameParent.setVisibility(View.GONE);
//                    return;
//                }
//
//                tvNameParent.setText(cityProvince.getNameParent());
    }

}

}
