package com.example.piteconternect.exection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import com.example.piteconternect.ConcentratorActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class UnCeHandler implements UncaughtExceptionHandler {

	private Thread.UncaughtExceptionHandler mDefaultHandler;
	public static final String TAG = "CatchExcep";
	MyApplication application;

	public UnCeHandler(MyApplication context) {
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		this.application = context;
	}
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			String adb = Log.getStackTraceString(ex);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Log.e(TAG, "error : ", e);
			}
			String Path = Environment.getExternalStorageDirectory() + "/" + "3_pite" + "/";
			File file = new File(Path);
			if (!file.exists()) {
				file.mkdirs();
			}
			BufferedWriter write;
			try {
				write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Path + "1.txt", true)));
				write.write(adb);
				write.newLine();
				// 需要删除数据的时候
				write.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Intent intent = new Intent(application.getApplicationContext(), ConcentratorActivity.class);
			PendingIntent restartIntent = PendingIntent.getActivity(application.getApplicationContext(), 0, intent,
					Intent.FLAG_ACTIVITY_NEW_TASK);
			// 退出程序
			AlarmManager mgr = (AlarmManager) application.getSystemService(Context.ALARM_SERVICE);
			mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10000, restartIntent); // 1秒钟后重启应用
			application.finishActivity();
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		// 使用Toast来显示异常信息
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				// Toast.makeText(application.getApplicationContext(),
				// "很抱歉,程序出现异常,即将退出.",
				//// Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}.start();
		return true;
	}
}