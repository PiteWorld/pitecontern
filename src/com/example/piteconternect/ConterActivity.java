package com.example.piteconternect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ConterActivity extends BaseActivity {
	private Button conter_bt_confirm;
	private EditText Savle, hostID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		conter_bt_confirm = (Button) findViewById(R.id.conter_bt_confirm);
		Savle = (EditText) this.findViewById(R.id.num_et);
		hostID = (EditText) this.findViewById(R.id.conter_et);
		conter_bt_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Savle.getText().toString().trim() == null || Savle.getText().toString().length() < 1) {
					showToast("ÇëÊäÈëÖ÷»úºÅ");
					return;
				} else {
					Intent intent = new Intent(ConterActivity.this, ConcentratorActivity.class);
					intent.putExtra("host_number", hostID.getText().toString());
					intent.putExtra("savle_number", Savle.getText().toString());
					startActivity(intent);
					finish();
				}

			}
		});

	}

	@Override
	public View getcontent() {
		return View.inflate(ConterActivity.this, R.layout.activity_conter_main, null);
	}

}
