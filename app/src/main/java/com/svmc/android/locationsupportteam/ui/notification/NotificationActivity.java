package com.svmc.android.locationsupportteam.ui.notification;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.ViewPagerAdapter;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.notification.invited.InvitedNotifyFragment;
import com.svmc.android.locationsupportteam.ui.notification.invited.MVPInvitedNotification;
import com.svmc.android.locationsupportteam.ui.notification.invited.PresenterInvitedNotificationImpl;
import com.svmc.android.locationsupportteam.ui.notification.room.MVPRoomNotification;
import com.svmc.android.locationsupportteam.ui.notification.room.alert.AlertNotifyFragment;
import com.svmc.android.locationsupportteam.ui.notification.room.alert.PresenterAlertNotifyImpl;
import com.svmc.android.locationsupportteam.ui.notification.room.shareplace.PresenterShareNotificationImpl;
import com.svmc.android.locationsupportteam.ui.notification.room.shareplace.ShareNotificationFragment;

/**
 * Create by TungTS on 5/2/2019
 */

public class NotificationActivity extends BaseActivity {

    private Toolbar toolbar;

    private ViewPagerAdapter pagerAdapter;
    private ViewPager viewPagerNotify;
    private TabLayout tabsNotification;

    @Override
    public int getContentView() {
        return R.layout.activity_notification;
    }

    @Override
    public void onViewCreated(View view) {
        innitView();
    }

    private void innitView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.notification));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        innitViewPager();
    }

    private void innitViewPager() {
        tabsNotification = findViewById(R.id.tabs_notification);
        viewPagerNotify = findViewById(R.id.view_pager_notification);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        InvitedNotifyFragment invitedNotifyFragment = InvitedNotifyFragment.newInstancce();
        MVPInvitedNotification.IPresenterInvitedNotification presenterInvitedNotification = new PresenterInvitedNotificationImpl(invitedNotifyFragment);
        pagerAdapter.addFragment(invitedNotifyFragment, "Lời mời");

        ShareNotificationFragment shareNotificationFragment = ShareNotificationFragment.newInstancce();
        MVPRoomNotification.IPresenterRoomNotification presenterShareNotification = new PresenterShareNotificationImpl(shareNotificationFragment);
        pagerAdapter.addFragment(shareNotificationFragment, "Chia sẻ");

        AlertNotifyFragment alertNotifyFragment = AlertNotifyFragment.newInstancce();
        MVPRoomNotification.IPresenterRoomNotification presenterAlertNotification = new PresenterAlertNotifyImpl(alertNotifyFragment);
        pagerAdapter.addFragment(alertNotifyFragment, "Sự cố");

        viewPagerNotify.setAdapter(pagerAdapter);
        tabsNotification.setupWithViewPager(viewPagerNotify);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
