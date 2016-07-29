package com.example.piteconternect;
import com.example.piteconternect.exection.MyApplication;
import com.example.piteconternect.pcs.PCSMainActivity;
import com.example.piteconternect.utils.TimerUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public abstract class  BaseActivity extends Activity {
	private TextView title;//ÿ��activity�ı���
	private LinearLayout content;//ÿ��activity�Ĳ�������
	private ImageButton base_image; //���ؼ�,
	private Button pcsBtn ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baseactivity_main);
		MyApplication.getInstance().addActivity(this);
		TimerUtil.context = this.getApplicationContext();
		init();
	}
	private void init() {
		title=(TextView) findViewById(R.id.Title);
		content=(LinearLayout) findViewById(R.id.content);
		base_image = (ImageButton) findViewById(R.id.base_image);
		pcsBtn = (Button) this.findViewById(R.id.PCS);
		base_image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		pcsBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),PCSMainActivity.class));
				
			}
		});
		//��������ļ�
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		content.addView(getcontent(),params);
	}
	public abstract View getcontent() ;
	
	/**
	 * �ı�Activity title �ķ���
	 */
	public void setTitie(String str){
		title.setText(str);
	}
	public void showToast(String string){
		Toast.makeText(BaseActivity.this , string, Toast.LENGTH_SHORT).show();
	}
	
}
