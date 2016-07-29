package com.example.piteconternect.pcs;

import com.example.piteconternect.R;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * ÊÐµçÊäÈë
 * 
 * @author Administrator
 *
 */
public class MIANMainActivity extends BaseActivitys {
	private TextView mian_tv, mian_tv2,mian_tv3, mian_tv4, mian_tv5, mian_tv6, mian_tv7, mian_tv8, mian_tv9, mian_tv10, mian_tv11,
			mian_tv12, mian_tv13, mian_tv14, mian_tv15, mian_tv16;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitie(R.string.mian_title);
		initData();
	}

	private void initData() {
		mian_tv = (TextView) findViewById(R.id.mian_tv);
		mian_tv6 = (TextView) findViewById(R.id.mian_tv6);
		mian_tv11 = (TextView) findViewById(R.id.mian_tv11);
		mian_tv12= (TextView) findViewById(R.id.mian_tv12);
		setText();
	}

	private void setText() {
		if (getInfo() != null&&getInfo().size()>0) {
			mian_tv.setText(getInfo().get(0).getInputUV());
			mian_tv6.setText(getInfo().get(0).getInputVV());
			
			mian_tv11.setText(getInfo().get(0).getInputFrequency());
			
			mian_tv12.setText(getInfo().get(0).getInputWV());
		}
	}

	@Override
	public View getcontent() {
		return View.inflate(MIANMainActivity.this, R.layout.activity_mianmain, null);
	}

	@Override
	public void updateData() {
		// TODO Auto-generated method stub
		setText();
	}

}
