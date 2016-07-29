package com.example.piteconternect.read;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.example.pite.model.PrcloModel;
import com.example.pite.tool.IDModel;
import com.example.pite.tool.PiteModel;
import com.example.pite.tool.PiteModleID;
import com.example.pite.tool.ReadData;
import com.example.pite.tool.StopCMD;
import com.example.pite.tool.UpLoad;
import com.example.pite.tool.IDModel.ModelWorkID;
import com.example.pite.tool.PiteModel.ModelWork;
import com.example.pite.tool.PiteModleID.ModelWorkDT;
import com.example.piteconternect.ConcentratorActivity;
import com.example.piteconternect.exection.MyApplication;
import com.example.piteconternect.netty.SimpleClientHandler;
import com.example.piteconternect.utils.SocketUtils;
import com.example.piteconternect.utils.TimerUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Xml.Encoding;
import android_serialport_api.SerialPortService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.socks.SocksMessageEncoder;

/**
 * 读取主机数据
 */
public class ReadDataHost implements ModelWorkDT, ModelWorkID {
	public static ChannelHandlerContext ctx = null;
	public List<byte[]> addDT = new ArrayList<byte[]>();
	private int sizelength = 0;
	private PiteModleID piteModleID = new PiteModleID(this);
	private IDModel idModel = new IDModel(this);
	private byte[] headbt = null;
	private int pakLength = 0;
	public static boolean isSynTime = false;
	private Timer timer = null;
	private boolean is25updata = false;
	private int time = 30;
	public static int resartCnt = 0;
	private int bmsdecode = 0;
	private int timecnt = 0;
	private int salve = 0; // 从机号判断
	private int updatacnt = 0;
	private TimerTask task = new TimerTask() {
		@Override
		public void run() {
			if (SerialPortService.isUpData) {
				updatacnt++;
				if (updatacnt > 20) {
					updatacnt = 0;
					SerialPortService.isUpData = false;
				}
			}
			if (is25updata && SerialPortService.isHostAndOther) {
				time--;
				if (time == 0) {
					time = 30;
					is25updata = false;
					SerialPortService.isHostAndOther = false;
				}
				return;
			}
			try {
				if (ctx != null) {
					byte[] bttime = StopCMD.setSynTime();
					ctx.writeAndFlush(Unpooled.copiedBuffer(bttime));
					Thread.sleep(1000);
					byte[] bts = StopCMD.setHostData();
					ctx.writeAndFlush(Unpooled.copiedBuffer(bts)); // 主动要数据
					Log.e("6", "同步主机时间：" + SerialPortService.bytesToHexString(bttime));
					// Thread.sleep(1000);
					// byte[] btreat = StopCMD.setHostOneData(); // 发送0x35
					// ctx.writeAndFlush(ctx.alloc().buffer().writeBytes(btreat));
				}

			} catch (Exception e) {
			}
		}
	};

	/**
	 * 添加 数据
	 * 
	 * @param bt
	 */
	public void addbytes(byte[] bt, ChannelHandlerContext ctx) {
		this.ctx = ctx;
		if (SerialPortService.upDataStatus) {
			is25updata = true;
			SocketUtils.context.sendHandler(bt);
			// SerialPortService.SendWirte(bt);
			Log.e("4", "更新程序当中3");
			if (bt[6] == 0x7A) {
				is25updata = true;
				SerialPortService.isUpData = false;
				SerialPortService.upDataStatus = false;
				Log.e("4", "更新成功  4：");
			}

		}
		idModel.addBytes(bt); // ID
		piteModleID.addBytes(bt); // DT
	}

	@Override
	public void afterWorkID(byte[] bt) {
		Log.e("10", "接收到数据请求发送 ID：" + SerialPortService.bytesToHexString(bt));
		headbt = bt;
		sizelength = 0;
		addDT.clear();
		for (int i = 11; i < bt.length; i++) {
			sizelength *= 10;
			sizelength += (bt[i] - 0x30);
		}
		pakLength = sizelength;
		// Log.e("1", "数据总长度：" + sizelength);
	}

