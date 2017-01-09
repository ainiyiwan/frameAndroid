package com.arlen.frame.common.thirdsdk.pay.payhandler;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.alipay.sdk.app.PayTask;
import com.arlen.frame.common.thirdsdk.pay.alipay.Result;
import com.arlen.frame.common.thirdsdk.pay.listener.PayListener;
import com.arlen.frame.common.thirdsdk.pay.model.PayInfo;


/**
 * Created by Administrator on 2015/7/17.
 */

public class AlipayHandler implements IPayHandler {

    private Activity mActivity;
    private Handler mHandler;
    private PayListener mPayListener;

    public static final String PAY_SUCCESS = "9000";

    public AlipayHandler(Activity activity,PayListener payListener) {
        this.mActivity = activity;
        this.mPayListener = payListener;
        mHandler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void doPay(final PayInfo payInfo) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(mActivity);
                String strResult = alipay.pay(payInfo.getInfo(), true);
                Result result = new Result(strResult);
                String code = result.getResultCode();
                if (code.equals(PAY_SUCCESS)) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mPayListener.onSuccess();
                        }
                    });
                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mPayListener.onFailure();
                        }
                    });
                }
            }
        };
        Thread payThread = new Thread(runnable);
        payThread.start();
    }
}
