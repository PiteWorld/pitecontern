package com.example.pite.tool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.example.pite.model.PrcloModel;
import com.example.pite.server.SocketContent;
import com.example.pite.tool.PiteModel.ModelWork;
import com.example.pite.tool.PiteModleID.ModelWorkDT;

import android.util.Log;
import android_serialport_api.SerialPortService;

public class NonBlockingIOModel implements ModelWork, ModelWorkDT{

	private Thread thdServer = null;
	private Thread thdRecv = null;
	public ArrayList<Socket> listSo = new ArrayList<Socket>();
	private ServerSocket server = null;
	private boolean bRun = true;
	private static byte[] bt = null;
	private static byte[] reBt = null;
	private SocketContent socket = null;
	private int savle = 0;
	private int cnts = 0;
	private PiteModel pite = new PiteModel(this);
	private PiteModleID pites = new PiteModleID(this);

	public NonBlockingIOModel() {
		thdServer = new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					server = new ServerSocket(9000);
					while (bRun) {
						Socket so = server.accept();
						synchronized (listSo) {
							listSo.add(so);
							if (savle == listSo.size()) {
								cnts = 0;
								socket.setHandler(2);
							}
							cnts++;
							if (cnts > 30) {
								if (savle == listSo.size()) {
									cnts = 0;
									socket.setHandler(2);
								}
							}
						}
						Log.e("3", "连接数据：" + listSo.size());
					}
				} catch (IOException e) {
					e.printStackTrace();

				}
			}
		};
		thdRecv = new Thread() {
			@Override
			public void run() {
				super.run();
				byte[] buf = new byte[4096];
				int len;
				while (bRun) {
					try {
						ArrayList<Socket> rmList = new ArrayList<Socket>();
						int size = listSo.size();
						InputStream is = null;
						for (int i = 0; i < size; i++) {
							Socket so = listSo.get(i);
							OutputStream os = so.getOutputStream();
							try {
//								Thread.sleep(1000);
								is = so.getInputStream();
							} catch (Exception e) {

							}
							len = is.available();
							if (len > 0) {
								is.read(buf);
								byte[] bt = new byte[len];
								System.arraycopy(buf, 0, bt, 0, len);
								pites.addBytes(bt);
								pite.addBytes(bt);
								Log.e("5", "socket读取的数据：" + SerialPortService.bytesToHexString(bt));
							} else if (len < 0) {
								rmList.add(so);
							}
						}
						for (Socket so : rmList) {
							listSo.remove(so);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}

	public void start(SocketContent socket, int savle) {
		this.savle = savle;
		this.socket = socket;
		thdServer.start();
		thdRecv.start();
	}

	@Override
	public void afterWork(byte[] bt) {
		// this.bt = bt;
		setBt(bt);
		Log.e("4", "截取的数据：" + SerialPortService.bytesToHexString(bt));
	}
	public static byte[] getBt() {
		return bt;
	}

	public static void setBt(byte[] bt) {
		NonBlockingIOModel.bt = bt;
	}

	public static byte[] getReBt() {
		return reBt;
	}

	public static void setReBt(byte[] reBt) {
		NonBlockingIOModel.reBt = reBt;
	}

	@Override
	public void afterWorkDT(byte[] bt, int len) {
		setReBt(bt);
		Log.e("4", "数据：---" + SerialPortService.bytesToHexString(bt));
	}

}