	@Override
	public void afterWorkDT(byte[] bt, int len) {
		// int check = 0;
		// for (int i = 11; i < bt.length - 3; i++) {
		// check += bt[i] < 0 ? bt[i] & 0xff : bt[i];
		// }
		// check %= 256;
		// byte[] checkBt = UpLoad.zeroBt(check + "", 3);
		// byte[] crc = SocketUtils.CRC_XModem(bt);
		// if (checkBt[0] != bt[bt.length - 3] && checkBt[1] != bt[bt.length -
		// 2] && checkBt[2] != bt[bt.length - 1]) {
		// ctx.writeAndFlush(Unpooled.copiedBuffer(StopCMD.setRestDataPacket()));
		// } else {
		// // byte[] crcbt = new byte[bt.length - 2];
		// // System.arraycopy(bt, 0, crcbt, 0, crcbt.length);
		// setUpdata(bt, len);
		// }
		byte[] crc = SocketUtils.CRC_XModem(bt);
		if (crc[0] != bt[bt.length - 2] && crc[1] != bt[bt.length - 1]) {
			ctx.writeAndFlush(Unpooled.copiedBuffer(StopCMD.setRestDataPacket()));
		} else {
			byte[] crcbt = new byte[bt.length - 2];
			System.arraycopy(bt, 0, crcbt, 0, crcbt.length);
			setUpdata(crcbt, len);
		}

	}

