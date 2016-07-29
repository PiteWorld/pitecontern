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
	 * �����������ݣ�ÿ�η��͵�һ����list.get(0);
	 */
	public static ArrayList<String> list = null;

	/**
	 * д�����ݣ������ǣ�д������ʱִ��
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
			Log.e("2", "д�����ɹ�");
			// ����
			write.newLine();
			// ��Ҫɾ�����ݵ�ʱ��
			write.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ��ȡ�ļ�
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
				Log.e("2", "��ȡ���ɹ�" + num++ + "   ��ȡlist:  " + list.size());
				read.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// ��ȡ�ļ� �ж��Ƿ�Ϊ��
	public static boolean FileSize() {
		if (getAutoFileOrFilesSize(Path + TimerUtil.versionNum + ".txt") > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ���ô˷����Զ�����ָ���ļ���ָ���ļ��еĴ�С
	 * 
	 * @param filePath
	 *            �ļ�·��
	 * @return ����õĴ�B��KB��MB��GB���ַ���
	 */
	public static long getAutoFileOrFilesSize(String filePath) {
		File file = new File(filePath);
		long blockSize = 0;
		try {
			blockSize = getFileSize(file);
		} catch (Exception e) {
			e.printStackTrace();
			// Log.e("��ȡ�ļ���С", "��ȡʧ��!");
		}
		return blockSize;
	}

	/**
	 * ��ȡָ���ļ���С
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
