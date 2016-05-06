package com.fushuai.captainamerica.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
/**
 * 监听手机开机启动的广播
 * */
public class BootCompleteReceiver extends BroadcastReceiver {

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		boolean protect = sp.getBoolean("protect", false);
		//只有在防盗保护开启的情况下才执行
		if(protect) {
			String sim = sp.getString("sim", null);//获取绑定的SIM卡序列号
			if(!TextUtils.isEmpty(sim)) {
				//获取当前手机的sim卡
				TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
				String currentSim = tm.getSimSerialNumber();//拿到当前手机的sim卡
				if(sim.equals(currentSim)) {
					System.out.println("手机安全");
				}else {
					String phone = sp.getString("safe_phone", "");
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(phone, null, "Sim card changed", null, null);
					
				}
			}
		}
	}
}