	/**
	 * 要发送的数据
	 * 
	 * @param bt
	 *            //数据 DT
	 * @param len
	 *            长度
	 * @param sizelength
	 * 
	 * @param pakLength
	 *            数据总长度
	 * @param headbt
	 *            ID
	 */
	public void setUpdata(byte[] bt, int len) {
		this.sizelength -= len;
		// Log.e("1", "剩余长度：" + this.sizelength + " len " + len);
		if (sizelength != 0) {
			byte[] nextbt = StopCMD.getNextPacket(4); // 下一包
			for (int i = 0; i < 2; i++) {
				ctx.writeAndFlush(Unpooled.copiedBuffer(nextbt));
			}
			byte[] addList = new byte[bt.length - 14];
			System.arraycopy(bt, 11, addList, 0, addList.length);
			addDT.add(addList);
			// Log.e("4", "分包后的数据：" + addList.length);
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
				// Log.e("1", "整包数据：" +
				// SerialPortService.bytesToHexString(bts));
				index += bts.length;
			}
			// Log.e("1", "send长度：" + sendBt.length);
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
			// Log.e("10", "最后的整包数据：" +
			// SerialPortService.bytesToHexString(fullbt) + " \n" +
			// fullbt.length);
			setData(fullbt);

		}
	}

	/**
	 * 设置 发送数据
	 * 
	 * @param bt
	 */
	public void setData(byte[] bt) {
		if (bt.length < 80) {
			if (bt[10] == 0x3B) {
				// SerialPortService.SendWirte(bt);
				isSynTime = true;
				Log.e("6", "同步时间： " + SerialPortService.bytesToHexString(bt));
			}
			switch (bt[10]) {
			case 0x6F:
				if (bt[29] == 0x40) {
					byte[] bts = StopCMD.setHostData();
					ctx.writeAndFlush(Unpooled.copiedBuffer(bts));
					Log.e("9", "回应有数据：" + SerialPortService.bytesToHexString(bts));
				}
				break;
			case 0x35:
				int hostId = 0;
				for (int i = 3; i < 9; i++) {
					hostId *= 10;
					hostId += (bt[i] - 0x30);
				}
				TimerUtil.hostID = hostId; // 设置主机号ym
				Log.e("6", "设置主机号为：" + hostId);
				setStartTimer(); // 定时
				break;
			case 0x5A: // 定检时间
				// SerialPortService.SendWirte(bt);
				SocketUtils.context.sendHandler(bt);
				Log.e("6", "定检时间  " + SerialPortService.bytesToHexString(bt));
				try {
					Thread.sleep(1000);
					byte[] btreat = StopCMD.setHostOneData(); // 发送0x35
					ByteBuf bts = ctx.alloc().buffer().writeBytes(btreat);
					ctx.writeAndFlush(bts);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case 0x60:// 重新运行;
				// SerialPortService.SendWirte(bt);
				SocketUtils.context.sendHandler(bt);
				Log.e("6", "重新运行  " + SerialPortService.bytesToHexString(bt));
				try {
					Thread.sleep(1000);
					byte[] btreat = StopCMD.setHostOneData(); // 发送0x35
					ByteBuf bts = ctx.alloc().buffer().writeBytes(btreat);
					ctx.writeAndFlush(bts);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case 0x5C: // 同步参数
				// SerialPortService.SendWirte(bt);
				SocketUtils.context.sendHandler(bt);
				Log.e("6", "同步参数  " + SerialPortService.bytesToHexString(bt));
				try {
					Thread.sleep(1000);
					byte[] btreat = StopCMD.setHostOneData(); // 发送0x35
					ByteBuf bts = ctx.alloc().buffer().writeBytes(btreat);
					ctx.writeAndFlush(bts);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case 0x26: // 仪器状态
				// SerialPortService.SendWirte(bt);
				SocketUtils.context.sendHandler(bt);
				Log.e("6", "仪器状态  " + SerialPortService.bytesToHexString(bt));
				break;
			case 0x5D:
				// SerialPortService.SendWirte(bt);
				SocketUtils.context.sendHandler(bt);
				Log.e("6", "位置信息：  " + SerialPortService.bytesToHexString(bt));
				break;
			case 0x25:
				is25updata = true;
				SocketUtils.context.sendHandler(bt);
				if (bt[30] == 0x64) {
					time = 30;
					is25updata = false;
				}
				Log.e("6", "从机更新状态百分比：  " + SerialPortService.bytesToHexString(bt));
				break;
			}
		} else {
			if (bt[0] == 0x7E) {
				int decods = 0;
				byte[] ProBt = new byte[bt.length]; // 解析数据
				byte[] decode = new byte[8];// 解码标志
				System.arraycopy(bt, 40, ProBt, 0, bt.length - 40);
				System.arraycopy(bt, 30, decode, 0, 8);// 29
				PrcloModel prclo = new PrcloModel();
				for (int i = 0; i < 8; i++) {
					decods *= 10;
					decods += decode[i] - 0x30;
				}
//				Log.e("4", "从机号：" + bt[42]);
				if (prclo.addByte(ProBt) != null) {
					if (bmsdecode != decods || salve != bt[42]) {
						bmsdecode = decods;
						salve = bt[42];
						SocketUtils.context.setHandler(1, prclo, ProBt, ReadDataHost.this);
						// Log.e("10", " 读取成功，解析完成3" +
						// SerialPortService.bytesToHexString(bt));
						boolean isout;
						try {
							isout = ReadData.out(bt);
							if (isout) {
								byte[] upBt = UpLoad.setHostData(salve);
								SocketUtils.context.sendHandler(upBt);
							}
//							Log.e("10",
//									" 数据删除成功 " + SerialPortService.bytesToHexString(StopCMD.setDelHostData(decode)));
							ReadDataHost.ctx.writeAndFlush(Unpooled.copiedBuffer(StopCMD.setDelHostData(decode)));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					} else {
						ReadDataHost.ctx.writeAndFlush(Unpooled.copiedBuffer(StopCMD.setDelHostData(decode)));
					}
				}
			}
		}
	}

	/**
	 * 定时启动
	 */
	public void setStartTimer() {
		if (timecnt == 0 || ConcentratorActivity.timerStatus) {
			timecnt = 1;
			TimerUtil.pool.scheduleAtFixedRate(task, TimerUtil.time, TimerUtil.time, TimeUnit.MINUTES);
		}
//		Log.e("2", "定时器状态：" + TimerUtil.pool.isShutdown() + "   " + TimerUtil.pool.isTerminated());
	}

	/**
	 * 注销定时
	 */
	public void setTimerDestroy() {
		if (task != null) {
			task.cancel();
		}
	}
}
