package com.fushuai.captainamerica.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	public static void toast(String str, Context context) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}
}
