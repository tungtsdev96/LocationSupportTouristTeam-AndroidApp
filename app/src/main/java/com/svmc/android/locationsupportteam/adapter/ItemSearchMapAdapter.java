package com.svmc.android.locationsupportteam.adapter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.googlemap.place.autocomplete.Prediction;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.homescreen.RecentedSearchInMap;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TungTS on 5/6/2019
 */

public class ItemSearchMapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean isLoading;

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case Constans.AdapterItem.ITEM_VIEW_SEARCH_IN_MAP:
                return new ItemRecentSearchViewHolder(
                        LayoutInflater.from(
                                viewGroup.getContext()
                        ).inflate(R.layout.item_search_place, viewGroup, false)
                );
            case Constans.AdapterItem.ITEM_TITLE:
                return new ItemTextTitleViewHolder(
                        LayoutInflater.from(
                                viewGroup.getContext()
                        ).inflate(R.layout.item_text_title, viewGroup, false)
                );
            case Constans.AdapterItem.ITEM_VIEW_LOAD_MORE:
                return new LoadMoreViewHolder(
                        LayoutInflater.from(
                                viewGroup.getContext()
                        ).inflate(R.layout.item_load_more, viewGroup, false)
                );
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ItemTextTitleViewHolder) {
            ((ItemTextTitleViewHolder) viewHolder).bind((String) items.get(i));
        } else if (viewHolder instanceof ItemRecentSearchViewHolder){
            if (items.get(i) instanceof RecentedSearchInMap) {
                ((ItemRecentSearchViewHolder) viewHolder).bind((RecentedSearchInMap) items.get(i));
            } else if (items.get(i) instanceof Place) {
                ((ItemRecentSearchViewHolder) viewHolder).bind((Place) items.get(i));
            } else if (items.get(i) instanceof Prediction) {
                ((ItemRecentSearchViewHolder) viewHolder).bind((Prediction) items.get(i));
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof RecentedSearchInMap || items.get(position) instanceof Place || items.get(position) instanceof Prediction) {
            return Constans.AdapterItem.ITEM_VIEW_SEARCH_IN_MAP;
        } else if (items.get(position) instanceof String) {
            return Constans.AdapterItem.ITEM_TITLE;
        } else {
            return Constans.AdapterItem.ITEM_VIEW_LOAD_MORE;
        }
    }

    protected class ItemTextTitleViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;

        public ItemTextTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }

        void bind(String title) {
            tvTitle.setText(title);
        }
    }

    protected class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    protected class ItemRecentSearchViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgIcon;
        private TextView tvNameProvinceCity;
        private TextView tvNameParent;

        public ItemRecentSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.img_icon);
            tvNameProvinceCity = itemView.findViewById(R.id.tv_name_place);
            tvNameParent = itemView.findViewById(R.id.tv_place_parent);


        }

        void bind(final RecentedSearchInMap recentedSearchInMap) {
            imgIcon.setImageResource(R.drawable.ic_history_black);
            tvNameProvinceCity.setText(
                    recentedSearchInMap.getNamePlace() != null ? recentedSearchInMap.getNamePlace() : ""
            );
            tvNameProvinceCity.setTypeface(null, Typeface.NORMAL);

            if (recentedSearchInMap.getNameAddress() == null) {
                tvNameParent.setVisibility(View.GONE);
                return;
            }

            tvNameParent.setText(recentedSearchInMap.getNameAddress());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickItemCallBack != null) {
                        onClickItemCallBack.onClick(recentedSearchInMap.getPlaceId(), recentedSearchInMap);
                    }
                }
            });
        }

        void bind(final Prediction prediction) {
            imgIcon.setImageResource(R.drawable.ic_location_green);
            tvNameProvinceCity.setText(
                    prediction.getStructuredFormatting().getMainText() != null ? prediction.getStructuredFormatting().getMainText() : ""
            );

            if (prediction.getStructuredFormatting().getSecondaryText() == null) {
                tvNameParent.setVisibility(View.GONE);
                return;
            }

            tvNameParent.setText(
                    prediction.getStructuredFormatting().getSecondaryText() != null ? prediction.getStructuredFormatting().getSecondaryText() : ""
            );

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickItemCallBack != null) {
                        onClickItemCallBack.onClick(prediction.getPlaceId(), prediction);
                    }
                }
            });
        }

        void bind(final Place place) {
            // place.getIcon()
            GlideUtils.loadImageByPath(
                    itemView.getContext(),
                    imgIcon,
                    place.getIcon()
            );

            tvNameProvinceCity.setText(
                    place.getName() != null ? place.getName() : ""
            );

            if (place.getVicinity() == null) {
                tvNameParent.setVisibility(View.GONE);
                return;
            }

            tvNameParent.setText(place.getVicinity());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickItemCallBack != null) {
                        onClickItemCallBack.onClick(place.getPlaceId(), place);
                    }
                }
            });
        }
    }

    private ClickItemCallBack onClickItemCallBack;

    public ClickItemCallBack getOnClickItemCallBack() {
        return onClickItemCallBack;
    }

    public void setOnClickItemCallBack(ClickItemCallBack onClickItemCallBack) {
        this.onClickItemCallBack = onClickItemCallBack;
    }

    public interface ClickItemCallBack {
        void onClick(String placeId, Object place);
    }

}
