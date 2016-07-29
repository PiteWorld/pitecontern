package com.example.pite.tool;

import com.example.piteconternect.utils.TimerUtil;

import android.util.Log;
import android_serialport_api.SerialPortService;

public class UpLoad {
	/**
	 * 服务器同步时间回应
	 */
	public static byte[] setRecDataTime(){
		byte[] bt = new byte[33] ;
		byte[] sendbt = cofingID();
		System.arraycopy(sendbt, 0, bt, 0, 3);
		byte[] idbt = zeroBt(String.valueOf(TimerUtil.hostID), 6);
		System.arraycopy(idbt, 0, bt, 3, 6);
		bt[9] = (byte) 0x3B;
		bt[10] = (byte) 0x3B;
		byte[] paklenbt = zeroBt(String.valueOf(1), 7);
		System.arraycopy(paklenbt, 0, bt, 11, 7);
		byte[] sendDT = cofingDT();
		System.arraycopy(sendDT, 0, bt, 18, 3);
		byte[] lengBt = zeroBt(String.valueOf(1), 4);
		System.arraycopy(lengBt, 0, bt, 21, 4);
		byte[] numBt = zeroBt(String.valueOf(1), 4);
		System.arraycopy(numBt, 0, bt, 25, 4);
		bt[29] = (byte) 0x3B;
		int check = 0;
		for (int i = 29; i < bt.length; i++) {
			check += bt[i] < 0 ? bt[i] & 0xff : bt[i];
		}
		check %= 256;
		byte[] checkBt = zeroBt(check + "", 3);
		System.arraycopy(checkBt, 0, bt, 30, 3);
	return bt ;
	}
	/**
	 * 返回的~DT包头
	 * 
	 * @param paklen
	 * @param packenum
	 * @return
	 */
	public static byte[] setHeader(int paklen, int packenum) {
		byte[] bt = new byte[11];
		byte[] sendDT = cofingDT();
		System.arraycopy(sendDT, 0, bt, 0, 3);
		byte[] lengBt = zeroBt(String.valueOf(paklen), 4);
		System.arraycopy(lengBt, 0, bt, 3, 4);
		byte[] numBt = zeroBt(String.valueOf(packenum), 4);
		System.arraycopy(numBt, 0, bt, 7, 4);
		return bt;
	}

	/**
	 * 回应0x26查询从机状态
	 */
	public static byte[] setSalveStatus(int len, int paklen, int length, int num, int salveNum, int concentStatus,
			int workStatus, byte[] ARM) {
		byte[] bt = new byte[len];
		byte[] sendbt = cofingID();
		System.arraycopy(sendbt, 0, bt, 0, 3);
		byte[] idbt = zeroBt(String.valueOf(TimerUtil.hostID), 6);
		System.arraycopy(idbt, 0, bt, 3, 6);
		bt[9] = (byte) 0x39;
		bt[10] = (byte) 0x26;
		byte[] paklenbt = zeroBt(String.valueOf(paklen), 7);
		System.arraycopy(paklenbt, 0, bt, 11, 7);
		byte[] sendDT = cofingDT();
		System.arraycopy(sendDT, 0, bt, 18, 3);
		byte[] lengBt = zeroBt(String.valueOf(length), 4);
		System.arraycopy(lengBt, 0, bt, 21, 4);
		byte[] numBt = zeroBt(String.valueOf(num), 4);
		System.arraycopy(numBt, 0, bt, 25, 4);
		bt[29] = (byte) salveNum;
		bt[30] = (byte) concentStatus;
		bt[31] = (byte) workStatus;
		System.arraycopy(ARM, 0, bt, 32, ARM.length); // 8
		int check = 0;
		for (int i = 29; i < bt.length; i++) {
			check += bt[i] < 0 ? bt[i] & 0xff : bt[i];
		}
		check %= 256;
		byte[] checkBt = zeroBt(check + "", 3);
		System.arraycopy(checkBt, 0, bt, 40, 3);
		return bt;
	}

