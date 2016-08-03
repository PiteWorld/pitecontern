package com.example.piteconternect;

import java.io.IOException;

import com.example.piteconternect.exection.MyApplication;
import com.example.piteconternect.utils.SettingData;
import com.example.piteconternect.utils.Sharedps;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingDialog extends Activity implements OnClickListener {
	private EditText Setting_Total_battery, Setting_group, Setting_et, Setting_et2, Setting_et3, Setting_et4,
			Setting_et5, Setting_et6, Setting_et7, Setting_et8, Setting_et9, Setting_et10, Setting_et11, Setting_et12,
			Setting_et13, Setting_et14;

	private TextView groupNum;// 当前电池组
	private Button Setting_bt, Setting_save;
	private String str=null;// 存储每组电池节数
	private int num;// 总组数
	private int a = 1;// 存了几次电池节数
	private Context context;
	private Sharedps sharedps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting);
		context = this;
		DisplayMetrics dis = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dis);
		MyApplication.getInstance().addActivity(this);
		android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
		p.height = (int) (dis.heightPixels * 0.8); // 高度设置为屏幕的0.8
		p.width = (int) (dis.widthPixels * 0.8); // 宽度设置为屏幕的0.8
		getWindow().setAttributes(p);
		init();
		if (sharedps.read() == null) {
			initData();
		} else {
			readData();
		}
	}

	/***
	 * 读取保存的数据
	 */
	private void readData() {
		SettingData settingdata = sharedps.read();
		Setting_Total_battery.setText(settingdata.getSetting_Total_battery());
		Setting_group.setText(settingdata.getSetting_group().split("--")[0]);
		Setting_et.setText(settingdata.getSetting_et());
		Setting_et2.setText(settingdata.getSetting_et2());
		Setting_et3.setText(settingdata.getSetting_et3());
		Setting_et4.setText(settingdata.getSetting_et4());
		Setting_et5.setText(settingdata.getSetting_et5());
		Setting_et6.setText(settingdata.getSetting_et6());
		Setting_et7.setText(settingdata.getSetting_et7());
		Setting_et8.setText(settingdata.getSetting_et8());
		Setting_et9.setText(settingdata.getSetting_et9());
		Setting_et10.setText(settingdata.getSetting_et10());
		Setting_et11.setText(settingdata.getSetting_et11());
		Setting_et12.setText(settingdata.getSetting_et12());
		Setting_et13.setText(settingdata.getSetting_et13());
		Setting_et14.setText(settingdata.getSetting_et14());
	}

	/***
	 * 数据的初始值
	 */
	private void initData() {
		// 初始化值
		Setting_Total_battery.setText("1");
		Setting_group.setText("1");
		Setting_et.setText("0.1");
		Setting_et2.setText("0.1");
		Setting_et3.setText("0.1");
		Setting_et4.setText("0.1");
		Setting_et5.setText("0.1");
		Setting_et6.setText("0.1");
		Setting_et7.setText("0.1");
		Setting_et8.setText("0.1");
		Setting_et9.setText("1");
		Setting_et10.setText("1");
		Setting_et11.setText("0.001");
		Setting_et12.setText("0.001");
		Setting_et13.setText("0.001");
		Setting_et14.setText("0.001");
	}

	private void init() {
		Setting_bt = (Button) findViewById(R.id.Setting_bt);
		Setting_save = (Button) findViewById(R.id.Setting_save);
		Setting_bt.setOnClickListener(this);
		Setting_save.setOnClickListener(this);
		groupNum = (TextView) findViewById(R.id.groupNum);
		Setting_Total_battery = (EditText) findViewById(R.id.Setting_Total_battery);
		Setting_group = (EditText) findViewById(R.id.Setting_group);
		Setting_et = (EditText) findViewById(R.id.Setting_et);
		Setting_et2 = (EditText) findViewById(R.id.Setting_et2);
		Setting_et3 = (EditText) findViewById(R.id.Setting_et3);
		Setting_et4 = (EditText) findViewById(R.id.Setting_et4);
		Setting_et5 = (EditText) findViewById(R.id.Setting_et5);
		Setting_et6 = (EditText) findViewById(R.id.Setting_et6);
		Setting_et7 = (EditText) findViewById(R.id.Setting_et7);
		Setting_et8 = (EditText) findViewById(R.id.Setting_et8);
		Setting_et9 = (EditText) findViewById(R.id.Setting_et9);
		Setting_et10 = (EditText) findViewById(R.id.Setting_et10);
		Setting_et11 = (EditText) findViewById(R.id.Setting_et11);
		Setting_et12 = (EditText) findViewById(R.id.Setting_et12);
		Setting_et13 = (EditText) findViewById(R.id.Setting_et13);
		Setting_et14 = (EditText) findViewById(R.id.Setting_et14);
		sharedps = new Sharedps(context);
	}

	/**
	 * 获取需要保存的数据
	 * 
	 * @throws IOException
	 */
	public SettingData saveData() {
		SettingData settingdata = SettingData.getInstart();
		settingdata.setSetting_Total_battery(Setting_Total_battery.getText().toString());
		settingdata.setSetting_group(str);
		settingdata.setSetting_et(Setting_et.getText().toString());
		settingdata.setSetting_et2(Setting_et2.getText().toString());
		settingdata.setSetting_et3(Setting_et3.getText().toString());
		settingdata.setSetting_et4(Setting_et4.getText().toString());
		settingdata.setSetting_et5(Setting_et5.getText().toString());
		settingdata.setSetting_et6(Setting_et6.getText().toString());
		settingdata.setSetting_et7(Setting_et7.getText().toString());
		settingdata.setSetting_et8(Setting_et8.getText().toString());
		settingdata.setSetting_et9(Setting_et9.getText().toString());
		settingdata.setSetting_et10(Setting_et10.getText().toString());
		settingdata.setSetting_et11(Setting_et11.getText().toString());
		settingdata.setSetting_et12(Setting_et12.getText().toString());
		settingdata.setSetting_et13(Setting_et13.getText().toString());
		settingdata.setSetting_et14(Setting_et14.getText().toString());
		return settingdata;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 每组电池节数
		case R.id.Setting_bt:
			if(getGroupData()==null)
				break;;
			a++;
			groupNum.setText("组" + a + "电池数");
			break;
		// 设置确定键
		case R.id.Setting_save:
			if (Setting_Total_battery.getText().toString().trim().equals("") || a != num) {
				Toast.makeText(context, "请输入正确的电池数", Toast.LENGTH_SHORT).show();
				return;
			}
			if (sharedps.Data(saveData())) {
				Toast.makeText(context, R.string.string100, Toast.LENGTH_SHORT).show();
			} else {
				Log.e("tag", "保存失败");
			}
			break;
		}
	}

	/***
	 * 获取每组电池数量
	 */
	private String getGroupData() {
		num = Integer.valueOf(Setting_Total_battery.getText().toString().trim());
		String setting_group = Setting_group.getText().toString().trim();
		if (setting_group != null && setting_group.equals(""))
			str += setting_group + "--";
		else{
			Toast.makeText(context, "请输入电池总组数", Toast.LENGTH_SHORT).show();
		}
		return str;
	}
}
