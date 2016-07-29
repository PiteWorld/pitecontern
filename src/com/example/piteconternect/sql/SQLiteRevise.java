package com.example.piteconternect.sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.piteconternect.utils.TimerUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

//���ݿ���ɾ�Ĳ�ķ���
public class SQLiteRevise {
	private MySQLite mysqlite;
	private SQLiteDatabase db;
	// ����һ�����������洢����
	public List<Information> list;
	private Information information;

	public SQLiteRevise(Context context) {
		// ������
		mysqlite = MySQLite.getInstance(context);
		list = new ArrayList<Information>();
	}

	/**
	 * ������ݵķ���
	 */
	public synchronized long add(String preson, String time, List<String[]> data) {
		// �õ���д��SQLdataBase����
		long id = -1;
		db = mysqlite.getWritableDatabase();
		db.beginTransaction();
		// �����Ϣ
		ContentValues values = new ContentValues();
		values.put("time", time);
		for (String[] str : data) {
			values.put("num", str[0]);
			values.put("V", str[1]);
			values.put("I", str[2]);
			values.put("U", str[3]);
			values.put("R1", str[4]);
			values.put("T", str[5]);
			values.put("C", str[6]);
			values.put("R2", str[7]);
			values.put("C2", str[8]);
			// listvalues.add(values);
			id = db.insert(preson, null, values);
		}

		// ���뵽ָ���ı���
		db.setTransactionSuccessful();
		// �ر����ݿ�
		// Log.e("tag",values.toString());
		db.endTransaction();
		db.close();
		return id;
	}

	/**
	 * ��ѯȫ������
	 */
	public List<Information> query(String preson) {
		// �õ���д��SQLdataBase����
		db = mysqlite.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from " + preson, null);
		list.clear();
		db.beginTransaction();
		if (cursor.getCount() != 0) {
			while (cursor.moveToNext()) {
				information = new Information();
				information.setTime(cursor.getString(cursor.getColumnIndex("time")));
				information.setNum(cursor.getString(cursor.getColumnIndex("num")));
				information.setV(cursor.getString(cursor.getColumnIndex("V")));
				information.setI(cursor.getString(cursor.getColumnIndex("I")));
				information.setU(cursor.getString(cursor.getColumnIndex("U")));
				information.setR1(cursor.getString(cursor.getColumnIndex("R1")));
				information.setT(cursor.getString(cursor.getColumnIndex("T")));
				information.setC(cursor.getString(cursor.getColumnIndex("C")));
				information.setR2(cursor.getString(cursor.getColumnIndex("R2")));
				information.setC2(cursor.getString(cursor.getColumnIndex("C2")));
				list.add(information);
			}
			delete(preson, db);
			// if (list.size() > 1000) { // ����1000��ɾ������
			// for (int i = 0; i < list.size() - 800; i++) {
			// delete(preson, list.get(i).getTime());
			// }
			// }
			Log.e("tag", list.toString());
		} else {
			Log.e("tag", "����û������");
		}
		db.setTransactionSuccessful();
		cursor.close();
		db.endTransaction();
		db.close();
		return list;
	}

	/**
	 * ��ѯһ�����ݵķ��� ��غŲ�
	 */
	public List<Information> oneQuery(String preson, String num) {
		db = mysqlite.getWritableDatabase();
		list.clear();
		Cursor cursor = db.rawQuery("select * from " + preson + " where num = ?", new String[] { num });
		db.beginTransaction();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				information = new Information();
				information.setTime(cursor.getString(cursor.getColumnIndex("time")));
				information.setNum(cursor.getString(cursor.getColumnIndex("num")));
				information.setV(cursor.getString(cursor.getColumnIndex("V")));
				information.setI(cursor.getString(cursor.getColumnIndex("I")));
				information.setU(cursor.getString(cursor.getColumnIndex("U")));
				information.setR1(cursor.getString(cursor.getColumnIndex("R1")));
				information.setT(cursor.getString(cursor.getColumnIndex("T")));
				information.setC(cursor.getString(cursor.getColumnIndex("C")));
				information.setR2(cursor.getString(cursor.getColumnIndex("R2")));
				information.setC2(cursor.getString(cursor.getColumnIndex("C2")));
				list.add(information);
			}
		} else {

			// Log.e("tag", "����û������");
		}
		db.setTransactionSuccessful();
		cursor.close();
		db.close();
		return list;
	}

	/**
	 * ��ѯһ�����ݵķ��� ʱ���
	 */
	public List<Information> oneQueryTime(String preson, String time) {
		db = mysqlite.getWritableDatabase();
		list.clear();
		Cursor cursor = db.rawQuery("select * from " + preson + " where time = ?", new String[] { time });
		db.beginTransaction();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				// if (cursor.moveToFirst()) {
				information = new Information();
				information.setTime(cursor.getString(cursor.getColumnIndex("time")));
				information.setNum(cursor.getString(cursor.getColumnIndex("num")));
				information.setV(cursor.getString(cursor.getColumnIndex("V")));
				information.setI(cursor.getString(cursor.getColumnIndex("I")));
				information.setU(cursor.getString(cursor.getColumnIndex("U")));
				information.setR1(cursor.getString(cursor.getColumnIndex("R1")));
				information.setT(cursor.getString(cursor.getColumnIndex("T")));
				information.setC(cursor.getString(cursor.getColumnIndex("C")));
				information.setR2(cursor.getString(cursor.getColumnIndex("R2")));
				information.setC2(cursor.getString(cursor.getColumnIndex("C2")));
				list.add(information);
				// Log.e("tag", "����ʱ���ѯ������" + information.toString() + "
				// list.add(information); " + list.size());
			}
			// }
		}
		// Log.e("7", "��ѯ�ɹ�");
		db.setTransactionSuccessful();
		db.endTransaction();
		cursor.close();
		db.close();
		return list;
	}

	/**
	 * �޸�һ�����ݵķ���
	 */
	public int update(String preson, String num, String U) {
		db = mysqlite.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("U", U);
		int ret = db.update(preson, values, "num=?", new String[] { num });
		db.close();
		return ret;
	}

	/**
	 * ɾ��һ������
	 * 
	 */
	public int delete(String preson, SQLiteDatabase db) {
		// �õ���д��SQLdataBase����
		// db = mysqlite.getWritableDatabase();
		int id = 0;
		// ɾ������
		if (list.size() > 1000) { // ����1000��ɾ������
			for (int i = 0; i < list.size() - 800; i++) {
				id = db.delete(preson, "time=?", new String[] { list.get(i).getTime() });
			}
		}
//		db.close();
		return id;
	}

	/**
	 * ɾ��һ������
	 * 
	 */
	public int deleteID(String preson, String ids) {
		// �õ���д��SQLdataBase����
		db = mysqlite.getWritableDatabase();
		// ɾ������
		int id = db.delete(preson, "id=?", new String[] { ids });
		db.close();
		// Log.e("1", "ɾ���ɹ�" + id);
		return id;
	}

	/**
	 * ��������ķ���
	 */
	public List<Information> ListSort(List<Information> information) {
		Collections.sort(information, new Comparator<Information>() {
			public int compare(Information arg0, Information arg1) {
				return arg0.getTime().compareTo(arg1.getTime());
			}
		});
		return information;
	}
}
