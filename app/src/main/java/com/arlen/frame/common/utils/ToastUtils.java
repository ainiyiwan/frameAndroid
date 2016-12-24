package com.arlen.frame.common.utils;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arlen.frame.view.AppContext;


/**
 * Created by Administrator on 2016/1/12 0012.
 */
public class ToastUtils {

    private static Toast mToast;


    private static void toast(int time, String s) {
        if (s == null)
            s = "";
        if (mToast == null) {
            mToast = android.widget.Toast.makeText(AppContext.getAppContext(), s, time);
        } else {
            mToast.setText(s);
        }
        mToast.show();
    }

    private static void toast(int time, int resId) {
        if (mToast == null) {
            mToast = android.widget.Toast.makeText(AppContext.getAppContext(), resId, time);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

    public static void toastLong(String s) {
        toast(1000, s);
    }

    public static void toastShort(String s) {
        toastCenter(s, 500);
    }

    public static void toastShort(int resId) {
        toast(500, resId);
    }

    public static void toastLong(int resId) {
        toast(1000, resId);
    }

    public static void toastDuration(String s, int time) {
        toast(time, s);
    }

    public static void toastDuration(int time, int resId) {
        toast(time, resId);
    }

    public static void toastCenter(String s) {
        toastCenter(s, 500);
    }

    public static void toastCenter(String s, int time) {
        if (mToast == null) {
            mToast = android.widget.Toast.makeText(AppContext.getAppContext(), s, time);
        } else {
            mToast.cancel();
            mToast = android.widget.Toast.makeText(AppContext.getAppContext(), s, time);
            mToast.setText(s);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }


    public static void toastCenterWithImg(String s, int time, ImageView view) {
        if (mToast == null) {
            mToast = android.widget.Toast.makeText(AppContext.getAppContext(), s, time);
        } else {
            mToast.cancel();
            mToast = android.widget.Toast.makeText(AppContext.getAppContext(), s, time);
            mToast.setText(s);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        ((LinearLayout) mToast.getView()).addView(view, 0);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        view.setLayoutParams(lp);

        mToast.show();
    }

    public static void toastCenterWithImg(String s, int time, int viewId) {
        if (mToast == null) {
            mToast = android.widget.Toast.makeText(AppContext.getAppContext(), s, time);
        } else {
            mToast.cancel();
            mToast = android.widget.Toast.makeText(AppContext.getAppContext(), s, time);
        }
        mToast.setText(s);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        ImageView iv = new ImageView(AppContext.getAppContext());

        iv.setBackgroundResource(viewId);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        iv.setLayoutParams(lp);
        ((LinearLayout) mToast.getView()).addView(iv, 0);
        mToast.show();
        mToast = null;
    }

    public static void toastLongCenterWithImg(String s, int viewId) {
        toastCenterWithImg(s, android.widget.Toast.LENGTH_LONG, viewId);
    }

    public static void toastShortCenterWithImg(String s, int viewId) {
        toastCenterWithImg(s, android.widget.Toast.LENGTH_SHORT, viewId);
    }
}
