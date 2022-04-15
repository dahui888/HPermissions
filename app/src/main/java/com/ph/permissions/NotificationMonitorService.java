package com.ph.permissions;

import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

/**
 *    author : i小灰
 *    github : https://github.com/dahui888/HPermissions
 *    desc   : 通知消息监控服务
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public final class NotificationMonitorService extends NotificationListenerService {

    /**
     * 当系统收到新的通知后出发回调
     */
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Bundle extras = sbn.getNotification().extras;
            if (extras != null) {
                //获取通知消息标题
                String title = extras.getString(Notification.EXTRA_TITLE);
                // 获取通知消息内容
                Object msgText = extras.getCharSequence(Notification.EXTRA_TEXT);
                Toast.makeText(this, "监听到新的通知消息，标题为：" + title + "，内容为：" + msgText, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 当系统通知被删掉后出发回调
     */
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }
}