package com.fushuai.captainamerica.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	public static String encode(String password) {
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest instance = MessageDigest.getInstance("MD5");//获取MD5算法对象
			byte[] digest = instance.digest(password.getBytes());//对字符串加密   返回字节数组
			
			for (byte b : digest) {
				int i = b & 0xff;
				String hexString = Integer.toHexString(i);
				//如果是一位   补0
				if(hexString.length() < 2) {
					hexString = "0" + hexString;
				}
				sb.append(hexString);
				//System.out.println(hexString);
			}
			System.out.println(sb);
		} catch (NoSuchAlgorithmException e) {
			//没有该算法时 抛出该异常
			e.printStackTrace();
		}
		return sb.toString();
		
	}
}
