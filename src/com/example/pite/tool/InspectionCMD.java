package com.example.pite.tool;

import com.example.piteconternect.utils.TimerUtil;

/**
 * 巡检命令
 */
public class InspectionCMD {
	/**
	 * 
	 * @param len
	 *            长度
	 * @param num
	 *            从机号
	 * @return
	 */
	public static byte[] startInspection(int len, int num) {
		int dateLen = len + 6;
		byte[] bt = new byte[dateLen + 6];
		byte[] send = StopCMD.configsendDatePackageHeadBt(0x39, dateLen);
		System.arraycopy(send, 0, bt, 0, 6);
		bt[6] = (byte) 0x87;
		bt[7] = (byte) num;
		bt[8] = (byte) ((num >> 8) & 0xff);
		bt[9] = (byte) 0xaa;
		bt[10] = 6;
		bt[11] = (byte) TimerUtil.hostID;
		bt[12] = (byte) ((TimerUtil.hostID >> 8) & 0xff);
		bt[13] = 0;
		bt[14] = 0x75;
		bt[15] = 0; // 从机上传地址
		bt[16] = 0;
		bt[bt.length - 1] = (byte) (0x39 + dateLen + bt[6] + bt[7] + bt[8] + bt[9] + bt[10] + bt[11] + bt[12] + bt[13]
				+ bt[14] + bt[15] + bt[16]);
		return bt;
	}

}
