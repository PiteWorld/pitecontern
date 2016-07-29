package com.example.piteconternect.pcs;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.piteconternect.ConcentratorActivity;
import com.example.piteconternect.R;
import com.example.piteconternect.exection.MyApplication;
import com.example.piteconternect.kehua.tool.kehua1234;
import com.example.piteconternect.kehua.tool.kehua235;
import com.example.piteconternect.kehua.tool.kehua2659;
import com.example.piteconternect.kehua.tool.kehua569;
import com.example.piteconternect.kehua.tool.kehuaProData04;
import com.example.piteconternect.kehua.tool.kehuaProl;
import com.example.piteconternect.pcs.BaseActivitys.MyBroadCast;
import com.example.piteconternect.utils.PCSInfo;
import com.example.piteconternect.utils.TimerUtil;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android_serialport_api.keHuaSerialPortService;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseActivitys extends Activity {
	private TextView title;// 每个activity的标题
	private LinearLayout content;// 每个activity的布局内容
	private ImageButton back;
	public static List<PCSInfo> infos = null;
	private kehuaProl kehuaProl;
	private MyBroadCast broadCast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.basesactivity);
		DisplayMetrics dis = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dis);
		MyApplication.getInstance().addActivity(this);
		android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
		p.height = (int) (dis.heightPixels * 0.8); // 高度设置为屏幕的0.8
		p.width = (int) (dis.widthPixels * 0.8); // 宽度设置为屏幕的0.8
		getWindow().setAttributes(p);
		TimerUtil.base = this;
		init();
		broadCast = new MyBroadCast();
		IntentFilter inflater = new IntentFilter();
		inflater.addAction(TimerUtil.action);
		registerReceiver(broadCast, inflater);
		if (keHuaSerialPortService.kehuaProl != null) {
			addData(keHuaSerialPortService.kehuaProl);
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if (keHuaSerialPortService.kehuaProl != null) {
					addData(keHuaSerialPortService.kehuaProl);
					updateData();
				}
				break;
			}
		}

	};

	public List<PCSInfo> getInfo() {

		return infos;
	}

	public class MyBroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(TimerUtil.action)) {
				// addData();
				updateData();
				// Log.e("tag", "接受到了广播");
			}
		}
	}

	private void addData(kehuaProl kehuaProl) {
		// 6000-6037

		kehuaProData04 kehuaProData04 = kehuaProl.getkehuaProData04();
		kehua1234 kehua1234 = kehuaProl.getkehua1234(); // 6038-6041
		kehua235 kehua235 = kehuaProl.getkehua235(); // 6042-6043
		kehua569 kehua569 = kehuaProl.getkehua569(); // 6044 -6058
		kehua2659 kehua2659 = kehuaProl.getkehua2659(); // 状态 6000 -6011
		PCSInfo info = new PCSInfo();
		info.setInputUV(getFloat(kehuaProData04.Mans1));
		info.setInputVV(getFloat(kehuaProData04.Mans2));
		info.setInputWV(getFloat(kehuaProData04.Mans3));
		info.setInputFrequency(getFloat(kehuaProData04.Mans4));
		info.setBypassUV(getFloat(kehuaProData04.Mans5));
		info.setBypassVV(getFloat(kehuaProData04.Mans6));
		info.setBypassWV(getFloat(kehuaProData04.Mans7));
		info.setBypassInputFrequency(getFloat(kehuaProData04.Mans8));
		info.setBatteryVoltage((kehuaProData04.Mans9 * 0.1 + ""));
		info.setBatteryCurrent(getFloat(kehuaProData04.Mans10));
		info.setBatteryTemperature(getFloat(kehuaProData04.Mans11));
		info.setBatteryPerformanceState(String.valueOf(kehuaProData04.Mans12));
		info.setOutputUV(getFloat(kehuaProData04.Mans13));
		info.setOutputVV(getFloat(kehuaProData04.Mans14));
		info.setOutputWV(getFloat(kehuaProData04.Mans15));
		info.setOutputUA(getFloat(kehuaProData04.Mans16));
		info.setOutputVA(getFloat(kehuaProData04.Mans17));
		info.setOutputWA(getFloat(kehuaProData04.Mans18));
		info.setOutputULoadRate(kehuaProData04.Mans19 + "");
		info.setOutputVLoadRate(kehuaProData04.Mans20 + "");
		info.setOutputWLoadRate(kehuaProData04.Mans21 + "");
		info.setOutputUAP(getFloat(kehuaProData04.Mans22));
		info.setOutputVAP(getFloat(kehuaProData04.Mans23));
		info.setOutputWAP(getFloat(kehuaProData04.Mans24));
		info.setOutputApparentPowerU(getFloat(kehuaProData04.Mans25));
		info.setOutputApparentPowerV(getFloat(kehuaProData04.Mans26));
		info.setOutputApparentPowerW(getFloat(kehuaProData04.Mans27));
		info.setOutputPFU(getFloat3(kehuaProData04.Mans28));
		info.setOutputPFV(getFloat3(kehuaProData04.Mans29));
		info.setOutputPFW(getFloat3(kehuaProData04.Mans30));
		info.setOutputFrequency(getFloat1(kehuaProData04.Mans31));
		info.setBatteryStatus(String.valueOf(kehuaProData04.Mans32));
		info.setLoadStatus(String.valueOf(kehuaProData04.Mans33));
		info.setOutputStatus(String.valueOf(kehuaProData04.Mans34));
		info.setMachineTemperature(String.valueOf(kehuaProData04.Mans35));

		info.setRunState(String.valueOf(kehuaProData04.Mans36));
		info.setPriceType(String.valueOf(kehuaProData04.Mans37));
		info.setCurrentPrice(getFloat5(kehuaProData04.Mans38));

		info.setInputP(getFloat(kehua1234.Mans39));
		info.setOutputP(getFloat(kehua1234.Mans40));

		info.setBatteryU(getFloat(kehua235.Mans42));
		info.setBatteryI(getFloat(kehua235.Mans43));

		info.setTotalInputElectricity(getFloat(kehua569.Mans44));
		info.setTotalInputElectricityFees(getFloat(kehua569.Mans45));
		info.setTotalOutputElectricity(getFloat(kehua569.Mans46));
		info.setTotalOutputElectricityFees(getFloat(kehua569.Mans47));
		info.setTotalProfit(getFloat(kehua569.Mans48));

		info.setWorkingMode(String.valueOf(kehua2659.Mans50));
		info.setInputPhaseSequence(String.valueOf(kehua2659.Mans51));
		info.setBypassStatus(String.valueOf(kehua2659.Mans52));
		info.setRectifieStatus(String.valueOf(kehua2659.Mans53));
		info.setInverterSwitchStatus(String.valueOf(kehua2659.Mans54));
		info.setInverterRunStatus(String.valueOf(kehua2659.Mans55));
		info.setUPSTemperature(String.valueOf(kehua2659.Mans56));
		info.setBatteryPolar(String.valueOf(kehua2659.Mans57));
		info.setFanStatus(String.valueOf(kehua2659.Mans58));
		info.setMachinelineStatus(String.valueOf(kehua2659.Mans59));
		info.setFuseStatus(String.valueOf(kehua2659.Mans60));
		info.setUPSMalfunction(String.valueOf(kehua2659.Mans61));
		infos = new ArrayList<PCSInfo>();
		infos.add(info);
		Log.e("4", "单机状态：" + kehua2659.Mans50 + " 电压：  " + kehua235.Mans42 + " 电流 " + kehua235.Mans43
				+ "  getInfo().get(0).getBypassUV()  " + getInfo().get(0).getBypassUV());
		// Log.e("infos", infos.toString() + "---");
	}

	// private upBreadCast cast =null ;
	private void init() {
		title = (TextView) findViewById(R.id.Title);
		content = (LinearLayout) findViewById(R.id.content);
		back = (ImageButton) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// 添加内容文件
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		content.addView(getcontent(), params);
	}

	public abstract View getcontent();

	public abstract void updateData();

	/**
	 * 改变Activity title 的方法
	 */
	public void setTitie(int str) {
		title.setText(str);
	}

	/**
	 * 隐藏返回键的方法
	 * 
	 * @param string
	 */
	public void setVisibility(int vis) {
		back.setVisibility(vis);
	}

	public void showToast(String string) {
		Toast.makeText(BaseActivitys.this, string, Toast.LENGTH_SHORT).show();
	}

	public void setHandler(int index, kehuaProl kehuaProl) {
		this.kehuaProl = kehuaProl;
		handler.sendEmptyMessage(index);
	}

	/**
	 * 保留0.1进度
	 */
	private String getFloat(float number) {
		DecimalFormat decimalFormat = new DecimalFormat("##0.0");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		String p = decimalFormat.format(number * 0.1);// format 返回的是字符串
		return p;
	}

	/**
	 * 保留1%进度
	 */
	private String getFloat3(float number) {
		DecimalFormat decimalFormat = new DecimalFormat("##0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		String p = decimalFormat.format(number * 0.01);// format 返回的是字符串
		return p;
	}

	/**
	 * 保留1%进度
	 */
	private String getFloat1(float number) {
		DecimalFormat decimalFormat = new DecimalFormat("##0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		String p = decimalFormat.format(number * 0.1);// format 返回的是字符串
		return p;
	}

	/**
	 * 当前电价
	 */
	private String getFloat5(float number) {
		DecimalFormat fnum = new DecimalFormat("##0.0000");
		String dd = fnum.format(number * 0.0001);
		return dd + "";
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(broadCast);
		super.onDestroy();
	}
}
