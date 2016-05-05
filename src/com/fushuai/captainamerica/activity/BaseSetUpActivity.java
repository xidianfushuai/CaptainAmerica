package com.fushuai.captainamerica.activity;

import com.fushuai.captainamerica.R;
import com.fushuai.captainamerica.utils.ToastUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
/**
 * 设置引导页的基类
 * 不需要在清单文件中注册 因为不需要界面展示*/
public abstract class BaseSetUpActivity extends Activity{
	public SharedPreferences mPref;
	private GestureDetector mDetector;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mPref = getSharedPreferences("config", MODE_PRIVATE);
		mDetector = new GestureDetector(this, new SimpleOnGestureListener() {
			//监听手势滑动事件
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				//e1表示起始点
				//getRawX  基于整个屏幕的坐标
				//getX  相对于父控件的坐标
				
				//判断纵向滑动幅度是否过大 过大则不允许滑动
				if(Math.abs(e2.getRawY() - e1.getRawY()) > 100) {
					ToastUtils.toast("不能这样滑啊", getApplicationContext());
					return true;
				}
				//判断滑动速度  太慢不行
				if(Math.abs(velocityX) < 100) {
					ToastUtils.toast("滑动太慢啦", getApplicationContext());
					return true;
				}
				if(e2.getRawX() - e1.getRawX() > 200) {
					showPreviousPage();
					return true;
				}
				if(e1.getRawX() - e2.getRawX() > 200) {
					showNextPage();
					return true;
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
	}
	/**
	 * 展示上一页*/
	public abstract void showPreviousPage();
	public abstract void showNextPage();
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDetector.onTouchEvent(event);//委托手势识别器处理触摸事件
		return super.onTouchEvent(event);
	}
	public void next(View v) {
		showNextPage();
	}
	public void previous(View v) {
		showPreviousPage();
	}
}
