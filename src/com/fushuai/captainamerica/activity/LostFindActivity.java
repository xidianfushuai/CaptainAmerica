package com.fushuai.captainamerica.activity;

import com.fushuai.captainamerica.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 手机防盗页面*/
public class LostFindActivity extends Activity{
	private SharedPreferences mPref;
	private TextView tvSafePhone;
	private ImageView ivProtect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mPref = getSharedPreferences("config", MODE_PRIVATE);
		//判断是否进入过设置向导
		boolean configed = mPref.getBoolean("configed", false);
		if(configed) {
			setContentView(R.layout.activity_lost_find);
			tvSafePhone = (TextView) findViewById(R.id.tv_safe_phone);
			String phone = mPref.getString("safe_phone", "");
			tvSafePhone.setText(phone);
			ivProtect = (ImageView) findViewById(R.id.iv_protect);
			boolean protect = mPref.getBoolean("protect", false);
			if(protect) {
				ivProtect.setImageResource(R.drawable.lock);
			}else {
				ivProtect.setImageResource(R.drawable.unlock);
			}
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
