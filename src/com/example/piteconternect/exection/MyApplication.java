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
		// 设置该CrashHandler为程序的默认处理器
		UnCeHandler catchExcep = new UnCeHandler(this);
		Thread.setDefaultUncaughtExceptionHandler(catchExcep);
	}

	/**
	 * Activity关闭时，删除Activity列表中的Activity对象
	 */
	public void removeActivity(Activity a) {
		list.remove(a);
	}

	/**
	 * 向Activity列表中添加Activity对象
	 */
	public void addActivity(Activity a) {
		list.add(a);
	}

	/**
	 * 关闭Activity列表中的所有Activity
	 */
	public void finishActivity() {
		for (Activity activity : list) {
			if (null != activity) {
				activity.finish();
			}
		}
		// 杀死该应用进程
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
