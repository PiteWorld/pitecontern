package com.example.piteconternect.Inspection;

import java.io.OutputStream;
import java.net.Socket;

import com.example.pite.tool.StopCMD;

import android.system.Os;
import android.util.Log;

public class InspectionStatusDelet extends InspectionStatusBase {
	private byte[] mark = null;

	@Override
	protected boolean isStatusOK(byte[] bt) {
		if(bt[16]==0){
			Log.e("3",id+  "  É¾³ý³É¹¦");
			return true;
		}
		return false;
	}

	@Override
	public void setStatus(Object param) {
		mark = (byte[]) param;
	}

	@Override
	protected InspectionStatusBase getOKStatus() {
		return new InspectionStatusReading();
	}

	@Override
	protected InspectionStatusBase getFailStatus() {
		return new InspectionStatusCheck();
	}

	@Override
	public void StatusRun(OutputStream so) throws Exception {
		byte[] reBt = StopCMD.RecDataSuccess(8, id, mark);
		so.write(reBt);
		Log.e("3", "É¾³ýÃüÁî");
	}

	@Override
	public ComStatue getCurStatus() {
		return ComStatue.DELET;
	}

	@Override
	public int getID() {
		return id;
	}

}
