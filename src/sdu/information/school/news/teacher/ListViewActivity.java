package sdu.information.school.news.teacher;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
























import sdu.information.school.news.teacher.R;
import sdu.information.school.news.adapter.MyListViewAdapter;
import sdu.information.school.news.instruments.DirverInformations;
import sdu.information.school.news.instruments.LoadedSQLHelper;
import sdu.information.school.news.instruments.WebServiceHelper;
import sdu.information.school.news.model.NewsListInfo;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewActivity extends Activity {
	private ImageView back_img;
	private ProgressDialog progressDialog;
	private String newstype;
	private ArrayList<HashMap<String, String>> newsList;
	private ListView listView;
	private MyListViewAdapter adapter;
	private TextView title;
	public static final String KEY_ID = "ID";
	public static final String KEY_TITLE = "TITLE";
	public static final String KEY_DATATIME = "DateAndTime";
	private String typeText;
	public static final String KEY_TITLELINK = "TitleLink";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);
		
		title=(TextView)findViewById(R.id.list_activity_title);
		
		Intent intent = getIntent();
		newstype=intent.getStringExtra("newsType");
		
		if(newstype.equals("0103")){
			typeText="学院新闻";
			title.setText(typeText);
		}
		else if(newstype.equals("0104")){
			typeText="学院通知";
			title.setText(typeText);
		}
		else if(newstype.equals("0105")){
			typeText="科研信息";
			title.setText(typeText);
		}
		else if(newstype.equals("0106")){
			typeText="学术交流";
			title.setText(typeText);
		}
		else if(newstype.equals("0107")){
			typeText="本科生教育";
			title.setText(typeText);
		}
		else if(newstype.equals("0108")){
			typeText="研究生培养";
			title.setText(typeText);
		}
		else if(newstype.equals("0102")){
			typeText="联播快讯";
			title.setText(typeText);
		}
		
		
		listView=(ListView)findViewById(R.id.list_activity_listview);
		back_img=(ImageView)findViewById(R.id.list_activity_back_img);
		back_img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.change_activity_left_in, R.anim.change_activity_left_out);
			}
		});
		
		if(isOpenNetwork()){
			progressDialog = new ProgressDialog(this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setCanceledOnTouchOutside(false);// 点击其他不消失
			progressDialog.setMax(100);
			progressDialog.setMessage("载入中。。。");
			progressDialog.show();
			// 设置progressDialog显示时屏幕不变暗 不影响其他操作
			Window window = progressDialog.getWindow();
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.dimAmount = 0f;
			window.setAttributes(lp);
			
			
			DownloadTask downloadTask = new DownloadTask();
			downloadTask.execute();
			
			
			
		}
		else{
			Toast.makeText(ListViewActivity.this,"没有网络连接", Toast.LENGTH_SHORT).show();
			SQLiteOpenHelper helper=new LoadedSQLHelper(this);
			SQLiteDatabase   db    =helper.getReadableDatabase();
			newsList = new ArrayList<HashMap<String, String>>();
			Cursor cursor=db.query("loaded", new String[]{"newsid","newstitle","newstime"}, "newstype=?", new String[]{newstype}, "", "", "id");
			while(cursor.moveToNext()){
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(KEY_ID, cursor.getString(cursor.getColumnIndex("newsid")));
				
				
				String str1=cursor.getString(cursor.getColumnIndex("newstime"));
				str1=str1.substring(0, 16);
				map.put(KEY_DATATIME, str1);
				
				map.put(KEY_TITLE, cursor.getString(cursor.getColumnIndex("newstitle")));
				newsList.add(map);
			}
			adapter = new MyListViewAdapter(ListViewActivity.this, newsList);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new MyListViewListener());
			db.close();
			
		}
		
	}
	
	public void finish(){
		super.finish();
		overridePendingTransition(R.anim.change_activity_left_in, R.anim.change_activity_left_out);
	}
	
	class DownloadTask extends AsyncTask<Integer, Integer, String>{

		@Override
		protected String doInBackground(Integer... params) {
			try {
				getList();
			}
			catch(Exception e){
				//Toast.makeText(ListViewActivity.this,"服务器链接异常", Toast.LENGTH_SHORT).show();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result){
			if(newsList.size()!=0){
				adapter = new MyListViewAdapter(ListViewActivity.this, newsList);
				listView.setAdapter(adapter);
				progressDialog.dismiss();
				
				listView.setOnItemClickListener(new MyListViewListener());
				
			}
			else{
				progressDialog.dismiss();
				Toast.makeText(ListViewActivity.this,"无数据", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	
	private void getList() {
		newsList = new ArrayList<HashMap<String, String>>();
		WebServiceHelper webServiceHelper = new WebServiceHelper();
			List<NewsListInfo> tasks = webServiceHelper.getnews(newstype,0,this);
			for (int i = 0; i < tasks.size(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(KEY_ID, tasks.get(i).getNewsId());
				
				String str1=tasks.get(i).getTime();
				str1=str1.substring(0, 16);
				map.put(KEY_DATATIME, str1);
				
				String str = tasks.get(i).getTitle();
				str.substring(0, str.length() - 2);
				map.put(KEY_TITLE, str);
				
				map.put(KEY_TITLELINK, tasks.get(i).getTitleLink());
				
				newsList.add(map);
			}
	}

	private boolean isOpenNetwork() {  
		ConnectivityManager connManager = (ConnectivityManager)getSystemService(ListViewActivity.CONNECTIVITY_SERVICE);  
		if(connManager.getActiveNetworkInfo() != null) {  
			return connManager.getActiveNetworkInfo().isAvailable();  
		}  
		return false;  
	}
	
	
	class MyListViewListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long id) {
			
			view.setBackgroundColor(Color.parseColor("#f0f0f0"));
			HashMap<String, String> map = newsList.get(position);
			
			TextView item_tex=(TextView)view.findViewById(R.id.news_item_title);
			String link="";
			if(map.get(KEY_TITLELINK)!=null){
				link = map.get(KEY_TITLELINK).toString();
			}
			//Log.v("tag", "link是+"+link+"1");
			
			
			String newsId = map.get(KEY_ID).toString();
			String title = map.get(KEY_TITLE).toString();
			
			item_tex.setText(title);
			item_tex.getPaint().setFakeBoldText(false);
			
			SharedPreferences isRead = getSharedPreferences("isRead", Activity.MODE_PRIVATE);
			Editor edit = isRead.edit();
			edit.putString(newsId, "").commit();
			if(link.equals("anyType{}")||link.equals("")|| link.equals(null)){
				gotonewsinfo(newsId,title);
			}
			else{
				Intent i = new Intent(Intent.ACTION_VIEW);
	            i.setData(Uri.parse(link));
	            startActivity(i);

			}
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public void gotonewsinfo(String newsId,String title) {
		Intent intent = new Intent();
		intent.setClass(ListViewActivity.this, NewsDetail.class);
		intent.putExtra("newsId", newsId);
		intent.putExtra("typeText", typeText);
		intent.putExtra("title", title);
		startActivity(intent);
		overridePendingTransition(R.anim.change_activity_right_in, R.anim.change_activity_right_out);
	}
	

}
