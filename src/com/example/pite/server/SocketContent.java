package com.example.pite.server;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import com.example.pite.model.PrcloModel;
import com.example.pite.tool.NonBlockingIOModel;
import com.example.pite.tool.PiteModel;
import com.example.pite.tool.PiteModel.ModelWork;
import com.example.pite.tool.StopCMD;
import com.example.piteconternect.ConcentratorActivity;
import com.example.piteconternect.netty.SimpleClientHandler;
import com.example.piteconternect.utils.SocketUtils;
import com.example.piteconternect.utils.nettyUtils;

import AppCommunicate.AppServer;
import android.net.Proxy;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;
import android_serialport_api.SerialPortService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressiveFutureListener;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.Channel.Unsafe;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelGroupFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import struct.StructException;

public class SocketContent {
	private List<Socket> mList = new ArrayList<Socket>();// ��ſͻ���socket
	private ConcentratorActivity context;
	private ServerSocket server = null;
	private ExecutorService mExecutorService = null;
	private Time time = new Time();
	private int year, mouth, date, hour, minute, seconds;
	private PrintWriter writer = null;
	private boolean SavleStatus = false; // ��ѯ״̬
	private boolean isStopStatus = false; // ��һ�α�־
	public boolean isTimer = false; // ��һ�α�־
	private int num = 1;
	private Timer timer = new Timer();
	private int hostID = 0;
	// private PiteModel pite = new PiteModel(this);
	private int salve = 1;// �ӻ���
	private int contentNum = 0;// ���Ӽ�����
	// private byte[] RecBt = null;
	private boolean isTimeStart = false;
	private Channel channel = null;
	private AppServer proNb = null;
	private int handlerNum = 0;
	private int cnts = 0;
	private Socket Recsocket = null;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				SavleStatus = false;
				Log.e("3", "handler: " + handlerNum);
				// LookData(handlerNum); // ���ݶ�ȡ
//				SocketUtils.setSalveNum(handlerNum); // ���ôӻ���
			} else if (msg.what == 2) {
				// Stop(); // ����ֹͣ����
			}
		}
	};
	public TimerTask task = new TimerTask() {
		@Override
		public void run() {
			for (int i = 1; i < 3; i++) {
				Log.e("3", "��ʱ����");
				// ��ʱ����){
				// Salve();
				if (SavleStatus) {
					return;
				}
			}
		}
	};

	public SocketContent() {
	}

	public SocketContent(ConcentratorActivity context, int hostID, int salve) {
		this.context = context;
		this.hostID = hostID;
		this.salve = salve;
		SocketUtils.context = context;
//		time.setToNow();
//		year = time.year;
//		mouth = time.month + 1;
//		date = time.monthDay;
//		hour = time.hour;
//		minute = time.minute;
//		seconds = time.second;
//		isTimer = true;
		try {
			proNb = new AppServer(9000);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		setTimer(100000);

	}

	/**
	 * ����ֹͣ��Ϣ
	 */
	/*
	 * public void Stop() { DataOutputStream dos = null; try { for (int j = 0; j
	 * < proNb.listSo.size(); j++) { dos = new
	 * DataOutputStream(proNb.listSo.get(j).getOutputStream()); for (int i = 1;
	 * i < proNb.listSo.size() + 1; i++) { byte[] bt = StopCMD.stopCMD(10, i,
	 * hostID, year - 2000, mouth, date, hour, minute, seconds); dos.write(bt);
	 * Log.e("3", "ֹͣ���͵Ĵӻ��ţ� " + i); Thread.sleep(100); }
	 * 
	 * } } catch (IOException e) { e.printStackTrace(); } catch
	 * (InterruptedException e) { e.printStackTrace(); } }
	 * 
	 *//**
		 * ���Ͷ�����Ϣ
		 * 
		 * @throws IOException
		 */
	/*
	 * public void Salve() { DataOutputStream dos = null; try { int len =
	 * proNb.listSo.size(); Log.e("3", "socket��С�� " + len); for (int j = 0; j <
	 * len; j++) { dos = new
	 * DataOutputStream(proNb.listSo.get(j).getOutputStream()); for (int i = 1;
	 * i < len + 1; i++) { byte[] bt = StopCMD.startCMD(14, i, hostID, year -
	 * 2000, mouth, date, hour, minute, seconds); dos.write(bt);
	 * Thread.sleep(1000); } Thread.sleep(6000); byte[] startBt =
	 * NonBlockingIOModel.getBt(); if (startBt != null && startBt.length > 3) {
	 * // pite.addBytes(startBt); Log.e("1", "�����յ������ݣ�" + j + 1 + " " +
	 * SerialPortService.bytesToHexString(startBt)); if (startBt[15] == 2 ||
	 * startBt[15] == 3 || startBt[15] == 4 || startBt[15] == 5 || startBt[15]
	 * == 6) { Log.e("3", "����ɹ�" + "  startBt[11]  " + startBt[11]);
	 * Inquire(proNb.listSo.get(j), startBt[11]); } else { // Log.e("3", "����ʧ��@"
	 * + j + 1); } } } } catch (IOException e) { e.printStackTrace(); } catch
	 * (InterruptedException e) { e.printStackTrace(); } };
	 * 
	 *//**
		 * ��ѯ�ӻ�����
		 * 
		 * @param bt
		 */
	/*
	 * public void Inquire(Socket sockets, int cnt) { // Log.e("3", "��ѯ���͵Ĵӻ��ţ� "
	 * + cnt); byte[] bt = StopCMD.slaveCMD(4, cnt, hostID); DataOutputStream
	 * dos = null; try { dos = new DataOutputStream(sockets.getOutputStream());
	 * dos.write(bt); Thread.sleep(5000); byte[] SalveBt =
	 * NonBlockingIOModel.getBt(); if (SalveBt != null && SalveBt.length > 3) {
	 * // pite.addBytes(SalveBt); Thread.sleep(6000); Log.e("1", "��ѯ�յ������ݣ� " +
	 * SerialPortService.bytesToHexString(SalveBt)); if (((SalveBt[16] == 0 ||
	 * SalveBt[16] == 1 || SalveBt[16] == 2 || SalveBt[16] == 3 || SalveBt[16]
	 * == 4 || SalveBt[16] == 6 || SalveBt[16] == 7 || SalveBt[16] == 16) &&
	 * (SalveBt[28] != 0) && (SalveBt[49] == 0))) { SavleStatus = true;
	 * Log.e("3", "���ݲ�ѯ�ɹ�" + cnt); handlerNum = cnt; Recsocket = sockets;
	 * handler.sendEmptyMessage(1); } } } catch (Exception e) { Log.e("3",
	 * "��ѯ�쳣��" + e); } }
	 * 
	 *//**
		 * ��ȡ����
		 */
	/*
	 * public void LookData(int cnt) { DataOutputStream dos = null; try { //
	 * Log.e("3", "��ȡ���ݽ�����"); byte[] bt = StopCMD.LookCMD(4, cnt, hostID); dos =
	 * new DataOutputStream(Recsocket.getOutputStream()); dos.write(bt);
	 * Thread.sleep(5000); byte[] lookBt = NonBlockingIOModel.getReBt(); if
	 * (lookBt != null && lookBt.length > 3) { // pite.addBytes(lookBt); byte[]
	 * ProBt = new byte[lookBt.length]; // �������� byte[] FullBt = new
	 * byte[lookBt.length + 1]; // ȫ������ 0x7E byte[] decode = new byte[8];// �����־
	 * FullBt[0] = 0x7E; System.arraycopy(lookBt, 40, ProBt, 0, lookBt.length -
	 * 40); System.arraycopy(lookBt, 0, FullBt, 1, lookBt.length);
	 * System.arraycopy(lookBt, 29, decode, 0, 8); PrcloModel prclo = new
	 * PrcloModel(); // Log.e("1", "��ȡ�������ݣ�" + num + " " + //
	 * SerialPortService.bytesToHexString(ProBt)); if (prclo.addByte(ProBt)) {
	 * context.setHandler(1, FullBt); Log.e("1", "���ݽ������    " + cnt);
	 * setRecSueccfuss(Recsocket, setDecodFlag(decode), cnt);// ɾ������ } } } catch
	 * (Exception e) { } }
	 * 
	 *//**
		 * ֪ͨ���ݽ��ճɹ�
		 * 
		 * @param bt
		 *//*
		 * public void setRecSueccfuss(Socket mSocket, byte[] mark, int cnt) {
		 * DataOutputStream dos = null; try { byte[] reBt =
		 * StopCMD.RecDataSuccess(8, cnt, hostID, mark); dos = new
		 * DataOutputStream(mSocket.getOutputStream()); dos.write(reBt); //
		 * Thread.sleep(5000); byte[] upBt = proNb.getBt(); if (upBt != null &&
		 * upBt.length > 2) { // pite.addBytes(upBt); // Log.e("4",
		 * "֪ͨ ���ݽ��ճɹ��������ݣ�%%%%%" + // SerialPortService.bytesToHexString(upBt));
		 * Log.e("4", "֪ͨ���ݽ��ճɹ�"); if (reBt[16] == 0) { Log.e("4", "���ݳɹ�������" +
		 * cnt); LookData(cnt); // ��ȡ���� } else {
		 * 
		 * } } } catch (IOException e) { e.printStackTrace(); } }
		 */

	/**
	 * �ж����ݽ�����ɽ����־
	 */
	public static byte[] setDecodFlag(byte[] bt) {
		int decod = 0;
		for (int i = 0; i < 8; i++) {
			decod *= 10;
			decod += bt[i] - 0x30;
		}
		return int2byte(decod);
	}

	public static byte[] int2byte(int res) {
		byte[] targets = new byte[4];
		targets[0] = (byte) (res & 0xff);// ���λ
		targets[1] = (byte) ((res >> 8) & 0xff);// �ε�λ
		targets[2] = (byte) ((res >> 16) & 0xff);// �θ�λ
		targets[3] = (byte) (res >>> 24);// ���λ,�޷������ơ�
		return targets;
	}

	// @Override
	// public void afterWork(byte[] bt) {
	// RecBt = bt;
	// Log.e("4", "��ȡ�����ݣ� " + SerialPortService.bytesToHexString(bt));
	// }
	/**
	 * ֪ͨ���ݶ�ȡ�ɹ�
	 * 
	 * @param index
	 */
	public void setHandler(int index) {

		handler.sendEmptyMessage(index);
	}

	/**
	 * ���ö�ʱ
	 */
	public void setTimer(int time) {
		if (isTimer) {
			Log.e("3", "��ʱ�趨");
			timer.schedule(task, time, time);
			isTimer = false;
			// handler.sendEmptyMessage(2);
		}
	}

}
