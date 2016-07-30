package android_serialport_api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android_serialport_api.keHuaSerialPortService.ReadThread;

public class keHuaServiceModelbus {
	private Context context;
	protected SerialPort mSerialPort;
	private OutputStream mOutputStream;
	private InputStream mInputStream;
	private String path = "/dev/ttyS5"; // 路径
	private ReadThread thread;
	private int baudrate = 9800; // 波特率
	private int flags = 0; // 标识
	private int databits = 8; // 数据位
	private int stopbits = 1; // 停止位
	private int parity = 'N'; // 校验类型
	private SparseArray<byte[]> sp = null;
	private int addterss = 6000 ;
	public keHuaServiceModelbus(Context context) {
		this.context = context;
		try {
			mSerialPort = new SerialPort(new File(path), baudrate, flags, databits, stopbits, parity);
			mOutputStream = mSerialPort.getOutputStream();
			mInputStream = mSerialPort.getInputStream();
			thread = new ReadThread();
			thread.start();
			sp = new SparseArray<>();
		} catch (SecurityException e) {
			e.printStackTrace();
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

	}
}
