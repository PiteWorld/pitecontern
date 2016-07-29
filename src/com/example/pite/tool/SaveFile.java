package com.example.pite.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.piteconternect.utils.SocketUtils;
import com.example.piteconternect.utils.TimerUtil;

import android.os.Environment;
import android.util.Log;
import android_serialport_api.SerialPortService;

public class SaveFile {
	private static String Path = null;

	static {
		Path = Environment.getExternalStorageDirectory() + "/" + "2_pite" + "/";
	}
	/**
	 * 保存所有数据，每次发送第一条，list.get(0);
	 */
	public static ArrayList<String> list = null;

	/**
	 * 写入数据，不覆盖，写入数据时执行
	 * 
	 * @param bt
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static boolean out(byte[] bt) throws FileNotFoundException, UnsupportedEncodingException {
		File file = new File(Path);
		if (!file.exists()) {
			file.mkdirs();
		}
		BufferedWriter write = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(Path + TimerUtil.versionNum + ".txt", false)));
		try {
			write.write(SerialPortService.bytesToHexStrings(bt));
			Log.e("2", "写入数成功");
			// 换行
			write.newLine();
			// 需要删除数据的时候
			write.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 读取文件
	 */
	static String str = "";
	static int num = 0;

	public static void read(int name) {
		list = new ArrayList<String>();
		if (FileSize()) {
			try {
				BufferedReader read = new BufferedReader(
						new InputStreamReader(new FileInputStream(Path + name + ".txt")));
				String line = "";
				while ((line = read.readLine()) != null) {
					list.add(line);
				}
				Log.e("2", "读取数成功" + num++ + "   读取list:  " + list.size());
				read.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 获取文件 判断是否为空
	public static boolean FileSize() {
		if (getAutoFileOrFilesSize(Path + TimerUtil.versionNum + ".txt") > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 调用此方法自动计算指定文件或指定文件夹的大小
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 计算好的带B、KB、MB、GB的字符串
	 */
	public static long getAutoFileOrFilesSize(String filePath) {
		File file = new File(filePath);
		long blockSize = 0;
		try {
			blockSize = getFileSize(file);
		} catch (Exception e) {
			e.printStackTrace();
			// Log.e("获取文件大小", "获取失败!");
		}
		return blockSize;
	}

	/**
	 * 获取指定文件大小
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	private static long getFileSize(File file) throws Exception {
		long size = 0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
		} else {
			file.createNewFile();
		}
		return size;
	}
}
