package com.arlen.frame.common.activity;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity手动管理栈
 */
public class ActivityManager {

    private static List<WeakReference<Activity>> mActivityStack;
    private static ActivityManager mActivityManager = new ActivityManager();

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        return mActivityManager;
    }


    /**
     * 出棧
     * @param activity
     */
    public void popActivity(Activity activity) {
        if (activity != null && mActivityStack != null) {

            int size = mActivityStack.size();
            int targetIndex = -1;

            for (int i = size - 1; i >= 0; i--) {
                WeakReference<Activity> weakReference = mActivityStack.get(i);
                Activity perActivity = weakReference.get();
                if (perActivity != null) {
                    if (activity == perActivity) {
                        targetIndex = i;
                        break;
                    }
                }
            }
            if (targetIndex != -1) {
                mActivityStack.remove(targetIndex);
            }
        }
    }


    /**
     * 獲得當前activity
     * @return
     */
    public Activity currentActivity() {
        WeakReference<Activity> activity = null;
        if (mActivityStack != null && mActivityStack.size() > 0) {
            activity = mActivityStack.get(mActivityStack.size() - 1);
        }
        if (activity == null) {
            return null;
        } else {
            return activity.get();
        }
    }

    /**
     * 入棧
     * @param activity
     */
    public void pushActivity(Activity activity) {
        WeakReference<Activity> weakReference = new WeakReference<>(activity);
        if (mActivityStack == null) {
            mActivityStack = new ArrayList<>();
        }
        mActivityStack.add(weakReference);
    }

    public void clearAllActivity() {
        if (mActivityStack != null) {
            mActivityStack.clear();
        }
    }
}