	/**
	 * 回应同步参数 33
	 */
	public static byte[] setSyn(int len, int id, int paklen, int length, int num, int synstatus) {
		int datelen = len;
		byte[] bt = new byte[len];
		byte[] sendbt = cofingID();
		System.arraycopy(sendbt, 0, bt, 0, 3);
		byte[] idbt = zeroBt(String.valueOf(id), 6);
		System.arraycopy(idbt, 0, bt, 3, 6);
		bt[9] = (byte) 0x39;
		bt[10] = (byte) 0x5C;
		byte[] paklenbt = zeroBt(String.valueOf(paklen), 7);
		System.arraycopy(paklenbt, 0, bt, 11, 7);
		byte[] sendDT = cofingDT();
		System.arraycopy(sendDT, 0, bt, 18, 3);
		byte[] lengBt = zeroBt(String.valueOf(length), 4);
		System.arraycopy(lengBt, 0, bt, 21, 4);
		byte[] numBt = zeroBt(String.valueOf(num), 4);
		System.arraycopy(numBt, 0, bt, 25, 4);
		bt[29] = (byte) synstatus;
		int check = 0;
		for (int i = 29; i < bt.length; i++) {
			check += bt[i] < 0 ? bt[i] & 0xff : bt[i];
		}
		check %= 256;
		byte[] checkBt = zeroBt(check + "", 3);
		System.arraycopy(checkBt, 0, bt, 30, 3);
		return bt;
	}

	/**
	 * 定检时间回应 33
	 */
	public static byte[] setUpStart(int len, int id, int paklen, int length, int num) {
		int datelen = len;
		byte[] bt = new byte[len];
		byte[] sendbt = cofingID();
		System.arraycopy(sendbt, 0, bt, 0, 3);
		byte[] idbt = zeroBt(String.valueOf(id), 6);
		System.arraycopy(idbt, 0, bt, 3, 6);
		bt[9] = (byte) 0x39;
		bt[10] = (byte) 0x5A;
		byte[] paklenbt = zeroBt(String.valueOf(paklen), 7);
		System.arraycopy(paklenbt, 0, bt, 11, 7);
		byte[] sendDT = cofingDT();
		System.arraycopy(sendDT, 0, bt, 18, 3);
		byte[] lengBt = zeroBt(String.valueOf(length), 4);
		System.arraycopy(lengBt, 0, bt, 21, 4);
		byte[] numBt = zeroBt(String.valueOf(num), 4);
		System.arraycopy(numBt, 0, bt, 25, 4);
		bt[29] = 0x5A;
		int check = 0;
		for (int i = 29; i < bt.length; i++) {
			check += bt[i] < 0 ? bt[i] & 0xff : bt[i];
		}
		check %= 256;
		byte[] checkBt = zeroBt(check + "", 3);
		System.arraycopy(checkBt, 0, bt, 30, 3);
		return bt;
	}

	/**
	 * 设备重启 34
	 */
	public static byte[] sendResrt(int len, int id, int paklen, int length, int num) {
		int datelen = len;
		byte[] bt = new byte[len];
		byte[] sendbt = cofingID();
		System.arraycopy(sendbt, 0, bt, 0, 3);
		byte[] idbt = zeroBt(String.valueOf(id), 6);
		System.arraycopy(idbt, 0, bt, 3, 6);
		bt[9] = (byte) 0x39;
		bt[10] = (byte) 0x60;
		byte[] paklenbt = zeroBt(String.valueOf(paklen), 7);
		System.arraycopy(paklenbt, 0, bt, 11, 7);
		byte[] sendDT = cofingDT();
		System.arraycopy(sendDT, 0, bt, 18, 3);
		byte[] lengBt = zeroBt(String.valueOf(length), 4);
		System.arraycopy(lengBt, 0, bt, 21, 4);
		byte[] numBt = zeroBt(String.valueOf(num), 4);
		System.arraycopy(numBt, 0, bt, 25, 4);
		bt[29] = (byte) TimerUtil.hostID;
		bt[30] = 0;
		int check = 0;
		for (int i = 29; i < bt.length; i++) {
			check += bt[i] < 0 ? bt[i] & 0xff : bt[i];
		}
		check %= 256;
		byte[] checkBt = zeroBt(check + "", 3);
		System.arraycopy(checkBt, 0, bt, 31, 3);
		return bt;
	}

	/**
	 * 回应服务器没有数据
	 * 
	 * @param len
	 * @param id
	 * @param paklen
	 * @param length
	 * @param num
	 * @return
	 */
	public static byte[] sendNoData(int len, int id, int paklen, int length, int num) {
		int datelen = len;
		byte[] bt = new byte[len];
		byte[] sendbt = cofingID();
		System.arraycopy(sendbt, 0, bt, 0, 3);
		byte[] idbt = zeroBt(String.valueOf(id), 6);
		System.arraycopy(idbt, 0, bt, 3, 6);
		bt[9] = (byte) 0x39;
		bt[10] = (byte) 0x6F;
		byte[] paklenbt = zeroBt(String.valueOf(paklen), 7);
		System.arraycopy(paklenbt, 0, bt, 11, 7);
		byte[] sendDT = cofingDT();
		System.arraycopy(sendDT, 0, bt, 18, 3);
		byte[] lengBt = zeroBt(String.valueOf(length), 4);
		System.arraycopy(lengBt, 0, bt, 21, 4);
		byte[] numBt = zeroBt(String.valueOf(num), 4);
		System.arraycopy(numBt, 0, bt, 25, 4);
		bt[29] = (byte) 0x41;
		bt[30] = 0x30;
		bt[31] = 0x36;
		bt[32] = 0x35;
		// int check = 0;
		// for (int i = 18; i < bt.length; i++) {
		// check += bt[i] < 0 ? bt[i] & 0xff : bt[i];
		// }
		// check %= 256;
		// // bt[30] = (byte) check;
		// byte[] checkBt = zeroBt(check + "", 3);
		// System.arraycopy(checkBt, 0, bt, 30, 3);
		return bt;
	}

