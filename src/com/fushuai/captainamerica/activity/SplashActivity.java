package com.fushuai.captainamerica.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.fushuai.captainamerica.R;
import com.fushuai.captainamerica.utils.StreamUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {
	protected static final int CODE_UPDATE_DIALOG = 0;
	protected static final int CODE_URL_ERROR = 1;
	protected static final int CODE_NET_ERROR = 2;
	protected static final int CODE_JSON_ERROR = 3;
	protected static final int CODE_ENTER_HOME = 4;//进入主页面
	
	private TextView tvVersion;
	private String mVersionName;//版本名    成员变量一般在前面加上m   member   布局里面的控件不加
	private int mVersionCode;//版本号
	private String mDesc;
	private String mDownloadUrl;
	
	
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch(msg.what) {
			case CODE_UPDATE_DIALOG:
				showUpdateDialog();
				break;
			case CODE_URL_ERROR:
				Toast.makeText(SplashActivity.this, "URL错误", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			case CODE_NET_ERROR:
				Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			case CODE_JSON_ERROR:
				Toast.makeText(SplashActivity.this, "JSON解析错误", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			case CODE_ENTER_HOME:
				enterHome();
				break;
			default:
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		tvVersion = (TextView) findViewById(R.id.tv_version);
		tvVersion.setText("版本名：" + getVersionName());
		
		chechVersion();
	}
	//获取本地app的版本名称
	private String getVersionName() {
		PackageManager packageManager = getPackageManager();
		try {
			//获取包的信息
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);//第二个参数不用管
			String versionName = packageInfo.versionName;
			return versionName;
		} catch (NameNotFoundException e) {
			// 没有找到包名时出现的异常
			e.printStackTrace();
		}
		return "";
	}
	//获取本地app的版本号
		private int getVersionCode() {
			PackageManager packageManager = getPackageManager();
			try {
				//获取包的信息
				PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);//第二个参数不用管
				int versionCode = packageInfo.versionCode;
				return versionCode;
			} catch (NameNotFoundException e) {
				// 没有找到包名时出现的异常
				e.printStackTrace();
			}
			return -1;
		}
	/**
	 * 从服务器获取版本信息进行校验*/
	private void chechVersion() {
		
		final long startTime = System.currentTimeMillis();
		
		//启动子线程异步加载数据
		new Thread() {
			@Override
			public void run() {
				Message msg = Message.obtain();
				HttpURLConnection conn = null;
				
				try {
					//本机地址用localhost，但是如果用模拟器加载本机地址时，可以用ip（10.0.2.2）来替换
					URL url = new URL("http://10.0.2.2:8080/update.json");
					conn = (HttpURLConnection)url.openConnection();
					conn.setRequestMethod("GET");//设置请求方法
					conn.setConnectTimeout(5000);//设置连接超时
					conn.setReadTimeout(5000);//设置响应超时    连接成功，但是服务器迟迟不给响应
					conn.connect();//连接服务器
					int responseCode = conn.getResponseCode();//获取响应码
					if(responseCode == 200) { 
						InputStream inputStream = conn.getInputStream();
						String result = StreamUtils.readFromStream(inputStream);
						
						result = "\"versionName\": \"2.0\", \"versionCode\": 2, \"description\": \"新增NB功能,赶紧体验!!!\", \"downloadUrl\":  \"http://www.baidu.com\"";
						//解析Json
						JSONObject jo = new JSONObject(result);
						mVersionName = jo.getString("versionName");
						mVersionCode = jo.getInt("versionCode");
						mDesc = jo.getString("description");
						mDownloadUrl = jo.getString("downloadUrl");
						//判断是否有更新
						if(mVersionCode > getVersionCode()) {
							//有更新 弹出升级对话框
							msg.what = CODE_UPDATE_DIALOG;
						}else {
							msg.what = CODE_ENTER_HOME;
						}
					}
					
				} catch (MalformedURLException e) {
					// URL错误的异常
					msg.what = CODE_URL_ERROR;
					e.printStackTrace();
				} catch (IOException e) {
					//网络错误异常
					msg.what = CODE_NET_ERROR;
					e.printStackTrace();
				} catch (JSONException e) {
					//json解析失败
					msg.what = CODE_JSON_ERROR;
					e.printStackTrace();
				}finally {
					long endTime = System.currentTimeMillis();
					long timeUsed = endTime - startTime;
					if(timeUsed < 2000) {
						//强制休眠一段时间  保证闪屏页面展示两秒
						try {
							Thread.sleep(2000 - timeUsed);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					mHandler.sendMessage(msg);
					//关闭网络连接
					if(conn != null)
						conn.disconnect();
				}
			}

			
		}.start();
		
		
	}
	/**
	 * 升级对话框*/
	private void showUpdateDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("最新版本：" + mVersionName);
		builder.setMessage(mDesc);
		builder.setPositiveButton("立即更新", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.setNegativeButton("以后再说", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				enterHome();
			}
		});
		builder.show();
		
	}
	/**
	 * 进入主界面*/
	private void enterHome() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}
}