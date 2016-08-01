package com.example.pite.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class shareXML {
	private SharedPreferences share;
	private Context mContext;

	public shareXML(Context context) {// MODE_WORLD_READABLE
		this.mContext = context;
		share = context.getSharedPreferences("setting", context.MODE_PRIVATE);
	}
	public int isExist(Context context) {
		int count = share.getInt("count", 0);
		return count;
	}

	public void addCount() {
		int count = share.getInt("count", 0);
		Editor edit = share.edit();
		edit.putInt("count", ++count);
		edit.commit();
	}

	/**
	 * 添加一个字符串
	 */
	public void addString(String name, String value) {
		Editor edit = share.edit();
		edit.putString(name, value);// id:name data:value add id:name
		edit.commit();
	}

	/**
	 * 添加int型
	 */
	public void addInt(String name, int value) {
		Editor edit = share.edit();
		edit.putInt(name, value);
		edit.commit();
	}

	/**
	 * 获取int内容
	 */
	public int getInt(String name) {
		return share.getInt(name, 0);

	}

	/**
	 * 获取String内容
	 */
	public String getString(String name) {

		return share.getString(name, null);
	}

	/**
	 * 默认返回0 从SharedPreferences中获取string型数据，如果为空则默认为“0”
	 */
	public String getString0(String name) {
		return share.getString(name, "0");
	}

	/**
	 * 清空sharedPreferences数据
	 */
	public boolean clearShare() {
		Editor editor = share.edit();
		editor.clear();
		return editor.commit();
	}
}
