package android_serialport_api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.example.pite.tool.UpLoad;
import com.example.piteconternect.kehua.tool.keHuaCMDTool;
import com.example.piteconternect.kehua.tool.keHuaModel;
import com.example.piteconternect.kehua.tool.keHuaModel.ModelWorkehua;
import com.example.piteconternect.kehua.tool.keHuaModel02;
import com.example.piteconternect.kehua.tool.keHuaModel02.ModelWorkehua02;
import com.example.piteconternect.kehua.tool.kehuaProl;
import com.example.piteconternect.read.ReadKeHuaData;
import com.example.piteconternect.utils.SocketUtils;
import com.example.piteconternect.utils.TimerUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.format.Time;
import android.util.Log;

public class keHuaSerialPortService implements ModelWorkehua, ModelWorkehua02 {
	protected SerialPort mSerialPort;
	public OutputStream mOutputStream;
	private InputStream mInputStream;
	private String path = "/dev/ttyS5"; // 路径
	private ReadThread thread;
	private static Context context;
	private int baudrate = 9600; // 波特率
	private int flags = 0; // 标识
	private int databits = 8; // 数据位
	private int stopbits = 1; // 停止位
	private int parity = 'N'; // 校验类型
	private Timer timer;
	private byte[] btdata;
	private List<byte[]> list_data = new ArrayList<byte[]>();
	private boolean flag = false;// 判断数据发送
	private keHuaModel keHua = new keHuaModel(this);
	private keHuaModel02 keHua02 = new keHuaModel02(this);
	private String PCS_UP = "pcs.up";
	public static kehuaProl kehuaProl = null;
	private byte[] data = new byte[] { (byte) 0x09, (byte) 0x24, (byte) 0x09, (byte) 0x1A, (byte) 0x09, (byte) 0x2E,
			(byte) 0x01, (byte) 0xF4, (byte) 0x09, (byte) 0x24, (byte) 0x09, (byte) 0x1A, (byte) 0x09, (byte) 0x24,
			(byte) 0x01, (byte) 0xF3, (byte) 0x0E, (byte) 0x24, (byte) 0x03, (byte) 0x12, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x01, (byte) 0x08, (byte) 0x8E, (byte) 0x08, (byte) 0x98, (byte) 0x08, (byte) 0x8E,
			(byte) 0x01, (byte) 0xFB, (byte) 0x01, (byte) 0x9B, (byte) 0x01, (byte) 0x76, (byte) 0x00, (byte) 0x25,
			(byte) 0x00, (byte) 0x1E, (byte) 0x00, (byte) 0x1B, (byte) 0x00, (byte) 0x64, (byte) 0x00, (byte) 0x52,
			(byte) 0x00, (byte) 0x4A, (byte) 0x00, (byte) 0x6C, (byte) 0x00, (byte) 0x59, (byte) 0x00, (byte) 0x4E,
			(byte) 0x00, (byte) 0x5C, (byte) 0x00, (byte) 0x5C, (byte) 0x00, (byte) 0x00, (byte) 0x4B, (byte) 0x55,
			(byte) 0x33, (byte) 0x33, (byte) 0x00, (byte) 0x5E, (byte) 0x01, (byte) 0xF3, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x20, (byte) 0x00, (byte) 0x02,
			(byte) 0x00, (byte) 0x00, (byte) 0x26, (byte) 0x48, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0xF9, (byte) 0x00, (byte) 0x00, (byte) 0x0E, (byte) 0x24, (byte) 0x03, (byte) 0x12,
			(byte) 0x28, (byte) 0x52, (byte) 0x00, (byte) 0x03, (byte) 0xC5, (byte) 0xE0, (byte) 0x00, (byte) 0x01,
			(byte) 0xCB, (byte) 0xDA, (byte) 0x00, (byte) 0x02, (byte) 0xC7, (byte) 0xE6, (byte) 0x00, (byte) 0x01,
			(byte) 0x02, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x4B, (byte) 0x55, (byte) 0x33, (byte) 0x33,
			(byte) 0x30, (byte) 0x31, (byte) 0x20, (byte) 0x30, (byte) 0x20, (byte) 0x20, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
	private TimerTask task = new TimerTask() {
		@Override
		public void run() {
//			if (!SerialPortService.isUpData) {
//				SendData(1);
//			}
			// Log.e("4", "科华定时启动：");
			test(data);
		}
	};

	/**
	 * 发送数据
	 * 
	 * @param inde
	 */
	public void SendData(int inde) {
		if (inde == 1) {
			SendWirte(keHuaCMDTool.getKeHuaData((short) (6000), (byte) 29));
		}
		if (inde == 2) {
			SendWirte(keHuaCMDTool.getKeHuaData((short) (6029), (byte) 30));
		}
		if (inde == 3) {
			SendWirte(keHuaCMDTool.getKeHuaData02((short) (6000), (byte) 12));
		}
	}

	/**
	 * 串口创建时调用
	 */
	public keHuaSerialPortService(Context context) {
		this.context = context;
		try {
			mSerialPort = new SerialPort(new File(path), baudrate, flags, databits, stopbits, parity);
			mOutputStream = mSerialPort.getOutputStream();
			mInputStream = mSerialPort.getInputStream();
			thread = new ReadThread();
			thread.start();
			TimerUtil.pool.scheduleAtFixedRate(task, 0, TimerUtil.KehuaTimer, TimeUnit.MINUTES);
		} catch (SecurityException e) {
			e.printStackTrace();
			Log.e("3", "科华数据打开异常：" + e);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送数据
	 * 
	 * @param bt
	 */
	public void SendWirte(byte[] bt) {
		if (bt.length == 0 || mOutputStream == null) {
			return;
		}
		try {
			mOutputStream.write(bt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 接收线程
	 */
	protected class ReadThread extends Thread {
		@Override
		public void run() {
			long ttt = System.currentTimeMillis();
			int size = 0;
			while (true) {
				try {
					byte[] buffer = new byte[mInputStream.available()];
					if (mInputStream == null)
						return;
					size = mInputStream.read(buffer);
					if (size > 0) {
						onDataReceived(buffer, size);
					}
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}

	/**
	 * 接收数据
	 */
	public void onDataReceived(final byte[] data, int size) {
		Log.e("4", " 科华接收到数据 " + " " + SerialPortService.bytesToHexString(data));
		keHua.addBytes(data);
		keHua02.addBytes(data);
	}

	@Override
	public void afterWorkkeHua(byte[] bt) {
		byte[] crc = keHuaCMDTool.CRC16_Check(bt, bt.length - 2);
		if (bt[bt.length - 2] != crc[3] && bt[bt.length - 1] != crc[2]) {
			Log.e("4", "CRC教岩不通过crc[3]：" + crc[3] + "  crc[2]" + crc[2] + " bt[bt.length-1]  " + bt[bt.length - 2]
					+ "   bt[bt.length]" + bt[bt.length - 1]);

			return;
		}
		Intercept(bt); // 获取数据(截取包头和包尾之后的数据)
	}

	@Override
	public void afterWorkkeHua02(byte[] bt) {
		byte[] crc = keHuaCMDTool.CRC16_Check(bt, bt.length - 2);
		if (bt[bt.length - 2] != crc[3] && bt[bt.length - 1] != crc[2]) {
			Log.e("4", "CRC教岩不通过crc[3]：" + crc[3] + "  crc[2]" + crc[2] + " bt[bt.length-1]  " + bt[bt.length - 2]
					+ "   bt[bt.length]" + bt[bt.length - 1]);
			return;

		}
		Intercept(bt); // 获取数据(截取包头和包尾之后的数据)

	}

	/**
	 * 获取数据(截取包头和包尾之后的数据)
	 * 
	 * @param bt
	 */
	public void Intercept(byte[] bt) {
		byte[] bts = new byte[bt.length - 5];
		System.arraycopy(bt, 3, bts, 0, bt.length - 5);
		list_data.add(bts);
		if (bts.length == 58) { // 29
			SendData(2);
		}
		if (bts.length == 60) {
			SendData(3);
		}
		int index = 0;
		if (list_data.size() == 3) {
			byte[] fulldata = new byte[287];
			btdata = new byte[130];
			for (byte[] idata : list_data) {
				for (byte ida : idata) {
					btdata[index++] = ida;
				}
			}
			for (int i = 0; i < btdata.length; i++) {
				if (btdata[i] == 0x27) {
					btdata[i] = 0x20;
				}
			}
			byte[] btheader = keHuaCMDTool.getHeader(255, 255, 1);
			System.arraycopy(btheader, 0, fulldata, 0, btheader.length);
			byte[] btkehuadata = keHuaCMDTool.getkehuaDatas();
			System.arraycopy(btkehuadata, 0, fulldata, btheader.length, btkehuadata.length);
			System.arraycopy(btdata, 0, fulldata, btkehuadata.length + btheader.length, btdata.length);
			// 如果没有网络连接的情况下，数据保存在本地 当上传的时候，只上传第一条数据
			int check = 0;
			for (int i = 29; i < fulldata.length; i++) {
				check += fulldata[i] < 0 ? fulldata[i] & 0xff : fulldata[i];
			}
			check %= 256;
			byte[] checkBt = UpLoad.zeroBt(check + "", 3);
			System.arraycopy(checkBt, 0, fulldata, fulldata.length - 3, checkBt.length);
			try {
				if (ReadKeHuaData.WriteKeHuaData(fulldata)) {
					list_data.clear();
					kehuaProl = new kehuaProl();
					if (kehuaProl.kehuabytes(btdata)) {
						Log.e("4", " $$$$$$ " + fulldata.length + " " + SerialPortService.bytesToHexString(fulldata));
						if (TimerUtil.base != null) {
							TimerUtil.base.setHandler(1, kehuaProl);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void test(byte[] bt) {
		byte[] fulldata = new byte[287];
		byte[] btheader = keHuaCMDTool.getHeader(255, 255, 1);
		System.arraycopy(btheader, 0, fulldata, 0, btheader.length);
		byte[] btkehuadata = keHuaCMDTool.getkehuaDatas();
		System.arraycopy(btkehuadata, 0, fulldata, btheader.length, btkehuadata.length);
		Log.e("4", "bt.length   " + bt.length);
		System.arraycopy(bt, 0, fulldata, btkehuadata.length + btheader.length, bt.length);
		// 如果没有网络连接的情况下，数据保存在本地 当上传的时候，只上传第一条数据
		int check = 0;
		for (int i = 29; i < fulldata.length; i++) {
			check += fulldata[i] < 0 ? fulldata[i] & 0xff : fulldata[i];
		}
		check %= 256;
		byte[] checkBt = UpLoad.zeroBt(check + "", 3);
		System.arraycopy(checkBt, 0, fulldata, fulldata.length - 3, checkBt.length);
		try {
			if (ReadKeHuaData.WriteKeHuaData(fulldata)) {
				kehuaProl = new kehuaProl();
				kehuaProl.kehuabytes(bt);
				byte[] upBt = UpLoad.setHostData(7);
				SocketUtils.context.sendHandler(upBt);
				Log.e("4", " $$$$$$ " + fulldata.length + " " + SerialPortService.bytesToHexString(fulldata));
				/*
				 * if(TimerUtil.context!=null){
				 * TimerUtil.context.sendBroadcast(new
				 * Intent(TimerUtil.action)); }
				 */
				if (TimerUtil.base != null) {
					// TimerUtil.kehuaBt = bt;
					TimerUtil.base.setHandler(1, kehuaProl);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
