package com.example.piteconternect.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android_serialport_api.SerialPortService;

public class SendHandler extends Handler {
	public SendHandler(Looper looper) {
		super(looper);
	}

	@Override
	public void handleMessage(Message msg) {
		SerialPortService.SendWirte((byte[]) msg.obj);
	}
}
