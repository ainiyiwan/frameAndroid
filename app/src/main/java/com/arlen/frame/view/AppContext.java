package com.arlen.frame.view;

import android.app.Application;
import android.content.Context;

import com.arlen.frame.common.activity.ActivityManager;
import com.arlen.frame.common.thirdsdk.jpush.JPushOperator;
import com.arlen.frame.common.utils.OsUtils;
import com.umeng.analytics.MobclickAgent;

import cn.sharesdk.framework.ShareSDK;

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
            //极光推送
            JPushOperator.init(mAppContext);
            //分享
            ShareSDK.initSDK(mAppContext);
            // 友盟功能初始化
            MobclickAgent.openActivityDurationTrack(false); // umeng 禁用默认的页面统计
            MobclickAgent.setDebugMode(true);
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
