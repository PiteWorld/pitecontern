package com.example.pite.tool;

import java.util.ArrayList;

import android.util.Log;

/**
 * 更新 Model
 */
public class PiteUpModel {
	public static interface ModelUpWork {
		public void afterUpWork(byte[] bt);
	}

	public ModelUpWork work = null;

	public static final byte[] HEAD = new byte[] { (byte) 0x43, (byte) 0x4D, (byte) 0x44, (byte) 0x5F, (byte) 0x31 };
	public static final int LENINDEX = 9;
	private ArrayList<ArrayList<Byte>> revList = new ArrayList<ArrayList<Byte>>();

	public PiteUpModel(ModelUpWork work) {
		this.work = work;
	}

	public void addBytes(byte[] bt) {
		catchHead(bt);
		updateList();
	}

	private void catchHead(byte[] bt) {
		for (int i = 0; i < bt.length; i++) {
			for (ArrayList<Byte> data : revList) {
				data.add(bt[i]);
			}
			if (bt[i] == HEAD[0]) {
				ArrayList<Byte> data = new ArrayList<Byte>();
				data.add(bt[i]);
				revList.add(data);
			}
		}
		ArrayList<ArrayList<Byte>> rmlist = new ArrayList<ArrayList<Byte>>();
		for (int index = 0; index < revList.size(); index++) {
			ArrayList<Byte> data = revList.get(index);
			if (data.size() >= HEAD.length) {
				for (int i = 0; i < HEAD.length; i++) {
					if (data.get(i) != HEAD[i]) {
						rmlist.add(data);
						break;
					}
				}
			}
		}
		for (int i = 0; i < rmlist.size(); i++) {
			revList.remove(rmlist.get(i));
		}
	}

	private void updateList() {
		byte[] btlen = new byte[4];
		ArrayList<ArrayList<Byte>> rmlist = new ArrayList<ArrayList<Byte>>();
		for (int i = 0; i < revList.size(); i++) {
			if (revList.get(i).size() <= LENINDEX)
				continue;
			int len = 0;
			int index = 0 ;
			for (int j = 6; j < 10; j++) {
				btlen[index++] = revList.get(i).get(j);
			}
			len = byte2int(btlen);
//			Log.e("4", "更新数据0x31 len：" + len);
			len += (LENINDEX + 12 + 1+1);
			if (revList.get(i).size() < len) {
				continue;
			}
			rmlist.add(revList.get(i));
			work.afterUpWork(getBytes(revList.get(i), len));
		}
		for (int i = 0; i < rmlist.size(); i++) {
			revList.remove(rmlist.get(i));
		}
	}

	private byte[] getBytes(ArrayList<Byte> bytlist, int n) {
		byte[] ret = new byte[n];
		Byte[] byttmp = new Byte[bytlist.size()];
		byttmp = bytlist.toArray(byttmp);
		for (int i = 0; i < n; i++) {
			ret[i] = byttmp[i];
		}
		return ret;
	}

	public static int byte2int(byte[] res) {
		// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000

		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) | ((res[2] << 24) >>> 8) | (res[3] << 24);
		return targets;
	}
}
