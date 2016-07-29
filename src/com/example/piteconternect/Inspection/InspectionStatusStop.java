package com.example.piteconternect.Inspection;

import java.io.OutputStream;

import com.example.pite.tool.StopCMD;
import com.example.piteconternect.utils.TimerUtil;

import android.text.format.Time;
import android.util.Log;

public class InspectionStatusStop extends InspectionStatusBase {

	@Override
	public void setStatus(Object param) {
	}
	@Override
	protected boolean isStatusOK(byte[] bt) {
		return true;
	}

	@Override
	protected InspectionStatusBase getOKStatus() {
		return new InspectionStatusCheck();
	}

	@Override
	protected InspectionStatusBase getFailStatus() {
		return new InspectionStatusCheck();
	}

	@Override
	public void StatusRun(OutputStream so) throws Exception {
		for (int i = 1; i < TimerUtil.hostNum + 1; i++) {
			byte[] bt = StopCMD.stopShort(10, i);
			so.write(bt);
		}
		Log.e("3", "Í£Ö¹ÃüÁî");
	}

	@Override
	public ComStatue getCurStatus() {
		return ComStatue.Stop;
	}

	@Override
	public int getID() {
		return 0;
	}
}
