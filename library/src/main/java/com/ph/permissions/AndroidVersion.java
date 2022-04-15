package com.ph.permissions;

import android.os.Build;

/**
 *    author : i小灰
 *    github : https://github.com/dahui888/HPermissions
 *    desc   : Android 版本判断
 */
@SuppressWarnings("all")
final class AndroidVersion {

    static final int ANDROID_12_L = Build.VERSION_CODES.S_V2;
    static final int ANDROID_12 = Build.VERSION_CODES.S;
    static final int ANDROID_11 = Build.VERSION_CODES.R;
    static final int ANDROID_10 = Build.VERSION_CODES.Q;
    static final int ANDROID_9 = Build.VERSION_CODES.P;
    static final int ANDROID_8_1 = Build.VERSION_CODES.O_MR1;
    static final int ANDROID_8 = Build.VERSION_CODES.O;
    static final int ANDROID_7_1 = Build.VERSION_CODES.N_MR1;
    static final int ANDROID_7 = Build.VERSION_CODES.N;
    static final int ANDROID_6 = Build.VERSION_CODES.M;
    static final int ANDROID_5_1 = Build.VERSION_CODES.LOLLIPOP_MR1;
    static final int ANDROID_5 = Build.VERSION_CODES.LOLLIPOP;
    static final int ANDROID_4_4 = Build.VERSION_CODES.KITKAT;
    static final int ANDROID_4_3 = Build.VERSION_CODES.JELLY_BEAN_MR2;
    static final int ANDROID_4_2 = Build.VERSION_CODES.JELLY_BEAN_MR1;
    static final int ANDROID_4_1 = Build.VERSION_CODES.JELLY_BEAN;

    /**
     * 是否是 Android 12 及以上版本
     */
    static boolean isAndroid12() {
        return Build.VERSION.SDK_INT >= ANDROID_12;
    }

    /**
     * 是否是 Android 11 及以上版本
     */
    static boolean isAndroid11() {
        return Build.VERSION.SDK_INT >= ANDROID_11;
    }

    /**
     * 是否是 Android 10 及以上版本
     */
    static boolean isAndroid10() {
        return Build.VERSION.SDK_INT >= ANDROID_10;
    }

    /**
     * 是否是 Android 9.0 及以上版本
     */
    static boolean isAndroid9() {
        return Build.VERSION.SDK_INT >= ANDROID_9;
    }

    /**
     * 是否是 Android 8.0 及以上版本
     */
    static boolean isAndroid8() {
        return Build.VERSION.SDK_INT >= ANDROID_8;
    }

    /**
     * 是否是 Android 6.0 及以上版本
     */
    static boolean isAndroid6() {
        return Build.VERSION.SDK_INT >= ANDROID_6;
    }

    /**
     * 是否是 Android 5.0 及以上版本
     */
    static boolean isAndroid5_1() {
        return Build.VERSION.SDK_INT >= ANDROID_5_1;
    }

    /**
     * 是否是 Android 5.0 及以上版本
     */
    static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= ANDROID_5;
    }

    /**
     * 是否是 Android 4.3 及以上版本
     */
    static boolean isAndroid4_3() {
        return Build.VERSION.SDK_INT >= ANDROID_4_3;
    }

    /**
     * 是否是 Android 4.2 及以上版本
     */
    static boolean isAndroid4_2() {
        return Build.VERSION.SDK_INT >= ANDROID_4_2;
    }
}