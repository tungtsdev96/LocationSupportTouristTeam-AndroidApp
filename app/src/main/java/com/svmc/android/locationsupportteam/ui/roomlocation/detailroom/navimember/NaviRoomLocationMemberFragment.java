package com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.navimember;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.ItemMemberOnOffAdapter;
import com.svmc.android.locationsupportteam.entity.UserLocation;
import com.svmc.android.locationsupportteam.entity.event.EventPostUpdateStatusUser;
import com.svmc.android.locationsupportteam.entity.event.EventPostUserOnOffline;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.DetailRoomLocationFragment;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.ConvertUnsigned;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/21/2019
 */

public class NaviRoomLocationMemberFragment extends BaseFragment implements MVPNaviRoomLocation.IViewNaviRoomLocation, View.OnClickListener, TextWatcher, View.OnFocusChangeListener, TextView.OnEditorActionListener, SwipeRefreshLayout.OnRefreshListener {

    private RoomLocation roomLocation;
    private MVPNaviRoomLocation.IPresenterNaviRoomLocation presenterNaviRoomLocation;

    private ImageView imgBack;
    private TextView tvTitle;

    private EditText edtSearch;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rcvMember;
    private ItemMemberOnOffAdapter itemMemberOnOffAdapter;
    private List itemMembers;
    private List<MemberOfRoomLocation> coppyList = new ArrayList<>();

    public static NaviRoomLocationMemberFragment newInstance(RoomLocation roomLocation){
        NaviRoomLocationMemberFragment naviRoomLocationMemberFragment = new NaviRoomLocationMemberFragment();
        naviRoomLocationMemberFragment.setTAG(Constans.TagFragment.NAVI_ROOM_LOCATION_FRAGMENT);
        naviRoomLocationMemberFragment.roomLocation = roomLocation;
        return naviRoomLocationMemberFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navi_room_location;
    }

    @Override
    protected void onFragmentCreateView(View view) {
    }

    @Override
    protected void onFragmentCreated(View view) {

    }

