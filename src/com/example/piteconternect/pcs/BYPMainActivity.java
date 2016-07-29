package com.example.piteconternect.pcs;

import com.example.piteconternect.R;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * 旁路输入
 * 
 * @author Administrator
 *
 */
public class BYPMainActivity extends BaseActivitys {
	private TextView byp_tv, byp_tv3, byp_tv4, byp_tv5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitie(R.string.byp_title);
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		byp_tv = (TextView) findViewById(R.id.byp_tv);
		byp_tv3 = (TextView) findViewById(R.id.byp_tv3);
		byp_tv4 = (TextView) findViewById(R.id.byp_tv4);
		byp_tv5 = (TextView) findViewById(R.id.byp_tv5);
		// 加载数据
		setText();
	}

	private void setText() {
		if (getInfo() != null && getInfo().size() > 0) {
			Log.e("4", getInfo().get(0).getBypassUV()+"---------");
			byp_tv.setText(getInfo().get(0).getBypassUV());
			byp_tv3.setText(getInfo().get(0).getBypassVV());
			byp_tv4.setText(getInfo().get(0).getBypassInputFrequency());
			byp_tv5.setText(getInfo().get(0).getBypassWV());
		}
	}

	@Override
	public View getcontent() {
		// TODO Auto-generated method stub
		return View.inflate(BYPMainActivity.this, R.layout.activity_bypmain, null);
	}

	@Override
	public void updateData() {
		// TODO Auto-generated method stub
		setText();
	}
}
