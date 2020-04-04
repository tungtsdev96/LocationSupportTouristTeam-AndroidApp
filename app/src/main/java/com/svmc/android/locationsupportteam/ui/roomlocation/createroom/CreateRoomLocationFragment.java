package com.svmc.android.locationsupportteam.ui.roomlocation.createroom;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sweetdialog.SweetAlertDialog;
import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.ItemMemberToAddAdapter;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.listeners.SweetAlertDialogListener;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.customviews.CircleImageView;
import com.svmc.android.locationsupportteam.ui.roomlocation.RoomLocationActivity;
import com.svmc.android.locationsupportteam.utils.CommonUtils;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.ErrCode;
import com.svmc.android.locationsupportteam.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by TUNGTS on 4/20/2019
 */

public class CreateRoomLocationFragment extends BaseFragment implements MVPCreateRoomLocation.IViewCreateRoomLocation, View.OnClickListener, TextView.OnEditorActionListener, View.OnFocusChangeListener {

    private MVPCreateRoomLocation.IPresenterCreateRoomLocation presenterCreateRoomLocation;

    private Toolbar toolbar;

    private RelativeLayout rltInforGroup;
    private CircleImageView imgRoom;
    private EditText edtNameRoom;

    private ImageView imgSearch;
    private ImageView imgClear;
    private EditText edtSearch;

    private RecyclerView rcvResultSearch;
    private ItemMemberToAddAdapter itemMemberToAddAdapter;
    private List items;
    private List itemsWatings;

    public static CreateRoomLocationFragment newInstance() {
        CreateRoomLocationFragment createRoomLocationFragment = new CreateRoomLocationFragment();
        createRoomLocationFragment.setTAG(Constans.TagFragment.CREATE_ROOM_LOCATION);
        return createRoomLocationFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_create_room_location;
    }

    @Override
    protected void onFragmentCreateView(View view) {

    }

    @Override
    protected void onFragmentCreated(View view) {

    }

    @Override
    protected void innitView(View view) {
        rltInforGroup = view.findViewById(R.id.rlt_infor_group);
        edtNameRoom = view.findViewById(R.id.edt_name_group);
        imgRoom = view.findViewById(R.id.img_group);

        GlideUtils.loadImageByPath(
                getContext(),
                imgRoom,
                CommonUtils.randomUrlImg(new Random().nextInt(9))
        );

        imgSearch = view.findViewById(R.id.img_search);
        imgClear = view.findViewById(R.id.img_clear);
        edtSearch = view.findViewById(R.id.edt_search);

        innitToolbar(view);
        innitRcvResultSearch(view);
    }

