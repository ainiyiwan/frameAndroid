package com.arlen.frame.common.thirdsdk.pay.payhandler;


import com.arlen.frame.common.thirdsdk.pay.model.PayInfo;

/**
 * Created by Administrator on 2015/7/17.
 */
public interface IPayHandler {

    void doPay(PayInfo payInfo);

}
