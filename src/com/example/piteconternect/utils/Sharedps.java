package com.example.piteconternect.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.codec.binary.Base64;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharedps {
	private Context context;
	private SharedPreferences sp;

	public Sharedps(Context context) {
		this.context = context;
	}
	/**
	 * 获取需要保存的数据
	 * 
	 * @throws IOException
	 */
	public boolean Data(SettingData settingdata) {
		sp = context.getSharedPreferences("data", context.MODE_PRIVATE);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(settingdata);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 将Product对象转换成byte数组，并将其进行base64编码
		String productBase64 = new String(Base64.encodeBase64(baos.toByteArray()));
		SharedPreferences.Editor editor = sp.edit();
		// 将编码后的字符串写到base64.xml文件中
		editor.putString("data", productBase64);
		return editor.commit();
	}
	/***
	 * 读取数据的方法
	 */
	public SettingData read(){
		sp = context.getSharedPreferences("data", context.MODE_PRIVATE);
		SettingData data = null;
		String productBase64 = sp.getString("data", null);
		// 对Base64格式的字符串进行解码
		byte[] base64Bytes = Base64.decodeBase64(productBase64.getBytes());
		ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(bais);
			// 从ObjectInputStream中读取Product对象
			data = (SettingData) ois.readObject();
		} catch (Exception e) {
		}
		return data;
	}
}
