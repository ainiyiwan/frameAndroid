package com.arlen.frame.view;

import android.app.Application;
import android.content.Context;

import com.arlen.frame.common.activity.ActivityManager;
import com.arlen.frame.common.utils.OsUtils;

/**
 * Created by Arlen on 2016/12/21 15:13.
 */
public class AppContext extends Application{

    private static AppContext mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        if (OsUtils.shouldInit(this)){
            mAppContext = this;
        }
    }

    /**
     * @return 全局的上下文context
     * @Title: getAppContext
     * @Description: 获取全局的上下文context
     */
    public static Context getAppContext() {
        if (mAppContext == null) {
            throw new IllegalStateException("Application is not created.");
        }
        return mAppContext;
    }

    /**
     * 退出App
     */
    public void exitApp() {
        ActivityManager.getInstance().clearAllActivity();
    }
}
