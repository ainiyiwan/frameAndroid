package com.arlen.frame.net.http;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/3/5.
 */
public class ReOperate {

    private static ReOperate instance;

    public static void init() {
        instance = new ReOperate();
    }

    public static ReOperate instance() {
        if (instance == null) {
            synchronized (ReOperate.class) {
                instance = new ReOperate();
            }
        }
        return instance;
    }


    public Subscription setObservable(Observable observable, final ReqCallBack mCallback) {
        return observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ReqSubscriber(mCallback));
    }
}
