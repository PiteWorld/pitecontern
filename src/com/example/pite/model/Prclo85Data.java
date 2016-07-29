package com.example.pite.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import struct.StructClass;
import struct.StructField;

@StructClass
public class Prclo85Data {
	@StructField(order = 0)
	public short EquipID1;
	@StructField(order = 1)
	public short EquipID2;
	@StructField(order = 2)
	public byte[] SoftVer = new byte[8];
	@StructField(order = 3)
	public byte[] ObservationTime = new byte[8];
	@StructField(order = 4)
	public short Temperature;
	@StructField(order = 5)
	public short GroupCount;
	@StructField(order = 6)
	public short[] BTCountOfGroup = new short[16];
	@StructField(order = 7)
	public short[] DataTypes = new short[16];
	@StructField(order = 8)
	public byte[] baoliu = new byte[20];
	@StructField(order = 9)
	public int positive; // 正线
	@StructField(order = 10)
	public int negative; // 负线

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
