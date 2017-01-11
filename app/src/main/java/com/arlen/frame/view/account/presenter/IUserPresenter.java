package com.arlen.frame.view.account.presenter;

import com.arlen.frame.common.base.IBasePresenter;

/**
 * Created by Arlen on 2016/12/21 18:01.
 */
public interface IUserPresenter<T> extends IBasePresenter<T> {
    void loadAccount();
}
