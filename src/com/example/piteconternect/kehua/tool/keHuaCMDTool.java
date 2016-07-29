package com.example.piteconternect.kehua.tool;

import java.util.Calendar;

import com.example.pite.tool.UpLoad;
import com.example.piteconternect.utils.TimerUtil;

import android.text.format.Time;
import android.util.Log;

public class keHuaCMDTool {
	private static int year;
	private static int month;
	private static int date;
	private static int hour;
	private static int minute;
	private static int soceds;

	/***
	 * 测试发送
	 * 
	 * @return
	 */
	public static byte[] getkehuaDatas() {
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(calendar.YEAR) - 2000;
		month = calendar.get(calendar.MONTH) + 1;
		date = calendar.get(calendar.DAY_OF_MONTH);
		hour = calendar.get(calendar.HOUR_OF_DAY);
		minute = calendar.get(calendar.MINUTE);
		soceds = calendar.get(calendar.SECOND);
		// 设备组号(集中器编号)
		byte[] bts = new byte[114];
		byte[] btId = shortToBytes((short) TimerUtil.hostID);
		System.arraycopy(btId, 0, bts, 0, 2);
		// 设备组序号号 固定为0xffff 表明是扩展非本公司的数据
		byte[] btNum = shortToBytes((short)7);
		System.arraycopy(btNum, 0, bts, 2, 2);
		// 数据结构版本 8.10 "VER:8.10"
		byte[] btSoftVer = setVersion();
		System.arraycopy(btSoftVer, 0, bts, 4, 8);
		
		// 数据测量时间
		byte[] btObservationTime = new byte[8];
		btObservationTime[0] = (byte)year;
		btObservationTime[1] = (byte) setTime(month);
		btObservationTime[2] = (byte) setTime(date);
		btObservationTime[3] = (byte) setTime(hour);
		btObservationTime[4] = (byte) setTime(minute);
		btObservationTime[5] = (byte) setTime(soceds);
		btObservationTime[6] = 0;
		btObservationTime[7] = 0;
		System.arraycopy(btObservationTime, 0, bts, 12, 8);
		// 环境温度
		byte[] btTemperature = new byte[2];
		System.arraycopy(btTemperature, 0, bts, 20, 2);
		// 存储电池组数
		byte[] btGroupCount = new byte[2];
		System.arraycopy(btGroupCount, 0, bts, 22, 2);
		// 每组电池节数
		byte[] BTCountOfGroup = new byte[32];
		System.arraycopy(BTCountOfGroup, 0, bts, 24, 32);
		// 数据类型
		byte[] DataTypes = new byte[32];
		System.arraycopy(DataTypes, 0, bts, 56, 32);
		byte[] Datas = new byte[20];
		System.arraycopy(Datas, 0, bts, 88, 20);
		// 公司编号 0:科华 1:普禄科
		bts[110] = 1;
		bts[111] = 0;
		// 设备类型 依据公司协议具体确定
		byte[] EquipType = new byte[2];
		System.arraycopy(EquipType, 0, bts, 110, 2);
		// 对应设备地址 依据公司协议具体确定
		bts[112] = 1;
		bts[113] = 0;
		return bts;
	}

	/**
	 * 获取包头信息
	 * 
	 * @param paklen
	 *            总数据长度
	 * @param length
	 *            本包数据长度
	 * @param num
	 *            本包序号
	 * @return
	 */

	public static byte[] getHeader(int paklen, int length, int num) {
		byte[] bt = new byte[40];
		byte[] sendbt = UpLoad.cofingID();
		System.arraycopy(sendbt, 0, bt, 0, 3);
		byte[] idbt = UpLoad.zeroBt(String.valueOf(TimerUtil.hostID), 6);
		System.arraycopy(idbt, 0, bt, 3, 6);
		bt[9] = (byte) 0x39;
		bt[10] = (byte) 0x6F;
		byte[] paklenbt = UpLoad.zeroBt(String.valueOf(paklen), 7);
		System.arraycopy(paklenbt, 0, bt, 11, 7);
		byte[] sendDT = UpLoad.cofingDT();
		System.arraycopy(sendDT, 0, bt, 18, 3);
		byte[] lengBt = UpLoad.zeroBt(String.valueOf(length), 4);
		System.arraycopy(lengBt, 0, bt, 21, 4);
		byte[] numBt = UpLoad.zeroBt(String.valueOf(num), 4);
		System.arraycopy(numBt, 0, bt, 25, 4);
		bt[29] = 0x42;
		byte[] delFlag = new byte[] { 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0, 0 };
		System.arraycopy(delFlag, 0, bt, 30, delFlag.length);
		return bt;
	}

