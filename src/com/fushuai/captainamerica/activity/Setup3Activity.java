package com.fushuai.captainamerica.activity;

import com.fushuai.captainamerica.R;
import com.fushuai.captainamerica.utils.ToastUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class Setup3Activity extends BaseSetUpActivity {
	private EditText etPhone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
		etPhone = (EditText) findViewById(R.id.et_phone);
		String phone = mPref.getString("safe_phone", "");
		etPhone.setText(phone);
	}
	@Override
	public void showPreviousPage() {
		startActivity(new Intent(this, Setup2Activity.class));
		finish();
		overridePendingTransition(R.anim.anim_previous_in, R.anim.anim_previous_out);
	}
	@Override
	public void showNextPage() {
		String phone = etPhone.getText().toString().trim();
		if(TextUtils.isEmpty(phone)) {
			ToastUtils.toast("输入不能为空", this);
			return;
		}
		mPref.edit().putString("safe_phone", phone).commit();//保存安全号码
		startActivity(new Intent(this, Setup4Activity.class));
		finish();
		overridePendingTransition(R.anim.anim_in, R.anim.tran_out);
	}
	public void selectContact(View view) {
		startActivityForResult(new Intent(this, ContactActivity.class), 0); 
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK) {
			String phone = data.getStringExtra("phone");
			phone = phone.replaceAll("-", "").replaceAll(" ", "");//替换短杠和空格
			//把电话号码设置给输入框
			etPhone.setText(phone);	
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
