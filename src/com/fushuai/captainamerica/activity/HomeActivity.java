package com.fushuai.captainamerica.activity;

import com.fushuai.captainamerica.R;
import com.fushuai.captainamerica.utils.MD5Utils;
import com.fushuai.captainamerica.utils.ToastUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {
	private GridView gvHome;
	private String[] mItems = new String[] { "手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心" };
	private int[] mPics = new int[] { R.drawable.home_safe, R.drawable.home_callmsgsafe, R.drawable.home_apps,
			R.drawable.home_taskmanager, R.drawable.home_netmanager, R.drawable.home_trojan,
			R.drawable.home_sysoptimize, R.drawable.home_tools, R.drawable.home_settings };
	private SharedPreferences mPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		mPref = getSharedPreferences("config", MODE_PRIVATE);
		gvHome = (GridView) findViewById(R.id.gv_home);
		gvHome.setAdapter(new HomeAdapter());
		gvHome.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				// 设置中心
				case 8:
					startActivity(new Intent(HomeActivity.this, SettingActivity.class));
					break;
				// 手机防盗
				case 0:
					showPasswordDialog();
					break;
				default:
					break;
				}
			}

		});
	}

	/**
	 * 显示密码弹窗
	 */
	protected void showPasswordDialog() {
		// 判断是否设置过密码
		String savedPassword = mPref.getString("password", null);
		if(!TextUtils.isEmpty(savedPassword)) {
			showPasswordInputDialog();
		}else {
			showPasswordSetDialog();
		}
	}

	private void showPasswordInputDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		View view = View.inflate(this, R.layout.dialog_input_password, null);
		// dialog.setView(view);
		dialog.setView(view, 0, 0, 0, 0);// 边距设置为0 保证在2.x 版本上运行没问题
		Button btnOk = (Button) view.findViewById(R.id.btn_ok);
		Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
		final EditText etPassword = (EditText) view.findViewById(R.id.et_password);
		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String password = etPassword.getText().toString();
				String savedPassword = mPref.getString("password", null);
				if(!TextUtils.isEmpty(password)) {
					if(MD5Utils.encode(password).equals(savedPassword)) {
						ToastUtils.toast("登陆成功", HomeActivity.this);
						dialog.dismiss();
						startActivity(new Intent(HomeActivity.this, LostFindActivity.class));
					}else {
						ToastUtils.toast("密码输入错误", HomeActivity.this);
					}
				} else {
					ToastUtils.toast("输入不能为空", HomeActivity.this);
				}
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	private void showPasswordSetDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		View view = View.inflate(this, R.layout.dialog_set_password, null);
		// dialog.setView(view);
		dialog.setView(view, 0, 0, 0, 0);// 边距设置为0 保证在2.x 版本上运行没问题
		Button btnOk = (Button) view.findViewById(R.id.btn_ok);
		Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
		final EditText etPassword = (EditText) view.findViewById(R.id.et_password);
		final EditText etPasswordConfirm = (EditText) view.findViewById(R.id.et_password_confirm);
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String password = etPassword.getText().toString();
				String passwordConfirm = etPasswordConfirm.getText().toString();
				if(!TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConfirm)) {
					if(password.equals(passwordConfirm)) {
						ToastUtils.toast("登陆成功", HomeActivity.this);
						mPref.edit().putString("password", MD5Utils.encode(password)).commit();
						startActivity(new Intent(HomeActivity.this, LostFindActivity.class));
						dialog.dismiss();
					}else {
						ToastUtils.toast("两次密码不一致", HomeActivity.this);
					}
				} else {
					ToastUtils.toast("输入不能为空", HomeActivity.this);
				}
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	class HomeAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mItems.length;
		}

		@Override
		public Object getItem(int position) {
			return mItems[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(HomeActivity.this, R.layout.home_list_item, null);
			ImageView ivItem = (ImageView) view.findViewById(R.id.iv_item);
			TextView tvItem = (TextView) view.findViewById(R.id.tv_item);
			tvItem.setText(mItems[position]);
			ivItem.setImageResource(mPics[position]);
			return view;
		}

	}
}
