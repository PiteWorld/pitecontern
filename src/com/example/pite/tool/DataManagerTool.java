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
 * ���ڲ���������
 */
public class DataManagerTool {
	/**
	 * ����0x35 hostId ������ 33
	 * 
	 * @param main
	 * @param bt
	 *            ���յ�����
	 * @param len
	 *            �����ܳ���
	 * @param hostId
	 *            ����ID
	 * @param paklen
	 *            ����
	 * @param length
	 * @param num
	 *            �ӻ���
	 * @return
	 */
	public static void CMDIndexof0x35(byte[] bt, int len, int paklen, int length, int num) {
		byte[] sendBt = UpLoad.sendUpLoad(34, paklen, length, num);
		SerialPortService.SendWirte(sendBt);
//		 Log.e("2", "�յ�53���ݣ�" + SerialPortService.bytesToHexString(sendBt));
		// return str;
	}

	/**
	 * �ж� �Ƿ����
	 * 
	 * @param main
	 * @param bt
	 *            ���յ�����
	 * @param len
	 *            �����ܳ���
	 * @param hostId
	 *            ����ID
	 * @param paklen
	 *            ����
	 * @param length
	 * @param num
	 *            �ӻ���
	 * @return
	 */
	public static void isInternet(byte[] bt, int len, int hostID, int paklen, int length, int num) {
		byte[] sendBt = UpLoad.sendUpLoad(len,  paklen, length, num);
		SerialPortService.SendWirte(sendBt);
		// Log.e("2", "������־��" + SerialPortService.bytesToHexString(sendBt));
	}

	/***
	 * �жϽ��������Ƿ���� 0X38 34
	 * 
	 * @param main
	 * @param bt
	 *            ���յ�����
	 * @param len
	 *            �����ܳ���
	 * @param hostId
	 *            ����ID
	 * @param paklen
	 *            ����
	 * @param length
	 * @param num
	 *            �ӻ���
	 * @return
	 */
	public static void setSendCMD0x38(ConcentratorActivity main, byte[] bt, int len, int hostId, int paklen, int length,
			int num) {
		if (ReadData.FileSize()) {
			main.setUpHandler(3);
		} else {
			// byte[] noBt = UpLoad.sendNoData(len, hostId, paklen, length,
			// num);
			// Log.e("2", "0x38������:--- " +
			// SerialPortService.bytesToHexString(noBt));
			// SerialPortService.SendWirte(noBt);
		}
	}

	/**
	 * ��Ӧ�Ƿ�������
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
	 * �ж����ݽ�����ɽ����־
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
	// * �ж����ݽ�����ɽ����־
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
	// targets[0] = (byte) (res & 0xff);// ���λ
	// targets[1] = (byte) ((res >> 8) & 0xff);// �ε�λ
	// targets[2] = (byte) ((res >> 16) & 0xff);// �θ�λ
	// targets[3] = (byte) (res >>> 24);// ���λ,�޷������ơ�
	// return targets;
	// }
}
