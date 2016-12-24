package com.arlen.frame.common.utils;

import android.os.Handler;
import android.os.SystemClock;

/**
 *秒倒计时
 *
 */
public final class SecondCounter {

	public interface Callback{
		void onCount(int Total);
		void onFinished();
	}
	
	private final Callback mCallback;
	private final Handler mHandler = new Handler();
	private final Object mCountingLock = new Object();
	private boolean mIsCounting;
	private int mCount;
	
	public SecondCounter(Callback cb){
		mCallback=cb;
	}

	public final boolean isCounting(){
		synchronized(mCountingLock) {
			return mIsCounting;
		}
	}

	public final boolean start(int total){
		synchronized(mCountingLock){
			if(mIsCounting){
				return false;
			}
			mIsCounting=true;
			mCount=total;
			mCallback.onCount(mCount);
									
			new Thread() {
				@Override
				public void run() {
					while (true) {
						SystemClock.sleep(1000);
						synchronized(mCountingLock){
							if(mIsCounting==false){
								return;
							}
							mCount--;
							if (mCount<=0) {
								mIsCounting=false;
								mHandler.post(new Runnable(){
									@Override
									public void run() {
										// TODO Auto-generated method stub
										mCallback.onFinished();
									}
								});
								return;
							}else{
								mHandler.post(new Runnable(){
									@Override
									public void run() {
										// TODO Auto-generated method stub
										mCallback.onCount(mCount);
									}
								});
							}
						}
					}
				}
			}.start();
			
			return true;
		}
	}
	
	public final void stop(){
		synchronized(mCountingLock){
			mIsCounting=false;
		}
	}
}
