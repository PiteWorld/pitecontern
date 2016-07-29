package com.example.piteconternect.Inspection;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.example.pite.tool.StopCMD;
import com.example.piteconternect.utils.SocketUtils;

import android.util.Log;
import android_serialport_api.SerialPortService;

public class InspectionStatusFind extends InspectionStatusBase {
	
	@Override
	protected boolean isStatusOK(byte[] bt) {
		if (((bt[15] == 0 || bt[15] == 1 || bt[15] == 2 || bt[15] == 3 || bt[15] == 4 || bt[15] == 6 || bt[15] == 7
				|| bt[15] == 16) && (bt[28] != 0) && (bt[49] == 0))) {
			Log.e("3", id + " 数据查询成功");
			byte[] ARM = new byte[8] ;
			System.arraycopy(bt, 47, ARM, 0, 2);
			System.arraycopy(bt, 50, ARM, 2, 6);
			Map<Integer, byte[]> map = new HashMap<Integer, byte[]>() ;
			map.put(id, ARM) ;
			SocketUtils.salveConnect = 1;
			SocketUtils.salveWork = 0;
			SocketUtils.setReadBt(map);
			return true;
		}else{
			SocketUtils.salveConnect = 0;
			SocketUtils.salveWork = 0;
		}
		Log.e("1", id + " 数据查询ERROR"+SerialPortService.bytesToHexString(bt));
		return false;
	}

	@Override
	public void setStatus(Object param) {

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
		byte[] bt = StopCMD.slaveCMD(4, id);
		so.write(bt);
//		Log.e("4", id + "  2主机号：" + TimerUtil.hostID);
//		Log.e("4", id+"  查询命令"+SerialPortService.bytesToHexString(bt));

	}

	@Override
	public ComStatue getCurStatus() {
		return ComStatue.FIND;
	}

	@Override
	public int getID() {

		return id;
	}

}
