package com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.members;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.ViewPagerAdapter;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.members.addmember.InviteMemberToRoomFragment;
import com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.members.addmember.MVPInviteMember;
import com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.members.addmember.PresenterInviteMemberImpl;
import com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.members.listmembers.MVPMemberInRoomLocation;
import com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.members.listmembers.MembersInRoomFragment;
import com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.members.listmembers.PresenterMemberInRoomLocationImpl;
import com.svmc.android.locationsupportteam.utils.Constans;

/**
 * Created by TungTS on 4/28/2019
 */

public class MemberActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private RoomLocation roomLocation;

    private Toolbar toolbar;
    private TabLayout tabMembers;

    private ViewPager viewPagerMembers;
    private ViewPagerAdapter pagerAdapter;

    private InviteMemberToRoomFragment inviteMemberFragment;
    private MembersInRoomFragment  membersInRoomFragment;

    @Override
    public int getContentView() {
        return R.layout.activity_add_member_to_room;
    }

    @Override
    public void onViewCreated(View view) {
        String jsonRoomLocation = getIntent().getStringExtra(Constans.KeyBundle.ROOM_LOCATION);
        roomLocation = new Gson().fromJson(jsonRoomLocation, RoomLocation.class);
        innitToolbar();
        innitViewPager();
    }

    private void innitToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(roomLocation.getNameRoom());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void innitViewPager() {
        tabMembers = findViewById(R.id.tab_members);
        viewPagerMembers = findViewById(R.id.view_pager_members);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        membersInRoomFragment = MembersInRoomFragment.newInstance(roomLocation);
        MVPMemberInRoomLocation.IPresenterMemeberInRoomLocation presenterMemeberInRoomLocation = new PresenterMemberInRoomLocationImpl(membersInRoomFragment);
        pagerAdapter.addFragment(membersInRoomFragment, "Thành viên");

        inviteMemberFragment = InviteMemberToRoomFragment.newInstance(roomLocation);
        MVPInviteMember.IPresenterInviteMember presenterInviteMember = new PresenterInviteMemberImpl(inviteMemberFragment);
        pagerAdapter.addFragment(inviteMemberFragment, "Thêm thành viên");

        viewPagerMembers.setAdapter(pagerAdapter);
        tabMembers.setupWithViewPager(viewPagerMembers);
        viewPagerMembers.addOnPageChangeListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isRefresh = false;

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        hideKeyboard();
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0 && isRefresh) {
            membersInRoomFragment.onRefresh();
            setRefresh(false);
        } else {
            inviteMemberFragment.onRefresh();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
