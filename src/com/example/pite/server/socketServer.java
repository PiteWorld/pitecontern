package com.example.pite.server;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.example.pite.tool.StopCMD;
import com.example.piteconternect.MainActivity;

import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

public class socketServer {
//	private List<Socket> mList = new ArrayList<Socket>();// ��ſͻ���socket
//	private ServerSocket server = null;
//	private ExecutorService mExecutorService = null;
//	private PrintWriter writer = null;
//	private int num = 0; // �ӻ���
//	private Time time = new Time();
//	private int year, mouth, date, hour, minute, seconds;
//	private boolean isStopStatus = false;
//	private int cnt = 0; // ��ѯ������
//	private Timer timer = new Timer();
//	// private BufferedReader socketRecData = null;
//	private DataInputStream dis = null;
//	private boolean SavleStatus = false; // ��ѯ״̬
//	private int SalveNum = 0; // �������
//	private Socket socket = null;
//	private MainActivity context;
//	private boolean isDataRec = false; // �����Ƿ���ճɹ�
//	private byte[] deBt = null; // ��־
//	private boolean isReadOK = false;
//
//	public socketServer() {
//	}
//
//	private TimerTask task = new TimerTask() {
//		@Override
//		public void run() {
//			for (int i = 0; i < 2; i++) {
//				// context.setHandler2("��ʱ����");
//				Log.e("2", "��ʱ����");
//				byte[] bt = StopCMD.startCMD(14, num, 4, year - 2000, mouth, date, hour, minute, seconds);
//				Salve(bt);
//				if (SavleStatus) {
////					byte[] lookBt = StopCMD.LookCMD(4, num, 4);
////					LookData(lookBt);
//					break;
//				}
//			}
//			// context.setHandler2("��ʱ����");
//			Log.e("2", "��ʱ����");
//		}
//	};
//
////	public socketServer(MainActivity context) {
////		this.context = context;
////		try {
////			isStopStatus = true;
////			server = new ServerSocket(9000);
////			mExecutorService = Executors.newCachedThreadPool();// ����һ���̳߳�
////			Socket client = null;
////			// SocketUtils.setSocket(this);
////			while (true) {
////				client = server.accept();
////				client.setReuseAddress(true);
////				mList.add(client);
////				mExecutorService.execute(new Service(client));// ����һ���ͻ����߳�.
////			}
////		} catch (Exception ex) {
////			Log.e("2", "�̳߳��쳣" + ex);
////
////		}
////	}
//
//	public class Service extends Thread {
//		private Socket socket;
//
//		public Service() {
//
//		}
//
//		@SuppressWarnings("deprecation")
//		public Service(Socket socket) {
//			this.socket = socket;
//			time.setToNow();
//			year = time.year;
//			mouth = time.month + 1;
//			date = time.monthDay;
//			hour = time.hour;
//			minute = time.minute;
//			seconds = time.second;
//			num = mList.size();
//			cnt++;
//			try {
//				dis = new DataInputStream(socket.getInputStream());
//				if (isStopStatus) {
//					isStopStatus = false;
//					byte[] bt = StopCMD.stopCMD(10, num, 4, year - 2000, mouth, date, hour, minute, seconds);
//					Stop(bt);// 1800000
//					timer.schedule(task, 200000, 200000);
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		@Override
//		public void run() {
//			try {
//				while (!interrupted()) {
//					// if (dis.read() == -1) {
//					// socket.close();
//					// mList.remove(socket);
//					// num--;
//					// cnt--;
//					// break;
//					// }
//				}
//			} catch (Exception ex) {
//				Toast.makeText(context, "�̳߳��쳣" + ex, 0).show();
//			}
//		}
//
//		/**
//		 * ������Ϣ�����пͻ���
//		 */
//		public int sendmsg() {
//			int num = mList.size();
//			int a = 0;
//			for (int i = 0; i < num; i++) {
//				Socket mSocket = mList.get(i);
//				try {
//					writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream())),
//							true);
//					// a = dis.read();
//					writer.flush();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			return a;
//		}
//
//	}
//
//	/**
//	 * ���Ͷ�����Ϣ
//	 */
//	public void Salve(byte[] bt) {
//		int num = mList.size();
//		DataOutputStream dos = null;
//		for (int i = 0; i < num; i++) {
//			Socket mSocket = mList.get(i);
//			try {
//				dos = new DataOutputStream(mSocket.getOutputStream());
//				dos.write(bt);
//				Thread.sleep(10000);
//				Log.e("2", "���췢�ͳɹ���  " + dis.read());
//				if (dis.read() != -1) {
//					byte[] startBt = new byte[dis.available()];
//					int len = 0;
//					int length = 0;
//					while (-1 != (len = dis.read(startBt))) {
//						length += len;
//						if (length > dis.available()) {
//							break;
//						}
//					}
//					// Log.e("2", "�����յ������ݣ� " +
//					// SerialPortService.bytesToHexString(startBt));
//					if (startBt[15] == 2 || startBt[15] == 3 || startBt[15] == 4 || startBt[15] == 5 || startBt[15] == 6
//							|| startBt[16] == 2 || startBt[16] == 3 || startBt[16] == 4 || startBt[16] == 5
//							|| startBt[16] == 6) {
//						// context.setHandler2("����ɹ�");
//						Log.e("2", "����ɹ�");
////						byte[] slaveBt = StopCMD.slaveCMD(4, num, 4);
////						Inquire(mSocket, slaveBt);
//					} else {
//						Log.e("2", "����ʧ��1");
//						return;
//					}
//				} else {
//					Log.e("2", "����ʧ��2");
//				}
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * ��ѯ�ӻ�����
//	 * 
//	 * @param bt
//	 */
//	public void Inquire(Socket sockets, byte[] bt) {
//		DataOutputStream dos = null;
//		try {
//			dos = new DataOutputStream(sockets.getOutputStream());
//			dos.write(bt);
//			Thread.sleep(10000);
//			if (dis.read() != -1) {
//				byte[] SalveBt = new byte[dis.available()];
//				int len = 0;
//				int length = 0;
//				while ((len = dis.read(SalveBt)) != -1) {
//					length += len;
//					if (length > dis.available()) {
//						break;
//					}
//				}
//				// Log.e("2", "��ѯ�յ������ݣ� " +
//				// SerialPortService.bytesToHexString(SalveBt));
//				if (((SalveBt[16] == 0 || SalveBt[16] == 1 || SalveBt[16] == 2 || SalveBt[16] == 3 || SalveBt[16] == 4
//						|| SalveBt[16] == 6 || SalveBt[16] == 7 || SalveBt[16] == 16) && (SalveBt[29] != 0)
//						&& (SalveBt[50] == 0))) {
//					SavleStatus = true;
//					socket = sockets;
//					// context.setHandler2("���ݲ�ѯ�ɹ�");
//					Log.e("2", "���ݲ�ѯ�ɹ�");
//				} else {
//					// context.setHandler2("���ݲ�ѯʧ��");
//					SavleStatus = false;
//					SalveNum++;
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	/**
//	 * ��ȡ����
//	 */
//	public void LookData(byte[] bt) {
//		DataOutputStream dos = null;
//		try {
//			dos = new DataOutputStream(socket.getOutputStream());
//			dos.write(bt);
//			Thread.sleep(10000);
//			if (dis.read() != -1) {
//				byte[] lookBt = new byte[dis.available()];
//				int len = 0;
//				int length = 0;
//				while ((len = dis.read(lookBt)) != -1) {
//					length += len;
//					if (length > dis.available()) {
//						break;
//					}
//				}
//				byte[] decode = new byte[8];
//				System.arraycopy(lookBt, 29, decode, 0, 8);
//				// Log.e("2", "��ȡ�յ�������------�� " +
//				// SerialPortService.bytesToHexString(lookBt));
//				byte[] recBt = new byte[length]; // ��������
//				byte[] sendBt = new byte[length + 1]; // ����0X7E
//				sendBt[0] = 0x7e;
//				System.arraycopy(lookBt, 39, recBt, 0, length - 40);
//				System.arraycopy(lookBt, 0, sendBt, 1, length);
//				// Log.e("2", "��ȡ�������ݣ�" +
//				// SerialPortService.bytesToHexString(sendBt));
//				if (lookBt[41] == 3) {
//					setDeviced(recBt);
//				} else {
//					DataPrclo prclo = new DataPrclo();
//					try {
//						BatteryCheckBean isDataStatus = prclo.Get805Data(recBt);
//						if (isDataStatus != null) {
////							context.list.add(isDataStatus);
//							//// context.setHandler2("���ݽ������");
//							setRecSueccfuss(setDecodFlag(decode)); // ɾ������
//							Log.e("2", "���ݽ������");
//							isDataRec = true;
////							context.setHandler(sendBt);
//							// byte[] noticBt = StopCMD.RecDataSuccess(8, num,
//							// cnt,
//							// 1);
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//
//			}
//		} catch (IOException | InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
//
//	private void setDeviced(byte[] bt) {
//		deBt = new byte[bt.length];
//		System.arraycopy(bt, 0, deBt, 0, bt.length);
////		if (context.cntnum == 1) {
////			DataPrclo prclo = new DataPrclo();
////			try {
////				BatteryCheckBean isDataStatus = prclo.Get805Data(deBt);
//////				context.list.add(isDataStatus);
//////				context.setLoad();
////			} catch (Exception e) {
////				e.printStackTrace();
////			}
////		}
//	}
//
//	public void setDevicedPsotion() {
//		if (deBt == null) {
//			return;
//		}
////		if (context.cntnum == 1) {
////			DataPrclo prclo = new DataPrclo();
////			try {
////				BatteryCheckBean isDataStatus = prclo.Get805Data(deBt);
////				context.list.add(isDataStatus);
//////				context.setLoad();
////			} catch (Exception e) {
////				e.printStackTrace();
////			}
////		}
//	}
//
//	/**
//	 * ֪ͨ���ݽ��ճɹ�
//	 * 
//	 * @param bt
//	 */
//	public void setRecSueccfuss(byte[] mark) {
//		int num = mList.size();
//		DataOutputStream dos = null;
////		byte[] reBt = StopCMD.RecDataSuccess(8, num, 4, mark);
//		// Log.e("2", "֪ͨ ���ݽ��ճɹ��������ݣ�***** " +
//		// SerialPortService.bytesToHexString(mark));
//		for (int i = 0; i < num; i++) {
//			Socket mSocket = mList.get(i);
//			try {
//				dos = new DataOutputStream(mSocket.getOutputStream());
////				dos.write(reBt);
//				// Thread.sleep(10000);
//				if (dis.read() != -1) {
//					byte[] upBt = new byte[dis.available()];
//					int len = 0;
//					int length = 0;
//					while ((len = dis.read(upBt)) != -1) {
//						length += len;
//						if (length > dis.available()) {
//							break;
//						}
//					}
//					// Log.e("2", "֪ͨ ���ݽ��ճɹ��������ݣ�%%%%%" +
//					// SerialPortService.bytesToHexString(upBt));
//					if (upBt[16] == 0) {
////						byte[] lookBt = StopCMD.LookCMD(4, num, 4);
////						LookData(lookBt);
//					} else {
//						break;
//					}
//				}
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * ����ֹͣ��Ϣ
//	 */
//	public void Stop(byte[] bt) {
//		int num = mList.size();
//		DataOutputStream dos = null;
//		for (int i = 0; i < num; i++) {
//			Socket mSocket = mList.get(i);
//			try {
//				dos = new DataOutputStream(mSocket.getOutputStream());
//				dos.write(bt);
//				// context.setHandler2("ֹͣ����");
//				Log.e("2", "ֹͣ����");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * �رն�ʱ��
//	 */
//	public void setCacelsTask() {
//		if (task != null) {
//			task.cancel();
//		}
//	}
//
//	/**
//	 * �ж����ݽ�����ɽ����־
//	 */
//	public static byte[] setDecodFlag(byte[] bt) {
//		// byte[] proBt = new byte[8];
//		int decod = 0;
//		// for (int j = 0; j < bt.length; j++) {
//		// proBt[j] = bt[j];
//		// }
//		for (int i = 0; i < 8; i++) {
//			decod *= 10;
//			decod += bt[i] - 0x30;
//		}
//		return int2byte(decod);
//	}
//
//	public static byte[] int2byte(int res) {
//		byte[] targets = new byte[4];
//		targets[0] = (byte) (res & 0xff);// ���λ
//		targets[1] = (byte) ((res >> 8) & 0xff);// �ε�λ
//		targets[2] = (byte) ((res >> 16) & 0xff);// �θ�λ
//		targets[3] = (byte) (res >>> 24);// ���λ,�޷������ơ�
//		return targets;
//	}
}
