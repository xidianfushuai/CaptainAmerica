package com.fushuai.captainamerica.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
/**
 * 获取经纬度坐标*/
public class LocationService extends Service {
	private LocationManager lm;
	private MyLocationListener listener;
	private SharedPreferences mPref;
	@Override
	public void onCreate() {
		super.onCreate();
		mPref = getSharedPreferences("config", MODE_PRIVATE);
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setCostAllowed(true);//是否允许付费  比如使用3G网络
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String bestProvider = lm.getBestProvider(criteria, true);//获取最佳提供者
		listener = new MyLocationListener();
		lm.requestLocationUpdates(bestProvider, 60, 50, listener);//位置提供者  最短更新时间    最短更新距离
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			//位置发生变化
			String j = "经度： " + location.getLongitude();
			String w = "纬度： " + location.getLatitude();
			//将获取的经纬度保存在SP中
			mPref.edit().putString("location", "j:" + location.getLongitude() + "; w:" + location.getLatitude()).commit();
			stopSelf();//一旦拿到经纬度坐标  就停掉Service 省电
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			//位置提供者的状态发生变化
		}

		@Override
		public void onProviderEnabled(String provider) {
			//打开GPS时
		}

		@Override
		public void onProviderDisabled(String provider) {
			//关闭GPS时
		}
		
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		lm.removeUpdates(listener);//当activity销毁时   停止更新坐标
	}
}