	/**
	 * 测试发送
	 * 
	 * @return
	 */
	public static byte[] setTest() {
		byte[] bt = new byte[8];
		bt[0] = 0x01;
		bt[1] = 0x04;
		bt[2] = 0x11;
		bt[3] = (byte) 0x95;
		bt[4] = 0;
		bt[5] = 0x15;
		byte[] crcbt = CRC16_Check(bt, 6);
		/*
		 * try { Log.e("6", "CRC校验： " +
		 * SerialPortService.bytesToHexString(CRC16_Check(bt, 6))); } catch
		 * (Exception e) { e.printStackTrace(); }
		 */
		bt[6] = crcbt[3];
		bt[7] = crcbt[2];
		return bt;
	}

	public static int setTime(int s) {
		StringBuffer sb = new StringBuffer();
		if (s < 10) {
			sb.append("0").append(s);
			return Integer.parseInt(new String(sb));
		}else{
			return s ;
		}
	}

	/**
	 * 取数据 0x01 0x04
	 * 
	 * @param len
	 * @return
	 */
	public static byte[] getKeHuaData(short num, byte length) {
		byte[] bt = new byte[8];
		bt[0] = 0x01;
		bt[1] = 0x04;
		byte[] startBt = short2Byte(num);
		System.arraycopy(startBt, 0, bt, 2, 2);
		bt[4] = 0;
		bt[5] = length; // 取的长度
		byte[] crcbt = CRC16_Check(bt, 6);
		bt[6] = crcbt[3];
		bt[7] = crcbt[2];
		return bt;
	}

	/**
	 * 取数据 0x01 0x02
	 * 
	 * @param len
	 * @return
	 */
	public static byte[] getKeHuaData02(short num, byte length) {
		byte[] bt = new byte[8];
		bt[0] = 0x01;
		bt[1] = 0x02;
		byte[] startBt = short2Byte(num);
		System.arraycopy(startBt, 0, bt, 2, 2);
		bt[4] = 0;
		bt[5] = length; // 取的长度
		byte[] crcbt = CRC16_Check(bt, 6);
		bt[6] = crcbt[3];
		bt[7] = crcbt[2];
		return bt;
	}

	/**
	 * 将short转成byte[2] 大端
	 * 
	 * @param a
	 * @return
	 */
	public static byte[] short2Byte(short a) {
		byte[] b = new byte[2];
		b[0] = (byte) (a >> 8);
		b[1] = (byte) (a);

		return b;
	}

	/**
	 * 将short转成byte[2] 小端
	 * 
	 * @param n
	 * @return
	 */
	public static byte[] shortToBytes(short s) {
		byte[] b = new byte[2];
		b[1] = (byte) (s >> 8);
		b[0] = (byte) (s >> 0);
		return b;
	}

	/*
	 * CRC校验
	 */
	public static byte[] CRC16_Check(byte Pushdata[], int length) {
		int Reg_CRC = 0xffff;
		int temp;
		int i, j;
		for (i = 0; i < length; i++) {
			temp = Pushdata[i];
			if (temp < 0)
				temp += 256;
			temp &= 0xff;
			Reg_CRC ^= temp;

			for (j = 0; j < 8; j++) {
				if ((Reg_CRC & 0x0001) == 0x0001)
					Reg_CRC = (Reg_CRC >> 1) ^ 0xA001;
				else
					Reg_CRC >>= 1;
			}
		}
		return intToByteArray((Reg_CRC & 0xffff));
	}

	/**
	 * // VER:8.10 设置版本号
	 * 
	 * @return
	 */
	public static byte[] setVersion() {
		byte[] bt = new byte[8];
		bt[0] = 0x56;
		bt[1] = 0x45;
		bt[2] = 0x52;
		bt[3] = 0x3A;
		bt[4] = 0x38;
		bt[5] = 0x2E;
		bt[6] = 0x31;
		bt[7] = 0x30;
		return bt;
	}

	/**
	 * int 转 byte[]
	 * 
	 * @param i
	 * @return
	 */
	public static byte[] intToByteArray(int i) {
		byte[] result = new byte[4];
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		return result;
	}

	public static int intByteToInt(byte[] i) {
		return i[3] & 0xFF | (i[2] & 0xFF) << 8 | (i[1] & 0xFF) << 16 | (i[0] & 0xFF) << 24;
	}
}
