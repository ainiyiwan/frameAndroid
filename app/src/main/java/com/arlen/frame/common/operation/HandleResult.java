package com.arlen.frame.common.operation;

/**
 * Created by Arlen on 2017/1/20 17:24.
 */
public interface HandleResult<T> {
    void onSuccess(T t);
}
