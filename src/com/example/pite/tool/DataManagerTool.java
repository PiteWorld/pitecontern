package com.example.pite.tool;

import java.io.IOException;
import java.io.OutputStream;

import com.example.piteconternect.ConcentratorActivity;
import com.example.piteconternect.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android_serialport_api.SerialPortService;

/**
 * 串口操作工具类
 */
public class DataManagerTool {
	/**
	 * 设置0x35 hostId 主机号 33
	 * 
	 * @param main
	 * @param bt
	 *            接收的数据
	 * @param len
	 *            数据总长度
	 * @param hostId
	 *            主机ID
	 * @param paklen
	 *            包长
	 * @param length
	 * @param num
	 *            从机号
	 * @return
	 */
	public static void CMDIndexof0x35(byte[] bt, int len, int paklen, int length, int num) {
		byte[] sendBt = UpLoad.sendUpLoad(34, paklen, length, num);
		SerialPortService.SendWirte(sendBt);
//		 Log.e("2", "收到53数据：" + SerialPortService.bytesToHexString(sendBt));
		// return str;
	}

	/**
	 * 判断 是否断网
	 * 
	 * @param main
	 * @param bt
	 *            接收的数据
	 * @param len
	 *            数据总长度
	 * @param hostId
	 *            主机ID
	 * @param paklen
	 *            包长
	 * @param length
	 * @param num
	 *            从机号
	 * @return
	 */
	public static void isInternet(byte[] bt, int len, int hostID, int paklen, int length, int num) {
		byte[] sendBt = UpLoad.sendUpLoad(len,  paklen, length, num);
		SerialPortService.SendWirte(sendBt);
		// Log.e("2", "断网标志：" + SerialPortService.bytesToHexString(sendBt));
	}

	/***
	 * 判断接收数据是否完成 0X38 34
	 * 
	 * @param main
	 * @param bt
	 *            接收的数据
	 * @param len
	 *            数据总长度
	 * @param hostId
	 *            主机ID
	 * @param paklen
	 *            包长
	 * @param length
	 * @param num
	 *            从机号
	 * @return
	 */
	public static void setSendCMD0x38(ConcentratorActivity main, byte[] bt, int len, int hostId, int paklen, int length,
			int num) {
		if (ReadData.FileSize()) {
			main.setUpHandler(3);
		} else {
			// byte[] noBt = UpLoad.sendNoData(len, hostId, paklen, length,
			// num);
			// Log.e("2", "0x38无数据:--- " +
			// SerialPortService.bytesToHexString(noBt));
			// SerialPortService.SendWirte(noBt);
		}
	}

	/**
	 * 回应是否有数据
	 * 
	 * @param bt
	 * @return
	 */
	public static boolean indexofFileData(byte[] bt) {
		if (bt.length < 10) {
			return false;
		}
		if (bt[9] == (byte) 0x6F && bt[10] == (byte) 48) {
			return true;
			// }
		}
		return false;
	}

	/**
	 * 判断数据接收完成解码标志
	 */
	public static int DecodFlag(byte[] bt) {
		byte[] proBt = new byte[8];
		int decod = 0;
		System.arraycopy(bt, 8, proBt, 0, 8);
		for (int i = 0; i < 8; i++) {
			decod *= 10;
			decod += proBt[i] - 0x30;
		}
		return decod;
	}

	// /**
	// * 判断数据接收完成解码标志
	// */
	// public static byte[] setDecodFlag(byte[] bt) {
	// byte[] proBt = new byte[8];
	// int decod = 0;
	// System.arraycopy(bt, 8, proBt, 0, 8);
	// for (int i = 0; i < 8; i++) {
	// decod *= 10;
	// decod += proBt[i] - 0x30;
	// }
	// return int2byte(decod);
	// }
	//
	// public static byte[] int2byte(int res) {
	// byte[] targets = new byte[4];
	// targets[0] = (byte) (res & 0xff);// 最低位
	// targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
	// targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
	// targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
	// return targets;
	// }
}
