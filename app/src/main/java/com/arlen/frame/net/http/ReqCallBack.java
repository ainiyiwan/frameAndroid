package com.arlen.frame.net.http;

/**
 * Created by Administrator on 2016/3/5.
 * 网络请求回调接口,定义了网络访问所需的回调方法
 */
public interface ReqCallBack<T> {
    void onCompleted();

    void onError(Throwable e);

    void onNext(T t);

    void onStart();
}
