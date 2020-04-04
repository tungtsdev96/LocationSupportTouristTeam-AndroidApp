package com.svmc.android.locationsupportteam.ui.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.example.sweetdialog.SweetAlertDialog;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.listeners.AlertListener;
import com.svmc.android.locationsupportteam.listeners.SweetAlertDialogListener;
import com.svmc.android.locationsupportteam.receivers.ConnectivityReceiver;
import com.svmc.android.locationsupportteam.service.LocationService;
import com.svmc.android.locationsupportteam.ui.common.activity.InternetErrActivity;
import com.svmc.android.locationsupportteam.ui.intro.SplashActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by TUNGTS on 2/24/2019
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        onViewCreated(getWindow().getDecorView().getRootView());
    }

    @TargetApi(19)
    public void setStatusBarTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public abstract int getContentView();

    public abstract void onViewCreated(View view);

    public void registerEventBus(){
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    public void unRegisterEventBus(){
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        connectivityReceiver.setOnConnectivityReceiver(onConnectivityChangeListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(connectivityReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean checkPermission(String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermisson(String[] perssions, int key) {
        ActivityCompat.requestPermissions(this, perssions, key);
    }

    public boolean isEnabledPermisson(String[] permissions, int key) {
        for (String permission : permissions) {
            if (!checkPermission(permission)) {
                requestPermisson(permissions, key);
                return false;
            }
        }
        return true;
    }

    public void setActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    // backstack fragment
    /***
     * check Back stack contain fragment
     * @param fragment
     * @return
     */
    public Fragment getFragmentInBackStack(BaseFragment fragment) {
        Fragment frg = getSupportFragmentManager().findFragmentByTag(fragment.getTAG());
        return frg;
    }

    /***
     * get top fragment
     * @return
     */
    public Fragment getCurrentFragment(int idContent) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(idContent);
        return currentFragment;
    }

    /***
     * push fragment into fragment
     * @param idContent
     * @param fragment
     */
    public void pushFragment(int idContent, BaseFragment fragment, boolean isAddToBackstack) {
        hideKeyboard();
        String backStackName = fragment.getTAG();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        // check is current fragment
        Fragment currentFragment = getCurrentFragment(idContent);
        if (currentFragment != null && currentFragment.getTag().equals(fragment.getTAG())) return;

        // pop frag in backstack
        boolean isFragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (isFragmentPopped) return;

        // if backstack not contain frag
        if (!isFragmentPopped && getFragmentInBackStack(fragment) == null) {
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.add(idContent, fragment, fragment.getTAG());
            if (isAddToBackstack) ft.addToBackStack(fragment.getTAG());
            ft.commit();
            return;
        }

        if (!isFragmentPopped) {
            ft.replace(idContent, fragment, fragment.getTAG());
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (isAddToBackstack) ft.addToBackStack(backStackName);
        ft.commit();
    }

    public void pushFragment(int idContent, BaseFragment fragment, int animIn, int animOut, int animPopEnter, int animPopExit, boolean isAddToBackstack) {
        hideKeyboard();
        String backStackName = fragment.getTAG();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        // check is current fragment
        Fragment currentFragment = getCurrentFragment(idContent);
        if (currentFragment != null && currentFragment.getTag().equals(fragment.getTAG())) return;

        // pop frag in backstack
        boolean isFragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (isFragmentPopped) return;

        // if backstack not contain frag
        if (!isFragmentPopped && getFragmentInBackStack(fragment) == null) {
            //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.add(idContent, fragment, fragment.getTAG());
            ft.setCustomAnimations(animIn, animOut, animPopEnter, animPopExit);
            if (isAddToBackstack) ft.addToBackStack(fragment.getTAG());
            ft.commit();
            return;
        }

        if (!isFragmentPopped) {
            ft.replace(idContent, fragment, fragment.getTAG());
        }
        //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.setCustomAnimations(animIn, animOut, animPopEnter, animPopExit);
        if (isAddToBackstack) ft.addToBackStack(backStackName);
        ft.commit();
    }

    /***
     * push fragment into activity
     * @param idContent
     * @param fragment
     */
    public void pushFragment(int idContent, BaseFragment fragment) {
        hideKeyboard();
        String backStackName = fragment.getTAG();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        // check is current fragment
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(idContent);
        if (currentFragment != null && currentFragment.getTag().equals(fragment.getTAG())) return;

        // pop frag in backstack
        boolean isFragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (isFragmentPopped) return;

        // if backstack not contain frag
        if (!isFragmentPopped && getFragmentInBackStack(fragment) == null) {
            ft.add(idContent, fragment, fragment.getTAG());
            ft.addToBackStack(fragment.getTAG());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
            return;
        }

        if (!isFragmentPopped) {
            ft.replace(idContent, fragment, fragment.getTAG());
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(backStackName);
        ft.commit();
    }

    public void replaceFragment(int idContent, BaseFragment fragment, boolean isAddToBackstack) {

        hideKeyboard();
        String backStackName = fragment.getTAG();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        // check is current fragment
        Fragment currentFragment = getCurrentFragment(idContent);
        if (currentFragment != null && currentFragment.getTag().equals(fragment.getTAG())) return;

        ft.replace(idContent, fragment, fragment.getTAG());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (isAddToBackstack) ft.addToBackStack(backStackName);
        ft.commit();
    }

    public void replaceFragment(int idContent, BaseFragment fragment, int animIn, int animOut, int animPopEnter, int animPopExit, boolean isAddToBackstack) {
        hideKeyboard();
        String backStackName = fragment.getTAG();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        // check is current fragment
        Fragment currentFragment = getCurrentFragment(idContent);
        if (currentFragment != null && currentFragment.getTag().equals(fragment.getTAG())) return;

        ft.setCustomAnimations(animIn, animOut, animPopEnter, animPopExit);
        ft.replace(idContent, fragment, fragment.getTAG());
        if (isAddToBackstack) ft.addToBackStack(backStackName);
        ft.commit();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////       Show Alert and progress dialog                    //////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void showAlert(final String title, final String message, final AlertListener listener) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(BaseActivity.this).setTitle(title)
                        .setMessage(message)
                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (listener != null)
                                    listener.onYesClicked();
                            }
                        })
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .create()
                        .show();
            }
        });
    }

    public void showAlert(final String title, final String message, final String noButton, final String yesButton, final AlertListener listener) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(BaseActivity.this).setTitle(title)
                        .setMessage(message)
                        .setNegativeButton(yesButton, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (listener != null)
                                    listener.onYesClicked();
                            }
                        })
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(true)
                        .create()
                        .show();
            }
        });
    }

    protected void showKeyboard(View target) {
        if (target == null) {
            return;
        }
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(target,
                InputMethodManager.SHOW_IMPLICIT);
    }

    protected void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public ProgressDialog mProgressDialog;
    public void showProgressGlobalDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressGlobalDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /***
     * Sweet Alert Dialog
     */
    public void showAlert() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setCancelText("No,cancel plx!")
                .setConfirmText("Yes,delete it!")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance, keep widget user state, reset them if you need
                        sDialog.setTitleText("Cancelled!")
                                .setContentText("Your imaginary file is safe :)")
                                .setConfirmText("OK")
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                        // or you can new a SweetAlertDialog to show
                               /* sDialog.dismiss();
                                new SweetAlertDialog(SampleActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .show();*/
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.setTitleText("Deleted!")
                                .setContentText("Your imaginary file has been deleted!")
                                .setConfirmText("OK")
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }

    /**
     * Progress dialog
     */
    private SweetAlertDialog pDialog;
    protected void showSweetProgressDialog(String message) {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setConfirmText(null);
        pDialog.setTitleText(message);
        pDialog.show();
        pDialog.setCancelable(false);
    }

    protected void hideSweetProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    protected void hideSweetProgressDialog(boolean isSuccess, String text) {
        if (pDialog == null || !pDialog.isShowing()) {
            showErrDialog(text);
            return;
        }

        if (isSuccess) {
            pDialog.setTitleText(text)
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            pDialog.dismiss();
                        }
                    })
                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        } else {
            pDialog.setTitleText(text)
                    .setConfirmText("Thoát")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            pDialog.dismiss();
                        }
                    })
                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
        }
    }

    protected void hideSweetProgressDialog(boolean isSuccess, String text, final SweetAlertDialogListener listener) {
        if (!pDialog.isShowing()) {
            showErrDialog(text);
            return;
        }

        if (isSuccess) {
            pDialog.setTitleText(text)
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            if (listener != null) {
                                listener.onConfirmClicked(sweetAlertDialog);
                            }
                        }
                    })
                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        } else {
            pDialog.setTitleText(text)
                    .setConfirmText("Thoát")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            if (listener != null) {
                                listener.onConfirmClicked(sweetAlertDialog);
                            }
                        }
                    })
                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
        }
    }

    protected void showErrDialog(String message) {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.setTitleText(message)
                    .setConfirmText("Thoát")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
            return;
        }

        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Thông báo")
                .setContentText(message)
                .setConfirmText("Thoát")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * New Sweet alert dialog
     * @param title
     * @param message
     * @param cancelText
     * @param confirmText
     * @param type
     * @param listener
     */
    protected void showSweetAlert(String title, String message, String cancelText, String confirmText, int type, final SweetAlertDialogListener listener) {
        if (pDialog != null && pDialog.isShowing()) pDialog.dismiss();
        SweetAlertDialog dialog = new SweetAlertDialog(this, type);
        dialog
                .setTitleText(title)
                .setContentText(message)
                .setCancelText(cancelText)
                .setConfirmText(confirmText)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        if (listener != null) {
                            listener.onConfirmClicked(sDialog);
                        }
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (listener != null) {
                            listener.onCancelClicked(sweetAlertDialog);
                        }
                    }
                }).show();
    }

    protected void showSweetAlert(String title, String message, String confirmText, int type, final SweetAlertDialogListener listener) {
        if (pDialog != null && pDialog.isShowing()) pDialog.dismiss();
        SweetAlertDialog dialog = new SweetAlertDialog(this, type);
        dialog
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText(confirmText)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        if (listener != null) {
                            listener.onConfirmClicked(sDialog);
                        }
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (listener != null) {
                            listener.onCancelClicked(sweetAlertDialog);
                        }
                    }
                }).show();
    }

    ////////////////////////////////////////////////////////////////////////
    //    service tracking location
    /////////////////////////////////////////////////////////////////////////
    private LocationService locationService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            String name = className.getClassName();

            if (name.endsWith("LocationService")) {
                locationService = ((LocationService.LocationServiceBinder) service).getService();
                locationService.startUpdatingLocation();
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            if (className.getClassName().equals("LocationService")) {
                locationService.stopUpdatingLocation();
                locationService = null;
            }
        }
    };

    private void startService() {
        // start location service
        final Intent locationService = new Intent(this.getApplication(), LocationService.class);
        locationService.setAction("tets");
        this.getApplication().startService(locationService);
        this.getApplication().bindService(locationService, serviceConnection, Context.BIND_AUTO_CREATE);

        // start SyncDataService
//        startService(new Intent(getApplicationContext(), SyncDataService.class));
    }

    public void stopTrackingLocation() {
        if (locationService != null && isMyServiceRunning(LocationService.class)) {
            locationService.stopLogging();
        }
    }

    public void startTrackingLocation() {
        if (locationService != null && isMyServiceRunning(LocationService.class)) {
            locationService.startUpdatingLocation();
        } else {
            startService();
        }
    }

    public Location getCurrentLocation() {
        if (locationService != null) {
            return locationService.getCurrentLocation();
        }
        return null;
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    /***
     * Check internet
     */
    ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
    ConnectivityReceiver.ConnectivityChangeListener onConnectivityChangeListener = new ConnectivityReceiver.ConnectivityChangeListener() {
        @Override
        public void onConnectivityChange(boolean isInternet) {
            if (!isInternet) {
                if (BaseActivity.this instanceof InternetErrActivity) return;
                Log.d("Connectivity", "" + isInternet);
                Intent i = new Intent(getApplicationContext(), InternetErrActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }
    };

    //////////////////////////// One Signal /////////////////////////////////////////////////////////
    OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();
    protected String getPlayerId() {
        return status.getSubscriptionStatus().getUserId();
    }

    protected String getPushToken() {
        return status.getSubscriptionStatus().getPushToken();
    }

}
