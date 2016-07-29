package com.example.pite.model;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.pite.tool.MyStruct;

import android.util.Log;
import struct.StructException;

/**
 * 解析数据的类
 */
public class PrcloModel {
	private List<Prclo85GroupData> group = new ArrayList<Prclo85GroupData>(); // 组
	private HashMap<Integer, ArrayList<Prclo85GroupData.proGroup>> tmpMap = null;
	private Prclo85Data prclo = null;

	public HashMap<Integer, ArrayList<Prclo85GroupData.proGroup>> addByte(byte[] bt) {
		tmpMap = new HashMap<Integer, ArrayList<Prclo85GroupData.proGroup>>();
		try {
			byte[] header = new byte[116];
			int curIndex = 0;
			System.arraycopy(bt, 0, header, 0, 116);
			prclo = new Prclo85Data();
			MyStruct.uppack(prclo, header, ByteOrder.LITTLE_ENDIAN);
			// Log.e("3", "数据头：" + prclo.EquipID2);
			curIndex += header.length;
			byte[] data = null;
			for (int i = 0; i < prclo.GroupCount; i++) {
				data = new byte[16];
				System.arraycopy(bt, header.length + i * 16, data, 0, data.length);
				curIndex += data.length;
				Prclo85GroupData prcGroup = new Prclo85GroupData();
				MyStruct.uppack(prcGroup, data, ByteOrder.LITTLE_ENDIAN);
				// prcGroup.updateValue();
				group.add(prcGroup);
				// Log.e("3", "数据组：" + prcGroup.groupU);
			}
			int[] start = new int[7];
			start[0] = curIndex;
			for (int i = 1; i < start.length; i++) {
				start[i] = start[i - 1] + prclo.GroupCount * prclo.BTCountOfGroup[0] * 2;
			}

			for (int i = 0; i < prclo.BTCountOfGroup[0]; i++) {
				for (int j = 0; j < prclo.GroupCount; j++) {
					ArrayList<Prclo85GroupData.proGroup> old = null;
					if (tmpMap.containsKey(j)) {
						old = tmpMap.get(j);
					} else {
						old = new ArrayList<Prclo85GroupData.proGroup>();
						tmpMap.put(j, old);
					}
					byte[] oneData = new byte[13];
					System.arraycopy(bt, start[0] + i * 2, oneData, 0, 2);
					System.arraycopy(bt, start[1] + i * 2, oneData, 2, 2);
					System.arraycopy(bt, start[2] + i * 2, oneData, 4, 2);
					System.arraycopy(bt, start[3] + i * 2, oneData, 6, 2);
					System.arraycopy(bt, start[4] + i * 2, oneData, 8, 2);
					System.arraycopy(bt, start[5] + i * 2, oneData, 10, 2);
					System.arraycopy(bt, start[6] + i, oneData, 12, 1);
					curIndex += oneData.length;
					Prclo85GroupData.proGroup pp = new Prclo85GroupData.proGroup();
					MyStruct.uppack(pp, oneData, ByteOrder.LITTLE_ENDIAN);
					// pp.updateValue();
					old.add(pp);
					// Log.e("3", "解析完成：" + pp.batterySOC);

				}
			}
			return tmpMap;
		} catch (Exception e) {
			Log.e("3", "解析异常： " + e);
			return null;
		}
	}

	public List<Prclo85GroupData> getGroup() {
		return group;
	}

	public HashMap<Integer, ArrayList<Prclo85GroupData.proGroup>> getMap() {
		return tmpMap;
	}

	public Prclo85Data getPrcHeader() {
		return prclo;
	}
}
