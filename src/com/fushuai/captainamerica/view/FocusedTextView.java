package com.fushuai.captainamerica.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * 获取焦点的TextView*/
public class FocusedTextView extends TextView {

	public FocusedTextView(Context context) {
		super(context);
	}
	//有style样式时调用此方法
	public FocusedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	/**
	 * 表示是否获取焦点
	 * 跑马灯要运行 首先调用此函数 判断是否有焦点 true表示有焦点，跑马灯才会有效果
	 * 所以不管TextView实际有没有焦点，都强制返回true 让跑马灯有焦点*/
	@Override
	public boolean isFocused() {
		return true;
	}

}
