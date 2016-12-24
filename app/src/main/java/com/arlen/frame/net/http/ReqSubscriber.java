package com.arlen.frame.net.http;

import com.arlen.frame.common.utils.Logger;
import com.arlen.frame.common.utils.ToastUtils;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/3/5.
 * <p/>
 * 继承订阅者,用途为实现网络请求后回调的接口
 * 只需要将自己写好的回调接口传入即可,然后调用自己写好的回调方法
 */
public class ReqSubscriber<T> extends Subscriber<T> {

    private final ReqCallBack<T> mCallBack;

    public ReqSubscriber(ReqCallBack<T> mCallBack) {
        this.mCallBack = mCallBack;
    }


    @Override
    public void onCompleted() {
        mCallBack.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        try {
            if (e instanceof HttpException) {
                HttpException exception = (HttpException) e;
                int code = exception.code();
                if (code == 401) {
                    unauthenticated(e);
                } else if (code >= 400 && code < 500) {
                    clientError(e);
                } else if (code >= 500 && code < 600) {
                    serverError(e);
                } else {
                    unexpectedError(e);
                }
            }
        } catch (Exception ex) {
            Logger.d(ex.getMessage());
        }
        mCallBack.onError(e);
    }

    public void unexpectedError(Throwable e) {

    }

    public void serverError(Throwable e) {
        ToastUtils.toastCenter("连接服务器失败,请重试");
    }


    public void unauthenticated(Throwable e) {
         //401,token失效的操作
        // TODO
    }

    public void clientError(Throwable e) {
        ToastUtils.toastCenter("连接网络失败,请重试");
    }


    @Override
    public void onStart() {
        super.onStart();
        mCallBack.onStart();
    }

    @Override
    public void onNext(T o) {
        mCallBack.onNext(o);
    }
}
