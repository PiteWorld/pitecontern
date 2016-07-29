package com.example.brodCast;


import com.example.piteconternect.ConcentratorActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * ¿ª»úÆô¶¯
 */
public class PiteBrodCast extends BroadcastReceiver {
	public static final String START_CAST = "android.intent.action.BOOT_COMPLETED";
	public static final String START_CAST_SD = "android.intent.action.MEDIA_MOUNTED";
	public static final String START_CAST_SD1 = "android.intent.action.MEDIA_UNMOUNTED";

	public PiteBrodCast() {

	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (START_CAST.equals(intent.getAction()) || START_CAST_SD.equals(intent.getAction())
				|| START_CAST_SD1.equals(intent.getAction())) {
			Intent intent2 = new Intent(context, ConcentratorActivity.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent2);
		}
	}

}
