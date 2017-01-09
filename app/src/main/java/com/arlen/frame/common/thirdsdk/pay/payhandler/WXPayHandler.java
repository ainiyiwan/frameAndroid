package com.arlen.frame.common.thirdsdk.pay.payhandler;

import com.arlen.frame.common.thirdsdk.pay.listener.PayListener;
import com.arlen.frame.common.thirdsdk.pay.model.PayInfo;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;

/**
 * Created by Administrator on 2015/7/17.
 */
public class WXPayHandler implements IPayHandler {
    private IWXAPI mApi;
    private PayListener mListener;

    public WXPayHandler(IWXAPI api, PayListener listener) {
        this.mApi = api;
        this.mListener = listener;
    }

    @Override
    public void doPay(PayInfo info) {
        PayReq req = new PayReq();
        req.appId = info.getAppId();
        req.partnerId = info.getPartnerId();
        req.prepayId = info.getPrepayId();
        req.nonceStr = info.getNonceStr();
        req.timeStamp = info.getTimeStamp();
        req.packageValue = info.getPackage();
        req.sign = info.getPaySignature();
        if (req.checkArgs()) {
            mApi.registerApp(info.getAppId());
            mApi.sendReq(req);
        } else {
            mListener.onFailure();
        }
    }
}
