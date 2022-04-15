package com.ph.permissions;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 *    author : i小灰
 *    github : https://github.com/dahui888/HPermissions
 *    desc   : 权限页跳转 Fragment
 */
@SuppressWarnings("deprecation")
public final class PermissionPageFragment extends Fragment implements Runnable {

    /** 权限设置页跳转请求码 */
    public static final int REQUEST_CODE = 1024 + 1;

    /** 请求的权限组 */
    private static final String REQUEST_PERMISSIONS = "request_permissions";

    /**
     * 开启权限申请
     */
    public static void beginRequest(Activity activity, ArrayList<String> permissions, OnPermissionPageCallback callback) {
        PermissionPageFragment fragment = new PermissionPageFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(REQUEST_PERMISSIONS, permissions);
        fragment.setArguments(bundle);
        // 设置保留实例，不会因为屏幕方向或配置变化而重新创建
        fragment.setRetainInstance(true);
        // 设置权限申请标记
        fragment.setRequestFlag(true);
        // 设置权限回调监听
        fragment.setCallBack(callback);
        // 绑定到 Activity 上面
        fragment.attachActivity(activity);
    }

    /**
     * 绑定 Activity
     */
    public void attachActivity(Activity activity) {
        activity.getFragmentManager().beginTransaction().add(this, this.toString()).commitAllowingStateLoss();
    }

    /**
     * 解绑 Activity
     */
    public void detachActivity(Activity activity) {
        activity.getFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
    }

    /** 权限回调对象 */
    private OnPermissionPageCallback mCallBack;

    /** 权限申请标记 */
    private boolean mRequestFlag;

    /** 是否申请了权限 */
    private boolean mStartActivityFlag;

    @Override
    public void onResume() {
        super.onResume();

        // 如果当前 Fragment 是通过系统重启应用触发的，则不进行权限申请
        if (!mRequestFlag) {
            detachActivity(getActivity());
            return;
        }

        if (mStartActivityFlag) {
            return;
        }

        mStartActivityFlag = true;

        Bundle arguments = getArguments();
        Activity activity = getActivity();
        if (arguments == null || activity == null) {
            return;
        }
        List<String> permissions = arguments.getStringArrayList(REQUEST_PERMISSIONS);
        startActivityForResult(PermissionPageIntent.getSmartPermissionIntent(getActivity(), permissions), HPermissions.REQUEST_CODE);
    }

    /**
     * 设置权限监听回调监听
     */
    public void setCallBack(OnPermissionPageCallback callback) {
        mCallBack = callback;
    }

    /**
     * 权限申请标记（防止系统杀死应用后重新触发请求的问题）
     */
    public void setRequestFlag(boolean flag) {
        mRequestFlag = flag;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE) {
            return;
        }
        // 需要延迟执行，不然有些华为机型授权了但是获取不到权限
        PermissionUtils.postDelayed(this, 300);
    }

    @Override
    public void run() {
        // 如果用户离开太久，会导致 Activity 被回收掉
        // 所以这里要判断当前 Fragment 是否有被添加到 Activity
        // 可在开发者模式中开启不保留活动来复现这个 Bug
        if (!isAdded()) {
            return;
        }

        Activity activity = getActivity();
        if (activity == null) {
            return;
        }

        OnPermissionPageCallback callback = mCallBack;
        mCallBack = null;

        if (callback == null) {
            detachActivity(getActivity());
            return;
        }

        Bundle arguments = getArguments();
        List<String> allPermissions = arguments.getStringArrayList(REQUEST_PERMISSIONS);

        List<String> grantedPermissions = PermissionApi.getGrantedPermissions(activity, allPermissions);
        if (grantedPermissions.size() == allPermissions.size()) {
            callback.onGranted();
        } else {
            callback.onDenied();
        }
    }
}