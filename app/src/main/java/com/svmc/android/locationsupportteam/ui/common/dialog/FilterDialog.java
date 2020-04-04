package com.svmc.android.locationsupportteam.ui.common.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.ui.base.BaseDialogFragment;

/**
 * Created by TUNGTS on 5/28/2019
 */

public class FilterDialog extends BaseDialogFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static final int ALL = 0;
    public static final int PRICE_ASCENDING = 1;
    public static final int PRICE_DESCENDING = 2;
    public static final int RATING_ASCENDING = 3;
    public static final int RATING_DESCENDING = 4;

    private boolean isOpenNow = true;
    private int filterType = 0;

    private RadioButton rdOpenAll;
    private RadioButton rdOpenNow;

    private RadioButton rdAll;
    private RadioButton rdPriceAscending;
    private RadioButton rdPriceDescending;
    private RadioButton rdRatingAscending;
    private RadioButton rdRatingDesending;
    private TextView btnApply;

    public static FilterDialog newInstance(FilterCallback onFilterCallback) {
        FilterDialog dialog = new FilterDialog();
        dialog.onFilterCallback = onFilterCallback;
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_sort_list_place, container, false);
        return view;
    }

    @Override
    protected void setUp(View view) {
        rdOpenAll = view.findViewById(R.id.rd_open_all);
        rdOpenNow = view.findViewById(R.id.rd_open_now);
        rdAll = view.findViewById(R.id.rd_all);
        rdPriceAscending = view.findViewById(R.id.rd_price_ascending);
        rdPriceDescending = view.findViewById(R.id.rd_price_descending);
        rdRatingAscending = view.findViewById(R.id.rd_rating_ascending);
        rdRatingDesending = view.findViewById(R.id.rd_rating_descending);
        btnApply = view.findViewById(R.id.btn_apply);
        addEvents();
    }

    private void addEvents() {
        btnApply.setOnClickListener(this);
        rdOpenAll.setOnCheckedChangeListener(this);
        rdOpenNow.setOnCheckedChangeListener(this);
        rdAll.setOnCheckedChangeListener(this);
        rdPriceAscending.setOnCheckedChangeListener(this);
        rdPriceDescending.setOnCheckedChangeListener(this);
        rdRatingAscending.setOnCheckedChangeListener(this);
        rdRatingDesending.setOnCheckedChangeListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_apply:
                if (onFilterCallback != null) {
                    dismiss();
                    onFilterCallback.onFilterPlace(isOpenNow, filterType);
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rd_open_all:
                isOpenNow = isChecked ? !isChecked : isOpenNow;
                break;
            case R.id.rd_open_now:
                isOpenNow = isChecked ? isChecked : isOpenNow;
                break;
            case R.id.rd_all:
                filterType = isChecked ? ALL : filterType;
                break;
            case R.id.rd_price_ascending:
                filterType = isChecked ? PRICE_ASCENDING : filterType;
                break;
            case R.id.rd_price_descending:
                filterType = isChecked ? PRICE_DESCENDING : filterType;
                break;
            case R.id.rd_rating_ascending:
                filterType = isChecked ? RATING_ASCENDING : filterType;
                break;
            case R.id.rd_rating_descending:
                filterType = isChecked ? RATING_DESCENDING : filterType;
                break;
        }
    }

    private FilterCallback onFilterCallback;

    public void setOnFilterCallback(FilterCallback onFilterCallback) {
        this.onFilterCallback = onFilterCallback;
    }

    public interface FilterCallback {
        void onFilterPlace(boolean isOpenNow, int filterType);
    }

}
