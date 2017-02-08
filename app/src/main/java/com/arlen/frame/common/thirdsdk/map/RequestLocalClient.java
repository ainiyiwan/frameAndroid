package com.arlen.frame.common.thirdsdk.map;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

public class RequestLocalClient {

	private BDLocationListener mMyListener;
	private LocalListener mLocalListener;
	private LocationClient mLocationClient;

	private GeoCoder mSearch;
	private PoiSearch mPoiSearch;
	private SuggestionSearch mSuggestionSearch;

	public RequestLocalClient(Context context) {
		initParams(context);
	}

	public RequestLocalClient(Context context, LocalListener localListener) {
		super();
		this.mLocalListener = localListener;
		initParams(context);
	}

	public RequestLocalClient(Context context, BDLocationListener localListener) {
		super();
		this.mMyListener = localListener;
		initParams(context);
	}

	public void removeCallBack() {
		if (mLocationClient != null) {
			mLocationClient.unRegisterLocationListener(mMyListener);
		}
	}

	public void initParams(Context context) {
		mLocationClient = new LocationClient(context.getApplicationContext()); // 声明LocationClient类
		if (mMyListener == null) {
			mMyListener = new MyLocationListener(mLocalListener);
		}
		mLocationClient.registerLocationListener(mMyListener); // 注册监听函数
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 1000;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
	}

	public static class MyLocationListener implements BDLocationListener {

		private LocalListener listener;

		public MyLocationListener(LocalListener listener) {
			this.listener = listener;
		}

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (listener != null) {
				listener.callBack(location);
			} else {
				// TODO 存储
			}
		}
	}

	public void stopLocal() {
		mLocationClient.stop();
	}

	public void startLocal() {
		mLocationClient.start();
	}

	public void getAddressPoint(String city, String address,
			OnGetGeoCoderResultListener coderResultListener) {
		mSearch = GeoCoder.newInstance();
		GeoCodeOption geoCodeOption = new GeoCodeOption();
		if (!TextUtils.isEmpty(city)) {
			geoCodeOption.city(city);
		}
		if (!TextUtils.isEmpty(address)) {
			geoCodeOption.address(address);
		} else {

		}
		if (coderResultListener == null) {
			throw new NullPointerException("亲，记得给回调接口");
		}
		mSearch.geocode(geoCodeOption);
		mSearch.setOnGetGeoCodeResultListener(coderResultListener);
		mSearch.destroy();
	}

	public void destroy() {
		if (mPoiSearch != null) {
			mPoiSearch.destroy();
		}
		if (mSearch != null) {
			mSearch.destroy();
		}
	}


	public void searchInCity(int pageIndex, int total, String city, String keyword,
							 OnGetPoiSearchResultListener poiListener) {
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
		mPoiSearch.searchInCity((new PoiCitySearchOption()).city(city)
				.keyword(keyword).pageNum(pageIndex).pageCapacity(total));
	}

	public void searchNearby(int pageIndex, int total, LatLng latLng, String keyword,
							 OnGetPoiSearchResultListener poiListener) {
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
		mPoiSearch.searchNearby((new PoiNearbySearchOption()).location(latLng)
				.keyword(keyword).pageNum(pageIndex).pageCapacity(total)
				.sortType(PoiSortType.distance_from_near_to_far));
	}

	public void requestSuggestion(SuggestionSearchOption option,
								  OnGetSuggestionResultListener listener) {
		mSuggestionSearch = SuggestionSearch.newInstance();
		mSuggestionSearch.setOnGetSuggestionResultListener(listener);
		mSuggestionSearch.requestSuggestion(option);
	}

	public void getAddressByLatLng(LatLng latlng,
								   OnGetGeoCoderResultListener coderResultListener) {
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(coderResultListener);
		mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latlng));
	}
}
