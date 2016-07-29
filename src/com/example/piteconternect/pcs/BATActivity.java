package com.example.piteconternect.pcs;

import com.example.piteconternect.R;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * �������
 * 
 * @author Administrator
 *
 */
public class BATActivity extends BaseActivitys {
	private TextView bata_tv, bata_tv2, bata_tv3, bata_tv4, bata_tv5, bata_tv6, bata_tv7, bata_tv8, bata_tv9,
			bata_current;//��ʾΪ����ŵ����
	/**
	 * �жϵ�ǰ���״̬
	 */
	private int Status;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitie(R.string.string21);
		initData();
	}

	private void initData() {
		bata_tv = (TextView) findViewById(R.id.bata_tv);
		bata_tv2 = (TextView) findViewById(R.id.bata_tv2);
		bata_tv3 = (TextView) findViewById(R.id.bata_tv3);
		bata_tv4 = (TextView) findViewById(R.id.bata_tv4);
		bata_tv5 = (TextView) findViewById(R.id.bata_tv5);
		bata_current = (TextView) findViewById(R.id.bata_current);
		setText();
	}

	private void setText() {
		if (getInfo() != null&&getInfo().size()>0) {
			bata_tv.setText(getInfo().get(0).getBatteryVoltage());
			// �жϵ�ǰΪ��ŵ����
			if (Double.valueOf(getInfo().get(0).getBatteryCurrent().trim()) > 0)
				bata_current.setText(R.string.string23);
			else
				bata_current.setText(R.string.string78);
				bata_tv2.setText(getInfo().get(0).getBatteryCurrent());
			bata_tv3.setText(getInfo().get(0).getBatteryTemperature() + "��");
			// �жϵ�ǰ״̬
			if (getInfo().get(0).getBatteryPerformanceState().equals("0"))
				bata_tv4.setText(R.string.string37);
			else
				bata_tv4.setText(R.string.string38);
		}
	}

	@Override
	public View getcontent() {
		return View.inflate(BATActivity.this, R.layout.batactivity, null);
	}
	/**
	 * �жϵ��״̬
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
	/* * �ж�����״̬
	 */
	private int otherStatus(String str) {
		if (str.equals("0"))
			Status = R.string.string57;
		else 
			Status = R.string.string58;
		return Status;
	}

	@Override
	public void updateData() {
		setText();
	}

}
