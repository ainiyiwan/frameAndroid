package com.arlen.frame.view.base;

import android.util.Log;

import com.arlen.frame.net.http.HttpProvider;
import com.arlen.frame.net.http.ReqCallBack;
import com.arlen.frame.net.http.ReqSubscriber;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Arlen on 2016/12/21 16:30.
 */
public abstract class BasePresenter<T> implements IBasePresenter<T>{

    public WeakReference<T> mView;
    private Subscription mSubscription;

    @Override
    public void attach(T view){
        this.mView = new WeakReference<T>(view);
    }

    @Override
    public T getView() {
        if(mView.get() == null)
            throw new RuntimeException("View has been detach by detachView method. " +
                    "Ensure calling detach method inside activity destroy method");
        return mView.get();
    }

    @Override
    public void detach(){
        if(mView != null) {
            mView.clear();
            mView = null;
        }
        if(mSubscription != null){
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void onResume(){
        Log.d("tag", "onResume: ");
    };

    public Subscription setObservable(Observable observable, ReqCallBack mCallback) {
        if(mSubscription == null) {
            mSubscription = observable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ReqSubscriber(mCallback));
        }
        return mSubscription;
    }

    public <V> V createService(final Class<V> service){
        return HttpProvider.getInstance().create(service);
    }
}
