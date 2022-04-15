package com.ph.permissions;

/**
 *    author : i小灰
 *    github : https://github.com/dahui888/HPermissions
 *    desc   : 权限设置页结果回调接口
 */
public interface OnPermissionPageCallback {

    /**
     * 权限已经授予
     */
    void onGranted();

    /**
     * 权限已经拒绝
     */
    void onDenied();
}