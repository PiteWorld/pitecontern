package com.example.piteconternect.Inspection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.example.piteconternect.utils.TimerUtil;

import android.util.Log;

public class InspectionAppServer {
	private ServerSocket server = null;
	private boolean bRun = false;
	private int port = 10000;
	private ArrayList<InspectionThreadReader> listReader = new ArrayList<InspectionThreadReader>();
	private InspectionThreadReader.ReaderWork readwork = new InspectionThreadReader.ReaderWork() {
		@Override
		public void closeWork(InspectionThreadReader reader) {
			listReader.remove(reader);
		}
	};
	private Runnable runAccept = new Runnable() {
		@Override
		public void run() {
			bRun = true;
			while (bRun) {
				try {
					Socket so = server.accept();
					InspectionThreadReader reader = new InspectionThreadReader(so, readwork);
					reader.start();
					listReader.add(reader);
					Log.e("3", "listReader:  " + listReader.size());
				} catch (IOException e) {
					bRun = false;
				}
			}
		}
	};

	public InspectionAppServer(int port) throws IOException {
		this.port = port;
		server = new ServerSocket(port);
		Thread thd = new Thread(runAccept);
		thd.start();
	}

	/**
	 * 设置定时
	 * 
	 * @param time
	 */
	public void InspectionSetTime(int time) {
		TimerUtil.InspectionTime = time;
		for (InspectionThreadReader rd : listReader) {
			rd.InspectionsetTimer();
		}
	}

	/**
	 * 重启
	 * 
	 * @param bts
	 */

	public boolean setRestart(byte[] bts) {
		sendBytes(bts);
		for (InspectionThreadReader rd : listReader) {
			try {
				rd.startStatus();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public void sendBytes(byte[] bts) {
		for (InspectionThreadReader rd : listReader) {
			try {
				rd.getOutputStream().write(bts);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void close() {
		bRun = false;
		try {
			if (server != null) {
				Socket tmp = new Socket("127.0.0.1", port);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
