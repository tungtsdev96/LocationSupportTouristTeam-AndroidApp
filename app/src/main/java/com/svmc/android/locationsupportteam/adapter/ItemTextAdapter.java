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
 * Created by TungTS on 5/12/2019
 */

public class ItemTextAdapter extends RecyclerView.Adapter<ItemTextAdapter.ItemTextViewHolder> {

    private List<String> items = new ArrayList<>();

    public ItemTextAdapter(){}

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    private RecycleViewClickListener onRecycleViewClickListener;

    public void setOnRecycleViewClickListener(RecycleViewClickListener onRecycleViewClickListener) {
        this.onRecycleViewClickListener = onRecycleViewClickListener;
    }

    @NonNull
    @Override
    public ItemTextViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemTextViewHolder(
                LayoutInflater.from(
                        viewGroup.getContext()
                ).inflate(R.layout.item_text_message, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ItemTextViewHolder itemTextViewHolder, int i) {
        itemTextViewHolder.bind(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ItemTextViewHolder extends RecyclerView.ViewHolder {

        private TextView tvText;

        public ItemTextViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecycleViewClickListener != null) {
                        onRecycleViewClickListener.onClick(getAdapterPosition());
                    }
                }
            });
        }

        void bind(String message) {
            tvText.setText(message);
        }
    }

}
