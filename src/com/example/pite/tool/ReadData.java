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

import android.os.Environment;
import android.util.Log;
import android_serialport_api.SerialPortService;

public class ReadData {
	private static String Path = null;

	static {
		Path = Environment.getExternalStorageDirectory() + "/" + "1_pite" + "/";
	}
	/**
	 * �����������ݣ�ÿ�η��͵�һ����list.get(0);
	 */
	private static  ArrayList<String> list = new ArrayList<String>();;

	/**
	 * д�����ݣ������ǣ�д������ʱִ��
	 * 
	 * @param bt
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static synchronized boolean out(byte[] bt) throws FileNotFoundException, UnsupportedEncodingException {
		File file = new File(Path);
		if (!file.exists()) {
			file.mkdirs();
		}
		BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Path + "1.txt", true)));
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
	 * д���ļ� ����,ɾ������ʱִ�д˷���
	 * 
	 * @throws FileNotFoundException
	 */
	public static synchronized void outfu() throws FileNotFoundException {
		// ��ȡ��ǰ�ļ���ɾ���ѷ����ļ�����д��
		read();
		Log.e("2", "ɾ�����ݳɹ�֮ǰ----" + list.size());
		if (list != null && list.size() > 0) {
			Iterator<String> it = list.iterator();
			if (it.hasNext()) {
				if (it.next() == list.get(0)) {
					it.remove();
					Log.e("2", "ɾ�����ݳɹ�----" + list.size());
				}
			}
		}
		BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Path + "1.txt")));
		for (String string : list) {
			try {
				write.write(string);
				// ����
				write.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (write != null)
			try {
				write.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * ��ȡ�ļ�
	 */
	static String str = "";
	static int num = 0;

	public static synchronized String read() {
		list.clear();
		if (FileSize()) {
			try {
				StringBuilder sb = new StringBuilder();
				BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(Path + "1.txt")));
				String line = "";
				while ((line = read.readLine()) != null) {
					sb.append(line);
				}
//				Log.e("2", "��ȡlist:  " + list.size());
				read.close();
				list.add(sb.toString());
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
		return "";
	}

	// ��ȡ�ļ� �ж��Ƿ�Ϊ��
	public static boolean FileSize() {
		if (getAutoFileOrFilesSize(Path + "1.txt") > 0) {
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
			// Log.e("��ȡ�ļ���С", "�ļ�������!");
		}
		return size;
	}
}
