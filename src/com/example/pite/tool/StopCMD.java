package com.example.pite.tool;

import java.util.Calendar;

import com.example.piteconternect.kehua.tool.keHuaCMDTool;
import com.example.piteconternect.utils.TimerUtil;

import android.text.format.Time;
import android.util.Log;
import android_serialport_api.SerialPortService;

public class StopCMD {
	public static Time times = new Time();
	public static int year = times.year;
	public static int mouth = times.month + 1;
	public static int date = times.monthDay;
	public static int hour = times.hour;
	public static int minute = times.minute;
	public static int seconds = times.second;
	public static void setUpTimes() {
		times.setToNow();
		year = times.year;
		mouth = times.month + 1;
		date = times.monthDay;
		hour = times.hour;
		minute = times.minute;
		seconds = times.second;
	}

	public StopCMD() {

	}
	/**
	 * 请求发送下一个数据块 0x36
	 */
	public static byte[] getNextPacket(int len) {
		int dateLen = len + 6;
		byte[] bt = new byte[dateLen];
		byte[] send = configsendDatePackageHeadBt(0x36, dateLen);
		System.arraycopy(send, 0, bt, 0, 6);
		bt[6] = 0x30;
		bt[7] = 0x30;
		bt[8] = 0x30;
		bt[9] = 0x30;
		return bt;
	}

	/**
	 * 请求重新发送数据
	 * 
	 * @return
	 */
	public static byte[] setRestDataPacket() {
		return new byte[] { (byte) 0x43, (byte) 0x4d, (byte) 0x44, (byte) 0x5f, (byte) 0x37, (byte) 0x04, (byte) 0x30,
				(byte) 0x30, (byte) 0x30, (byte) 0x30 };
	}

	/**
	 * 通知从机数据接收成功
	 * 
	 * @param len
	 * @param hostip
	 * @param num
	 * @param mark
	 *            数据包标志
	 * @return
	 */
	public static byte[] RecDataSuccess(int len, int num, byte[] mark) {
		int dateLen = len + 6;
		byte[] bt = new byte[dateLen + 6];
		byte[] send = configsendDatePackageHeadBt(0x39, dateLen);
		System.arraycopy(send, 0, bt, 0, 6);
		bt[6] = (byte) 0x87;
		bt[7] = (byte) num;
		bt[8] = (byte) ((num >> 8) & 0xff);
		bt[9] = (byte) 0xaa;
		bt[10] = 8;
		bt[11] = (byte) TimerUtil.hostID;
		bt[12] = (byte) ((TimerUtil.hostID >> 8) & 0xff);
		bt[13] = 0;
		bt[14] = (byte) 0x3F;
		System.arraycopy(mark, 0, bt, 15, mark.length);
		bt[bt.length - 1] = (byte) (0x39 + dateLen + bt[6] + bt[7] + bt[8] + bt[9] + bt[10] + bt[11] + bt[12] + bt[13]
				+ bt[14] + bt[15] + bt[16] + bt[17] + bt[18] + bt[19]);
		return bt;
	}

	/**
	 * 同步主机时间
	 * 
	 * @return
	 */
	public static byte[] setSynTime() {
		Calendar calendar = Calendar.getInstance();
		int year = (calendar.get(calendar.YEAR) - 2000);
		int month = (calendar.get(calendar.MONTH) + 1);
		int date = calendar.get(calendar.DAY_OF_MONTH);
		int hour = calendar.get(calendar.HOUR_OF_DAY);
		int minute = calendar.get(calendar.MINUTE);
		int soceds = calendar.get(calendar.SECOND);
		byte[] bt = new byte[21];
		byte[] send = configsendDatePackageHeadBt(0x39, 0x0e);
		System.arraycopy(send, 0, bt, 0, 6);
		bt[6] = (byte) 0x3B;
		bt[7] = (byte) (year / 10 + 0x30);
		bt[8] = (byte) (year % 10 + 0x30);
		bt[9] = (byte) (month / 10 + 0x30);
		bt[10] = (byte) (month % 10 + 0x30);
		bt[11] = (byte) (date / 10 + 0x30);
		bt[12] = (byte) (date % 10 + 0x30);
		bt[13] = (byte) (hour / 10 + 0x30);
		bt[14] = (byte) (hour % 10 + 0x30);
		bt[15] = (byte) (minute / 10 + 0x30);
		bt[16] = (byte) (minute % 10 + 0x30);
		bt[17] = (byte) (soceds / 10 + 0x30);
		bt[18] = (byte) (soceds % 10 + 0x30);
		int check = 0x3B;
		for (int i = 7; i < bt.length - 2; i++) {
			check += bt[i] - 0x30;
		}
		check %= 100;
		byte[] checkBt = UpLoad.zeroBt(check + "", 2);
		System.arraycopy(checkBt, 0, bt, bt.length - 2, 2);
		return bt;
	}

