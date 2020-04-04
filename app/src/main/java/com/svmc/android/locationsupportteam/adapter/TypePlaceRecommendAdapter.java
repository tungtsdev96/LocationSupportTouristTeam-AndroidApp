package com.svmc.android.locationsupportteam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/1/2019
 */

public class TypePlaceRecommendAdapter extends RecyclerView.Adapter<TypePlaceRecommendAdapter.TypePlaceRecommendViewHolder> {

    private List<TypePlaceRecommnedItem> items = new ArrayList();

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
       for (int i = 0; i < items.size(); i++){
           this.items.add(new TypePlaceRecommnedItem((String) items.get(i),currentTypePlace == i));
       }
       notifyDataSetChanged();
    }

    public void addItem(String item) {
        items.add(new TypePlaceRecommnedItem(item, false));
    }

    private int currentTypePlace;

    public void setCurrentTypePlace(int currentTypePlace) {
        this.currentTypePlace = currentTypePlace;
    }

    public TypePlaceRecommendAdapter() {
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
    public TypePlaceRecommendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TypePlaceRecommendViewHolder(
                LayoutInflater.from(
                        viewGroup.getContext()
                ).inflate(R.layout.item_tabs_text, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TypePlaceRecommendViewHolder typePlaceRecommendViewHolder, int i) {
        typePlaceRecommendViewHolder.bind(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class TypePlaceRecommnedItem implements AdapterItem {

        private String typeName;

        private boolean isChoose;

        public TypePlaceRecommnedItem(String typeName, boolean isChoose) {
            this.typeName = typeName;
            this.isChoose = isChoose;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public boolean isChoose() {
            return isChoose;
        }

        public void setChoose(boolean choose) {
            isChoose = choose;
        }
    }

    protected class TypePlaceRecommendViewHolder extends RecyclerView.ViewHolder {

        TextView tvTypePlaceRecommend;

        public TypePlaceRecommendViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTypePlaceRecommend = itemView.findViewById(R.id.tv_title_tabs);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecycleViewClickListener != null) {
//                        items.get(currentTypePlace).setChoose(false);
//                        notifyItemChanged(currentTypePlace);
//                        currentTypePlace = getAdapterPosition();
//                        items.get(currentTypePlace).setChoose(true);
//                        notifyItemChanged(currentTypePlace);
                        onRecycleViewClickListener.onClick(currentTypePlace);
                    }
                }
            });
        }

        void bind(TypePlaceRecommnedItem typePlace) {
            tvTypePlaceRecommend.setText(typePlace.getTypeName());
            if (typePlace.isChoose) {
                itemView.setBackgroundDrawable(itemView.getContext().getResources().getDrawable(R.drawable.bg_item_type_place_choose));
            } else {
                itemView.setBackgroundDrawable(itemView.getContext().getResources().getDrawable(R.drawable.bg_item_type_place));
            }
        }
    }

}
