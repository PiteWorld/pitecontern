package com.example.piteconternect.utils;

import java.util.Map;

import com.example.piteconternect.ConcentratorActivity;

/**
 * 
 */
public class SocketUtils {
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

	public static ConcentratorActivity context = null;
	public static Map<Integer, byte[]> ReadBt = null; // 0x26����汾
	public static int salveConnect;
	public static int salveWork;

	public static Map<Integer, byte[]> getReadBt() {
		return ReadBt;
	}

	public static void setReadBt(Map<Integer, byte[]> readBt) {
		ReadBt = readBt;
	}

	/**
	 * CRCУ��
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] CRC_XModem(byte[] bytes) {
		int crc = 0x00;
		int polynomial = 0x1021;
		for (int index = 11; index < bytes.length - 5; index++) {
			byte b = bytes[index];
			for (int i = 0; i < 8; i++) {
				boolean bit = ((b >> (7 - i) & 1) == 1);
				boolean c15 = ((crc >> 15 & 1) == 1);
				crc <<= 1;
				if (c15 ^ bit)
					crc ^= polynomial;
			}
		}
		crc &= 0xffff;
		return shortToBytes((short) crc);
	}

	/**
	 * ��shortת��byte[2] С��
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
}
