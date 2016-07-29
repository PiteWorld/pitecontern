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
// // 编号，定检时间和从机号
// public static List<BatteryCheckBean> list = new
// ArrayList<BatteryCheckBean>();
// private int cnt = 0;
// private WifiManager wifiManager;
// private boolean flag = false;
// private Button send;
// private List<String[]> contents;
// // 表格标题数据源
// private String[] titles = null;
// private AbTable table = null;
// private ListView mListView = null;
// private int[] cellTypes = null;
// private int[] cellWidth = null;
// private int[] rowHeight = null;
// // (6)行文字大小（索引0标题，1内容列表）
// private int[] rowTextSize = null;
// // (7)行文字颜色（索引0标题，1内容列表）
// private int[] rowTextColor = null;
// // (8)背景资源
// private int[] tableResource = null;
// private Context context;
// // 表格的Adapter
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
// public static boolean isUploadOK = false; // 上传是否完成
// public static boolean isWirteSuecc = false;// 是否写入成功
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
// Log.e("2", "发送*");
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
// // this.setTitleText("集中器");
// // this.setLogo(R.drawable.button_selector_back);
// // this.setTitleLayoutBackground(R.drawable.top_bg);
// // this.setTitleTextMargin(10, 0, 0, 0);
// // this.setLogoLine(R.drawable.line);
// // port = new SerialPortService(MainActivity.this); // 初始化串口
// // port.initATModel(null);
// // 模拟数据
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
// Toast.makeText(MainActivity.this, "WIFI打开成功", 0).show();
// }
//// timer.schedule(task, 0, 5000);
// }
//
// // wifi热点开关
// public boolean setWifiApEnabled(boolean enabled) {
// if (enabled) { // disable WiFi in any case
// // wifi和热点不能同时打开，所以打开热点的时候需要关闭wifi
// wifiManager.setWifiEnabled(false);
// }
// try {
// // 热点的配置类
// WifiConfiguration apConfig = new WifiConfiguration();
// // 配置热点的名称(可以在名字后面加点随机数什么的)
// apConfig.SSID = "PITE0000";
// // 配置热点的密码
// apConfig.preSharedKey = "12345678";
// // 通过反射调用设置热点
// java.lang.reflect.Method method =
// wifiManager.getClass().getMethod("setWifiApEnabled",
// WifiConfiguration.class, Boolean.TYPE);
// // 返回热点打开状态
// return (Boolean) method.invoke(wifiManager, apConfig, enabled);
// } catch (Exception e) {
// return false;
// }
// }
//
// public void initView() {
// final AbTitleBar mAbTitleBar = this.getTitleBar(); // 标题
// mAbTitleBar.setTitleText(R.string.battery_conter);
// mAbTitleBar.setLogo(R.drawable.button_selector_back);
// mAbTitleBar.setTitleBarBackground(R.drawable.top_bg);
// mAbTitleBar.setTitleTextMargin(30, 0, 0, 0);
// mAbTaskPool = AbTaskPool.getInstance();
// // (1)标题配置
// titles = new String[] { "No.", "U(V)", "R2(mΩ)", "T(℃)", "No.", "U(V)",
// "R2(mΩ)", "T(℃)" };//
// ,MenuActivity.this.getResources().getString(R.string.ba_operate)
// // (2)内容列表配置
// contents = new ArrayList<String[]>();
// // (3)列类型配置
// cellTypes = new int[] { AbCellType.STRING, AbCellType.STRING,
// AbCellType.STRING, AbCellType.STRING,
// AbCellType.STRING, AbCellType.STRING, AbCellType.STRING, AbCellType.STRING };
// // (4)列宽配置(%) 超过100% 可以横向滑动
// cellWidth = new int[] { 12, 12, 12, 12, 12, 12, 12, 15 };
// // (5)行高（索引0：标题高，1：内容列表高）
// rowHeight = new int[] { 60, 60 };
// // (6)行文字大小（索引0标题，1内容列表
// rowTextSize = new int[] { 12, 12 };
// // (7)行文字颜色（索引0标题，1内容列表）
// rowTextColor = new int[] { Color.BLACK, Color.BLACK };
// // (8)背景资源 透明色
// tableResource = new int[] { android.R.color.transparent,
// R.drawable.title_cell, android.R.color.transparent,
// R.drawable.content_cell };
// // (9)表格实体
// table = AbTable.newAbTable(this, 8);
// table.setTitles(titles);
// table.setContents(contents);
// table.setCellTypes(cellTypes);
// table.setCellWidth(cellWidth);
// table.setRowHeight(rowHeight);
// table.setRowTextSize(rowTextSize);
// table.setTableResource(tableResource);
// table.setRowTextColor(rowTextColor);
// // (10)TableAdapter对象
// tableAdapter = new AbTableArrayAdapter(MainActivity.this, table);
// // (12)ListView
// mListView = (ListView) findViewById(R.id.menu_listview);
// // (11)设置Adapter
// mListView.setAdapter(tableAdapter);
// }
//
// public void setLoad() {
// showProgressDialog(); // 查询信息
// final AbTaskItem taskItem = new AbTaskItem();
// taskItem.listener = new AbTaskListener() {
// @Override
// public void update() {
// removeProgressDialog(); // 移除进度条
// if (list != null) {
// // Log.e("2", "电池组号：" + list.get(0).getBatterynum());
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
// + "V"); // 组电压
// texI.setText(getDoubleFormatString(list.get(0).getGroupI().get(0)[0] / 100.0)
// + "A"); // 组电流
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
// * 发送更新界面消息
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
// * 将double数据保存小数点后面两位数字
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
