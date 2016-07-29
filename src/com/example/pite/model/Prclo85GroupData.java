package com.example.pite.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import struct.StructClass;
import struct.StructField;

@StructClass
public class Prclo85GroupData {
	@StructField(order = 0)
	public int groupU;
	@StructField(order = 1)
	public int groupI;
	@StructField(order = 2)
	public int groupT;
	@StructField(order = 3)
	public int groupT2;

	@StructClass
	public static class proGroup {
		@StructField(order = 0)
		public short batteryV;
		@StructField(order = 1)
		public short batteryR1;
		@StructField(order = 2)
		public short batteryR2;
		@StructField(order = 3)
		public short batteryC2;
		@StructField(order = 4)
		public short batterySOC;
		@StructField(order = 5)
		public short batterySOH;
		@StructField(order = 6)
		public byte batteryT;

		public void updateValue() {
			Field[] field = this.getClass().getDeclaredFields();
			for (int i = 0; i < field.length; i++) {
				field[i].setAccessible(true);
				if (field[i].getGenericType().toString().equals("int")) {
					setIntLittleEndian(field[i]);
				} else if (field[i].getGenericType().toString().equals("short")) {
					setShortLittleEndian(field[i]);
				}
			}
		}

		public void setIntLittleEndian(Field field) {
			int s = 0;
			try {
				s = field.getInt(this);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			ByteBuffer buffer = ByteBuffer.allocate(4);
			buffer.asIntBuffer().put(s);
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			try {
				field.setInt(this, buffer.getInt());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		public void setShortLittleEndian(Field field) {
			short s = 0;
			try {
				s = field.getShort(this);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			ByteBuffer buffer = ByteBuffer.allocate(2);
			buffer.asShortBuffer().put(s);
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			try {
				field.setShort(this, buffer.getShort());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateValue() {
		Field[] field = this.getClass().getDeclaredFields();
		for (int i = 0; i < field.length; i++) {
			field[i].setAccessible(true);
			if (field[i].getGenericType().toString().equals("int")) {
				setIntLittleEndian(field[i]);
			} else if (field[i].getGenericType().toString().equals("short")) {
				setShortLittleEndian(field[i]);
			}
		}
	}

	public void setIntLittleEndian(Field field) {
		int s = 0;
		try {
			s = field.getInt(this);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.asIntBuffer().put(s);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		try {
			field.setInt(this, buffer.getInt());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setShortLittleEndian(Field field) {
		short s = 0;
		try {
			s = field.getShort(this);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		ByteBuffer buffer = ByteBuffer.allocate(2);
		buffer.asShortBuffer().put(s);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		try {
			field.setShort(this, buffer.getShort());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