    private void innitToolbar(View view) {
        setHasOptionsMenu(true);
        toolbar = view.findViewById(R.id.toolbar);
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
        ((BaseActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void innitRcvResultSearch(View view) {
        rcvResultSearch = view.findViewById(R.id.rcv_result_search);
        items = new ArrayList();
        itemsWatings = new ArrayList();
        itemMemberToAddAdapter = new ItemMemberToAddAdapter();
        itemMemberToAddAdapter.setItems(items);
        rcvResultSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvResultSearch.setAdapter(itemMemberToAddAdapter);
    }

    @Override
    protected void addEvents() {
        edtNameRoom.addTextChangedListener(textWatcherEdtName);
        edtSearch.addTextChangedListener(textWatcherEdtSearch);
        edtSearch.setOnEditorActionListener(this);
        edtSearch.setOnFocusChangeListener(this);
        imgClear.setOnClickListener(this);
        imgSearch.setOnClickListener(this);

        itemMemberToAddAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
            @Override
            public void onClick(int position) {
                if (items.get(position) instanceof String) return;
                MemberOfRoomLocation member = (MemberOfRoomLocation) items.get(position);
                if (member.getStatus() == MemberOfRoomLocation.MEMBER_INVITED) {
                    presenterCreateRoomLocation.removeMember(position);
                } else {
                    presenterCreateRoomLocation.addMember(member, position);
                }
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.action_done, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        hideKeyboard();
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.action_done:
                showSweetProgressDialog("Đang tạo nhóm");
                presenterCreateRoomLocation.createRoomLocaion();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /***
     * TextWatcher for edt Search
     */
    private TextWatcher textWatcherEdtSearch = new TextWatcher() {
        // edt Search
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                imgClear.setVisibility(View.VISIBLE);
//                if (s.length() >= 3) {
                    if (!itemMemberToAddAdapter.isLoading()) {
                        // clear
                        removeListSearch();
                        items.add(null);
                        itemMemberToAddAdapter.notifyDataSetChanged();
                        itemMemberToAddAdapter.setLoading(true);
                        presenterCreateRoomLocation.showListResultSearch(0, s.toString());
                    }
//                }
            } else {
                removeListSearch();
                imgClear.setVisibility(View.GONE);
                presenterCreateRoomLocation.showListRecentSearch();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /***
     * TextWatcher for EdtName
     */
    private TextWatcher textWatcherEdtName = new TextWatcher() {
        // edt Search
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            presenterCreateRoomLocation.setNameRoom(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.edt_search:
                if (hasFocus) {
                    rltInforGroup.setVisibility(View.GONE);
                } else {
                    rltInforGroup.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            hideKeyboard();
            if (edtSearch.getText().length() > 0 && !itemMemberToAddAdapter.isLoading()) {
                presenterCreateRoomLocation.showListResultSearch(0, edtSearch.getText().toString());
            }
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_clear:
                edtSearch.setText("");
                break;
            case R.id.img_search:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenterCreateRoomLocation.start();
    }

    @Override
    public void setPresenter(MVPCreateRoomLocation.IPresenterCreateRoomLocation iPresenterCreateRoomLocation) {
        if (iPresenterCreateRoomLocation != null) {
            presenterCreateRoomLocation = iPresenterCreateRoomLocation;
        }
    }

    @Override
    public void bindListRecentSearch(ArrayList<MemberOfRoomLocation> users) {
        if (users == null || users.isEmpty()) return;
        items.add("Gần đây");
        items.addAll(users);
        itemMemberToAddAdapter.notifyDataSetChanged();
    }

    @Override
    public void bindListResult(int page, ArrayList<MemberOfRoomLocation> users) {
        if (itemMemberToAddAdapter.isLoading()) {
            items.remove(itemMemberToAddAdapter.getItemCount() - 1);
            itemMemberToAddAdapter.setLoading(false);
            itemMemberToAddAdapter.notifyItemRemoved(itemMemberToAddAdapter.getItemCount() - 1);
        }

        if (users == null || users.size() == 0) return;

        itemMemberToAddAdapter.setLoading(false);
        items.add("Kết quả tìm kiếm");
        items.addAll(users);
        itemMemberToAddAdapter.notifyItemRangeInserted(itemsWatings.size(), users.size() + 1);
    }

    @Override
    public void removeListSearch() {

        int sizeItemWating = itemsWatings.size();
        int sizeItem = items.size();

        if (sizeItemWating == 0) {
            items.clear();
            itemMemberToAddAdapter.notifyDataSetChanged();
            return;
        }

        for (int i = sizeItem - 1; i > sizeItemWating; i--) {
            items.remove(i);
        }

        itemMemberToAddAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateUIAddMember(int pos, MemberOfRoomLocation member) {

        items.remove(pos);
        itemMemberToAddAdapter.notifyItemRemoved(pos);

        itemsWatings.add(member);
        if (itemsWatings.size() == 1) {
            items.add(0, "Đã thêm");
            itemMemberToAddAdapter.notifyItemInserted(0);
        }

        items.add(itemsWatings.size(), member);
        itemMemberToAddAdapter.notifyItemInserted(itemsWatings.size());

        if (items.get(items.size() - 1) instanceof String) {
            items.remove(items.size() - 1);
            itemMemberToAddAdapter.notifyItemRemoved(items.size() - 1);
        }

        rcvResultSearch.smoothScrollToPosition(0);
    }

    @Override
    public void updateUIRemoveMember(int pos) {

        items.remove(pos);
        itemMemberToAddAdapter.notifyItemRemoved(pos);

        itemsWatings.remove(pos - 1);

        if (itemsWatings.size() == 0) {
            items.remove(0);
            itemMemberToAddAdapter.notifyItemRemoved(0);
        }
    }

    @Override
    public void onCreateErr(int errCode) {
        switch (errCode) {
            case ErrCode.RoomLocationCode.ERR_REQUIRE_NAME:
                showErrMessage("Bạn chưa điền tên nhóm");
                break;
            case ErrCode.RoomLocationCode.ERR_REQUIRE_MEMBER:
                showErrMessage("Nhóm phải có ít nhất 2 thành viên");
                break;
            case ErrCode.CommonCode.BAD_REQUEST:
            case ErrCode.CommonCode.ERR_SERVER:
                showErrMessage("Đã có lỗi xảy ra");
                break;
        }
    }

    @Override
    public void onCreateSuccess(final RoomLocation roomLocation) {
        Log.d(getTAG(), new Gson().toJson(roomLocation));
        hideSweetProgressDialog(true, "Đã xong", new SweetAlertDialogListener() {
            @Override
            public void onConfirmClicked(SweetAlertDialog dialog) {
                dialog.dismiss();
                ((RoomLocationActivity) getActivity()).innitShowOnMapFragment(roomLocation);
            }

            @Override
            public void onCancelClicked(SweetAlertDialog dialog) {

            }
        });
    }

}
