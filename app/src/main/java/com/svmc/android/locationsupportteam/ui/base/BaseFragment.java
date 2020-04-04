package com.svmc.android.locationsupportteam.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.sweetdialog.SweetAlertDialog;
import com.svmc.android.locationsupportteam.listeners.SweetAlertDialogListener;
import com.svmc.android.locationsupportteam.ui.auth.SignInActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by TUNGTS on 2/24/2019
 */

public abstract class BaseFragment extends Fragment {

    private String TAG;

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        onFragmentCreateView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onFragmentCreated(view);
        innitView(view);
        addEvents();
    }

    protected abstract int getLayoutId();

    protected abstract void onFragmentCreateView(View view);

    protected abstract void onFragmentCreated(View view);

    protected abstract void innitView(View view);

    protected abstract void addEvents();

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
    public void onStart() {
        super.onStart();
        Log.d(this.getTAG(), "onStart ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(this.getTAG(), "onResume ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(this.getTAG(), "onPause ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(this.getTAG(), "onStop ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(this.getTAG(), "onDestroy ");
    }

    // set up status bar
    public void setUpSatusBar(){
        ((BaseActivity) getActivity()).setStatusBarTranslucent();
    }

    /***
     * check Back stack contain fragment
     * @param fragment
     * @return
     */
    public Fragment getFragmentInBackStack(BaseFragment fragment) {
        Fragment frg = getChildFragmentManager().findFragmentByTag(fragment.getTAG());
        return frg;
    }

    /***
     * get top fragment
     * @return
     */
    public Fragment getCurrentFragment(int idContent) {
        Fragment currentFragment = getChildFragmentManager().findFragmentById(idContent);
        return currentFragment;
    }

    /***
     * push fragment into fragment
     * @param idContent
     * @param fragment
     */
    public void pushFragment(int idContent, BaseFragment fragment) {
        hideKeyboard();
        String backStackName = fragment.getTAG();

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        // check is current fragment
        Fragment currentFragment = getChildFragmentManager().findFragmentById(idContent);
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

    protected void showKeyboard(View target) {
        if (target == null) {
            return;
        }
        target.requestFocus();
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(target,
                InputMethodManager.SHOW_IMPLICIT);
        if (target instanceof EditText)
            ((EditText) target).setSelection(((EditText) target).getText().length());
    }

    protected void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            view.clearFocus();
        }
    }

    /**
     * Requests the fine location permission. If a rationale with an additional explanation should
     * be shown to the user, displays a dialog that triggers the request.
     */
    public void requestPermission(Fragment fragment, int requestId,
                                         String permission, boolean finishActivity) {
        fragment.requestPermissions(new String[]{permission}, requestId);
    }

    /**
     * Checks if the result contains a {@link PackageManager#PERMISSION_GRANTED} result for a
     * permission from a runtime permissions request.
     *
     * @see android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback
     */
    public boolean isPermissionGranted(String[] grantPermissions, int[] grantResults,
                                              String permission) {
        for (int i = 0; i < grantPermissions.length; i++) {
            if (permission.equals(grantPermissions[i])) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }

    /******************************************************/
    /******** Sweet Progress Dialog ***********************/

    protected void showSweetProgressDialog(String message) {
        ((BaseActivity) getActivity()).showSweetProgressDialog(message);
    }

    protected void hideSweetProgressDialog(){
        ((BaseActivity) getActivity()).hideSweetProgressDialog();
    }

    protected void hideSweetProgressDialog(boolean isSuccess, String message) {
        ((BaseActivity) getActivity()).hideSweetProgressDialog(isSuccess, message);
    }

    protected void hideSweetProgressDialog(boolean isSuccess, String text, final SweetAlertDialogListener listener){
        ((BaseActivity) getActivity()).hideSweetProgressDialog(isSuccess, text, listener);
    }

    protected void showErrMessage(String message) {
        ((BaseActivity) getActivity()).showErrDialog(message);
    }

    protected void showSweetAlert(String title, String message, String confirmText, int type, final SweetAlertDialogListener listener){
        ((BaseActivity) getActivity()).showSweetAlert(title, message, confirmText, type, listener);
    }

    protected void showSweetAlert(String title, String message, String cancelText, String confirmText, int type, SweetAlertDialogListener listener) {
        ((BaseActivity) getActivity()).showSweetAlert(title, message, cancelText, confirmText, type, listener);
    }

    /***
     * Login
     */
    protected void signIn(){
        showSweetAlert(
                "Thông báo",
                "Đã có lỗi xảy ra hãy đăng nhập lại",
                null,
                "Ok",
                SweetAlertDialog.WARNING_TYPE,
                new SweetAlertDialogListener() {
                    @Override
                    public void onConfirmClicked(SweetAlertDialog dialog) {
                        startActivity(new Intent(getActivity(), SignInActivity.class));
                        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }

                    @Override
                    public void onCancelClicked(SweetAlertDialog dialog) {

                    }
                });
    }

}