	public static int setTime(int s) {
		StringBuffer sb = new StringBuffer();
		if (s < 10) {
			sb.append("0").append(s);
			return Integer.parseInt(new String(sb));
		} else {
			return s;
		}
	}

	/**
	 * 
	 * @param len
	 *            数据长度
	 * @param hostip
	 *            本机地址
	 * @param num
	 *            目标地址
	 * @return
	 */
	public static byte[] LookCMD(int len, int num) {
		int dateLen = len + 6;
		byte[] bt = new byte[dateLen + 6];
		byte[] send = configsendDatePackageHeadBt(0x39, dateLen);
		System.arraycopy(send, 0, bt, 0, 6);
		bt[6] = (byte) 0x87;
		bt[7] = (byte) num;
		bt[8] = (byte) ((num >> 8) & 0xff);
		bt[9] = (byte) 0xaa;
		bt[10] = 4;
		bt[11] = (byte) TimerUtil.hostID;
		bt[12] = (byte) ((TimerUtil.hostID >> 8) & 0xff);
		bt[13] = 0;
		bt[14] = (byte) 0x39;
		bt[bt.length - 1] = (byte) (0x39 + dateLen + bt[6] + bt[7] + bt[8] + bt[9] + bt[10] + bt[11] + bt[12] + bt[13]
				+ bt[14]);
		return bt;
	}

	/**
	 * 数据长度
	 * 
	 * @param len
	 *            目标地址
	 * @param num
	 *            本机地址
	 * @param hostip
	 * 
	 * @param year
	 *            年 月 日 时 分
	 * 
	 * @param mouth
	 * @param date
	 * @param hour
	 * @param scodes
	 *            int len, int hostip, int num, int year, int mouth, int date,
	 *            int hour, int minute, int seconds
	 */
	public static byte[] stopShort(int len, int num) {
		setUpTimes();
		return stopCMD(len, num, TimerUtil.hostID, year - 2000, mouth, date, hour, minute, seconds);
	}

	public static byte[] stopCMD(int len, int num, int hostip, int year, int mouth, int date, int hour, int minute,
			int seconds) {
		int dateLen = len + 6;
		byte[] bt = new byte[dateLen + 6];
		byte[] send = configsendDatePackageHeadBt(0x39, dateLen);
		System.arraycopy(send, 0, bt, 0, 6);
		bt[6] = (byte) 0x87;
		bt[7] = (byte) num;
		bt[8] = (byte) ((num >> 8) & 0xff);
		bt[9] = (byte) 0xaa;
		bt[10] = 10;
		bt[11] = (byte) hostip;
		bt[12] = (byte) ((hostip >> 8) & 0xff);
		bt[13] = 0;
		bt[14] = (byte) 0x46;
		bt[15] = (byte) year;
		bt[16] = (byte) mouth;
		bt[17] = (byte) date;
		bt[18] = (byte) hour;
		bt[19] = (byte) minute;
		bt[20] = (byte) seconds;
		bt[bt.length - 1] = (byte) (0x39 + dateLen + bt[6] + bt[7] + bt[8] + bt[9] + bt[10] + bt[11] + bt[12] + bt[13]
				+ bt[14] + bt[15] + bt[16] + bt[17] + bt[18] + bt[19] + bt[20]);
		return bt;

	}

	/**
	 * 开始命令
	 * 
	 * @param len
	 * @param num
	 * @param hostip
	 * @param year
	 * @param mouth
	 * @param date
	 * @param hour
	 * @param minute
	 * @param seconds
	 * @return
	 */
	public static byte[] StartShort(int len, int num) {
		setUpTimes();
		return startCMD(len, num, TimerUtil.hostID, year - 2000, mouth, date, hour, minute, seconds);
	}

