package com.fushuai.captainamerica.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.fushuai.captainamerica.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ContactActivity extends Activity {
	private ListView lvList;
	private ArrayList<HashMap<String,String>> readContact;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		lvList = (ListView) findViewById(R.id.lv_list);
		readContact = readContact();
		lvList.setAdapter(new SimpleAdapter(this, readContact, R.layout.contact_list_item, new String[] {"name", "phone"}, new int[] {R.id.tv_name, R.id.tv_phone}));
		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//读取当前联系人的电话号码
				String phone = readContact.get(position).get("phone");
				Intent intent = new Intent();
				intent.putExtra("phone", phone);
				setResult(Activity.RESULT_OK, intent);//将数据放在Intent中返回
				finish();
			}
		});
	}
private ArrayList<HashMap<String, String>> readContact() {
		
		
		//然后，根据mimetype来区分哪个是联系人，哪个是电话号码
		//首先从raw_contact中读取联系人的id（“contact_id”）
		Uri rawContactUri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri dataUri = Uri.parse("content://com.android.contacts/data");
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		Cursor rawContactsCursor = getContentResolver().query(rawContactUri, new String[] {"contact_id"}, null, null, null);
		if(rawContactsCursor != null) {
			while(rawContactsCursor.moveToNext()) {
				String contactId = rawContactsCursor.getString(0);
				//其次根据contact_id在data表中查询出相应的电话号码和联系人名称  实际上查询的是视图view_data
				Cursor dataCursor = getContentResolver().query(dataUri, new String[] {"data1", "mimetype"}, "contact_id=?", new String[] {contactId}, null);
				if(dataCursor !=null) {
					HashMap<String, String> map = new HashMap<String, String>();
					while (dataCursor.moveToNext()) {
						String data1 = dataCursor.getString(0);
						String mimetype = dataCursor.getString(1);
						if("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
							map.put("phone", data1);
						}else if("vnd.android.cursor.item/name".equals(mimetype)) {
							map.put("name", data1);
						}
					}
					list.add(map);
					dataCursor.close();
				}
			}
			rawContactsCursor.close();
		}
		return list;
	}
}
