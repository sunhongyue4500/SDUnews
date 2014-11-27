package sdu.information.school.news.teacher;



import sdu.information.school.news.teacher.R;
import sdu.information.school.news.instruments.LoadedSQLHelper;
import sdu.information.school.news.instruments.WebServiceHelper;


import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsDetail extends Activity {
	public static final String KEY_CONTENT = "content";
	private ProgressDialog progressDialog;
	private String newsId, typeText, title, content;
	private ImageView img_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_detail);
		getinfo();
		img_back=(ImageView)findViewById(R.id.news_detail_activity_back_img);
		img_back.setOnClickListener(new OnClickListener() {
			
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
			DownloadTask download = new DownloadTask();
			download.execute();
			
		}
		else{
			Toast.makeText(NewsDetail.this,"没有网络连接", Toast.LENGTH_SHORT).show();
			SQLiteOpenHelper helper=new LoadedSQLHelper(this);
			SQLiteDatabase   db    =helper.getReadableDatabase();
			Cursor cursor=db.query("loaded", new String[]{"newscontent"}, "newsid=?", new String[]{newsId}, "", "", "id");
			String str=null;
			while(cursor.moveToNext()){
				TextView textView = (TextView) findViewById(R.id.news_detail_content);
				str=cursor.getString(cursor.getColumnIndex("newscontent"));
			}
			TextView textView = (TextView) findViewById(R.id.news_detail_content);
			if(str!=null){
				textView.setText(Html.fromHtml(str));
			}
			db.close();
		}
		
		
	}
	
	public void finish(){
		super.finish();
		overridePendingTransition(R.anim.change_activity_left_in, R.anim.change_activity_left_out);
	}
	
	private void getinfo() {
		Bundle extras = getIntent().getExtras();// 获得传值
		newsId = extras.getString("newsId");
		typeText = extras.getString("typeText");
		title = extras.getString("title");
		TextView textView = (TextView) findViewById(R.id.news_detail_activity_title);
		TextView textView2 = (TextView) findViewById(R.id.news_detail_title);
		textView.setText(typeText);
		textView2.setText(title);
	}
	
	private boolean isOpenNetwork() {  
		ConnectivityManager connManager = (ConnectivityManager)getSystemService(ListViewActivity.CONNECTIVITY_SERVICE);  
		if(connManager.getActiveNetworkInfo() != null) {  
			return connManager.getActiveNetworkInfo().isAvailable();  
		}  
		return false;  
	}
	
	class DownloadTask extends AsyncTask<Integer, Integer, String>{

		@Override
		protected String doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			try{
				gettext();
			}
			catch(Exception e){
				Toast.makeText(NewsDetail.this,"网络不给力了。。。", Toast.LENGTH_SHORT).show();
			}
			
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result){
			try {
				if (content.equals("") || content.equals(null)) {
					Toast.makeText(NewsDetail.this,"此公告没有详情信息", Toast.LENGTH_SHORT).show();
				}
				else{
					TextView textView = (TextView) findViewById(R.id.news_detail_content);
					textView.setText(Html.fromHtml(content));
					textView.setMovementMethod(LinkMovementMethod.getInstance());
				}
			}
			catch(Exception e){
				Toast.makeText(NewsDetail.this,"获取异常。。。", Toast.LENGTH_SHORT).show();
			}
			progressDialog.dismiss();
		}
		
	}
	
	private void gettext() {

		WebServiceHelper webServiceHelper = new WebServiceHelper();
		try {
			content = webServiceHelper.getContexttext(newsId,this);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
