package com.fushuai.captainamerica.activity;

import com.fushuai.captainamerica.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
/**
 * 手机防盗页面*/
public class LostFindActivity extends Activity{
	private SharedPreferences mPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mPref = getSharedPreferences("config", MODE_PRIVATE);
		//判断是否进入过设置向导
		boolean configed = mPref.getBoolean("configed", false);
		if(configed) {
			setContentView(R.layout.activity_lost_find);
		}else {
			//跳转到设置向导
			startActivity(new Intent(LostFindActivity.this, Setup1Activity.class));
			finish();
		}
	}
	/**
	 * 重新进入设置向导*/
	public void reEnter(View view) {
		startActivity(new Intent(this, Setup1Activity.class));
		finish();
	}
}
