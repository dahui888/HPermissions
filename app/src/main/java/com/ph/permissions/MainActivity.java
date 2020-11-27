package com.ph.permissions;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

/**
 *    author : i小灰
 *    github : https://github.com/dahui888
 *    time   : 2020/11/11
 *    desc   : HPermissions 权限请求框架使用案例
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_main_request_1).setOnClickListener(this);
        findViewById(R.id.btn_main_request_2).setOnClickListener(this);
        findViewById(R.id.btn_main_request_3).setOnClickListener(this);
        findViewById(R.id.btn_main_request_4).setOnClickListener(this);
        findViewById(R.id.btn_main_request_5).setOnClickListener(this);
        findViewById(R.id.btn_main_request_6).setOnClickListener(this);
        findViewById(R.id.btn_main_request_7).setOnClickListener(this);
        findViewById(R.id.btn_main_request_8).setOnClickListener(this);
        findViewById(R.id.btn_main_app_details).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_main_request_1:
                HPermissions.with(this)
                        .permission(Permission.CAMERA)
                        .request(new OnPermission() {

                            @Override
                            public void hasPermission(List<String> granted, boolean all) {
                                if (all) {
                                    toast("获取拍照权限成功");
                                }
                            }

                            @Override
                            public void noPermission(List<String> denied, boolean never) {
                                if (never) {
                                    toast("被永久拒绝授权，请手动授予拍照权限");
                                    // 如果是被永久拒绝就跳转到应用权限系统设置页面
                                    HPermissions.startPermissionActivity(MainActivity.this, denied);
                                } else {
                                    toast("获取拍照权限失败");
                                }
                            }
                        });
                break;
            case R.id.btn_main_request_2:
                HPermissions.with(this)
                        .permission(Permission.RECORD_AUDIO)
                        .permission(Permission.Group.CALENDAR)
                        .request(new OnPermission() {

                            @Override
                            public void hasPermission(List<String> granted, boolean all) {
                                if (all) {
                                    toast("获取录音和日历权限成功");
                                } else {
                                    toast("获取部分权限成功，但是部分权限未正常授予");
                                }
                            }

                            @Override
                            public void noPermission(List<String> denied, boolean never) {
                                if (never) {
                                    toast("被永久拒绝授权，请手动授予录音和日历权限");
                                    // 如果是被永久拒绝就跳转到应用权限系统设置页面
                                    HPermissions.startPermissionActivity(MainActivity.this, denied);
                                } else {
                                    toast("获取权限失败");
                                }
                            }
                        });
                break;
            case R.id.btn_main_request_3:
                HPermissions.with(this)
                        .permission(Permission.Group.LOCATION)
                        .request(new OnPermission() {

                            @Override
                            public void hasPermission(List<String> granted, boolean all) {
                                if (all) {
                                    toast("获取定位权限成功");
                                }
                            }

                            @Override
                            public void noPermission(List<String> denied, boolean never) {
                                if (never) {
                                    toast("被永久拒绝授权，请手动授予定位权限");
                                    // 如果是被永久拒绝就跳转到应用权限系统设置页面
                                    HPermissions.startPermissionActivity(MainActivity.this, denied);
                                    return;
                                }

                                if (denied.size() == 1 && Permission.ACCESS_BACKGROUND_LOCATION.equals(denied.get(0))) {
                                    toast("没有授予后台定位权限，请您选择\"始终允许\"");
                                } else {
                                    toast("获取定位权限失败");
                                }
                            }
                        });
                break;
            case R.id.btn_main_request_4:
                long delayMillis = 0;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    delayMillis = 2000;
                    toast("当前版本不是 Android 11 以上，会自动变更为旧版的请求方式");
                }

                view.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        HPermissions.with(MainActivity.this)
                                // 不适配 Android 11 可以这样写
                                //.permission(Permission.Group.STORAGE)
                                // 适配 Android 11 需要这样写，这里无需再写 Permission.Group.STORAGE
                                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                                .request(new OnPermission() {

                                    @Override
                                    public void hasPermission(List<String> granted, boolean all) {
                                        if (all) {
                                            toast("获取存储权限成功");
                                        }
                                    }

                                    @Override
                                    public void noPermission(List<String> denied, boolean never) {
                                        if (never) {
                                            toast("被永久拒绝授权，请手动授予存储权限");
                                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                                            HPermissions.startPermissionActivity(MainActivity.this, denied);
                                        } else {
                                            toast("获取存储权限失败");
                                        }
                                    }
                                });
                    }
                }, delayMillis);
                break;
            case R.id.btn_main_request_5:
                HPermissions.with(this)
                        .permission(Permission.REQUEST_INSTALL_PACKAGES)
                        .request(new OnPermission() {

                            @Override
                            public void hasPermission(List<String> granted, boolean all) {
                                toast("获取安装包权限成功");
                            }

                            @Override
                            public void noPermission(List<String> denied, boolean never) {
                                toast("获取安装包权限失败，请手动授予权限");
                            }
                        });
                break;
            case R.id.btn_main_request_6:
                HPermissions.with(this)
                        .permission(Permission.SYSTEM_ALERT_WINDOW)
                        .request(new OnPermission() {

                            @Override
                            public void hasPermission(List<String> granted, boolean all) {
                                toast("获取悬浮窗权限成功");
                            }

                            @Override
                            public void noPermission(List<String> denied, boolean never) {
                                toast("获取悬浮窗权限失败，请手动授予权限");
                            }
                        });
                break;
            case R.id.btn_main_request_7:
                HPermissions.with(this)
                        .permission(Permission.NOTIFICATION_SERVICE)
                        .request(new OnPermission() {

                            @Override
                            public void hasPermission(List<String> granted, boolean all) {
                                toast("获取通知栏权限成功");
                            }

                            @Override
                            public void noPermission(List<String> denied, boolean never) {
                                toast("获取通知栏权限失败，请手动授予权限");
                            }
                        });
                break;
            case R.id.btn_main_request_8:
                HPermissions.with(this)
                        .permission(Permission.WRITE_SETTINGS)
                        .request(new OnPermission() {

                            @Override
                            public void hasPermission(List<String> granted, boolean all) {
                                toast("获取系统设置权限成功");
                            }

                            @Override
                            public void noPermission(List<String> denied, boolean never) {
                                toast("获取系统设置权限失败，请手动授予权限");
                            }
                        });
                break;
            case R.id.btn_main_app_details:
                HPermissions.startApplicationDetails(this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HPermissions.REQUEST_CODE) {
            toast("检测到你刚刚从权限设置界面返回回来");
        }
    }


    public void toast(CharSequence text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}