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
		// ��ȡϵͳĬ�ϵ�UncaughtException������
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		this.application = context;
	}
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			// ����û�û�д�������ϵͳĬ�ϵ��쳣������������
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
				// ��Ҫɾ�����ݵ�ʱ��
				write.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Intent intent = new Intent(application.getApplicationContext(), ConcentratorActivity.class);
			PendingIntent restartIntent = PendingIntent.getActivity(application.getApplicationContext(), 0, intent,
					Intent.FLAG_ACTIVITY_NEW_TASK);
			// �˳�����
			AlarmManager mgr = (AlarmManager) application.getSystemService(Context.ALARM_SERVICE);
			mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10000, restartIntent); // 1���Ӻ�����Ӧ��
			application.finishActivity();
		}
	}

	/**
	 * �Զ��������,�ռ�������Ϣ ���ʹ��󱨸�Ȳ������ڴ����.
	 * 
	 * @param ex
	 * @return true:��������˸��쳣��Ϣ;���򷵻�false.
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		// ʹ��Toast����ʾ�쳣��Ϣ
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				// Toast.makeText(application.getApplicationContext(),
				// "�ܱ�Ǹ,��������쳣,�����˳�.",
				//// Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}.start();
		return true;
	}
}