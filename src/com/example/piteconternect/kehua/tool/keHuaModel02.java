package com.example.piteconternect.kehua.tool;

import java.util.ArrayList;

import com.example.piteconternect.kehua.tool.keHuaModel.ModelWorkehua;

import android.util.Log;

/**
 * 科华Model 0x02 功能
 */
public class keHuaModel02 {

	public static interface ModelWorkehua02 {
		public void afterWorkkeHua02(byte[] bt);
	}

	public ModelWorkehua02 work = null;

	public static final byte[] HEAD = new byte[] { (byte) 0x01, (byte) 0x02 };
	public static final int LENINDEX = 2;
	private ArrayList<ArrayList<Byte>> revList = new ArrayList<ArrayList<Byte>>();

	public keHuaModel02(ModelWorkehua02 work) {
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
		ArrayList<ArrayList<Byte>> rmlist = new ArrayList<ArrayList<Byte>>();
		for (int i = 0; i < revList.size(); i++) {
			if (revList.get(i).size() <= LENINDEX)
				continue;
			int len = revList.get(i).get(LENINDEX) < 0 ?( revList.get(i).get(LENINDEX) & 0xff)
					: revList.get(i).get(LENINDEX);
			len += (LENINDEX + 3);
			if (revList.get(i).size() < len) {
				continue;
			}
			rmlist.add(revList.get(i));
			work.afterWorkkeHua02(getBytes(revList.get(i), len));
		}
		for (int i = 0; i < rmlist.size(); i++) {
			revList.remove(rmlist.get(i));
		}
	}

	private byte[] getBytes(ArrayList<Byte> bytlist, int n) {
		Log.e("6", "len: " + n);
		byte[] ret = new byte[n];
		Byte[] byttmp = new Byte[bytlist.size()];
		byttmp = bytlist.toArray(byttmp);
		for (int i = 0; i < n; i++) {
			ret[i] = byttmp[i];
		}
		return ret;
	}

}
