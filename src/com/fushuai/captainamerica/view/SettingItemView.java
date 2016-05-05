package com.fushuai.captainamerica.view;

import com.fushuai.captainamerica.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 設置中心的自定義Item*/
public class SettingItemView extends RelativeLayout {
	private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.fushuai.captainamerica";
	private TextView tvTitle;
	private TextView tvDesc;
	private CheckBox cbStatus;
	private String mDescOff;
	private String mDescOn;
	private String mTitle;
	public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//根據屬性名稱獲取屬性的值
		mTitle = attrs.getAttributeValue(NAMESPACE, "title");
		mDescOn = attrs.getAttributeValue(NAMESPACE, "desc_on");
		mDescOff = attrs.getAttributeValue(NAMESPACE, "desc_off");
		initView();
	}

	public SettingItemView(Context context) {
		super(context);
		initView();
	}
	/**
	 * 初始化佈局*/
	private void initView() {
		//將自定義的佈局文件設置給當前SettingItemView
		View  view = View.inflate(getContext(), R.layout.view_setting_item, this);
		tvDesc = (TextView) findViewById(R.id.tv_desc);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		cbStatus = (CheckBox) findViewById(R.id.cb_status);
		
		setTitle(mTitle);
	}
	public void setTitle(String title) {
		tvTitle.setText(title);
	}
	public void setDesc(String desc) {
		tvDesc.setText(desc);
	}
	//判斷當前勾選狀態
	public boolean isChecked() {
		return cbStatus.isChecked();
	}
	public void setChecked(boolean check) {
		cbStatus.setChecked(check);
		//根據選擇的狀態 更新文本描述
		if(check) {
			setDesc(mDescOn);
		}else {
			setDesc(mDescOff);
		}
	}
}
