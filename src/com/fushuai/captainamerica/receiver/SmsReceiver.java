package com.fushuai.captainamerica.receiver;

import com.fushuai.captainamerica.R;
import com.fushuai.captainamerica.service.LocationService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

/**
 * 拦截短信
 */
public class SmsReceiver extends BroadcastReceiver {

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		Object[] objects = (Object[]) intent.getExtras().get("pdus");
		for (Object object : objects) {
			SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
			String originatingAddress = message.getOriginatingAddress();// 短信来源号码
			String messageBody = message.getMessageBody();// 短信内容
			if ("#*alarm*#".equals(messageBody)) {
				// 播放报警音乐
				MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);
				player.setVolume(1f, 1f);
				player.setLooping(true);
				player.start();
				abortBroadcast();// 中断短信传递 从而系统app就收不到内容了
			}else if("#*location*#".equals(messageBody)) {
				//获取经纬度坐标
				context.startActivity(new Intent(context, LocationService.class));
				SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
				String location = sp.getString("location", "getting location");
				abortBroadcast();// 中断短信传递 从而系统app就收不到内容了
			}else if("#*wipedata*#".equals(messageBody)) {
				
				abortBroadcast();// 中断短信传递 从而系统app就收不到内容了
			}else if("#*lockscreen*#".equals(messageBody)) {
				
				abortBroadcast();// 中断短信传递 从而系统app就收不到内容了
			}
		}
	}

}
