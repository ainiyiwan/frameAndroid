package com.arlen.frame.common.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.telephony.TelephonyManager;

import java.util.List;
import java.util.UUID;

/**
 * 系统工具类
 */
public final class OsUtils {

    public static final int OS_BUILD_VERSION = Build.VERSION.SDK_INT;

    /**
     * 防止Application onCreate重复初始化
     *
     * @return boolean
     */
    public static boolean shouldInit(Context ctx) {
        ActivityManager am = ((ActivityManager) ctx
                .getSystemService(Context.ACTIVITY_SERVICE));
        List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = ctx.getPackageName();
        int myPid = Process.myPid();
        for (RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static String getUniqueId(Context ctx) {
        final TelephonyManager tm = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
         final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = ""
                + android.provider.Settings.Secure.getString(
                ctx.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

    @SuppressLint("NewApi")
    public static boolean support64Bit() {
        if (OS_BUILD_VERSION >= 21) {
            String ss[] = Build.SUPPORTED_64_BIT_ABIS;
            return ss != null && ss.length > 0;
        } else if (OS_BUILD_VERSION >= 4) {
            return Build.CPU_ABI != null && Build.CPU_ABI.contains("64");
        }
        return false;
    }

    public static final boolean osVerGreaterThan4()
    {
        return OS_BUILD_VERSION>=14;
    }

    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasIceCreamSandwich() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 获取当前运行的Activity数量
     */
    public static int getActivityStackCount(Context ctx) {
        int result = 0;
        if (ctx == null) {
            return result;
        }
        final String PKG_NAME = ctx.getPackageName();
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return result;
        }
        List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);
        if (taskInfoList == null) {
            return result;
        }
        for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
            if ((taskInfo.baseActivity != null && PKG_NAME.equals(taskInfo.baseActivity.getPackageName())) ||
                    (taskInfo.topActivity != null && PKG_NAME.equals(taskInfo.topActivity.getPackageName()))
                    ) {
                result = taskInfo.numActivities;
                break;
            }
        }
        return result;
    }
}
