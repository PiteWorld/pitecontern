package com.example.piteconternect.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLite extends SQLiteOpenHelper {
	private static final String name = "database.db";// 数据库名称
	private static final int version = 1;// 数据库版本
	private Context context;

	public MySQLite(Context context) {
		super(context, name, null, version);
	}

	public static MySQLite mySQlite = null;

	public static MySQLite getInstance(Context context) {
		if (mySQlite == null) {
			synchronized (MySQLite.class) {
				if (mySQlite == null) {
					mySQlite = new MySQLite(context);
				}
			}
		}
		return mySQlite;
	}

	// 数据库第一次创建的时候调用
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table preson1(id integer primary key autoincrement,time varchar(20) not null,"
				+ "num varchar(20),V varchar(20),I varchar(20),U varchar(20),R1 varchar(20),T varchar(20), C varchar(20),R2 varchar(20),C2 varchar(20));";
		String sql2 = "create table preson2(id integer primary key autoincrement,time varchar(20) not null,"
				+ "num varchar(20),V varchar(20),I varchar(20),U varchar(20),R1 varchar(20),T varchar(20), C varchar(20),R2 varchar(20),C2 varchar(20));";
		String sql3 = "create table preson3(id integer primary key autoincrement,time varchar(20) not null,"
				+ "num varchar(20),V varchar(20),I varchar(20),U varchar(20),R1 varchar(20),T varchar(20), C varchar(20),R2 varchar(20),C2 varchar(20));";
		String sql4 = "create table preson4(id integer primary key autoincrement,time varchar(20) not null,"
				+ "num varchar(20),V varchar(20),I varchar(20),U varchar(20),R1 varchar(20),T varchar(20), C varchar(20),R2 varchar(20),C2 varchar(20));";
		String sql5 = "create table preson5(id integer primary key autoincrement,time varchar(20) not null,"
				+ "num varchar(20),V varchar(20),I varchar(20),U varchar(20),R1 varchar(20),T varchar(20), C varchar(20),R2 varchar(20),C2 varchar(20));";
		String sql6 = "create table preson6(id integer primary key autoincrement,time varchar(20) not null,"
				+ "num varchar(20),V varchar(20),I varchar(20),U varchar(20),R1 varchar(20),T varchar(20), C varchar(20),R2 varchar(20),C2 varchar(20));";
		String sql7 = "create table preson7(id integer primary key autoincrement,time varchar(20) not null,"
				+ "num varchar(20),V varchar(20),I varchar(20),U varchar(20),R1 varchar(20),T varchar(20), C varchar(20),R2 varchar(20),C2 varchar(20));";
		String sql8 = "create table preson8(id integer primary key autoincrement,time varchar(20) not null,"
				+ "num varchar(20),V varchar(20),I varchar(20),U varchar(20),R1 varchar(20),T varchar(20), C varchar(20),R2 varchar(20),C2 varchar(20));";

		db.execSQL(sql);
		db.execSQL(sql2);
		db.execSQL(sql3);
		db.execSQL(sql4);
		db.execSQL(sql5);
		db.execSQL(sql6);
		db.execSQL(sql7);
		db.execSQL(sql8);
		Log.e("tag", "创建数据库成功");
	}

	// 版本号升级的时候调用
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// String sql = "create table preson1(id integer primary key
		// autoincrement,time varchar(20) not null,"
		// + "num varchar(20),V varchar(20),I varchar(20),U varchar(20),R1
		// varchar(20),T varchar(20), C varchar(20),R2 varchar(20),C2
		// varchar(20));";
		// String sql2 = "create table preson2(id integer primary key
		// autoincrement,time varchar(20) not null,"
		// + "num varchar(20),V varchar(20),I varchar(20),U varchar(20),R1
		// varchar(20),T varchar(20), C varchar(20),R2 varchar(20),C2
		// varchar(20));";
		// String sql3 = "create table preson3(id integer primary key
		// autoincrement,time varchar(20) not null,"
		// + "num varchar(20),V varchar(20),I varchar(20),U varchar(20),R1
		// varchar(20),T varchar(20), C varchar(20),R2 varchar(20),C2
		// varchar(20));";
		// String sql4 = "create table preson4(id integer primary key
		// autoincrement,time varchar(20) not null,"
		// + "num varchar(20),V varchar(20),I varchar(20),U varchar(20),R1
		// varchar(20),T varchar(20), C varchar(20),R2 varchar(20),C2
		// varchar(20));";
		// String sql5 = "create table preson5(id integer primary key
		// autoincrement,time varchar(20) not null,"
		// + "num varchar(20),V varchar(20),I varchar(20),U varchar(20),R1
		// varchar(20),T varchar(20), C varchar(20),R2 varchar(20),C2
		// varchar(20));";
		// String sql6 = "create table preson6(id integer primary key
		// autoincrement,time varchar(20) not null,"
		// + "num varchar(20),V varchar(20),I varchar(20),U varchar(20),R1
		// varchar(20),T varchar(20), C varchar(20),R2 varchar(20),C2
		// varchar(20));";
		// String sql7 = "create table preson7(id integer primary key
		// autoincrement,time varchar(20) not null,"
		// + "num varchar(20),V varchar(20),I varchar(20),U varchar(20),R1
		// varchar(20),T varchar(20), C varchar(20),R2 varchar(20),C2
		// varchar(20));";
		// String sql8 = "create table preson8(id integer primary key
		// autoincrement,time varchar(20) not null,"
		// + "num varchar(20),V varchar(20),I varchar(20),U varchar(20),R1
		// varchar(20),T varchar(20), C varchar(20),R2 varchar(20),C2
		// varchar(20));";
		//
		// db.execSQL(sql);
		// db.execSQL(sql2);
		// db.execSQL(sql3);
		// db.execSQL(sql4);
		// db.execSQL(sql5);
		// db.execSQL(sql6);
		// db.execSQL(sql7);
		// db.execSQL(sql8);
		// Log.e("tag", "创建数据库成功 版本 2");
	}

}
