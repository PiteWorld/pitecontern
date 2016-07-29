package com.example.piteconternect;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.ab.activity.AbActivity;
import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListener;
import com.ab.task.AbTaskPool;
import com.ab.view.table.AbCellType;
import com.ab.view.table.AbTable;
import com.ab.view.table.AbTableArrayAdapter;
import com.ab.view.titlebar.AbTitleBar;
import com.example.pite.server.socketServer;
import com.example.pite.tool.DataManagerTool;
import com.example.pite.tool.ReadData;
import com.example.pite.tool.UpLoad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HeterogeneousExpandableList;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android_serialport_api.SerialPortService;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends Activity {
}
// // ��ţ�����ʱ��ʹӻ���
// public static List<BatteryCheckBean> list = new
// ArrayList<BatteryCheckBean>();
// private int cnt = 0;
// private WifiManager wifiManager;
// private boolean flag = false;
// private Button send;
// private List<String[]> contents;
// // ����������Դ
// private String[] titles = null;
// private AbTable table = null;
// private ListView mListView = null;
// private int[] cellTypes = null;
// private int[] cellWidth = null;
// private int[] rowHeight = null;
// // (6)�����ִ�С������0���⣬1�����б�
// private int[] rowTextSize = null;
// // (7)��������ɫ������0���⣬1�����б�
// private int[] rowTextColor = null;
// // (8)������Դ
// private int[] tableResource = null;
// private Context context;
// // ����Adapter
// private AbTableArrayAdapter tableAdapter;
// private View noView = null;
// private com.ab.task.AbTaskPool mAbTaskPool = null;
// private TextView texV, texI, texTime;
// private int num = 0;
// private SerialPortService port = null;
// // public static EditText edt = null;
// private static byte[] bt = null;
// String str = null;
// private Button btn = null;
// public static boolean isUploadOK = false; // �ϴ��Ƿ����
// public static boolean isWirteSuecc = false;// �Ƿ�д��ɹ�
// public static socketServer server = null;
// private Timer timer = new Timer();
// public int netNum = 0;
// private Spinner spBattery = null;
// public int cntnum = 0;
// private ArrayAdapter<String> spAdapter = null;
// private List<String> spList = null;
// private TimerTask task=new TimerTask(){@Override public void
// run(){handler.sendEmptyMessage(4);}};
// Handler handler = new Handler() {
// @Override
// public void handleMessage(Message msg) {
// if (msg.what == 1) {
// setLoad();
// try {
// isWirteSuecc = ReadData.out(bt);
// } catch (FileNotFoundException e) {
// e.printStackTrace();
// } catch (UnsupportedEncodingException e) {
// e.printStackTrace();
// }
// } else if (msg.what == 2) {
// // edt.setText(str + " ");
// } else if (msg.what == 3) {
// if (ReadData.FileSize()) {
// ReadData.read();
//// if (ReadData.list == null) {
//// return;
//// }
//// if (ReadData.list.size() > 0) {
//// SerialPortService.SendWirte(SerialPortService.hexStringToBytes(ReadData.list.get(0)));
//// isUploadOK = true;
//// try {
//// ReadData.outfu();
//// } catch (FileNotFoundException e) {
//// e.printStackTrace();
//// }
//// }
//// }
// } else if (msg.what == 4) {
// Log.e("2", "����*");
//
// SerialPortService.SendWirte("*".getBytes());
// // if (!SerialPortService.isInternet) {
// // netNum++;
// // }
// if (ReadData.FileSize()) {
// SerialPortService.setUpload();
// }
//
// }
// }
// };
//
// @Override
// protected void onCreate(Bundle savedInstanceState) {
// super.onCreate(savedInstanceState);
// setAbContentView(mInflater.inflate(R.layout.activity_main, null));
// texV = (TextView) this.findViewById(R.id.groupV);
// texI = (TextView) this.findViewById(R.id.groupI);
// // edt = (EditText) this.findViewById(R.id.tex);
// // texTime = (TextView) this.findViewById(R.id.tex_time);
// spBattery = (Spinner) this.findViewById(R.id.sp_battery);
// wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
// initView();
// // this.setTitleText("������");
// // this.setLogo(R.drawable.button_selector_back);
// // this.setTitleLayoutBackground(R.drawable.top_bg);
// // this.setTitleTextMargin(10, 0, 0, 0);
// // this.setLogoLine(R.drawable.line);
// // port = new SerialPortService(MainActivity.this); // ��ʼ������
// // port.initATModel(null);
// // ģ������
// spBattery.setOnItemSelectedListener(new OnItemSelectedListener() {
// @Override
// public void onItemSelected(AdapterView<?> parent, View view, int position,
// long id) {
// cntnum = position;
// if (position == 1) {
// // SocketUtils.getSocket().setDevicedPsotion();
// }
// }
//
// @Override
// public void onNothingSelected(AdapterView<?> parent) {
//
// }
// });
// flag = !flag;
// boolean wifiStatus = setWifiApEnabled(flag);
// if (wifiStatus) {
// Thread thread = new Thread() {
// @Override
// public void run() {
// server = new socketServer(MainActivity.this);
// }
// };
// thread.start();
// Toast.makeText(MainActivity.this, "WIFI�򿪳ɹ�", 0).show();
// }
//// timer.schedule(task, 0, 5000);
// }
//
// // wifi�ȵ㿪��
// public boolean setWifiApEnabled(boolean enabled) {
// if (enabled) { // disable WiFi in any case
// // wifi���ȵ㲻��ͬʱ�򿪣����Դ��ȵ��ʱ����Ҫ�ر�wifi
// wifiManager.setWifiEnabled(false);
// }
// try {
// // �ȵ��������
// WifiConfiguration apConfig = new WifiConfiguration();
// // �����ȵ������(���������ֺ���ӵ������ʲô��)
// apConfig.SSID = "PITE0000";
// // �����ȵ������
// apConfig.preSharedKey = "12345678";
// // ͨ��������������ȵ�
// java.lang.reflect.Method method =
// wifiManager.getClass().getMethod("setWifiApEnabled",
// WifiConfiguration.class, Boolean.TYPE);
// // �����ȵ��״̬
// return (Boolean) method.invoke(wifiManager, apConfig, enabled);
// } catch (Exception e) {
// return false;
// }
// }
//
// public void initView() {
// final AbTitleBar mAbTitleBar = this.getTitleBar(); // ����
// mAbTitleBar.setTitleText(R.string.battery_conter);
// mAbTitleBar.setLogo(R.drawable.button_selector_back);
// mAbTitleBar.setTitleBarBackground(R.drawable.top_bg);
// mAbTitleBar.setTitleTextMargin(30, 0, 0, 0);
// mAbTaskPool = AbTaskPool.getInstance();
// // (1)��������
// titles = new String[] { "No.", "U(V)", "R2(m��)", "T(��)", "No.", "U(V)",
// "R2(m��)", "T(��)" };//
// ,MenuActivity.this.getResources().getString(R.string.ba_operate)
// // (2)�����б�����
// contents = new ArrayList<String[]>();
// // (3)����������
// cellTypes = new int[] { AbCellType.STRING, AbCellType.STRING,
// AbCellType.STRING, AbCellType.STRING,
// AbCellType.STRING, AbCellType.STRING, AbCellType.STRING, AbCellType.STRING };
// // (4)�п�����(%) ����100% ���Ժ��򻬶�
// cellWidth = new int[] { 12, 12, 12, 12, 12, 12, 12, 15 };
// // (5)�иߣ�����0������ߣ�1�������б�ߣ�
// rowHeight = new int[] { 60, 60 };
// // (6)�����ִ�С������0���⣬1�����б�
// rowTextSize = new int[] { 12, 12 };
// // (7)��������ɫ������0���⣬1�����б�
// rowTextColor = new int[] { Color.BLACK, Color.BLACK };
// // (8)������Դ ͸��ɫ
// tableResource = new int[] { android.R.color.transparent,
// R.drawable.title_cell, android.R.color.transparent,
// R.drawable.content_cell };
// // (9)���ʵ��
// table = AbTable.newAbTable(this, 8);
// table.setTitles(titles);
// table.setContents(contents);
// table.setCellTypes(cellTypes);
// table.setCellWidth(cellWidth);
// table.setRowHeight(rowHeight);
// table.setRowTextSize(rowTextSize);
// table.setTableResource(tableResource);
// table.setRowTextColor(rowTextColor);
// // (10)TableAdapter����
// tableAdapter = new AbTableArrayAdapter(MainActivity.this, table);
// // (12)ListView
// mListView = (ListView) findViewById(R.id.menu_listview);
// // (11)����Adapter
// mListView.setAdapter(tableAdapter);
// }
//
// public void setLoad() {
// showProgressDialog(); // ��ѯ��Ϣ
// final AbTaskItem taskItem = new AbTaskItem();
// taskItem.listener = new AbTaskListener() {
// @Override
// public void update() {
// removeProgressDialog(); // �Ƴ�������
// if (list != null) {
// // Log.e("2", "�����ţ�" + list.get(0).getBatterynum());
// contents.clear();
// int length = list.get(0).getTestV().get(0).length / 2;
// for (int i = 0; i < length; i++) {
// String[] data = new String[] { ++num + "",
// getDoubleFormatString(list.get(0).getTestV().get(0)[i] / 1000.0),
// getDoubleFormatString(list.get(0).getTestre2().get(0)[i] / 100.0),
// String.valueOf(list.get(0).getTestT().get(0)[i]),
// ((list.get(0).getTestV().get(0).length / 2 + num)) + "",
// getDoubleFormatString(list.get(0).getTestV().get(0)[length + i] / 1000.0),
// getDoubleFormatString(list.get(0).getTestre2().get(0)[length + i] / 100.0),
// String.valueOf(list.get(0).getTestT().get(0)[length + i]) };
// contents.add(data);
// }
// num = 0;
// // String str = "";
// // for (int i = 0; i < list.get(0).getTestTime().length;
// // i++) {
// // str += list.get(0).getTestTime()[i];
// // }
// // texTime.setText(str);
// // spList = new ArrayList<String>();
// // for (int i = 0; i <
// // list.get(0).getTestV().get(cnt).length; i++) {
// // spList.add(list.get(0).get);
// // }
// // spAdapter = new ArrayAdapter<String>(MainActivity.this,
// // android.R.layout.simple_spinner_item,spList);
// //
// spAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
// // spBattery.setAdapter(spAdapter);
// texV.setText(getDoubleFormatString(list.get(0).getGroupV().get(0)[0] / 100.0)
// + "V"); // ���ѹ
// texI.setText(getDoubleFormatString(list.get(0).getGroupI().get(0)[0] / 100.0)
// + "A"); // �����
// list.clear();
// tableAdapter.notifyDataSetChanged();
// } else {
// contentLayout.removeAllViews();
// contentLayout.addView(noView, layoutParamsFF);
// }
// }
// };
// mAbTaskPool.execute(taskItem);
// }
//
// /**
// * ���͸��½�����Ϣ
// */
// public void setHandler(byte[] bt) {
// this.bt = bt;
// handler.sendEmptyMessage(1);
// }
//
// public void setRecHandler(int index) {
// handler.sendEmptyMessage(index);
// }
//
// /**
// * ��double���ݱ���С���������λ����
// */
// public static String getDoubleFormatString(double value) {
// DecimalFormat df;
//
// if (value >= 100.0) {
// df = new DecimalFormat("0.0");
// } else if (value >= 10.0) {
// df = new DecimalFormat("0.00");
// } else {
// df = new DecimalFormat("0.000");
// }
// return df.format(value);
// }
//
// public void setHandler2(String str) {
// this.str = str;
// handler.sendEmptyMessage(2);
// }
//
// @Override
// protected void onDestroy() {
// if (server != null) {
// server.setCacelsTask();
// }
// if (task != null) {
// task.cancel();
// }
// super.onDestroy();
// }
