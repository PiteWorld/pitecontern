package com.example.piteconternect.kehua.tool;

import android.content.Context;
import android.util.SparseArray;

public class kehuaModelbusTool {
	private Context context;

	public kehuaModelbusTool(Context context) {
		this.context = context;
	}

	public static void InputData(SparseArray<byte[]> bt, int adress, int groupCnt) {
		bt.append(adress++, keHuaCMDTool.short2Byte((short) 3923));
		bt.append(adress++, keHuaCMDTool.short2Byte((short) 65));
		bt.append(adress++, keHuaCMDTool.short2Byte((short) 0));
		bt.append(adress++, keHuaCMDTool.short2Byte((short) 0));
		adress = 6500;
		bt.append(adress++, keHuaCMDTool.short2Byte((short) groupCnt));
		for (int i = 0; i < groupCnt; i++) {
			bt.append(adress++, keHuaCMDTool.short2Byte((short) groupCnt));
		}
		adress = 7000;
		bt.append(adress++, keHuaCMDTool.short2Byte((short) 0));
		bt.append(adress++, keHuaCMDTool.short2Byte((short) 1));
		bt.append(adress++, keHuaCMDTool.short2Byte((short) 2));
		bt.append(adress++, keHuaCMDTool.short2Byte((short) 3));
		bt.append(adress++, keHuaCMDTool.short2Byte((short) 4));
		bt.append(adress++, keHuaCMDTool.short2Byte((short) 5));
		bt.append(adress++, keHuaCMDTool.short2Byte((short) 6));
		bt.append(adress++, keHuaCMDTool.short2Byte((short) 7));
		bt.append(adress++, keHuaCMDTool.short2Byte((short) 8));
		bt.append(adress++, keHuaCMDTool.short2Byte((short) 9));
		bt.append(adress++, keHuaCMDTool.short2Byte((short) 10));
		bt.append(adress++, keHuaCMDTool.short2Byte((short) 11));
		bt.append(adress++, keHuaCMDTool.short2Byte((short) 12));
		bt.append(adress++, keHuaCMDTool.short2Byte((short) 13));
	}

}
