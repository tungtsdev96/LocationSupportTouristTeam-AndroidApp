package com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.members.addmember;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.ItemMemberToAddAdapter;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.members.MemberActivity;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.ArrayList;
import java.util.List;

public class InviteMemberToRoomFragment extends BaseFragment
        implements TextWatcher, View.OnClickListener, MVPInviteMember.IViewInviteMember, TextView.OnEditorActionListener, SwipeRefreshLayout.OnRefreshListener {

    private RoomLocation roomLocation;

    private MVPInviteMember.IPresenterInviteMember presenterMemeberInGroup;

    private EditText edtSearch;
    private ImageView btnClear;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rcvResultSearch;
    private ItemMemberToAddAdapter itemMemberToAddAdapter;
    private List items = new ArrayList();

    private ArrayList<MemberOfRoomLocation> listMemberWaitting = new ArrayList<>();

    public static InviteMemberToRoomFragment newInstance(RoomLocation roomLocation) {
        InviteMemberToRoomFragment addMemberToRoomFragment = new InviteMemberToRoomFragment();
        addMemberToRoomFragment.setTAG(Constans.TagFragment.INVITE_MEMBER_TO_ROOM);
        addMemberToRoomFragment.roomLocation = roomLocation;
        return addMemberToRoomFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tabs_member;
    }

    @Override
    protected void onFragmentCreateView(View view) {

    }

    @Override
    protected void onFragmentCreated(View view) {
        refreshLayout = view.findViewById(R.id.swipe_refresh_member);
    }

    @Override
    protected void innitView(View view) {
        edtSearch = view.findViewById(R.id.edt_search);
        btnClear = view.findViewById(R.id.img_clear);
        innitRecycleView(view);
    }

    private void innitRecycleView(View view) {
        rcvResultSearch = view.findViewById(R.id.rcv_members);
        itemMemberToAddAdapter = new ItemMemberToAddAdapter();
        itemMemberToAddAdapter.setItems(items);
        rcvResultSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvResultSearch.setAdapter(itemMemberToAddAdapter);
    }

    @Override
    protected void addEvents() {
        edtSearch.addTextChangedListener(this);
        btnClear.setOnClickListener(this);
        edtSearch.setOnEditorActionListener(this);
        refreshLayout.setOnRefreshListener(this);

        itemMemberToAddAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
            @Override
            public void onClick(int position) {
                if (items.get(position) instanceof String) return;
                handleListWaitting(position);
            }
        });
    }

    @Override
    public void onRefresh() {
        if (itemMemberToAddAdapter.isLoading() || edtSearch.getText().length() == 0) {
            refreshLayout.setRefreshing(false);
            return;
        }

        if (edtSearch.getText().length() > 0) {
            refreshLayout.setRefreshing(true);
            presenterMemeberInGroup.showListResultSearch(0, edtSearch.getText().toString(), roomLocation.getRoomId());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        refreshLayout.setRefreshing(false);
    }

    // edtSearch
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            btnClear.setVisibility(View.VISIBLE);
            if (s.length() >= 3) {
                if (!itemMemberToAddAdapter.isLoading()) {
                    items.clear();
                    items.add(null);
                    itemMemberToAddAdapter.notifyDataSetChanged();
                    itemMemberToAddAdapter.setLoading(true);
                    presenterMemeberInGroup.showListResultSearch(0, s.toString(), roomLocation.getRoomId());
                }
            }
        } else {
            items.clear();
            itemMemberToAddAdapter.notifyDataSetChanged();
            btnClear.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            hideKeyboard();
            if (edtSearch.getText().length() > 0){
                presenterMemeberInGroup.showListResultSearch(0, edtSearch.getText().toString(), roomLocation.getRoomId());
            }
            return true;
        }
        return false;
    }

    // on click
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_clear:
                edtSearch.setText("");
                btnClear.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenterMemeberInGroup.start();
    }

    @Override
    public void setPresenter(MVPInviteMember.IPresenterInviteMember iPresenterInviteMember) {
        if (iPresenterInviteMember != null) {
            presenterMemeberInGroup = iPresenterInviteMember;
        }
    }

    private void handleListWaitting(int pos) {
        MemberOfRoomLocation member = (MemberOfRoomLocation) items.get(pos);
        if (member.getStatus() == MemberOfRoomLocation.MEMBER_LEFT) {
            showSweetProgressDialog("Đang cập nhật");
            presenterMemeberInGroup.inviteMemberToRoom(member, roomLocation.getRoomId(), pos);
        } else {
            showSweetProgressDialog("Đang xóa");
            presenterMemeberInGroup.removeInvitation(member, roomLocation.getRoomId(), pos);
        }
    }

    @Override
    public void removeMemberToListWaiting(int pos, MemberOfRoomLocation member) {
        hideSweetProgressDialog(true, "Đã xóa");
        items.remove(pos);
        itemMemberToAddAdapter.notifyItemRemoved(pos);

        listMemberWaitting.remove(pos - 1);
        if (listMemberWaitting.size() == 0) {
            items.remove(0);
            itemMemberToAddAdapter.notifyItemRemoved(0);
        }

        ((MemberActivity) getActivity()).setRefresh(true);
    }

    @Override
    public void addMemberToListWaiting(int pos, MemberOfRoomLocation member) {
        hideSweetProgressDialog(true, "Đã thêm");

        listMemberWaitting.add(member);

        items.remove(pos);
        itemMemberToAddAdapter.notifyItemRemoved(pos);

        if (listMemberWaitting.size() == 1) {
            items.add(0, "Đã thêm");
            itemMemberToAddAdapter.notifyItemInserted(0);
        }

        member.setStatus(MemberOfRoomLocation.MEMBER_INVITED);
        items.add(listMemberWaitting.size(), member);
        itemMemberToAddAdapter.notifyItemInserted(listMemberWaitting.size());

        if (items.get(items.size() - 1) instanceof String) {
            items.remove(items.size() - 1);
            itemMemberToAddAdapter.notifyItemRemoved(items.size() - 1);
        }

        rcvResultSearch.smoothScrollToPosition(0);
        ((MemberActivity) getActivity()).setRefresh(true);
    }

    @Override
    public void onErr(int errCode) {
//        hideSweetProgressDialog(false, getContext().getString(R.string.check_internet));
    }

    @Override
    public void bindListResult(int page, ArrayList<MemberOfRoomLocation> users) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }

        if (users == null || users.isEmpty()) {
            items.clear();
            itemMemberToAddAdapter.notifyItemRemoved(itemMemberToAddAdapter.getItemCount());
            itemMemberToAddAdapter.setLoading(false);
            return;
        }

        listMemberWaitting.clear();
        items.clear();
        items.add("Kết quả tìm kiếm");

        for (MemberOfRoomLocation user: users) {
            if (user.getStatus() == MemberOfRoomLocation.MEMBER_INVITED) {
                listMemberWaitting.add(user);
                if (listMemberWaitting.size() == 1) {
                    items.add(0, "Đã thêm");
                }
                items.add(listMemberWaitting.size(), user);
                continue;
            }

            items.add(user);
        }

        itemMemberToAddAdapter.notifyDataSetChanged();
        itemMemberToAddAdapter.setLoading(false);
    }

}
