package com.arlen.frame.common.db.shpref;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.arlen.frame.view.AppContext;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class TinyDB {
	private Context mContext;
	private static TinyDB mInstance;
	private SharedPreferences mPreferences;

	public TinyDB() {
		mContext = AppContext.getAppContext();
		mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
	}

	public TinyDB(String tinyName) {
		mContext = AppContext.getAppContext();
		setPreferences(tinyName);
	}

	public TinyDB getInstance(){
		if(mInstance == null){
			synchronized (TinyDB.class){
				if(mInstance == null){
					mInstance = new TinyDB();
				}
			}
		}
		return mInstance;
	}

	@SuppressLint("InlinedApi")
	public void setPreferences(String tinyName){
		if (Build.VERSION.SDK_INT < 11) {
			mPreferences = mContext.getSharedPreferences(tinyName,
					Context.MODE_PRIVATE);
		} else {
			mPreferences = mContext.getSharedPreferences(tinyName,
					Context.MODE_MULTI_PROCESS);
		}
	}

	public Bitmap getImage(String path) {
		Bitmap theGottenBitmap = null;
		try {
			theGottenBitmap = BitmapFactory.decodeFile(path);
		} catch (Exception e) {
		}
		return theGottenBitmap;
	}

	public int getInt(String key) {
		return mPreferences.getInt(key, 0);
	}

	public int getInt(String key, int def) {
		return mPreferences.getInt(key, def);
	}

	public long getLong(String key) {
		return mPreferences.getLong(key, 0l);
	}

	public long getLongDefaultMaxValue(String key){
		return mPreferences.getLong(key, Long.MAX_VALUE);
	}

	public String getString(String key) {
		return mPreferences.getString(key, "");
	}

	public double getDouble(String key) {
		String number = getString(key);
		try {
			double value = Double.parseDouble(number);
			return value;
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public void putInt(String key, int value) {
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putInt(key, value);
		editor.apply();
	}

	public void putLong(String key, long value) {
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putLong(key, value);
		editor.apply();
	}

	public void putDouble(String key, double value) {
		putString(key, String.valueOf(value));
	}

	public void putString(String key, String value) {

		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putString(key, value);
		editor.apply();
	}

	public void putList(String key, ArrayList<String> array) {

		SharedPreferences.Editor editor = mPreferences.edit();
		String[] mStringList = array.toArray(new String[array.size()]);
		editor.putString(key, TextUtils.join("‚‗‚", mStringList));
		editor.apply();
	}

	public ArrayList<String> getList(String key) {
		String[] mList = TextUtils
				.split(mPreferences.getString(key, ""), "‚‗‚");
		ArrayList<String> list = new ArrayList<String>(
				Arrays.asList(mList));
		return list;
	}

	public void putListInt(String key, ArrayList<Integer> array,
						   Context context) {
		SharedPreferences.Editor editor = mPreferences.edit();
		Integer[] mStringList = array.toArray(new Integer[array.size()]);
		editor.putString(key, TextUtils.join("‚‗‚", mStringList));
		editor.apply();
	}

	public ArrayList<Integer> getListInt(String key, Context context) {
		String[] mList = TextUtils
				.split(mPreferences.getString(key, ""), "‚‗‚");
		ArrayList<String> gList = new ArrayList<String>(
				Arrays.asList(mList));
		ArrayList<Integer> gList2 = new ArrayList<Integer>();
		for (int i = 0; i < gList.size(); i++) {
			gList2.add(Integer.parseInt(gList.get(i)));
		}

		return gList2;
	}

	public void putListBoolean(String key, ArrayList<Boolean> array) {
		ArrayList<String> origList = new ArrayList<>();
		for (Boolean b : array) {
			if (b == true) {
				origList.add("true");
			} else {
				origList.add("false");
			}
		}
		putList(key, origList);
	}

	public ArrayList<Boolean> getListBoolean(String key) {
		ArrayList<String> origList = getList(key);
		ArrayList<Boolean> mBool = new ArrayList<Boolean>();
		for (String b : origList) {
			if (b.equals("true")) {
				mBool.add(true);
			} else {
				mBool.add(false);
			}
		}
		return mBool;
	}

	public void putBoolean(String key, boolean value) {
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putBoolean(key, value);
		editor.apply();
	}

	public boolean getBoolean(String key) {
		return mPreferences.getBoolean(key, false);
	}

	public boolean getBooleanDefaultTrue(String key) {
		return mPreferences.getBoolean(key, true);
	}

	public void putFloat(String key, float value) {
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putFloat(key, value);
		editor.apply();
	}

	public float getFloat(String key) {
		return mPreferences.getFloat(key, 0f);
	}

	public void remove(String key) {
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.remove(key);
		editor.apply();
	}

	public Boolean deleteImage(String path) {
		File toImage = new File(path);
		Boolean isDeleted = toImage.delete();
		return isDeleted;
	}

	public void clear() {
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.clear();
		editor.apply();
	}

	public Map<String, ?> getAll() {
		return mPreferences.getAll();
	}

	public void registerOnSharedPreferenceChangeListener(
			SharedPreferences.OnSharedPreferenceChangeListener listener) {
		mPreferences.registerOnSharedPreferenceChangeListener(listener);
	}

	public void unregisterOnSharedPreferenceChangeListener(
			SharedPreferences.OnSharedPreferenceChangeListener listener) {
		mPreferences.unregisterOnSharedPreferenceChangeListener(listener);
	}

}
