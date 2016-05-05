package com.fushuai.captainamerica.activity;

import com.fushuai.captainamerica.R;
import com.fushuai.captainamerica.view.SettingItemView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
/**
 * 設置中心*/
public class SettingActivity extends Activity {
	private SettingItemView sivUpdate;
	private SharedPreferences mPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mPref = getSharedPreferences("config", MODE_PRIVATE);
		
		setContentView(R.layout.activity_setting);
		sivUpdate = (SettingItemView) findViewById(R.id.siv_update);
		//sivUpdate.setTitle("自動更新設置");
		boolean autoUpdate = mPref.getBoolean("auto_update", true);
		if(autoUpdate) {
			//sivUpdate.setDesc("自動更新設置已開啟");
			sivUpdate.setChecked(true);
		}else {
			//sivUpdate.setDesc("自動更新設置已關閉");
			sivUpdate.setChecked(false);
		}
		
		
		sivUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//判斷當前勾選狀態
				if(sivUpdate.isChecked()) {
					sivUpdate.setChecked(false);
					sivUpdate.setDesc("自動更新已關閉");
					mPref.edit().putBoolean("auto_update", false).commit();
				}else {
					sivUpdate.setChecked(true);
					sivUpdate.setDesc("自動更新已開啟");
					mPref.edit().putBoolean("auto_update", true).commit();
				}
			}
		});
	}
}
