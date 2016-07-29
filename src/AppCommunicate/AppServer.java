package AppCommunicate;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.example.piteconternect.utils.TimerUtil;

import android.util.Log;

public class AppServer {
	private ServerSocket server = null;
	private boolean bRun = false;
	private int port = 10000;
	private ArrayList<ThreadReader> listReader = new ArrayList<ThreadReader>();
	private ThreadReader.ReaderWork readwork = new ThreadReader.ReaderWork() {
		@Override
		public void closeWork(ThreadReader reader) {
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
					ThreadReader reader = new ThreadReader(so, readwork);
					reader.start();
					listReader.add(reader);
					Log.e("3", "listReader:  " + listReader.size());
				} catch (IOException e) {
					bRun = false;
				}
			}
		}
	};

	public AppServer(int port) throws IOException {
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
	public void setTime(int time) {
		TimerUtil.time = time;
		for (ThreadReader rd : listReader) {
			rd.setTimer();
		}
	}

	/**
	 * 重启
	 * 
	 * @param bts
	 */

	public boolean setRestart(byte[] bts) {
		sendBytes(bts);
		for (ThreadReader rd : listReader) {
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
		for (ThreadReader rd : listReader) {
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