	/**
	 * 发送服务器通知有数据
	 * 
	 * @param len
	 * @param id
	 * @param paklen
	 * @param length
	 * @param num
	 * @return
	 */
	public static byte[] sendUpLoad(int len, int paklen, int length, int num) {
		int datelen = len;
		byte[] bt = new byte[len];
		byte[] sendbt = cofingID();
		System.arraycopy(sendbt, 0, bt, 0, 3);
		byte[] idbt = zeroBt(String.valueOf(TimerUtil.hostID), 6);
		System.arraycopy(idbt, 0, bt, 3, 6);
		bt[9] = 0x35;
		bt[10] = 0x35;
		byte[] paklenbt = zeroBt(String.valueOf(paklen), 7);
		System.arraycopy(paklenbt, 0, bt, 11, 7);
		byte[] sendDT = cofingDT();
		System.arraycopy(sendDT, 0, bt, 18, 3);
		byte[] lengBt = zeroBt(String.valueOf(length), 4);
		System.arraycopy(lengBt, 0, bt, 21, 4);
		byte[] numBt = zeroBt(String.valueOf(num), 4);
		System.arraycopy(numBt, 0, bt, 25, 4);
		int check = 0;
		bt[29] = 0;
		for (int i = 29; i < bt.length; i++) {
			check += bt[i] < 0 ? bt[i] & 0xff : bt[i];
		}
		check %= 256;
		byte[] checkBt = zeroBt(check + "", 3);
		System.arraycopy(checkBt, 0, bt, 30, 3);
		return bt;
	}

	/**
	 * 主动通知服务器有数据
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static byte[] setHostData(int num) {
		byte[] bt = new byte[35];
		byte[] sendbt = cofingID();
		System.arraycopy(sendbt, 0, bt, 0, 3);
		byte[] idbt = zeroBt(String.valueOf(TimerUtil.hostID), 6);
		System.arraycopy(idbt, 0, bt, 3, 6);
		bt[9] = 0x39;
		bt[10] = 0x6F;
		byte[] paklenbt = zeroBt(String.valueOf(3), 7);
		System.arraycopy(paklenbt, 0, bt, 11, 7);
		byte[] sendDT = cofingDT();
		System.arraycopy(sendDT, 0, bt, 18, 3);
		byte[] lengBt = zeroBt(String.valueOf(3), 4);
		System.arraycopy(lengBt, 0, bt, 21, 4);
		byte[] numBt = zeroBt(String.valueOf(1), 4);
		System.arraycopy(numBt, 0, bt, 25, 4);
		bt[29] = 0x40;
		bt[30] = (byte) num;
		bt[31] = 0;
		int check = 0;
		for (int i = 29; i < bt.length; i++) {
			check += bt[i] < 0 ? bt[i] & 0xff : bt[i];
		}
		check %= 256;
		byte[] checkBt = zeroBt(check + "", 3);
		System.arraycopy(checkBt, 0, bt, 32, 3);
		return bt;
	}

	public static byte[] zeroBt(String str, int length) {
		int strlen = str.length();
		// Log.e("2", "str.length " + strlen);
		if (strlen < length) {
			while (strlen < length) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);
				str = sb.toString();
				strlen = str.length();
			}
		}
		return str.getBytes();
	}

	public static byte[] cofingDT() {
		byte[] bt = new byte[3];
		bt[0] = "~".getBytes()[0];
		bt[1] = "D".getBytes()[0];
		bt[2] = "T".getBytes()[0];
		return bt;
	}

	public static byte[] cofingID() {
		byte[] bt = new byte[3];
		bt[0] = "~".getBytes()[0];
		bt[1] = "I".getBytes()[0];
		bt[2] = "D".getBytes()[0];
		// bt[3] = (byte) commCode;
		// bt[4] = (byte) dateLen;
		return bt;
	}
}