	public static byte[] startCMD(int len, int num, int hostip, int year, int mouth, int date, int hour, int minute,
			int seconds) {
		int dateLen = len + 6;
		byte[] bt = new byte[dateLen + 6];
		byte[] send = configsendDatePackageHeadBt(0x39, dateLen);
		System.arraycopy(send, 0, bt, 0, 6);
		bt[6] = (byte) 0x87;
		bt[7] = (byte) num;
		bt[8] = (byte) ((num >> 8) & 0xff);
		bt[9] = (byte) 0xaa;
		bt[10] = 14;
		bt[11] = (byte) hostip;
		bt[12] = (byte) ((hostip >> 8) & 0xff);
		bt[13] = 0;
		bt[14] = (byte) 0x31;
		bt[15] = 0;
		bt[16] = (byte) year;
		bt[17] = (byte) mouth;
		bt[18] = (byte) date;
		bt[19] = (byte) hour;
		bt[20] = (byte) minute;
		bt[21] = (byte) seconds;
		bt[22] = 0;
		bt[23] = 0;
		bt[24] = 0;
		bt[bt.length - 1] = (byte) (0x39 + dateLen + bt[6] + bt[7] + bt[8] + bt[9] + bt[10] + bt[11] + bt[12] + bt[13]
				+ bt[14] + bt[15] + bt[16] + bt[17] + bt[18] + bt[19] + bt[20] + bt[21] + bt[22] + bt[23] + bt[24]);
		return bt;
	}

	/**
	 * 查询命令
	 */
	public static byte[] slaveCMD(int len, int num) {
		int dateLen = len + 6;
		byte[] bt = new byte[dateLen + 6];
		byte[] send = configsendDatePackageHeadBt(0x39, dateLen);
		System.arraycopy(send, 0, bt, 0, 6);
		bt[6] = (byte) 0x87;
		bt[7] = (byte) num;
		bt[8] = (byte) ((num >> 8) & 0xff);
		bt[9] = (byte) 0xaa;
		bt[10] = 4;
		bt[11] = (byte) TimerUtil.hostID;
		bt[12] = (byte) ((TimerUtil.hostID >> 8) & 0xff);
		bt[13] = 0;
		bt[14] = (byte) 0x40;
		bt[bt.length - 1] = (byte) (0x39 + dateLen + bt[6] + bt[7] + bt[8] + bt[9] + bt[10] + bt[11] + bt[12] + bt[13]
				+ bt[14]);
		return bt;
	}

	/**
	 * 向从机要数据 0x30
	 */
	public static byte[] setHostData() {
		return new byte[] { (byte) 0x43, (byte) 0x4D, (byte) 0x44, (byte) 0x5F, (byte) 0x39, (byte) 0x04, (byte) 0x6F,
				(byte) 0x30, (byte) 0x31, (byte) 0x31 };
	}

	/**
	 * 连接上主机第一次发送0x35
	 * 
	 * @return
	 */
	public static byte[] setHostOneData() {
		return new byte[] { (byte) 0x43, (byte) 0x4D, (byte) 0x44, (byte) 0x5F, (byte) 0x35, (byte) 0x0C, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x30,
				(byte) 0x30, (byte) 0x30, (byte) 0x30 };
	}

	/**
	 * 删除主机数据
	 * @return
	 */
	public static byte[] setDelHostData(byte[] decode) {
		byte[] bt = new byte[18];
		byte[] send = configsendDatePackageHeadBt(0x39, 0x0C);
		System.arraycopy(send, 0, bt, 0, 6);
		bt[6] = 0x6F;
		bt[7] = 0x31;
		System.arraycopy(decode, 0, bt, 8, decode.length);
		int check = 0x6F;
		for (int i = 7; i < bt.length - 2; i++) {
			// check += bt[i] < 0 ? bt[i] & 0xff : bt[i];
			check += bt[i] - 0x30;
		}
		check %= 100;
		byte[] checkBt = UpLoad.zeroBt(check + "", 2);
		System.arraycopy(checkBt, 0, bt, bt.length - 2, 2);
		return bt;
	}

	/**
	 * 配置数据包的命令头
	 * 
	 * @param commCode
	 *            命令码
	 * @param dateLen
	 *            命令数据的长度
	 */
	public static byte[] configsendDatePackageHeadBt(int commCode, int dateLen) {
		byte[] bt = new byte[6];
		bt[0] = "C".getBytes()[0];
		bt[1] = "M".getBytes()[0];
		bt[2] = "D".getBytes()[0];
		bt[3] = "_".getBytes()[0];
		bt[4] = (byte) commCode;
		bt[5] = (byte) dateLen;
		return bt;
	}
}
