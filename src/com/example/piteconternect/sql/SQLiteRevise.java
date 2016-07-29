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

//数据库增删改查的方法
public class SQLiteRevise {
	private MySQLite mysqlite;
	private SQLiteDatabase db;
	// 创建一个集合用来存储数据
	public List<Information> list;
	private Information information;

	public SQLiteRevise(Context context) {
		// 创建表
		mysqlite = MySQLite.getInstance(context);
		list = new ArrayList<Information>();
	}

	/**
	 * 添加数据的方法
	 */
	public synchronized long add(String preson, String time, List<String[]> data) {
		// 拿到读写的SQLdataBase对象
		long id = -1;
		db = mysqlite.getWritableDatabase();
		db.beginTransaction();
		// 添加信息
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

		// 插入到指定的表中
		db.setTransactionSuccessful();
		// 关闭数据库
		// Log.e("tag",values.toString());
		db.endTransaction();
		db.close();
		return id;
	}

	/**
	 * 查询全部数据
	 */
	public List<Information> query(String preson) {
		// 拿到读写的SQLdataBase对象
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
			// if (list.size() > 1000) { // 超过1000条删除数据
			// for (int i = 0; i < list.size() - 800; i++) {
			// delete(preson, list.get(i).getTime());
			// }
			// }
			Log.e("tag", list.toString());
		} else {
			Log.e("tag", "表中没有数据");
		}
		db.setTransactionSuccessful();
		cursor.close();
		db.endTransaction();
		db.close();
		return list;
	}

	/**
	 * 查询一条数据的方法 电池号查
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

			// Log.e("tag", "表中没有数据");
		}
		db.setTransactionSuccessful();
		cursor.close();
		db.close();
		return list;
	}

	/**
	 * 查询一条数据的方法 时间查
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
				// Log.e("tag", "根据时间查询的数据" + information.toString() + "
				// list.add(information); " + list.size());
			}
			// }
		}
		// Log.e("7", "查询成功");
		db.setTransactionSuccessful();
		db.endTransaction();
		cursor.close();
		db.close();
		return list;
	}

	/**
	 * 修改一条数据的方法
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
	 * 删除一条数据
	 * 
	 */
	public int delete(String preson, SQLiteDatabase db) {
		// 拿到读写的SQLdataBase对象
		// db = mysqlite.getWritableDatabase();
		int id = 0;
		// 删除数据
		if (list.size() > 1000) { // 超过1000条删除数据
			for (int i = 0; i < list.size() - 800; i++) {
				id = db.delete(preson, "time=?", new String[] { list.get(i).getTime() });
			}
		}
//		db.close();
		return id;
	}

	/**
	 * 删除一条数据
	 * 
	 */
	public int deleteID(String preson, String ids) {
		// 拿到读写的SQLdataBase对象
		db = mysqlite.getWritableDatabase();
		// 删除数据
		int id = db.delete(preson, "id=?", new String[] { ids });
		db.close();
		// Log.e("1", "删除成功" + id);
		return id;
	}

	/**
	 * 集合排序的方法
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
