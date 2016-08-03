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
	 * ��ȡ��Ҫ���������
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
		// ��Product����ת����byte���飬���������base64����
		String productBase64 = new String(Base64.encodeBase64(baos.toByteArray()));
		SharedPreferences.Editor editor = sp.edit();
		// ���������ַ���д��base64.xml�ļ���
		editor.putString("data", productBase64);
		return editor.commit();
	}
	/***
	 * ��ȡ���ݵķ���
	 */
	public SettingData read(){
		sp = context.getSharedPreferences("data", context.MODE_PRIVATE);
		SettingData data = null;
		String productBase64 = sp.getString("data", null);
		// ��Base64��ʽ���ַ������н���
		byte[] base64Bytes = Base64.decodeBase64(productBase64.getBytes());
		ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(bais);
			// ��ObjectInputStream�ж�ȡProduct����
			data = (SettingData) ois.readObject();
		} catch (Exception e) {
		}
		return data;
	}
}
