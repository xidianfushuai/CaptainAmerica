package com.fushuai.captainamerica.activity;

import com.fushuai.captainamerica.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Setup4Activity extends BaseSetUpActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
	}
	@Override
	public void showPreviousPage() {
		startActivity(new Intent(this, Setup3Activity.class));
		finish();
		overridePendingTransition(R.anim.anim_previous_in, R.anim.anim_previous_out);
		
	}

	@Override
	public void showNextPage() {
		//表示已经展示过设置向导了  下次进入就不展示了
				mPref.edit().putBoolean("configed", true).commit();
				startActivity(new Intent(this, LostFindActivity.class));
				finish();
				overridePendingTransition(R.anim.anim_in, R.anim.tran_out);
		
	}
}
