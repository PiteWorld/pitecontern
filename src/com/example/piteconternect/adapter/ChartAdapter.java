package com.example.piteconternect.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.pite.model.Prclo85GroupData;
import com.example.piteconternect.R;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * @param <T>
 *            Õº±Ì  ≈‰∆˜
 */
public class ChartAdapter<T> extends BaseAdapter {
	private Context content;
	private int NumItem;
	private List<String[]> list;
	private LinearLayout ll;
	private LinearLayout chlickll;
	private float tvWight, viewWight;
	private int flags; // ±Í÷æ

	public ChartAdapter(Context content, List<String[]> list, int NumItem, float tvWight, float viewWight, int flags) {
		this.content = content;
		this.NumItem = NumItem;
		this.list = list;
		this.tvWight = tvWight;
		this.viewWight = viewWight;
		this.flags = flags;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyHolder holder = null;
		if (convertView == null) {
			holder = new MyHolder();
			holder.tex1 = new TextView[NumItem];
			convertView = LayoutInflater.from(content).inflate(R.layout.item, null);
			ll = (LinearLayout) convertView.findViewById(R.id.item_ll);
			for (int i = 0; i < NumItem; i++) {
				holder.tv = new TextView(content);
				holder.tv.setBackground(content.getResources().getDrawable(R.drawable.content_cell));
				holder.tex1[i] = holder.tv;
				holder.tv.setTag(i);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT, tvWight);
				params.gravity = Gravity.CENTER;
				holder.view = new View(content);
				holder.view.setBackground(content.getResources().getDrawable(R.drawable.line));
				LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT, viewWight);
				chlickll = new LinearLayout(content);
				LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT, 1.0f);
				if (i == NumItem - 1) {
					LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT, 1.0f);
					chlickll.addView(holder.tv, params4);
					ll.addView(chlickll, params3);
				} else {
					chlickll.addView(holder.tv, params);
					chlickll.addView(holder.view, params2);
					ll.addView(chlickll, params3);
				}
				convertView.setTag(holder);
			}
		} else {
			holder = (MyHolder) convertView.getTag();
		}
		for (int i = 0; i < holder.tex1.length; i++) {
			holder.tex1[i].setText(list.get(position)[i]);
			holder.tex1[i].setTextColor(Color.BLACK);
			holder.tex1[i].setTextSize(12);
			holder.tex1[i].setGravity(Gravity.CENTER);
		}
		return convertView;
	}

	static class MyHolder {
		private TextView tv;
		private View view;
		private TextView[] tex1 = null;
	}
}
