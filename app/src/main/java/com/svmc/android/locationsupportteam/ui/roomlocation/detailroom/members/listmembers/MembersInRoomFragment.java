package com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.members.listmembers;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sweetdialog.SweetAlertDialog;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.ItemMemberInRoomLocationAdapter;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.listeners.SweetAlertDialogListener;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.ConvertUnsigned;
import com.svmc.android.locationsupportteam.utils.ErrCode;

import java.util.ArrayList;
import java.util.List;

public class MembersInRoomFragment extends BaseFragment implements TextWatcher, View.OnClickListener, MVPMemberInRoomLocation.IViewMemberInRoomLocation, SwipeRefreshLayout.OnRefreshListener {

    private RoomLocation roomLocation;

    private MVPMemberInRoomLocation.IPresenterMemeberInRoomLocation presenterMemeberInRoomLocation;

    private EditText edtSearch;
    private ImageView btnClear;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rcvMember;
    private ItemMemberInRoomLocationAdapter itemMemberInRoomLocationAdapter;
    private List items = new ArrayList();
    private List<MemberOfRoomLocation> coppyList = new ArrayList<>();

    public static MembersInRoomFragment newInstance(RoomLocation roomLocation) {
        MembersInRoomFragment membersInRoomFragment = new MembersInRoomFragment();
        membersInRoomFragment.setTAG(Constans.TagFragment.MEMBERS_IN_GROUP_FRAGMENT);
        membersInRoomFragment.roomLocation = roomLocation;
        return membersInRoomFragment;
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

    }

    @Override
    protected void innitView(View view) {
        edtSearch = view.findViewById(R.id.edt_search);
        btnClear = view.findViewById(R.id.img_clear);
        innitRecycleView(view);
    }

    private void innitRecycleView(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_member);
        swipeRefreshLayout.setRefreshing(true);
        rcvMember = view.findViewById(R.id.rcv_members);
        itemMemberInRoomLocationAdapter = new ItemMemberInRoomLocationAdapter();
        itemMemberInRoomLocationAdapter.setItems(items);
        rcvMember.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvMember.setAdapter(itemMemberInRoomLocationAdapter);
    }

    @Override
    protected void addEvents() {
        edtSearch.addTextChangedListener(this);
        btnClear.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);

        itemMemberInRoomLocationAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
            @Override
            public void onClick(int position) {
                MemberOfRoomLocation member = (MemberOfRoomLocation) items.get(position);
                showDialogQuestion(position, member);
            }
        });
    }

    private SweetAlertDialog mDialog;
    private void showDialogQuestion(final int position, final MemberOfRoomLocation member) {
        showSweetAlert(
                "Thông báo",
                "Bạn có chắc chắn muốn xóa " + member.getDisplayName() + " ra khỏi nhóm ?",
                "Trở về",
                "Xóa",
                SweetAlertDialog.WARNING_TYPE,
                new SweetAlertDialogListener() {
                    @Override
                    public void onConfirmClicked(SweetAlertDialog dialog) {
                        mDialog = dialog;
                        mDialog.setConfirmText(null).setCancelText(null);
                        mDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                        if (member.getStatus() == MemberOfRoomLocation.MEMBER_JOINED) {
                            presenterMemeberInRoomLocation.removeMember(position, member);
                        } else {
                            presenterMemeberInRoomLocation.removeInviation(position, member, roomLocation.getRoomId());
                        }
                        dialog.dismiss();
                        showSweetProgressDialog("Đang xóa");
                    }

                    @Override
                    public void onCancelClicked(SweetAlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void onRefresh() {
        presenterMemeberInRoomLocation.showAllMemberInGroup(roomLocation.getRoomId());
    }

    // edtSearch
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().length() > 0) {
            btnClear.setVisibility(View.VISIBLE);
            filterList(s.toString());
        } else {
            btnClear.setVisibility(View.GONE);
        }
    }

    private void filterList(String query) {
        items.clear();
        if (query.length() == 0) {
            items.addAll(coppyList);
            itemMemberInRoomLocationAdapter.notifyDataSetChanged();
            return;
        }

        ConvertUnsigned convertUnsigned = new ConvertUnsigned();
        for (MemberOfRoomLocation member : coppyList) {
            String nameMember = member.getDisplayName().toLowerCase();
            if (convertUnsigned.convertString(nameMember).contains(query.toLowerCase())) {
                items.add(member);
            }
        }
        itemMemberInRoomLocationAdapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable s) {

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
    public void onPause() {
        super.onPause();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setPresenter(MVPMemberInRoomLocation.IPresenterMemeberInRoomLocation iPresenterMemeberInRoomLocation) {
        if (iPresenterMemeberInRoomLocation != null) {
            presenterMemeberInRoomLocation = iPresenterMemeberInRoomLocation;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenterMemeberInRoomLocation.showAllMemberInGroup(roomLocation.getRoomId());
    }

    @Override
    public void showAllMemberInRoom(ArrayList<MemberOfRoomLocation> members){
        swipeRefreshLayout.setRefreshing(false);
        items.clear();
        coppyList.clear();
        coppyList.addAll(members);
        items.addAll(members);
        itemMemberInRoomLocationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRemoveMemberSuccess(int pos, MemberOfRoomLocation member) {
        hideSweetProgressDialog(true, "Đã xóa");

        items.remove(pos);
        itemMemberInRoomLocationAdapter.notifyItemRemoved(pos);
    }

    @Override
    public void onRemoveMemberFail(int errCode) {
        hideSweetProgressDialog(false, "Không thành công");
    }

    @Override
    public void onLoadDataFail(int errCode) {
        if (errCode == ErrCode.CommonCode.ERR_SERVER){
            showErrMessage("Lỗi kết nối");
        }
    }

}
