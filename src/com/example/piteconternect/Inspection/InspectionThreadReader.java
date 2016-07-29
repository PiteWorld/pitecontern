package com.example.piteconternect.Inspection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.pite.tool.IDModel.ModelWorkID;
import com.example.pite.tool.IDModel;
import com.example.pite.tool.PiteModel;
import com.example.pite.tool.PiteModel.ModelWork;
import com.example.pite.tool.PiteModleID;
import com.example.pite.tool.PiteModleID.ModelWorkDT;
import com.example.pite.tool.StopCMD;
import com.example.pite.tool.UpLoad;
import com.example.piteconternect.Inspection.InspectionStatusBase.ComStatue;
import com.example.piteconternect.utils.TimerUtil;
import android.util.Log;
import android_serialport_api.SerialPortService;

public class InspectionThreadReader extends Thread implements ModelWorkDT, ModelWork, ModelWorkID {

	private Socket so = null;
	private OutputStream ou = null;
	private InputStream in = null;
	private int id = 0;
	private InspectionStatusBase status = null; // 起始状态
	private ReaderWork work = null;
	private Timer timer = null;
	private PiteModleID piteModleID = new PiteModleID(this);
	private PiteModel pite = new PiteModel(this);
	private IDModel idModel = new IDModel(this);
	private int sizelength = 0;
	private List<byte[]> addDT = new ArrayList<>();
	private byte[] headbt = null;
	private int pakLength;// 数据包总长度
	// 定时器
	public TimerTask task = null;

	public interface ReaderWork {
		public void closeWork(InspectionThreadReader reader);
	}

	public InspectionThreadReader(Socket so, ReaderWork work) throws IOException {
		this.so = so;
		this.ou = so.getOutputStream();
		this.in = so.getInputStream();
		this.work = work;
		InspectionsetTimer(); // 定时器
		try {
			startStatus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startStatus() throws Exception {
		status = new InspectionStatusStop();
		status.StatusRun(ou);
		status = status.getOKStatus();
	}

	@Override
	public void run() {
		super.run();
		byte[] buf = new byte[2048];
		while (true) {
			try {
				int len = in.read(buf);
				if (len < 0)
					break;
				byte[] data = new byte[len];
				System.arraycopy(buf, 0, data, 0, data.length);
				if (sizelength != 0) {
					Log.e("5", "socket读取的数据：" + SerialPortService.bytesToHexString(data));
				}
				idModel.addBytes(data);
				piteModleID.addBytes(data);
				pite.addBytes(data);

			} catch (IOException e) {
				break;
			}
		}
		try {
			in.close();
			ou.close();
			so.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		work.closeWork(this);
	}

	public void InspectionsetTimer() {
		if (task != null) {
			task.cancel();
		}
		task = new TimerTask() {
			@Override
			public void run() {
				try {
					status.StatusRun(ou);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		timer = new Timer();
		timer.schedule(task, TimerUtil.InspectionTime, TimerUtil.InspectionTime);
	}

	private void updateStatus(byte[] bt) {
		int id = status.getID();
		if (status.isStatusOK(bt)) {
			id = status.getID();
			status = status.getOKStatus();
		} else {
			status = status.getFailStatus();
		}
		status.setID(id);
		try {
			if (status.getCurStatus() != ComStatue.Inspection) {
				status.StatusRun(ou);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterWork(byte[] bt) {
		updateStatus(bt);
	}

	public OutputStream getOutputStream() {
		return ou;
	}

	@Override
	public void afterWorkDT(byte[] bt, int len) { // DT包头
		setUpdata(bt, len);
	}

	@Override
	public void afterWorkID(byte[] bt) { // 7E ID 包头
		headbt = bt;
		sizelength = 0;
		addDT.clear();
		for (int i = 11; i < bt.length; i++) {
			sizelength *= 10;
			sizelength += (bt[i] - 0x30);
		}
		pakLength = sizelength;
		Log.e("1", "数据总长度：" + sizelength);
	}

	/**
	 * 要发送的数据
	 * 
	 * @param bt
	 * @param len
	 */
	public void setUpdata(byte[] bt, int len) {
		if (status.getCurStatus() == ComStatue.READ) {
			sizelength -= len;
			// Log.e("1", "剩余长度：" + sizelength);
			if (sizelength != 0) {
				try {
					byte[] nextbt = StopCMD.getNextPacket(4); // 下一包
					for (int i = 0; i < 2; i++) {
						ou.write(nextbt);
					}
					byte[] addList = new byte[bt.length - 14];
					System.arraycopy(bt, 11, addList, 0, addList.length);
					addDT.add(addList);
					// Log.e("4", "分包后的数据：" + addList.length);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				int lenth = 0;
				byte[] sendBt = null;
				for (int i = 0; i < addDT.size(); i++) {
					lenth += addDT.get(i).length;
				}
				sendBt = new byte[lenth + bt.length - 14];
				int index = 0;
				for (byte[] bts : addDT) {
					System.arraycopy(bts, 0, sendBt, index, bts.length);
					Log.e("1", "整包数据：" + SerialPortService.bytesToHexString(bts));
					index += bts.length;
				}
				Log.e("1", "send长度：" + sendBt.length);
				System.arraycopy(bt, 11, sendBt, index, bt.length - 14);

				byte[] fullbt = new byte[sendBt.length + headbt.length + 11 + 3];
				System.arraycopy(headbt, 0, fullbt, 0, headbt.length);
				byte[] DTBt = UpLoad.setHeader(pakLength, 1);
				System.arraycopy(DTBt, 0, fullbt, headbt.length, DTBt.length);
				System.arraycopy(sendBt, 0, fullbt, headbt.length + DTBt.length, sendBt.length);
				int check = 0;
				for (int i = headbt.length + DTBt.length; i < fullbt.length - 3; i++) {
					check += fullbt[i] < 0 ? fullbt[i] & 0xff : fullbt[i];
				}
				check %= 256;
				byte[] checkBt = UpLoad.zeroBt(check + "", 3);
				System.arraycopy(checkBt, 0, fullbt, fullbt.length - 3, 3);
				Log.e("1", "最后的整包数据：" + check);
				updateStatus(fullbt);
			}
		} else {
			updateStatus(bt);
		}
	}
}
