package com.svmc.android.locationsupportteam.ui.common.dialog.platetype;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.entity.SubPlaceType;
import com.svmc.android.locationsupportteam.ui.base.BaseDialogFragment;
import com.svmc.android.locationsupportteam.utils.FileUtils;

import java.util.List;

/**
 * Created by TUNGTS on 5/29/2019
 */

public class ChoosePlaceTypeDialog extends BaseDialogFragment implements View.OnClickListener {

    private ItemChoosePlaceTypeAdapter adapter;
    private RecyclerView rcvPlaceType;
    private List<SubPlaceType> items;

    private TextView tvDone;

    public static ChoosePlaceTypeDialog newInstance(ChoosePlaceTypeCallBack choosePlaceTypeCallBack) {
        ChoosePlaceTypeDialog dialog = new ChoosePlaceTypeDialog();
        dialog.choosePlaceTypeCallBack = choosePlaceTypeCallBack;
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_choose_place_type, container, false);
    }

    @Override
    protected void setUp(View view) {
        tvDone = view.findViewById(R.id.btn_done);
        innitRcv(view);
        addEvents();
    }

    private void innitRcv(View view) {
        rcvPlaceType = view.findViewById(R.id.rcv_place_type);
        adapter = new ItemChoosePlaceTypeAdapter();
        items = FileUtils.getAllSubPlaceTypes(getContext());
        String oldPlaceTypes = AppPreferencens.getInstance().getListPlaceType();
        adapter.setSubPlaceTypes(items, oldPlaceTypes);
        rcvPlaceType.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvPlaceType.setAdapter(adapter);
    }

    private void addEvents() {
        tvDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_done:
                if (adapter.getListIdChoose() != null && adapter.getListIdChoose().length() > 0) {
                    if (choosePlaceTypeCallBack != null) {
                        choosePlaceTypeCallBack.onChoose(adapter.getListIdChoose());
                        dismiss();
                    }
                } else {
                    Toast.makeText(getContext(), "Bạn chưa chọn loại địa điểm nào", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private ChoosePlaceTypeCallBack choosePlaceTypeCallBack;

    public interface ChoosePlaceTypeCallBack {
        void onChoose(String listPlaceTypeIds);
    }
}
