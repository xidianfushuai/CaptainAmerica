package com.fushuai.captainamerica.activity;

import com.fushuai.captainamerica.R;
import com.fushuai.captainamerica.view.SettingItemView;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

public class Setup2Activity extends BaseSetUpActivity {
	private SettingItemView sivSim;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		sivSim = (SettingItemView) findViewById(R.id.siv_update);
		String sim = mPref.getString("sim", null);
		if(!TextUtils.isEmpty(sim)) {
			sivSim.setChecked(true);
		}else {
			sivSim.setChecked(false);
		}
		sivSim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(sivSim.isChecked()) {
					sivSim.setChecked(false);
					mPref.edit().remove("sim").commit();//删除已绑定的sim卡
				}else {
					sivSim.setChecked(true);
					//保存SIM卡信息
					TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
					String simSerialNumber = tm.getSimSerialNumber();//获取Sim卡序列号
					//将Sim卡序列号保存到SP中
					mPref.edit().putString("sim", simSerialNumber).commit();
				}
			}
		});
	}
	public void showPreviousPage() {
		startActivity(new Intent(this, Setup1Activity.class));
		finish();
		overridePendingTransition(R.anim.anim_previous_in, R.anim.anim_previous_out);
	}
	public void showNextPage() {
		startActivity(new Intent(this, Setup3Activity.class));
		finish();
		overridePendingTransition(R.anim.anim_in, R.anim.tran_out);
	}
	
}
