package com.arlen.frame.common.thirdsdk.share;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.arlen.frame.common.utils.ToastUtils;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.onekeyshare.model.ShareContent;
import cn.sharesdk.system.text.ShortMessage;

/**
 * Created by Arlen on 2016/4/27.
 */
public class ShareController implements Handler.Callback {

    private OnekeyShare mOks;
    private Handler mHandler;


    public static final int SHARE_SUCCESS = 1;
    public static final int SHARE_FAIL = -1;
    public static final int SHARE_CANCEL = 0;

    public ShareController() {
        mOks = new OnekeyShare();
        mHandler = new Handler(this);
    }

    private static class ShareControllerHolder {
        private static ShareController shareController = new ShareController();
    }

    public static ShareController getInstance() {
        return ShareControllerHolder.shareController;
    }

    public void setOutSideHandler(Handler _handler) {
        this.mHandler = _handler;
    }

    public void showShare(Context context, String title, String text,
                          String imageUrl, String url) {
        ShareContent shareContent = new ShareContent();
        shareContent.setTitle(title);
        shareContent.setContent(text);
        shareContent.setIconUrl(imageUrl);
        shareContent.setLink(url);
        showShare(context, null, shareContent);
    }

    public void showShare(Context context, PlatformActionListener platformActionListener, String title, String text,
                          String imageUrl, String url) {
        ShareContent shareContent = new ShareContent();
        shareContent.setTitle(title);
        shareContent.setContent(text);
        shareContent.setIconUrl(imageUrl);
        shareContent.setLink(url);
        showShare(context, platformActionListener, shareContent);
    }

    public void showShare(Context context, final ShareContent shareContent) {
        showShare(context, null, shareContent);
    }

    public void showShare(Context context, PlatformActionListener platformActionListener, final ShareContent shareContent) {
        if (shareContent == null)
            return;
        // 关闭sso授权
        mOks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
        // oks.setNotification(R.drawable.ic_launcher,
        // getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        mOks.setTitle(shareContent.getTitle());
        mOks.setSiteUrl(shareContent.getLink());
        mOks.setTitleUrl(shareContent.getLink());
        // text是分享文本，所有平台都需要这个字段
        mOks.setText(shareContent.getContent());
        // oks.setImagePath(imageUrl);// 确保SDcard下面存在此张图片
        mOks.setImageUrl(shareContent.getIconUrl());
        // url仅在微信（包括好友和朋友圈）中使用
        mOks.setUrl(shareContent.getLink());
        // site是分享此内容的网站名称，仅在QQ空间使用
        // oks.setSite(site);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        // oks.setSiteUrl(siteurl);
        mOks.setCallback(platformActionListener != null ? platformActionListener : new OneKeyCallBack());
        mOks.setSilent(true);
        mOks.disableSSOWhenAuthorize();
        mOks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {

            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {

                if (ShortMessage.NAME.equals(platform.getName())) {
                    paramsToShare.setText(shareContent.getTitle() + "\n" + shareContent.getContent() + "\n" + shareContent.getLink());
                    paramsToShare.setImageUrl("");
                }
            }
        });
        // 启动分享GUI
        mOks.show(context.getApplicationContext());
    }

    /**
     * 默认回调处理
     *
     * @author Change
     */
    class OneKeyCallBack implements PlatformActionListener {

        @Override
        public void onCancel(Platform arg0, int arg1) {
            mHandler.sendMessage(mHandler.obtainMessage(SHARE_CANCEL));
        }

        @Override
        public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
            mHandler.sendMessage(mHandler.obtainMessage(SHARE_SUCCESS));
        }

        @Override
        public void onError(Platform arg0, int arg1, Throwable arg2) {
            mHandler.sendMessage(mHandler.obtainMessage(SHARE_FAIL));
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case SHARE_FAIL:
                ToastUtils.toastShort("分享失败！");
                break;
            case SHARE_SUCCESS:
                ToastUtils.toastShort("分享成功！");

                break;
            case SHARE_CANCEL:
                ToastUtils.toastShort("取消分享！");
                break;
            default:
                break;
        }
        return true;
    }
}
