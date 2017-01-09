package com.arlen.frame.common.thirdsdk.pay.payhandler;

import android.app.Activity;

import com.arlen.frame.common.thirdsdk.pay.model.PayInfo;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

/**
 * Created by Administrator on 2015/7/17.
 */
public class UpPayHandler implements IPayHandler {

    private Activity mActivity;

    public static final String UPPay_ENV = "00";// 银联支付环境 01为测试环境 00为正式环境

    public UpPayHandler(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void doPay(PayInfo payInfo) {
        String spId = null;
        String sysProvider = null;
        UPPayAssistEx.startPayByJAR(mActivity, PayActivity.class, spId, sysProvider,
                payInfo.getInfo(), UPPay_ENV);
    }
}
