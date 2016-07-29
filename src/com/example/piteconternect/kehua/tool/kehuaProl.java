package com.example.piteconternect.kehua.tool;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.example.pite.tool.MyStruct;

import android.util.Log;
import android_serialport_api.SerialPortService;
import struct.JavaStruct;
import struct.StructException;

public class kehuaProl {
	public kehuaProData04 kehuaProData04;
	public kehua1234 kehua1234;
	public kehua235 kehua235;
	public kehua569 kehua569;
	public kehua2659 kehua2659;
	public static int kehuaIndex = 0;

	public boolean kehuabytes(byte[] bt) {
		byte[] bt1 = new byte[76];
		System.arraycopy(bt, 0, bt1, 0, bt1.length);
		kehuaProData04 = new kehuaProData04();
		try {
			JavaStruct.unpack(kehuaProData04, bt1, ByteOrder.BIG_ENDIAN);

			byte[] bt2 = new byte[8];
			System.arraycopy(bt, bt1.length, bt2, 0, bt2.length);
			kehua1234 = new kehua1234();
			JavaStruct.unpack(kehua1234, bt2, ByteOrder.BIG_ENDIAN);

			byte[] bt3 = new byte[4];
			System.arraycopy(bt, bt1.length + bt2.length, bt3, 0, bt3.length);
			kehua235 = new kehua235();
			JavaStruct.unpack(kehua235, bt3, ByteOrder.BIG_ENDIAN);
			byte[] bt4 = new byte[30];
			System.arraycopy(bt, bt1.length + bt2.length + bt3.length, bt4, 0, bt4.length);
			kehua569 = new kehua569();
			// MyStruct.uppack(kehua569, bt4, ByteOrder.LITTLE_ENDIAN);
			byte[] bt6 = new byte[30];
			for (int i = 0; i < bt4.length; i++) {
				if (i == 0) {
					bt6[0] = bt4[2];
				}
				if (i == 1) {
					bt6[1] = bt4[3];
				}
				if (i == 2) {
					bt6[2] = bt4[0];
				}
				if (i == 3) {
					bt6[3] = bt4[1];
				}
				if (i == 4) {
					bt6[4] = bt4[6];
				}
				if (i == 5) {
					bt6[5] = bt4[7];
				}
				if (i == 6) {
					bt6[6] = bt4[4];
				}
				if (i == 7) {
					bt6[7] = bt4[5];
				}
				if (i == 8) {
					bt6[8] = bt4[10];
				}
				if (i == 9) {
					bt6[9] = bt4[11];
				}
				if (i == 10) {
					bt6[10] = bt4[8];
				}
				if (i == 11) {
					bt6[11] = bt4[9];
				}
				if (i == 12) {
					bt6[12] = bt4[14];
				}
				if (i == 13) {
					bt6[13] = bt4[15];
				}
				if (i == 14) {
					bt6[14] = bt4[12];
				}
				if (i == 15) {
					bt6[15] = bt4[13];
				}
				if (i == 16) {
					bt6[16] = bt4[18];
				}
				if (i == 17) {
					bt6[17] = bt4[19];
				}
				if (i == 18) {
					bt6[18] = bt4[16];
				}
				if (i == 19) {
					bt6[19] = bt4[17];
				}

			}
			JavaStruct.unpack(kehua569, bt6, ByteOrder.BIG_ENDIAN);
			byte[] bt5 = new byte[12];
			System.arraycopy(bt, bt1.length + bt2.length + bt3.length + bt4.length, bt5, 0, bt5.length);
			kehua2659 = new kehua2659();
			JavaStruct.unpack(kehua2659, bt5, ByteOrder.BIG_ENDIAN);
			return true;
		} catch (StructException e) {
			e.printStackTrace();
			return false;
		}
	}

	public kehuaProData04 getkehuaProData04() {
		return kehuaProData04;
	}

	public kehua1234 getkehua1234() {
		return kehua1234;
	}

	public kehua235 getkehua235() {
		return kehua235;
	}

	public kehua569 getkehua569() {
		return kehua569;
	}

	public kehua2659 getkehua2659() {
		return kehua2659;
	}

}
