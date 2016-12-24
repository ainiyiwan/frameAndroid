package com.arlen.frame.common.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

/**
 * 加载图片工具类
 */
public class ImageUtil {

    public static void load(Context mContext, String uri, ImageView view) {
        load(mContext, uri, view, 0, 0);
    }

    public static void load(Context mContext, String uri, ImageView view, int errId) {
        load(mContext, uri, view, errId, errId);
    }

    public static void load(Context mContext, String uri, ImageView view, int errId, int placeholder) {
        try {
            Glide.with(mContext)
                    .load(uri)
                    .placeholder(placeholder)
                    .error(errId)
                    .crossFade()
                    .into(view);
        } catch (Exception ex) {
            Logger.d("Glide", ex.getMessage());
        }
    }

    /**
     * 圆角图片
     * @param mContext
     * @param imageView
     * @param uri
     * @param placeholder
     */
    public static void showRoundHeadImage(Context mContext, ImageView imageView, String uri, int placeholder) {
        RequestManager glideRequest = Glide.with(mContext);
        glideRequest
                .load(uri)
                .placeholder(placeholder)
                .transform(new GlideCircleTransform(mContext))
                .into(imageView);
    }
}
