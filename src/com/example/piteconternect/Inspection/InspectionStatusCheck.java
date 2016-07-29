package com.example.piteconternect.Inspection;

import java.io.OutputStream;
import java.net.Socket;

import com.example.pite.tool.InspectionCMD;
import com.example.pite.tool.StopCMD;
import com.example.piteconternect.utils.SocketUtils;
import com.example.piteconternect.utils.TimerUtil;

import android.util.Log;
import android_serialport_api.SerialPortService;

/**
 * 巡检
 */
public class InspectionStatusCheck extends InspectionStatusBase {
	@Override
	public void setStatus(Object param) {

	}

	@Override
	protected boolean isStatusOK(byte[] bt) {
		if (bt[15] == 0 || bt[15] == 3 || bt[15] == 5 || bt[15] == 6) {
			Log.e("3", "巡检成功  " + "  startBt[11]  " + bt[11]);
			if (id == 0) {
				id = bt[11];
			}
			return true;
		}
		Log.e("3", "巡检成功 ERROR" + Thread.currentThread().getId());
		return false;
	}

	@Override
	protected InspectionStatusBase getOKStatus() {

		return new InspectionStatusFind();
	}

	@Override
	protected InspectionStatusBase getFailStatus() {
		return this;
	}

	@Override
	public void StatusRun(OutputStream so) throws Exception {
		if (id == 0) {
			for (int i = 1; i < TimerUtil.hostNum + 1; i++) {
				byte[] bt = InspectionCMD.startInspection(6, i);
				so.write(bt);
				// Log.e("4",
				// "定检发送的数据："+SerialPortService.bytesToHexString(bt));
			}
			Log.e("4", id + "  1主机号：" + TimerUtil.hostID);
		} else {
			byte[] bt = StopCMD.StartShort(14, id);
			so.write(bt);
		}

		// Log.e("3", id+" 定检发送"+Thread.currentThread().getId());
	}

	@Override
	public ComStatue getCurStatus() {
		return ComStatue.Inspection;
	}

	@Override
	public int getID() {
		return id;
	}

}
