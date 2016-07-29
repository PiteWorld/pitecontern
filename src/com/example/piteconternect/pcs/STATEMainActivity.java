package com.example.piteconternect.pcs;

import com.example.piteconternect.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * ¹¤×÷×´Ì¬
 * 
 * @author Administrator
 *
 */
public class STATEMainActivity extends BaseActivitys {

	private TextView state_tv, state_tv2, state_tv3, state_tv4, state_tv5, state_tv6, state_tv7, state_tv8, state_tv9,
			state_tv10, state_tv11, state_tv12, state_tv13, state_tv14, state_tv15, state_tv16;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitie(R.string.state_title);
		initData();
	}

	private void initData() {
		state_tv = (TextView) findViewById(R.id.state_tv);
		state_tv2 = (TextView) findViewById(R.id.state_tv2);
		state_tv3 = (TextView) findViewById(R.id.state_tv3);
		state_tv4 = (TextView) findViewById(R.id.state_tv4);
		state_tv5 = (TextView) findViewById(R.id.state_tv5);
		state_tv6 = (TextView) findViewById(R.id.state_tv6);
		state_tv7 = (TextView) findViewById(R.id.state_tv7);
		state_tv8 = (TextView) findViewById(R.id.state_tv8);
		state_tv9 = (TextView) findViewById(R.id.state_tv9);
		state_tv10 = (TextView) findViewById(R.id.state_tv10);
		state_tv11 = (TextView) findViewById(R.id.state_tv11);
		state_tv12 = (TextView) findViewById(R.id.state_tv12);
		state_tv13 = (TextView) findViewById(R.id.state_tv13);
		state_tv14 = (TextView) findViewById(R.id.state_tv14);
		state_tv15 = (TextView) findViewById(R.id.state_tv15);
		state_tv16 = (TextView) findViewById(R.id.state_tv16);
		setText();
	}

	private void setText() {
		// ÉèÖÃÖµ
		if (getInfo() != null&&getInfo().size()>0) {
			state_tv.setText(WorkingMode(getInfo().get(0).getWorkingMode()));
			state_tv2.setText(otherStatus(getInfo().get(0).getInputPhaseSequence()));
			state_tv3.setText(otherStatus(getInfo().get(0).getBypassStatus()));
			state_tv4.setText(otherStatus(getInfo().get(0).getRectifieStatus()));
			state_tv5.setText(otherStatus(getInfo().get(0).getInverterSwitchStatus()));
			state_tv6.setText(otherStatus(getInfo().get(0).getInverterRunStatus()));
			state_tv7.setText(otherStatus(getInfo().get(0).getUPSTemperature()));
			state_tv8.setText(otherStatus(getInfo().get(0).getBatteryPolar()));
			state_tv9.setText(otherStatus(getInfo().get(0).getFanStatus()));
			state_tv10.setText(otherStatus(getInfo().get(0).getMachinelineStatus()));
			state_tv11.setText(otherStatus(getInfo().get(0).getFuseStatus()));
			state_tv12.setText(UPSMalfunction(getInfo().get(0).getUPSMalfunction()));
			state_tv13.setText(batteryStatus(getInfo().get(0).getBatteryStatus()));
			state_tv14.setText(loadStatus(getInfo().get(0).getLoadStatus()));
			state_tv15.setText(outStatus(getInfo().get(0).getOutputStatus()));
			state_tv16.setText(getInfo().get(0).getMachineTemperature() + "¡æ");
		}
	}

	@Override
	public View getcontent() {
		return View.inflate(STATEMainActivity.this, R.layout.activity_statemain, null);
	}

	/**
	 * ÅÐ¶Ïµ±Ç°µç³Ø×´Ì¬
	 */
	private int Status;

	/**
	 * ÅÐ¶Ïµç³Ø×´Ì¬
	 */
	private int batteryStatus(String str) {
		if (str.equals("0"))
			Status = R.string.string57;
		else if (str.equals("1"))
			Status = R.string.string58;
		else if (str.equals("2"))
			Status = R.string.string59;
		else 
			Status = R.string.string60;
		return Status;
	}

	/**
	 * ÅÐ¶Ï¸ºÔØ×´Ì¬
	 */
	private int loadStatus(String str) {
		if (str.equals("0"))
			Status = R.string.string57;
		else if (str.equals("1"))
			Status = R.string.string62;
		else 
			Status = R.string.string63;
		return Status;
	}

	/**
	 * ÅÐ¶ÏÊä³ö×´Ì¬
	 */
	private int outStatus(String str) {
		if (str.equals("0")) {
			Status = R.string.string58;
			//state_tv15.setTextColor(getResources().getColor(R.color.lvse));
		} else if (str.equals("1")) {
			Status = R.string.string64;
			//state_tv15.setTextColor(Color.YELLOW);
		} else  {
			Status = R.string.string65;
			//state_tv15.setTextColor(Color.GREEN);
		}
		return Status;
	}

	/**
	 * ÅÐ¶ÏÆäËû×´Ì¬
	 */
	private int otherStatus(String str) {
		if (str.equals("0")) {
			Status = R.string.string57;
			//getColor(getResources().getColor(R.color.lvse));
		} else  {
			Status = R.string.string58;
			//getColor(Color.RED);
		}
		return Status;
	}

	/**
	 * µ¥²¢»ú×´Ì¬
	 */
	private int WorkingMode(String str) {
		if (str.equals("0"))
			Status = R.string.string55;
		else 
			Status = R.string.string56;
		return Status;
	}

	/**
	 * ÅÐ¶Ïups Í¨Ñ¶¹ÊÕÏ
	 */
	private int UPSMalfunction(String str) {
		if (str.equals("0")){
			Status = R.string.string57;
			//state_tv12.setTextColor(getResources().getColor(R.color.lvse));
			}
		else {
			Status = R.string.string61;
			//state_tv12.setTextColor(Color.RED);
			}
		return Status;
	}

	/**
	 * state_tv2-tv11ÅÐ¶ÏÑÕÉ«µÄ·½·¨
	 */
	@SuppressLint("Override")
	public void getColor(int color) {
		state_tv2.setTextColor(color);
		state_tv3.setTextColor(color);
		state_tv4.setTextColor(color);
		state_tv5.setTextColor(color);
		state_tv6.setTextColor(color);
		state_tv7.setTextColor(color);
		state_tv8.setTextColor(color);
		state_tv9.setTextColor(color);
		state_tv10.setTextColor(color);
		state_tv11.setTextColor(color);
	}

	@Override
	public void updateData() {
		// TODO Auto-generated method stub
		setText();
	}
}
