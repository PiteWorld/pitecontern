package com.example.piteconternect;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.pite.model.Prclo85Data;
import com.example.pite.model.Prclo85GroupData;
import com.example.pite.model.PrcloModel;
import com.example.pite.server.SocketClient;
import com.example.pite.server.SocketContent;
import com.example.pite.tool.ReadData;
import com.example.pite.tool.UpLoad;
import com.example.piteconternect.adapter.ChartAdapter;
import com.example.piteconternect.handler.SendHandler;
import com.example.piteconternect.kehua.tool.keHuaCMDTool;
import com.example.piteconternect.kehua.tool.kehuaProl;
import com.example.piteconternect.netty.SimpleClientHandler;
import com.example.piteconternect.read.ReadDataHost;
import com.example.piteconternect.read.ReadKeHuaData;
import com.example.piteconternect.sql.Information;
import com.example.piteconternect.sql.SQLiteRevise;
import com.example.piteconternect.utils.FormatUtils;
import com.example.piteconternect.utils.SocketUtils;
import com.example.piteconternect.utils.TimerUtil;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android_serialport_api.SerialPortService;
import android_serialport_api.keHuaSerialPortService;
import io.netty.channel.EventLoop;

public class ConcentratorActivity extends BaseActivity implements OnClickListener, OnItemSelectedListener {
	private TextView total_voltage, total_current, tv;// �ܵ�ѹ���ܵ���
	private EditText group;// ����
	private Spinner concentrator_sp;
	private List<String> spAdapter;
	private View view;
	private LinearLayout chlickll;
	private Context mcontent;
	private Button concentrator_up, concentrator_down; // ��һҳ ��һҳ
	private ListView Concentratoe_lv;
	private LinearLayout Concentratoe_lv_title;
	private String[] titles = { "NO", "SOC(%)", "U(V)", "T(��)", "NO", "SOC(%)", "U(V)", "T(��)" };
	private List<String[]> list;
	private ChartAdapter adapter;
	private WifiManager wifiManager; // Wifi ������
	private String[] str;
	private SocketContent server = null;
	private SerialPortService port = null;
	private int num = 0; // ��ʾ�����
	private boolean flag = false;
	private int hostID = 37;// ������
	private int svlve = 6; // �ӻ���
	private byte[] bt = null; // ������ʾ����
	private byte[] prcBt = null; // ������ʾ����
	private Prclo85Data prcHeader; // ���ͷ����
	private List<Prclo85GroupData> prcGroup;
	public static boolean isSendOK = false; // ������־�ж�
	private PrcloModel mode = null;
	private int deviceCnt = 0;
	private SQLiteRevise sql = null;
	private int cnt = 1; // ��ʾ���ж�
	private long isOk = 0; // д�������ж�
	public static boolean isFileUp = false;
	public static boolean isKeHuaOk = false; // �ƻ������ϴ��ɹ���־
	private ReadDataHost readHost;
	private ArrayAdapter<String> spStr = null;
	private List<Information> listSq = null; // ���ݿ���Ϣ
	private List<String> listConcentrator_sp = null; // ���ݼ���
	private int timeCnt = 0;
	public static int gpsRecCnt = 0; // ģ�鸴λ����
	private WifiManager manger;
	public static int portRceCnt = 0;
	public ScheduledExecutorService executorService = null;
	public static boolean timerStatus = false;
	private int timecnt = 0;
	private int repeat = 0;
	private int salvenum = 0;
	private int notice = 0; // ֪ͨ
	public static byte[] kehuaBt;
	private SendHandler sendHandler = null;
	private TimerTask taskRest = new TimerTask() {
		@Override
		public void run() {
			openWIFI();
			Log.e("4", "������������  " + ReadDataHost.resartCnt);
			if (!SocketClient.isSocketStutas) {
				SocketClient.isSocketStutas = false;
				SocketClient client = new SocketClient("192.168.11.254", 4400); // 192.168.16.253",
			}
			setResrtConnect(); // ����
			// restartApplication();
			portRceCnt++;
			if (portRceCnt > 3) {
				Log.e("4", "portRceCnt++:" + portRceCnt);
				Process proc;
				try {
					proc = Runtime.getRuntime().exec(new String[] { "su", "-c", "reboot " });
					proc.waitFor();
				} catch (IOException e) {
					e.printStackTrace();
				} // �ػ�
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};
	private TimerTask task = new TimerTask() {
		@Override
		public void run() {
			notice++;
			handler.sendEmptyMessage(2);
			if (notice > 1) {
				notice = 0;
				if ((ReadData.FileSize() || ReadKeHuaData.KeHuaFileSize()) && !SerialPortService.isUpData) {
					byte[] upBt = UpLoad.setHostData(1);
					sendHandler(upBt);
					Log.e("5", "����֪ͨ������������1��" + SerialPortService.bytesToHexString(UpLoad.setHostData(1)));
				}
			}
		}
	};
	private Handler handler = new Handler() {
		public void handleMessage(final android.os.Message msg) {
			if (msg.what == 1) {
				if (mode != null) {
					salvenum = mode.getPrcHeader().EquipID2;
				}
				setInsertData(mode);
			} else if (msg.what == 2) {
				sendHandler("*".getBytes());
				isSendOK = true;
				Log.e("2", "����*");
			} else if (msg.what == 3) {
				try {
					if (ReadData.FileSize()) {
						String data = ReadData.read(); //
						if (data != null) {
							int decode = 0;
							byte[] readBt = SerialPortService.hexStringToBytes(data);
							byte[] repBt = new byte[3];
							System.arraycopy(readBt, readBt.length - 3, repBt, 0, repBt.length);
							for (int i = 0; i < repBt.length; i++) {
								decode *= 10;
								decode += repBt[i] - 0x30;
							}
							if (repeat == decode || readBt.length < 80) {
								ReadData.outfu();
								return;
							}
							if (repeat != decode) {
								repeat = decode;
								TimerUtil.isSalve = readBt[42];
								sendHandler(readBt);
								isFileUp = true;
								Log.e("8", "�ļ��������ϴ����:  " + readBt[42]);
							}
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else if (msg.what == 4) {
				setSpAdapter(); // spinner
				setShowLoad(); // ���ݸ���
			} else if (msg.what == 5) {
				setShowLoad(); // ���ݸ���
			} else if (msg.what == 6) {
				setSpAdapter();
				concentrator_sp.setSelection(0, true);
			} else if (msg.what == 7) {
				if (ReadKeHuaData.KeHuaFileSize()) {
					String keHuaData = ReadKeHuaData.ReadKehuaData();
					if (keHuaData != null) {
						sendHandler(SerialPortService.hexStringToBytes(keHuaData));
						isKeHuaOk = true;
						Log.e("8", "�ƻ��ļ��������ϴ����");
					}
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mcontent = this;
		manger = (WifiManager) getSystemService(mcontent.WIFI_SERVICE);
		// ��WIFI
		openWIFI();
		spAdapter = new ArrayList<String>();
		initData();
		setResrtTimer(60);// ������ʱ
		HandlerThread handlerThread = new HandlerThread("handlerThread");
		handlerThread.start();
		handler.getLooper();
		sendHandler = new SendHandler(handlerThread.getLooper());
		TimerUtil.sendHandler = sendHandler;
		setWifiManger();
	}

	/**
	 * ��ʼ������,���.......
	 */
	public void initData() {
		total_voltage = (TextView) findViewById(R.id.total_voltage1);
		total_current = (TextView) findViewById(R.id.total_current2);
		group = (EditText) findViewById(R.id.group);
		concentrator_up = (Button) findViewById(R.id.concentrator_up);
		concentrator_down = (Button) findViewById(R.id.concentrator_down);
		// wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		concentrator_up.setOnClickListener(this);
		concentrator_down.setOnClickListener(this);
		Concentratoe_lv = (ListView) findViewById(R.id.Concentrator_lv);
		Concentratoe_lv_title = (LinearLayout) findViewById(R.id.Concentratoe_lv_title);
		concentrator_sp = (Spinner) findViewById(R.id.concentrator_sp);
		concentrator_sp.setOnItemSelectedListener(this);
		sql = new SQLiteRevise(ConcentratorActivity.this);
		executorService = Executors.newScheduledThreadPool(4);

		// hostID = Integer.parseInt(getIntent().getStringExtra("host_number"));
		// svlve = Integer.parseInt(getIntent().getStringExtra("savle_number"));
		addTitle(titles);
		SocketUtils.context = this;
		group.setText(hostID + "-" + cnt);
		TimerUtil.hostID = hostID;
		TimerUtil.hostNum = svlve;
		TimerUtil.time = 1;// 900000 60000
		TimerUtil.KehuaTimer = 2;
		TimerUtil.pool = executorService;
		listSq = sql.query("preson" + cnt);
		if (listSq.size() > 0)
			setSpAdapter();
		// ConcentratorActivity.this.deleteDatabase("database.db");
		upgradeRootPermission(getPackageCodePath());
	}

	@Override
	public View getcontent() {
		return View.inflate(ConcentratorActivity.this, R.layout.activity_concentrator, null);
	}

	@Override
	public void onClick(View v) {
		// ��¼��ǰҳ
		switch (v.getId()) {
		case R.id.concentrator_up: // ��һҳ
			if (cnt > 1) {
				cnt--;
				listSq = sql.query("preson" + cnt);
				group.setText(hostID + "-" + cnt);
			}
			break;
		case R.id.concentrator_down: // ��һҳ
			if (cnt < svlve) {
				cnt++;
				listSq = sql.query("preson" + cnt);
				group.setText(hostID + "-" + cnt);
			}
			break;
		}
		handler.sendEmptyMessage(4);
	}
	/**
	 * ����WIFI�ȵ�
	 */
	public void setWifiManger() {
		port = new SerialPortService(ConcentratorActivity.this, hostID, svlve); // ��ʼ������
		// port.initATModel(null);
		SocketClient client = new SocketClient("192.168.11.254", 4400); // 192.168.16.253",
		keHuaSerialPortService keHua = new keHuaSerialPortService(ConcentratorActivity.this);
	}

	// ��ȡroot Ȩ��
	public boolean upgradeRootPermission(String pkgCodePath) {
		Process process = null;
		DataOutputStream os = null;
		try {
			String cmd = "chmod 777 " + pkgCodePath;
			process = Runtime.getRuntime().exec("su"); // �л���root�ʺ�
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(cmd + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
			}
		}
		return true;
	}

	/**
	 * ��� ����
	 * 
	 * @param titles
	 */
	public void addTitle(String[] titles) {
		for (int i = 0; i < titles.length; i++) {
			tv = new TextView(mcontent);
			tv.setText(titles[i]);
			tv.setTextColor(Color.BLACK);
			tv.setBackground(mcontent.getResources().getDrawable(R.drawable.title_cell));
			tv.setTextSize(12);
			tv.setGravity(Gravity.CENTER);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, 0.03f);
			params.gravity = Gravity.CENTER;
			view = new View(mcontent);
			view.setBackground(mcontent.getResources().getDrawable(R.drawable.line));
			LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, 0.97f);
			chlickll = new LinearLayout(mcontent);
			LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, 1.0f);

			if (i == titles.length - 1) {
				LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT, 1.0f);
				chlickll.addView(tv, params4);
				Concentratoe_lv_title.addView(chlickll, params3);
			} else {
				chlickll.addView(tv, params);
				chlickll.addView(view, params2);
				Concentratoe_lv_title.addView(chlickll, params3);
			}
		}
	}

	/**
	 * ���ݿ���ʾ����
	 */
	public void setShowLoad() {
		list = new ArrayList<String[]>();
		if (listSq.size() == 0) {
			Concentratoe_lv
					.setAdapter(new ChartAdapter(ConcentratorActivity.this, list, titles.length, 0.03f, 0.97f, 2));
			return;
		}
		group.setText(hostID + "-" + cnt);

		int len = (listSq.size()) / 2;
		Log.e("1", "listSq.size()  " + listSq.size());
		int lens = (listSq.size()) % 2;
		for (int i = 0; i < len; i++) {
			String[] data = new String[] { ++num + "", listSq.get(i).getC(), listSq.get(i).getU(), listSq.get(i).getT(),
					(len + num) + "", listSq.get(len + i).getC(), listSq.get(len + i).getU(),
					listSq.get(len + i).getT() };
			list.add(data);
		}
		total_voltage.setText(listSq.get(0).getV()); // ���ѹ
		total_current.setText(listSq.get(0).getI()); // �����
		num = 0;
		if (lens == 1) {
			int cnt = listSq.size() - 1;
			String[] datas = new String[] { listSq.size() + "", listSq.get(listSq.size() - 1).getC(),
					listSq.get(listSq.size() - 1).getU(), listSq.get(listSq.size() - 1).getT(), "-", "-", "-", "-" };
			list.add(datas);
		}
		Concentratoe_lv.setAdapter(new ChartAdapter(ConcentratorActivity.this, list, titles.length, 0.03f, 0.97f, 2));

	}

	// wifi�ȵ㿪��
	public boolean setWifiApEnabled(boolean enabled) {
		if (enabled) { // disable WiFi in any case
			// wifi���ȵ㲻��ͬʱ�򿪣����Դ��ȵ��ʱ����Ҫ�ر�wifi
			wifiManager.setWifiEnabled(false);
		}
		try {
			// �ȵ��������
			WifiConfiguration apConfig = new WifiConfiguration();
			// �����ȵ������(���������ֺ���ӵ������ʲô��)PITE0000
			apConfig.SSID = "PITE129125";
			// apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			// �����ȵ������
			apConfig.preSharedKey = "12345678";
			// ͨ��������������ȵ�
			java.lang.reflect.Method method = wifiManager.getClass().getMethod("setWifiApEnabled",
					WifiConfiguration.class, Boolean.TYPE);
			// �����ȵ��״̬
			return (Boolean) method.invoke(wifiManager, apConfig, enabled);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * ��������
	 */
	public void setInsertData(PrcloModel mode) {
		try {
			Thread.sleep(20);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		int sqCnt = 1;
		HashMap<Integer, ArrayList<Prclo85GroupData.proGroup>> map = mode.getMap();
		prcHeader = mode.getPrcHeader();
		cnt = prcHeader.EquipID2;
		int len = map.get(0).size();
		byte[] timeBt = prcHeader.ObservationTime;
		String timeStr = "";
		for (int i = 0; i < timeBt.length; i++) {
			if (timeBt[i] < 10) {
				timeStr += 0;
			}
			timeStr += timeBt[i];
		}
		float rate = 100.0f;
		prcGroup = mode.getGroup();
		String[] data = null;
		List<String[]> listdata = new ArrayList<String[]>();
		for (int j = 0; j < map.size(); j++) {
			for (int i = 0; i < len; i++) {
				String batU = FormatUtils.setFormatGroupU(map.get(j).get(i).batteryV / 1000, 4);
				if (Float.parseFloat(batU) < 2.5) {
					rate = 10.0f;
				}
				data = new String[] { sqCnt++ + "", FormatUtils.setFormatGroupU(prcGroup.get(j).groupU / 100, 4),
						FormatUtils.setFormatGroupU(prcGroup.get(j).groupI / 100, 3),
						FormatUtils.setFormatGroupU(map.get(j).get(i).batteryV / 1000.0, 4),
						FormatUtils.setFormatGroupU(map.get(j).get(i).batteryR1 / 100, 3),
						(int) (map.get(j).get(i).batteryT) + "", (int) (map.get(j).get(i).batterySOC) + "",
						FormatUtils.setFormatGroupU(map.get(j).get(i).batteryR2 / 100, 3),
						FormatUtils.setFormatGroupU(map.get(j).get(i).batteryC2 / rate, 3) };
				listdata.add(data);
			}
		}
		isOk = sql.add("preson" + prcHeader.EquipID2, setTimeFarmat(timeStr.substring(2, 10)), listdata);
		if (isOk != -1) {
			try {
				Thread.sleep(100);
				listSq = sql.query("preson" + prcHeader.EquipID2);
				handler.sendEmptyMessage(6);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Log.e("9", "���ݿ������� " + isOk + "preson+ prcHeader.EquipID2 " + prcHeader.EquipID2);
		}
	}

	/**
	 * ����Spinner
	 */
	public void setSpAdapter() {
		listConcentrator_sp = new ArrayList<String>();
		if (listSq.size() < 1) {
			spStr = new ArrayAdapter<String>(this, R.layout.item_spinner, listConcentrator_sp);
			concentrator_sp.setAdapter(spStr);
			Log.e("7", "listSq.size() spinner :" + listSq.size());
			return;
		}
		group.setText(hostID + "-" + cnt);
		for (int i = listSq.size() - 1; i > 0; i--) {
			if (!listConcentrator_sp.contains(listSq.get(i).getTime())) {
				listConcentrator_sp.add(listSq.get(i).getTime());
			}
		}
		spStr = new ArrayAdapter<String>(this, R.layout.item_spinner, listConcentrator_sp);
		spStr.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		concentrator_sp.setAdapter(spStr);
	}

	// /**
	// * handler �������� ���յ� �ؼ�����Ϣ
	// */
	public void setHandler(int index, PrcloModel mode, byte[] prcBt, ReadDataHost readHost) {
		this.readHost = readHost;
		// this.bt = bt;
		this.mode = mode;
		this.prcBt = prcBt;
		handler.sendEmptyMessage(index); //
	}

	/**
	 * ����֪ͨ����������
	 * 
	 * @param index
	 */
	public void setUpHandler(int index) {
		handler.sendEmptyMessage(index);
	}

	public void setKeHuaHandler(int index) {
		handler.sendEmptyMessageDelayed(index, 2000);
	}

	public void setInternetTimer(int time) {
		if (timecnt == 0) {
			timecnt = 1;
			executorService.scheduleAtFixedRate(task, 0, time, TimeUnit.SECONDS);
			Log.e("3", "������ʱ" + executorService.isShutdown());
		}
		if (executorService.isShutdown() || executorService.isTerminated()) {
			timecnt = 0;
			timerStatus = true;
			executorService.scheduleAtFixedRate(task, 0, time, TimeUnit.SECONDS);
		}

	}

	/**
	 * ������ʱ
	 * 
	 * @param time
	 */
	public void setResrtTimer(int time) {
		executorService.scheduleAtFixedRate(taskRest, time, time, TimeUnit.SECONDS);
		Log.e("3", "������ʱ:  " + executorService.isShutdown());
		if (executorService.isShutdown() || executorService.isTerminated()) {
			executorService.scheduleAtFixedRate(taskRest, 0, time, TimeUnit.SECONDS);
		}

	}

	@Override
	protected void onDestroy() {
		if (task != null) {
			task.cancel();
		}
		readHost.setTimerDestroy();
		if (executorService != null) {
			executorService.shutdown();
		}
		closeWIFI();
		super.onDestroy();
	}

	/**
	 * ��ʽ��ʱ�� ����ʱ��
	 * 
	 * @param time
	 * @return
	 */
	public String setTimeFarmat(String time) {
		StringBuilder builder = new StringBuilder();
		builder.append(time);
		builder.insert(2, "-");
		builder.insert(5, "  ");
		builder.insert(9, ":");
		return builder.toString();
	}

	// Spinner ����
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		listSq = sql.oneQueryTime("preson" + cnt, listConcentrator_sp.get(position));
		if (listSq.size() == 0 && list != null) {
			list.clear();
		}
		handler.sendEmptyMessage(5);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	// ��WIFI�ķ���
	public void openWIFI() {
		if (!manger.isWifiEnabled()) {
			manger.setWifiEnabled(true);
			setToast("WIFI��");
			Log.e("4", "WIFI��:  ");
		}
	}

	// ��WIFI�ķ���
	public void closeWIFI() {
		if (manger.isWifiEnabled()) {
			try {
				manger.setWifiEnabled(false);
				executorService.shutdown();
				if (TimerUtil.group != null) {
					TimerUtil.group.shutdownGracefully();
				}
				if (TimerUtil.channelFuture != null) {
					TimerUtil.channelFuture.channel().close().sync();
				}
				Thread.sleep(5000);
				Log.e("4", "WIFI�ر�:  ");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setToast(String toast) {
		Toast.makeText(getApplicationContext(), toast, 0).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(this).setTitle("�˳�").setMessage("ȷ���˳�")
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();

						}
					}).setNegativeButton("ȡ��", null).show();
		}
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			return true;
		}
		return true;
	}

	/**
	 * ������������
	 */
	public void setResrtConnect() {
		ReadDataHost.resartCnt++;
		if (ReadDataHost.resartCnt > 2) {
			Log.e("4", "����������  " + ReadDataHost.resartCnt);
			closeWIFI();
			Intent intent = new Intent(TimerUtil.application.getApplicationContext(), ConcentratorActivity.class);
			PendingIntent restartIntent = PendingIntent.getActivity(TimerUtil.application.getApplicationContext(), 0,
					intent, Intent.FLAG_ACTIVITY_NEW_TASK);
			// �˳�����
			AlarmManager mgr = (AlarmManager) TimerUtil.application.getSystemService(Context.ALARM_SERVICE);
			mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 60000, restartIntent); // 10���Ӻ�����Ӧ��
			TimerUtil.application.finishActivity();
		}
	}

	public void restartApplication() {
		ReadDataHost.resartCnt++;
		if (ReadDataHost.resartCnt > 2) {
			closeWIFI();
			final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	}

	/**
	 * ������Ϣ
	 */
	public void sendHandler(byte[] bt) {
		Message msg = sendHandler.obtainMessage();
		msg.obj = bt;
		sendHandler.sendMessage(msg);
	}
}
