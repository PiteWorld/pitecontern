package com.example.piteconternect;

import com.example.piteconternect.exection.MyApplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingDialog extends Activity implements OnClickListener {
	private EditText Setting_Total_battery, Setting_group, setting_et1, setting_et2, setting_et3, setting_et4,
			setting_et5, setting_et6, setting_et7, setting_et8, setting_et11, setting_et12, setting_et13, setting_et14,
			setting_et15, setting_et16, setting_et17, setting_et18, setting_et21, setting_et22, setting_et23,
			setting_et26, setting_et27, setting_et29, setting_et30, setting_et31, setting_et32, setting_et33,
			setting_et36, setting_et37, setting_et39, setting_et40;
	private TextView groupNum;//当前电池组
	private Button Setting_bt,Setting_save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting);
		DisplayMetrics dis = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dis);
		MyApplication.getInstance().addActivity(this);
		android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
		p.height = (int) (dis.heightPixels * 0.8); // 高度设置为屏幕的0.8
		p.width = (int) (dis.widthPixels * 0.8); // 宽度设置为屏幕的0.8
		getWindow().setAttributes(p);
		init();
	}

	private void init() {
		Setting_bt = (Button) findViewById(R.id.Setting_bt);
		Setting_save = (Button) findViewById(R.id.Setting_save);
		Setting_bt.setOnClickListener(this);
		Setting_save.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		}
	}
}
