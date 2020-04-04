package com.svmc.android.locationsupportteam.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.homescreen.HomeScreenModel;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.List;

/**
 * Created by TUNGTS on 4/9/2019
 */

public class HomeScreenAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List items;

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    private boolean isLocading;

    public boolean isLocading() {
        return isLocading;
    }

    public void setLocading(boolean locading) {
        isLocading = locading;
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == Constans.AdapterItem.ITEM_HOME_SCREEN) {
            return new ListItemViewHolder(
                    LayoutInflater.from(
                            viewGroup.getContext()
                    ).inflate(R.layout.item_new_feed, viewGroup, false)
            );
        } else {
            return new LoadMoreViewHolder(
                    LayoutInflater.from(
                            viewGroup.getContext()
                    ).inflate(R.layout.item_load_more, viewGroup, false)
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ListItemViewHolder) {
            ((ListItemViewHolder) viewHolder).bind((HomeScreenModel) items.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof HomeScreenModel) {
            return Constans.AdapterItem.ITEM_HOME_SCREEN;
        } else if (items.get(position) == null) {
            return Constans.AdapterItem.ITEM_VIEW_LOAD_MORE;
        } else {
            return Constans.AdapterItem.ITEM_VIEW_LOADING;
        }
    }

    protected class ListItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitleList;
        private LinearLayout llSeeAll;

        private RecyclerView rcvListItems;
        private HomeScreenItemOfListAdapter itemOfListAdapter;

        public ListItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvTitleList = itemView.findViewById(R.id.tv_title_item_new_feed);
            llSeeAll = itemView.findViewById(R.id.ll_see_all);

            rcvListItems = itemView.findViewById(R.id.rcv_items);
            itemOfListAdapter = new HomeScreenItemOfListAdapter();

            llSeeAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecycleViewClickListener != null) {
                        onRecycleViewClickListener.onClick(getAdapterPosition());
                    }
                }
            });

        }

        void bind(final HomeScreenModel screenModel) {

            if (screenModel.getPlaces() != null) {
                itemOfListAdapter.setItems(screenModel.getPlaces());
            } else if (screenModel.getTripInfors() != null) {
                itemOfListAdapter.setItems(screenModel.getTripInfors());
            } else {
                itemView.setVisibility(View.GONE);
                return;
            }

            tvTitleList.setText(screenModel.getTitle());
            ViewCompat.setNestedScrollingEnabled(rcvListItems, false);
            rcvListItems.setHasFixedSize(true);
            rcvListItems.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            rcvListItems.setAdapter(itemOfListAdapter);

            itemOfListAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
                @Override
                public void onClick(int position) {
                    if (onClickItemPlace != null) {
                        onClickItemPlace.onItemClick(screenModel.getPlaces().get(position).getPlaceId(), position);
                    }
                }
            });
        }
    }

    protected class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private ClickItemPlace onClickItemPlace;

    public void setOnClickItemPlace(ClickItemPlace onClickItemPlace) {
        this.onClickItemPlace = onClickItemPlace;
    }

    public interface ClickItemPlace {

        void onItemClick(String placeId, int pos);

    }

    public interface ClickItemTrip{

        void onItemClick(String tripId, int pos);

    }

}
