package com.arlen.frame.common.utils;

import android.util.Log;

import com.arlen.frame.BuildConfig;


public class Logger {
    public static String TAG = "Logger";


    public static void info(String msg) {
        if (BuildConfig.DEBUG)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, msg);
    }

    public static void e(String msg, Throwable tr) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, msg, tr);
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.d(tag, msg);
    }


}
