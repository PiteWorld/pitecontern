package com.example.pite.tool;

import java.util.ArrayList;

import com.example.pite.tool.PiteModel.ModelWork;

import android.util.Log;

public class IDModel {
	public static interface ModelWorkID {
		public void afterWorkID(byte[] bt);
	}
	public ModelWorkID work = null;

	public static final byte[] HEAD = new byte[] { (byte) 0x7E, (byte) 0x49, (byte) 0x44 };
	public static final int LENINDEX = 3;
	private ArrayList<ArrayList<Byte>> revList = new ArrayList<ArrayList<Byte>>();

	public IDModel(ModelWorkID work) {
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
			int len = 18 ;
			if (revList.get(i).size() < len) {
				continue;
			}
			rmlist.add(revList.get(i));
			work.afterWorkID(getBytes(revList.get(i), len));
		}
		for (int i = 0; i < rmlist.size(); i++) {
			revList.remove(rmlist.get(i));
		}
	}

	private byte[] getBytes(ArrayList<Byte> bytlist, int n) {
		Log.e("10", " len ÕýÔÚÌí¼Ó   " + n );
		byte[] ret = new byte[n];
		Byte[] byttmp = new Byte[bytlist.size()];
		byttmp = bytlist.toArray(byttmp);
		for (int i = 0; i < n; i++) {
			ret[i] = byttmp[i];
		}
		return ret;
	}
}
