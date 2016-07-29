package com.example.piteconternect.pcs;

import com.example.piteconternect.R;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * 储能实时信息
 * 
 * @author Administrator
 *
 */
public class StoredEnergyactivity extends BaseActivitys {
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11,tv30;//设置电价颜色

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitie(R.string.StoredEnergy);
		initData();
	}

	// 绑定id,加载数据
	private void initData() {
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv5 = (TextView) findViewById(R.id.tv5);
		tv6 = (TextView) findViewById(R.id.tv6);
		tv7 = (TextView) findViewById(R.id.tv7);
		tv8 = (TextView) findViewById(R.id.tv8);
		tv9 = (TextView) findViewById(R.id.tv9);
		tv10 = (TextView) findViewById(R.id.tv10);
		tv11 = (TextView) findViewById(R.id.tv11);
		tv30 = (TextView) findViewById(R.id.tv30);
		// 添加数据
		setText();
	}

	private void setText() {
		if (getInfo() != null&&getInfo().size()>0) {
			// 判断运行状态
			String str = getInfo().get(0).getRunState();
			if (str.equals("0"))
				tv1.setText(R.string.string36);
			else if (str.equals("1"))
				tv1.setText(R.string.string37);
			else
				tv1.setText(R.string.string38);
			tv2.setText(getInfo().get(0).getTotalInputElectricity());
			//判断当前电价类型+当前电价
			tv3.setText("|"+getInfo().get(0).getCurrentPrice());
			tv30.setText(getCurrentPrice(getInfo().get(0).getPriceType()));
			tv4.setText(getInfo().get(0).getTotalInputElectricityFees());
			tv5.setText(getInfo().get(0).getInputP());
			tv6.setText(getInfo().get(0).getTotalOutputElectricity());
			tv7.setText(getInfo().get(0).getOutputP());
			tv8.setText(getInfo().get(0).getTotalOutputElectricityFees());
			tv9.setText(getInfo().get(0).getBatteryU());
			tv10.setText(getInfo().get(0).getTotalProfit());
			tv11.setText(getInfo().get(0).getBatteryI());
		}
	}

	@Override
	public View getcontent() {
		return View.inflate(StoredEnergyactivity.this, R.layout.stored_energyactivity_main, null);
	}
	/**
	 * 判断当前电价状态 设置颜色
	 * 
	 */
	public int getCurrentPrice(String str){
		if(str.equals("0")){
			//tv30.setTextColor(Color.RED);
		return R.string.string66;
		}
		else if(str.equals("1")){
			//tv30.setTextColor(Color.YELLOW);
			return R.string.string67;
		}
		else if(str.equals("2")){
			//tv30.setTextColor(Color.GREEN);
			return R.string.string68;
			}
		return R.string.string66;
	}

	@Override
	public void updateData() {
		// TODO Auto-generated method stub
		setText();
	}
}
