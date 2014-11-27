package sdu.information.school.news.adapter;

import java.util.ArrayList;
import java.util.HashMap;










import sdu.information.school.news.teacher.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyListViewAdapter extends BaseAdapter {
	
	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	
	public static final String KEY_ID = "ID";
	public static final String KEY_TITLE = "TITLE";
	public static final String KEY_DATATIME = "DateAndTime";
	
	
	public MyListViewAdapter(Activity a, ArrayList<HashMap<String, String>> d){
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View news_item_view=null;
		if(convertView==null){
			news_item_view=inflater.inflate(R.layout.listview_news_item, null);
		}
		else{
			news_item_view=convertView;
		}
		
		TextView title = (TextView) news_item_view.findViewById(R.id.news_item_title); // 标题
		TextView time = (TextView) news_item_view.findViewById(R.id.news_item_time); // 时间
		HashMap<String, String> item = new HashMap<String, String>();
		item=data.get(position);
		SharedPreferences isRead = activity.getSharedPreferences("isRead", Activity.MODE_PRIVATE);
		if(isRead.contains(item.get(KEY_ID))){
			news_item_view.setBackgroundColor(Color.parseColor("#f0f0f0"));
			title.setText(item.get(KEY_TITLE));
		}
		else{
			title.setText(item.get(KEY_TITLE));
			TextPaint tp = title.getPaint();
			tp.setFakeBoldText(true);
		}
		
		
		time.setText(item.get(KEY_DATATIME));
		return news_item_view;
	}

}