    @Override
    protected void innitView(View view) {
        refreshLayout = view.findViewById(R.id.swipe_refresh);
        imgBack = view.findViewById(R.id.img_back);
        tvTitle = view.findViewById(R.id.tv_title);
        edtSearch = view.findViewById(R.id.edt_search);
        innitRcvMember(view);

        view.findViewById(R.id.btn_choose_map_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setItems(R.array.map_type, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        ((DetailRoomLocationFragment) getParentFragment()).setMapType(position);
                    }
                });
                builder.create().show();
            }
        });
    }

    private void innitRcvMember(View view) {
        rcvMember = view.findViewById(R.id.rcv_members);
        itemMembers = new ArrayList();
        itemMembers.add(null);
        itemMemberOnOffAdapter = new ItemMemberOnOffAdapter();
        itemMemberOnOffAdapter.setItems(itemMembers);
        rcvMember.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvMember.setAdapter(itemMemberOnOffAdapter);
    }

    @Override
    protected void addEvents() {
        imgBack.setOnClickListener(this);
        edtSearch.addTextChangedListener(this);
        edtSearch.setOnFocusChangeListener(this);
        edtSearch.setOnEditorActionListener(this);

        itemMemberOnOffAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
            @Override
            public void onClick(int position) {
                presenterNaviRoomLocation.changeCameraTo(((MemberOfRoomLocation)itemMembers.get(position)).getUserId());
            }
        });

        itemMemberOnOffAdapter.setOnClickCallAction(new ItemMemberOnOffAdapter.ClickCallAction() {
            @Override
            public void clickCall(int pos) {
                phone = ((MemberOfRoomLocation)itemMembers.get(pos)).getPhone();
                callTo();
            }
        });

        refreshLayout.setOnRefreshListener(this);
    }

    private String phone;
    private void callTo() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            requestPermission(this, Constans.RequestCode.RC_CALL_PHONE, Manifest.permission.CALL_PHONE, true);
        } else {
            String dial = "tel:" + phone;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (isPermissionGranted(permissions, grantResults, Manifest.permission.CALL_PHONE)) {
            callTo();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                break;
        }
    }

    @Override
    public void onRefresh() {
        itemMembers.clear();
        itemMemberOnOffAdapter.notifyDataSetChanged();
        presenterNaviRoomLocation.showListMember();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        filterList(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void filterList(String query) {
        itemMembers.clear();
        if (query.length() == 0) {
            itemMembers.addAll(coppyList);
            itemMemberOnOffAdapter.notifyDataSetChanged();
            return;
        }

        ConvertUnsigned convertUnsigned = new ConvertUnsigned();
        for (MemberOfRoomLocation member : coppyList) {
            String nameMember = member.getDisplayName().toLowerCase();
            if (convertUnsigned.convertString(nameMember).contains(query.toLowerCase())) {
                itemMembers.add(member);
            }
        }
        itemMemberOnOffAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            hideKeyboard();
            return true;
        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerEventBus();
    }

    @Override
    public void onStop() {
        super.onStop();
        unRegisterEventBus();
    }

    @Override
    public void onPause() {
        super.onPause();
        refreshLayout.setRefreshing(false);
    }

    /***
     * Update when user change status on room location
     * joined -> left
     * invited -> joined
     * @param eventPostUpdateStatusUser
     */
    @Subscribe
    public void onEventUpdateStatusUser(EventPostUpdateStatusUser eventPostUpdateStatusUser) {
        for (int i = 0; i < itemMembers.size(); i++) {
            MemberOfRoomLocation member = (MemberOfRoomLocation) itemMembers.get(i);
            if (member.getUserId().equals(eventPostUpdateStatusUser.userId)) {
                if (eventPostUpdateStatusUser.status == MemberOfRoomLocation.MEMBER_LEFT) {
                    itemMembers.remove(i);
                    itemMemberOnOffAdapter.notifyItemRemoved(i);
                } else if (eventPostUpdateStatusUser.status == MemberOfRoomLocation.MEMBER_JOINED){
                    ((MemberOfRoomLocation) itemMembers.get(i)).setStatus(MemberOfRoomLocation.MEMBER_JOINED);
                    itemMemberOnOffAdapter.notifyItemChanged(i);
                }
                return;
            }
        }
    }

    /***
     * Update when user On, offline
     * @param eventPostUserOnOffline
     */
    @Subscribe
    public void onEventUpdateStatusUser(EventPostUserOnOffline eventPostUserOnOffline) {
        UserLocation userLocation = eventPostUserOnOffline.userLocation;
        for (int i = 0; i < itemMembers.size(); i++) {
            Log.d(getTAG(), userLocation.getLastTimeOnline() + "");
            MemberOfRoomLocation member = (MemberOfRoomLocation) itemMembers.get(i);
            if (member.getUserId().equals(userLocation.getUserId())) {
                ((MemberOfRoomLocation) itemMembers.get(i)).setUserLocation(userLocation);
                itemMemberOnOffAdapter.notifyItemChanged(i);
                return;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        presenterNaviRoomLocation.setRoomLocation(roomLocation);
        presenterNaviRoomLocation.showListMember();
    }

    @Override
    public void setPresenter(MVPNaviRoomLocation.IPresenterNaviRoomLocation iPresenterNaviRoomLocation) {
        if (iPresenterNaviRoomLocation != null) {
            this.presenterNaviRoomLocation = iPresenterNaviRoomLocation;
        }
    }

    @Override
    public void showListMember(final ArrayList<MemberOfRoomLocation> memberOfRoomLocations) {
        refreshLayout.setRefreshing(false);
        itemMembers.clear();
        coppyList.clear();
        coppyList.addAll(memberOfRoomLocations);
        itemMembers.addAll(memberOfRoomLocations);
        itemMemberOnOffAdapter.notifyDataSetChanged();
    }

    @Override
    public void onInvalidToken() {

    }

}
