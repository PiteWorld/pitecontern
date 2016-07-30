package com.example.piteconternect.exection;

import java.util.ArrayList;

import com.example.piteconternect.utils.TimerUtil;

import android.app.Activity;
import android.app.Application;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class MyApplication extends Application {
	private ArrayList<Activity> list = new ArrayList<Activity>();

	@Override
	public void onCreate() {
		TimerUtil.application = this;
		init();
	}
	public static MyApplication instance;

	public static MyApplication getInstance() {
		return instance == null ? new MyApplication() : instance;
	}

	
	public void init() {
		Log.e("5", "jinru  init");
		// ���ø�CrashHandlerΪ�����Ĭ�ϴ�����
		UnCeHandler catchExcep = new UnCeHandler(this);
		Thread.setDefaultUncaughtExceptionHandler(catchExcep);
	}

	/**
	 * Activity�ر�ʱ��ɾ��Activity�б��е�Activity����
	 */
	public void removeActivity(Activity a) {
		list.remove(a);
	}

	/**
	 * ��Activity�б������Activity����
	 */
	public void addActivity(Activity a) {
		list.add(a);
	}

	/**
	 * �ر�Activity�б��е�����Activity
	 */
	public void finishActivity() {
		for (Activity activity : list) {
			if (null != activity) {
				activity.finish();
			}
		}
		// ɱ����Ӧ�ý���
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
