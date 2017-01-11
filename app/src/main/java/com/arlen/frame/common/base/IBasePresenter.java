package com.arlen.frame.common.base;

/**
 * Created by Arlen on 2016/12/21 17:25.
 */
public interface IBasePresenter<T> {

    //绑定view,这个方法在activity调用
    void attach(T view);
    //解绑
    void detach();
    //在activity的onResume调用
    void onResume();
    //获得view
    T getView();
}
