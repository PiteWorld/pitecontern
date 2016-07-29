package com.example.pite.tool;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.example.piteconternect.kehua.tool.kehuaProl;

import android.util.Log;
import struct.JavaStruct;
import struct.StructClass;
import struct.StructException;
import struct.StructField;

public class MyStruct extends JavaStruct {

	private static ByteOrder turnOrder = ByteOrder.BIG_ENDIAN;

	public MyStruct() {

	}

	public static void uppack(Object arg0, byte[] arg1, ByteOrder arg2) {
		try {
			unpack(arg0, arg1, arg2);
		} catch (StructException e) {
			e.printStackTrace();
		}
		turnOrder = arg2;
		if (turnOrder == ByteOrder.BIG_ENDIAN)
			return;
		if (!arg0.getClass().isAnnotationPresent(StructClass.class))
			return;
		Field[] fields = arg0.getClass().getDeclaredFields();
		for (Field f : fields) {
			Annotation[] as = f.getAnnotations();
			for (Annotation a : as) {
				if (a.annotationType().isAssignableFrom(StructField.class)) {
					try {
						distributeFiles(arg0, f);
					} catch (IllegalArgumentException e) {
						Log.e("2", "解析里面异常：" + e);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void distributeFiles(Object obj, Field f) throws IllegalArgumentException, IllegalAccessException {
		String type = f.getType().toString();
		if (type.toLowerCase().contains("b"))
			return;
		f.setAccessible(true);
		if (type.contains("short")) {
			setShort(obj, f);
		} else if (type.contains("[S")) {
			short[] tmp = (short[]) f.get(obj);
			for (int i = 0; i < tmp.length; i++) {
				tmp[i] = getShort(tmp[i], turnOrder);
			}
		} else if (type.contains("int")) {
			setInt(obj, f);
		} else if (type.contains("[I")) {
			int[] tmp = (int[]) f.get(obj);
			for (int i = 0; i < tmp.length; i++) {
				tmp[i] = getInt(tmp[i], turnOrder);
			}
		} else if (type.contains("float")) {
			setFloat(obj, f);
		} else if (type.contains("[F")) {
			float[] tmp = (float[]) f.get(obj);
			for (int i = 0; i < tmp.length; i++) {
				tmp[i] = getFloat(tmp[i], turnOrder);
			}
		}
	}

	private static void setInt(Object obj, Field field) {
		int s = 0;
		try {
			s = field.getInt(obj);
			field.setInt(obj, getInt(s, turnOrder));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private static void setShort(Object obj, Field field) {
		short s = 0;
		try {
			s = field.getShort(obj);
			field.setShort(obj, getShort(s, turnOrder));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private static void setFloat(Object obj, Field field) {
		float s = 0;
		try {
			s = field.getFloat(obj);
			field.setFloat(obj, getFloat(s, turnOrder));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private static short getShort(short s, ByteOrder od) {
		ByteBuffer buffer = ByteBuffer.allocate(2);
		buffer.asShortBuffer().put(s);
		buffer.order(od);
		return buffer.getShort();
	}

	private static int getInt(int s, ByteOrder od) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.asIntBuffer().put(s);
		buffer.order(od);
		return buffer.getInt();
	}

	private static int getIntD(int s, ByteOrder od) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.asIntBuffer().put(s);
		buffer.order(ByteOrder.BIG_ENDIAN);
		return buffer.getInt();
	}

	private static float getFloat(float s, ByteOrder od) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.asFloatBuffer().put(s);
		buffer.order(od);
		return buffer.getFloat();
	}

}
