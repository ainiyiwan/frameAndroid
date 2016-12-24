package com.arlen.frame.net.http;

import android.app.Activity;

/**
 * Created by Administrator on 2016/3/5.
 * 接口回调的实现类
 */
public class ReqDataCallBack<T> implements ReqCallBack<T> {

    public ReqDataCallBack() {

    }

    public ReqDataCallBack(Activity activity) {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onStart() {

        }
}
