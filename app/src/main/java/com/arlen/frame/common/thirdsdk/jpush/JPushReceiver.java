package com.arlen.frame.common.thirdsdk.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * 极光推送接受广播
 *
 * @author lugf
 */
public class JPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            // 记录返回的registrationId
            String regId = bundle
                    .getString(JPushInterface.EXTRA_REGISTRATION_ID);
            if (!TextUtils.isEmpty(regId)) {

            }
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {
            // 处理用户点击通知事件
            openNotification(context, bundle);
        }
    }
    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        if (TextUtils.isEmpty(extras)) {
            // 普通进入应用

            return;
        }
    }

}
