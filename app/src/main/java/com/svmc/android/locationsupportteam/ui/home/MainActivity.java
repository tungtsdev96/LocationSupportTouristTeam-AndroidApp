package com.svmc.android.locationsupportteam.ui.home;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.data.local.LocationDataLocal;
import com.svmc.android.locationsupportteam.data.local.entity.LocationCache;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.event.EventPostLoadingData;
import com.svmc.android.locationsupportteam.entity.event.EventPostRoomLocation;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.firebase.FirebaseUtils;
import com.svmc.android.locationsupportteam.firebase.auth.EmailPasswordAuth;
import com.svmc.android.locationsupportteam.firebase.auth.GoogleAuth;
import com.svmc.android.locationsupportteam.network.ApiConfig;
import com.svmc.android.locationsupportteam.network.ApiFactory;
import com.svmc.android.locationsupportteam.network.BaseCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.common.dialog.BottomSheetAlertRoomDialog;
import com.svmc.android.locationsupportteam.ui.customviews.CircleImageView;
import com.svmc.android.locationsupportteam.ui.historylocation.HistoryLocationActivity;
import com.svmc.android.locationsupportteam.ui.notification.NotificationActivity;
import com.svmc.android.locationsupportteam.ui.placesaved.PlaceSavedActivity;
import com.svmc.android.locationsupportteam.ui.roomlocation.startdialog.MVPDialogRoom;
import com.svmc.android.locationsupportteam.ui.roomlocation.startdialog.PresenterDialogRoomImpl;
import com.svmc.android.locationsupportteam.ui.roomlocation.startdialog.RoomLocationDialog;
import com.svmc.android.locationsupportteam.ui.auth.SignInActivity;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.ui.setting.SettingActivity;
import com.svmc.android.locationsupportteam.utils.CommonUtils;
import com.svmc.android.locationsupportteam.utils.Constans;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by TUNGTS
 */

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    RequestOptions options = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.ic_place_hoder)
            .error(R.drawable.ic_place_hoder)
            .skipMemoryCache(true);

    //navigation
    private DrawerLayout drawerLayout;
    private NavigationView navigationHome;

    private Handler mHandler;

    private MainFragment mainFragment;

    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void onViewCreated(View view) {
        mHandler = new Handler();
        setStatusBarTranslucent();
        checkIsFirstStartApp();
        innitViewHeaderLayout();
        addEvents();
    }

    private View headerLayout;
    private void innitViewHeaderLayout() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationHome = findViewById(R.id.navigation_main);
        headerLayout =
                navigationHome.inflateHeaderView(R.layout.navigation_header);

        boolean isTurnOnLocation = AppPreferencens.getInstance().checkTurnOnLocation();
        final SwitchCompat switchLocation = navigationHome.getMenu().findItem(R.id.menu_turn_location).getActionView().findViewById(R.id.switch_location);
        switchLocation.setChecked(isTurnOnLocation);
        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeTurnOnLocaiton(switchLocation);
            }
        });
    }

    private void innitHeaderNavigation(View headerLayout, String name) {
        TextView tvDisplayName = headerLayout.findViewById(R.id.tv_display_name);
        tvDisplayName.setText(name);
    }

    private void addEvents() {
        navigationHome.setNavigationItemSelectedListener(this);
    }

    /**
     * if this is the first start app => Pushfragment Login
     * else PushFragment HomeScreen
     */
    private void checkIsFirstStartApp() {
        if (AppPreferencens.getInstance().isFirstStartApp()) {
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        mainFragment = MainFragment.newInstance();
        MVPMainFragment.IPresenterMain presenterMain = new PresenterMainImpl(mainFragment);
        replaceFragment(R.id.content_main, mainFragment, 0, 0, 0, 0, false);
    }

    public void setUpDrawerLayout(Toolbar toolbar) {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.syncState();
    }

    public void openDrawer() {
        if (drawerLayout != null && !drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.openDrawer(Gravity.START);
        }
    }

    public void closeDrawer() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerEventBus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        innitDataHeaderLayout();
    }

    /***
     * innit data header layout
     */
    private void innitDataHeaderLayout() {

        LinearLayout llProfile = headerLayout.findViewById(R.id.ll_profile_header);
        TextView tvDisplayName = headerLayout.findViewById(R.id.tv_display_name);
        CircleImageView imgAvatar = headerLayout.findViewById(R.id.img_avatar);

        final User user = AppPreferencens.getInstance().getUser();
        if (user != null) {
            tvDisplayName.setText(user.getDisplayName());
            Glide.with(this)
                    .load(ApiConfig.ConfigUrl.URL_NODEJS + user.getUrlImage())
                    .apply(options)
                    .into(imgAvatar);
        } else {
            tvDisplayName.setText("Đăng nhập");
            Glide.with(this)
                    .load(CommonUtils.randomUrlImg(1))
                    .apply(options)
                    .into(imgAvatar);
        }

        llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    login();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unRegisterEventBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mainFragment != null) {
            mainFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mainFragment.onKeyDown(keyCode, event)) {
            return super.onKeyDown(keyCode, event);
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_profile:
                if (!checkLogin()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            login();
                        }
                    }, 350);
                    break;
                }

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                        intent.putExtra(Constans.KeyBundle.UPDATE_PROFILE, true);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                }, 350);
                break;
            case R.id.menu_place:
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, PlaceSavedActivity.class);
                        startActivity(i);
                    }
                }, 350);
                break;
            case R.id.menu_history_location:
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, HistoryLocationActivity.class);
                        startActivity(i);
                    }
                }, 350);
                break;
            case R.id.menu_notification:
                if (!checkLogin()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            login();
                        }
                    }, 350);
                    break;
                }

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                }, 350);
                break;
            case R.id.menu_room_location:
                if (!checkLogin()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            login();
                        }
                    }, 350);
                    break;
                }

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RoomLocationDialog roomLocationDialog = RoomLocationDialog.newInstance();
                        MVPDialogRoom.IPresenterDialogRoom presenterDialogRoom = new PresenterDialogRoomImpl(roomLocationDialog);
                        roomLocationDialog.show(getSupportFragmentManager());
                    }
                }, 350);
                break;
            case R.id.menu_post_alert:
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (AppPreferencens.getInstance().getRoomId() != null) {
                            openDialogAlert();
                        } else {
                            Toast.makeText(MainActivity.this, "Bạn chưa tham gia vào nhóm nào", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 300);
                break;
            case R.id.menu_turn_location:
                ((SwitchCompat) menuItem.getActionView().findViewById(R.id.switch_location)).toggle();
                changeTurnOnLocaiton((SwitchCompat) menuItem.getActionView().findViewById(R.id.switch_location));
                return true;
            case R.id.menu_setting:
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                }, 350);
                break;
            case R.id.menu_sign_out:
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AppPreferencens.getInstance().logout();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            // update offline
                            updateOffline(user.getUid());

                            // sign out
                            EmailPasswordAuth.getInstance().signOut();
                            GoogleAuth.getInstance().signOut();

                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            startActivity(intent);
                            finish();
                        }
                    }
                }, 350);
                break;
        }
        closeDrawer();
        return true;
    }

    private void openDialogAlert() {
        BottomSheetAlertRoomDialog dialog = new BottomSheetAlertRoomDialog();
        dialog.setOnBottomSheetCallBack(new BottomSheetAlertRoomDialog.BottomSheetCallBack() {
            @Override
            public void onCreatedMessage(String message, String alertType, String image) {
                postAlert(message, alertType, image);
            }
        });
        dialog.show(getSupportFragmentManager(), dialog.getTag());
    }

    private void postAlert(String message, String alertType, String image) {
        MultipartBody.Part part = null;
        if (image != null) {
            File fileImage = new File(image);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileImage);
            part = MultipartBody.Part.createFormData("uploaded_file", fileImage.getName(), requestBody);
        }

        User user = AppPreferencens.getInstance().getUser();
        LocationCache location = new LocationDataLocal().getLastLocationCache();
        ParamsPostNotification.RoomParams room = new ParamsPostNotification.RoomParams(AppPreferencens.getInstance().getRoomId());
        ParamsPostNotification.SenderParams senderParams = new ParamsPostNotification.SenderParams(user.getUserId(), user.getDisplayName(), user.getPhoneNumber());
        ParamsPostNotification notification = new ParamsPostNotification(senderParams, room, message, alertType, new LocationPoint(location.getLat(), location.getLng()));
        ApiFactory.getApiRoomLocation().postAlert(part, notification).enqueue(new BaseCallBack<BaseResultResponse>() {
            @Override
            public void onSuccess(BaseResultResponse result) {
                Toast.makeText(MainActivity.this, "Đã gửi", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeTurnOnLocaiton(SwitchCompat actionView) {
        boolean isTurnOn = AppPreferencens.getInstance().setTurnOnLocation();
        Log.d("MainActivity", isTurnOn + "");
        actionView.setChecked(isTurnOn);
    }

    private void updateOffline(String uid) {
        ApiFactory.getApiUser().updateOnOffLine(uid, false).enqueue(new BaseCallBack<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody result) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void login() {
        startActivity(new Intent(MainActivity.this, SignInActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private boolean checkLogin() {
        return FirebaseUtils.getFirebaseAuth().getCurrentUser() != null;
    }

    /***
     * check user invited or joined
     */
    private String roomIdJoined;
    boolean isLoadingData = false;

    public boolean isLoadingData() {
        return isLoadingData;
    }

    public String getRoomIdJoined() {
        return roomIdJoined;
    }

    public void setRoomIdJoined(String roomIdJoined) {
        this.roomIdJoined = roomIdJoined;
    }

    @Subscribe
    public void onEvents(EventPostRoomLocation eventPostRoomLocation) {
        this.roomIdJoined = eventPostRoomLocation.roomId;
    }

    @Subscribe
    public void onEvents(EventPostLoadingData eventPostLoadingData) {
        this.isLoadingData = true;
    }

    /***
     * show notification invited user to room
     */
    private void showNotifiInvitedRoom() {

    }

}
