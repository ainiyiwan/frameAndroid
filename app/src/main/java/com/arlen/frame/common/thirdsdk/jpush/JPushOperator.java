package com.arlen.frame.common.thirdsdk.jpush;

import android.content.Context;

import com.arlen.frame.view.AppContext;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 极光推送操作类
 *
 * @author Administrator
 */
public class JPushOperator {

    /**
     * 初始化JPush
     */
    public static void init(final Context ctx) {
        JPushInterface.setDebugMode(false);
        JPushInterface.init(ctx);
    }

    /**
     * 恢复推送功能
     */
    public static void resumePush(final Context ctx) {
        JPushInterface.resumePush(ctx);
    }

    /**
     * 暂停推送功能
     */
    public static void stopPush(final Context ctx) {
        JPushInterface.stopPush(ctx);
    }

    /**
     * 返回推送状态
     *
     * @return t 暂停 /f 运行中
     */
    public static boolean isPushStopped(final Context ctx) {
        return JPushInterface.isPushStopped(ctx);
    }

    /**
     * 返回regId
     */
    public static String getRegistrationID(final Context ctx) {
        String regId = JPushInterface.getRegistrationID(ctx);
        return regId;
    }

    /**
     * 设置城市code的 Tag
     */
    public static void bindCityCodeTag() {
        setTag(AppContext.getAppContext());
    }

    /**
     * 绑定userid Tag
     */
    public static void bindUserIdTag() {
        setTag(AppContext.getAppContext());
    }

    /**
     * 解绑userid Tag
     */
    public static void unBindUserIdTag() {
        setTag(AppContext.getAppContext());
    }

    /**
     * 本地 tag与服务器同步
     */
    public static void syncTags() {
        setTag(AppContext.getAppContext());
    }

    private static void setTag(final Context ctx) {
        Set<String> setTags = new HashSet<String>();

//        // 城市code
//        String citycode = CityInfoOperation.getInstance().getCurrentCityCode();
//        if (!TextUtils.isEmpty(citycode)) {
//            setTags.add("citycode" + citycode);
//        }
//
//        // 用户id
//        String userid = UserInfoOperation.getInstance().getUserIdtoString();
//        if (!TextUtils.isEmpty(userid)) {
//            setTags.add(userid);
//        }
//
//        // 服务器tag
//        PushTagDao dao = new PushTagDao(ctx);
//        List<String> serverTags = dao.openDatabase().getList(
//                PushTagDao.NODE_SERVER_TAGS);
//        if (serverTags != null && serverTags.size() > 0) {
//            for (String sstag : serverTags) {
//                setTags.add(sstag);
//            }
//        }
        JPushInterface.setTags(ctx, setTags, new CTagAliasCallback(setTags));
    }

    static String parserTagSet(Set<String> tagset) {

        StringBuilder builder = new StringBuilder();

        for (String tag : tagset) {
            builder.append(tag);
            builder.append(',');
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    /**
     * 绑定标签与别名的回调
     */
    private static class CTagAliasCallback implements TagAliasCallback {

        private Set<String> tagset;

        CTagAliasCallback(Set<String> tagset) {
            this.tagset = tagset;
        }

        @Override
        public void gotResult(int responseCode, String alias, Set<String> tags) {
            if (responseCode == 0 && tagset != null) {
                String parseResult = parserTagSet(tagset);
//                PushTagDao dao = new PushTagDao(AppContext.getAppContext());
//                dao.openDatabase().putString(PushTagDao.NODE_HOLD_TAGS,
//                        parseResult);
                // 上报标签
//                APIRestClient.getUpdatePushTag(AppContext.getAppContext(),
//                        UserInfoOperation.getInstance().getUserIdtoString(), parseResult,
//                        new JPushOperator.UpTag());
            }
        }
    }
}
