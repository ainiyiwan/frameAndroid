package com.arlen.frame.common.utils;

/**
 * 倒计时工具类 day hour minute second
 *
 */
public abstract class CountDownTimer extends android.os.CountDownTimer {

	long dayMills = 24 * 60 * 60 * 1000;
	long hourMills = 60 * 60 * 1000;
	long minuteMills = 60 * 1000;
	long secondMills = 1000;
	private OnCountDownTimeListener mCountDownTimeListener;

	public CountDownTimer(long millisInFuture) {
		super(millisInFuture, 1000);
	}

	@Override
	public void onTick(long mills) {
		
		long days = mills / dayMills;
		mills %= dayMills;
		long hours = mills / hourMills;
		mills %= hourMills;
		long minutes = mills / minuteMills;
		mills %= minuteMills;
		long seconds = mills / secondMills;

		String daysStr = timeStrFormat(String.valueOf(days));
		String hoursStr = timeStrFormat(String.valueOf(hours));
		String minutesStr = timeStrFormat(String.valueOf(minutes));
		String secondStr = timeStrFormat(String.valueOf(seconds));
		
		if (mCountDownTimeListener!= null) {
			mCountDownTimeListener.onGetTime(daysStr, hoursStr, minutesStr, secondStr);
		}
	}
	
	@Override
	public void onFinish() {
		if (mCountDownTimeListener!= null) {
			mCountDownTimeListener.onFinished();
		}
	}
	
	public interface OnCountDownTimeListener {
		 void onGetTime(String days, String hours, String minutes, String seconds);
		 void onFinished();
	}
	
	public void setOnCountDownTimeListener(OnCountDownTimeListener listener) {
		this.mCountDownTimeListener = listener;
	}
	
	private String timeStrFormat(String timeStr) {
		switch (timeStr.length()) {
		case 1:
			timeStr = "0" + timeStr;
			break;
		}
		return timeStr;
	}

}
