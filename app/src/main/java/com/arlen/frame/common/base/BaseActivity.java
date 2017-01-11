package com.arlen.frame.common.base;

import android.os.Bundle;

/**
 * Created by Arlen on 2016/12/21 17:24.
 */
public abstract class BaseActivity<V,T extends BasePresenter<V>> extends DelegateActivity{

    public T mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = initPresenter();

        mPresenter.attach((V)this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }


    public T getPresenter(){
        return mPresenter;
    }

    public abstract T initPresenter();
}
