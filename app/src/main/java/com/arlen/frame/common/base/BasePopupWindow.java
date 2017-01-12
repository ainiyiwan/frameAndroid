package com.arlen.frame.common.base;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Arlen on 2017/1/12 10:05.
 */
public class BasePopupWindow extends PopupWindow{

    private Activity mActivity;

    public BasePopupWindow(Activity activity){

        super(activity);
        mActivity = activity;

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        update();
        ColorDrawable dw = new ColorDrawable(0000000000);
        setBackgroundDrawable(dw);

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setAlpha(1f);
            }
        });
    }

    protected void setAlpha(float alpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = alpha;
        mActivity.getWindow().setAttributes(lp);
    }

    @Override
    public void showAsDropDown(View anchor) {
        setAlpha(0.5f);
        super.showAsDropDown(anchor);
    }
}
