package com.example.piteconternect.pcs;

import com.example.piteconternect.R;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
	/**
	 * 系统输出
	 * @author Administrator
	 *
	 */
public class LOADMainActivity extends BaseActivitys {
	private TextView load_tv, load_tv2, load_tv3, load_tv4, load_tv5, load_tv6, load_tv7, load_tv8, load_tv9, load_tv10,
			load_tv11, load_tv12, load_tv13, load_tv14, load_tv15, load_tv16, load_tv17, load_tv18, load_tv19;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitie(R.string.load_title);
		initData();
	}

	private void initData() {
		load_tv = (TextView) findViewById(R.id.load_tv);
		load_tv2 = (TextView) findViewById(R.id.load_tv2);
		load_tv3 = (TextView) findViewById(R.id.load_tv3);
		load_tv4 = (TextView) findViewById(R.id.load_tv4);
		load_tv5 = (TextView) findViewById(R.id.load_tv5);
		load_tv6 = (TextView) findViewById(R.id.load_tv6);
		load_tv7 = (TextView) findViewById(R.id.load_tv7);
		load_tv8 = (TextView) findViewById(R.id.load_tv8);
		load_tv9 = (TextView) findViewById(R.id.load_tv9);
		load_tv10 = (TextView) findViewById(R.id.load_tv10);
		load_tv11= (TextView) findViewById(R.id.load_tv11);
		load_tv12 = (TextView) findViewById(R.id.load_tv12);
		load_tv13 = (TextView) findViewById(R.id.load_tv13);
		load_tv14 = (TextView) findViewById(R.id.load_tv14);
		load_tv15 = (TextView) findViewById(R.id.load_tv15);
		load_tv16 = (TextView) findViewById(R.id.load_tv16);
		load_tv17 = (TextView) findViewById(R.id.load_tv17);
		load_tv18 = (TextView) findViewById(R.id.load_tv18);
		load_tv19= (TextView) findViewById(R.id.load_tv19);
		//获取请求结果
		setText();
	}

	private void setText() {
		if(getInfo()!=null&&getInfo().size()>0){
			load_tv.setText(getInfo().get(0).getOutputUV());
			load_tv2.setText(getInfo().get(0).getOutputUA());
			load_tv3.setText(getInfo().get(0).getOutputULoadRate());
			load_tv4.setText(getInfo().get(0).getOutputUAP());
			load_tv5.setText(getInfo().get(0).getOutputApparentPowerU());
			//PF值
			load_tv6.setText(getInfo().get(0).getOutputPFU());
			
			load_tv7.setText(getInfo().get(0).getOutputVV());
			load_tv8.setText(getInfo().get(0).getOutputVA());
			load_tv9.setText(getInfo().get(0).getOutputVLoadRate());
			load_tv10.setText(getInfo().get(0).getOutputVAP());
			load_tv11.setText(getInfo().get(0).getOutputApparentPowerV());
			//PF值
			load_tv12.setText(getInfo().get(0).getOutputPFV());
			load_tv13.setText(getInfo().get(0).getOutputFrequency());
			
			load_tv14.setText(getInfo().get(0).getOutputWV());
			load_tv15.setText(getInfo().get(0).getOutputWA());
			load_tv16.setText(getInfo().get(0).getOutputWLoadRate());
			load_tv17.setText(getInfo().get(0).getOutputWAP());
			load_tv18.setText(getInfo().get(0).getOutputApparentPowerW());
			//PF值
			load_tv19.setText(getInfo().get(0).getOutputPFW());
		}
	}

	@Override
	public View getcontent() {
		// TODO Auto-generated method stub
		return View.inflate(LOADMainActivity.this, R.layout.activity_loadmain, null);
	}

	@Override
	public void updateData() {
		// TODO Auto-generated method stub
		setText();
	}
}
