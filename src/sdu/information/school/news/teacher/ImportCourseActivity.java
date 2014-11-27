package sdu.information.school.news.teacher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;







import sdu.information.school.news.teacher.R;
import sdu.information.school.news.instruments.CopyToSdcard;
import sdu.information.school.news.instruments.Daba;
import sdu.information.school.news.instruments.Login;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ImportCourseActivity extends Activity {
	
	private EditText 		username;
	private EditText 		password;
	private String	  		user_name;
	private String   		pass_word;
	private Button 	  		submit;
	
	private	 ProgressBar 	progressbar1;
	private	 ProgressBar 	progressbar2;
	public static ProgressBar 	progressbar3;
	private	 ProgressBar 	progressbar4;
	
	private	  TextView		progresstext_all;
	private  TextView		progresstext1;
	private  TextView		progresstext2;
	private  TextView		progresstext3;
	private  TextView		progresstext4;
	private  ImageButton	img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_import_course);
		
		username = (EditText)findViewById(R.id.username);
		password = (EditText)findViewById(R.id.password);
		submit	 = (Button)findViewById(R.id.submit);
		
		progressbar1 = (ProgressBar)findViewById(R.id.progressBar1);
		progressbar2 = (ProgressBar)findViewById(R.id.progressBar2);
		progressbar3 = (ProgressBar)findViewById(R.id.progressBar3);
		progressbar4 = (ProgressBar)findViewById(R.id.progressBar4);
		
		progresstext_all = (TextView)findViewById(R.id.progress_text_all);
		progresstext1 	 = (TextView)findViewById(R.id.progress_text1);
		progresstext2 	 = (TextView)findViewById(R.id.progress_text2);
		progresstext3 	 = (TextView)findViewById(R.id.progress_text3);
		progresstext4 	 = (TextView)findViewById(R.id.progress_text4);
		img				 = (ImageButton)findViewById(R.id.import_course_activity_img_1);
		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ImportCourseActivity.this.finish();
			}
		});
		
		submit.setOnClickListener(new Submit());
		
	}
	
	class Submit implements OnClickListener{

		@Override
		public void onClick(View v) {
			user_name = username.getText().toString();
			pass_word = password.getText().toString();
			if((user_name.equals("")|pass_word.equals(""))==false){
			if(isOpenNetwork()){
				MyAsyncTask myAsyncTask = new MyAsyncTask();
				progresstext1.setVisibility(View.VISIBLE);
				progressbar1.setVisibility(View.VISIBLE);
				progresstext_all.setVisibility(View.VISIBLE);
				//	Log.v("tag",Thread.currentThread().getId()+"");
				//	Log.v("tag",Thread.currentThread().getName());
				myAsyncTask.execute();
			}
			else{
				Dialog dialog2 = new AlertDialog.Builder(ImportCourseActivity.this)
				.setTitle("出问题了")
				.setMessage("好像没有可用的网络。。。")
				.setNegativeButton("我知道了", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				}).create();
				dialog2.show();
			}
			}
			else{
				Toast.makeText(ImportCourseActivity.this,"请输入学号和密码", Toast.LENGTH_SHORT).show();
			}
			
		}
	}
	
	class MyAsyncTask extends AsyncTask<Integer,Integer,String>{
		private HttpClient 		client 			= null;
		private HttpResponse 	httpResponse2 	= null;
		private  int i=0;//这是第一个进度条的值
		private  int j=0;//这是第二个进度条的值
		private  int l=0;//这是第四个进度条的值
		private  int k=0;//这是重修的重复次数
		private  int trs_num=0;//表格的大小
		private String table = null;
		private Document doc = null;
		
	protected String doInBackground(Integer... params) {
		// TODO Auto-generated method stub
		
	//	Log.v("tag",Thread.currentThread().getId()+"");
	//	Log.v("tag",Thread.currentThread().getName());
//第一步开始			
		progressbar1.setProgress(i);
		publishProgress(i);
		
		String LoginUrl = "http://jwxt.sdu.edu.cn:7890/pls/wwwbks/bks_login2.login?jym2005=";//登陆地址
		//建立客户端对象
		client = new DefaultHttpClient();		
		//建立Post链接
		HttpPost httpPost = new HttpPost(LoginUrl);			
		//创建Post键值对
		List<NameValuePair> params1 = new ArrayList<NameValuePair>();	
		//加载键值对
		params1.add(new BasicNameValuePair("stuid",user_name));   
		params1.add(new BasicNameValuePair("pwd",pass_word));    			
		
		try {
			//编码Post请求
			httpPost.setEntity(new UrlEncodedFormEntity(params1, HTTP.UTF_8));	
			try {
				//执行Post请求
				client.execute(httpPost);				
			//	String responseCode=httpResponse.getStatusLine().getStatusCode()+"";		//判断是否登陆成功
			//	System.out.println(responseCode);
				
				//释放资源
				httpPost.abort(); 											
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		i=i+30;
		progressbar1.setProgress(i);
		
		do{
		HttpGet httpGet = new HttpGet("http://jwxt.sdu.edu.cn:7890/pls/wwwbks/xk.CourseView");		//选课界面
		try {
			httpResponse2 = client.execute(httpGet);				//执行Get方法
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		i=i+30;
		progressbar1.setProgress(i);
		
		//创建字符串流
		StringBuffer sb = new StringBuffer();	
		//获取Http实体
		HttpEntity httpEntity = httpResponse2.getEntity();
		//创建输入流
        InputStream is = null;			
		try {
			//获取内容
			is = httpEntity.getContent();	
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        BufferedReader br = null;
		try {
			SharedPreferences code = getSharedPreferences("config", Activity.MODE_PRIVATE);
			br = new BufferedReader(new InputStreamReader(is, code.getString("code", "gb2312")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        String data = "";  
        try {
			while ((data = br.readLine()) != null) {  
			    sb.append(data);  
			}
			table = sb.toString();
	//		Log.v("tag",table);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}while(table==null);
		
		
        i=i+40;
    	progressbar1.setProgress(i);
		i=i+1;
		publishProgress(i);
		
//第二步开始		
		
		progressbar2.setProgress(j);
		doc = Jsoup.parse(table);
		Elements a = doc.select("table table:contains(上课地点) ");
		Login login = new Login(a);
	//	Log.v("tag", login.getweek(2)+"");
	//	Log.v("tag", login.getnum(2)+"");
		trs_num = login.trs()-1;
		progressbar2.setMax(trs_num);
		
		SharedPreferences share = getSharedPreferences("course", Activity.MODE_PRIVATE);
		Editor edit=share.edit();
		edit.clear().commit(); 
		edit.putString("isGet", "1").commit();
		for(int i=1;i<=trs_num;i++){
			int week	= login.getweek(i);
			int num		= login.getnum(i);
			if(week!=20){
				if(share.contains(week+""+num+"name")){
					k=k+1;
					String tem_name= share.getString(week+""+num+"name","");
					String tem_num_1= share.getString(week+""+num+"num_1","");
					String tem_num_2= share.getString(week+""+num+"num_2","");
					String tem_exam= share.getString(week+""+num+"exam","");
					String tem_loc= share.getString(week+""+num+"loc","");
					String tem_week= share.getString(week+""+num+"week","");
					String tem_week_sub= share.getString(week+""+num+"week_sub","");
					
					edit.putString("repeat"+k+"name", tem_name).commit();
					edit.putString("repeat"+k+"num_1", tem_num_1).commit();
					edit.putString("repeat"+k+"num_2", tem_num_2).commit();
					edit.putString("repeat"+k+"exam", tem_exam).commit();
					edit.putString("repeat"+k+"loc", tem_loc).commit();
					edit.putString("repeat"+k+"week", tem_week).commit();
					edit.putString("repeat"+k+"week_sub", tem_week_sub).commit();
					edit.putString("repeat"+k+"isrepeat", "1").commit();
					
					edit.putString(week+""+num+"name", login.jousp(i, 0)).commit();
					edit.putString(week+""+num+"num_1", login.jousp(i, 2)).commit();
					edit.putString(week+""+num+"num_2", login.jousp(i, 3)).commit();
					edit.putString(week+""+num+"exam", login.jousp(i, 5)).commit();
					edit.putString(week+""+num+"loc", login.jousp(i, 6)).commit();
					edit.putString(week+""+num+"week", login.getweek(i)+"").commit();
					edit.putString(week+""+num+"week_sub", login.getnum(i)+"").commit();
					edit.putString(week+""+num+"isrepeat", "1").commit();
				}
				else{
					edit.putString(week+""+num+"name", login.jousp(i, 0)).commit();
					edit.putString(week+""+num+"num_1", login.jousp(i, 2)).commit();
					edit.putString(week+""+num+"num_2", login.jousp(i, 3)).commit();
					edit.putString(week+""+num+"exam", login.jousp(i, 5)).commit();
					edit.putString(week+""+num+"loc", login.jousp(i, 6)).commit();
					edit.putString(week+""+num+"week", login.getweek(i)+"").commit();
					edit.putString(week+""+num+"week_sub", login.getnum(i)+"").commit();
				}
			}
			j=j+1;
			progressbar2.setProgress(j);
		}
		edit.putString("howRepeat", k+"").commit();
		edit.putInt("isExist", 1).commit();
//第三步开始	
		i=i+1;
		publishProgress(i);
		SQLiteDatabase db = null;
		boolean sdCardExist = android.os.Environment.getExternalStorageState()   
                .equals(android.os.Environment.MEDIA_MOUNTED);
		/*
		if(sdCardExist){
			Log.v("tag", "1");
			CopyToSdcard cts= new CopyToSdcard(ImportCourseActivity.this);
			try {
				cts.check();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String path= Environment.getExternalStorageDirectory().getAbsolutePath();
			db=SQLiteDatabase.openDatabase(path+"/kuangmo_sdu/mycourse", null, SQLiteDatabase.OPEN_READONLY);
		}*/
		
//		else{
			Log.v("tag", "2");
		Daba  daba = new Daba(ImportCourseActivity.this);
		try {
			
			daba.check();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db=daba.getReadableDatabase();
//		}
//第四步开始
		i=i+1;
		publishProgress(i); 
		progressbar4.setMax(trs_num);
		for(int i=1;i<=7;i++){
			for(int j=1;j<=7;j++){
				if(share.contains(i+""+j+"name")){
					String num_1=share.getString(i+""+j+"num_1", "");
					String num_2=share.getString(i+""+j+"num_2", "");
					Cursor cursor = db.query("course", new String[]{"course_school","course_price","course_how_time","course_teacher_1","course_teacher_2","course_space","course_how_week","course_class","course_grade","course_property"}, "course_num_1=? AND course_num_2=?", new String[]{num_1,num_2}, "", "", "course_num_2");
					while(cursor.moveToNext()){
						edit.putString(i+""+j+"school", cursor.getString(cursor.getColumnIndex("course_school"))).commit();
						edit.putString(i+""+j+"price", cursor.getString(cursor.getColumnIndex("course_price"))).commit();
						edit.putString(i+""+j+"how_time", cursor.getString(cursor.getColumnIndex("course_how_time"))).commit();
						edit.putString(i+""+j+"teacher_1", cursor.getString(cursor.getColumnIndex("course_teacher_1"))).commit();
						edit.putString(i+""+j+"teacher_2", cursor.getString(cursor.getColumnIndex("course_teacher_2"))).commit();
						edit.putString(i+""+j+"space", cursor.getString(cursor.getColumnIndex("course_space"))).commit();
						edit.putString(i+""+j+"how_week", cursor.getString(cursor.getColumnIndex("course_how_week"))).commit();
						edit.putString(i+""+j+"class", cursor.getString(cursor.getColumnIndex("course_class"))).commit();
						edit.putString(i+""+j+"grade", cursor.getString(cursor.getColumnIndex("course_grade"))).commit();
						edit.putString(i+""+j+"property", cursor.getString(cursor.getColumnIndex("course_property"))).commit();
						l=l+1;
						progressbar4.setProgress(l);
					}
				}
			}
		}
		
		if(k!=0){
			for(int i= 1;i<=k;i++){
				String num_1=share.getString("repeat"+i+"num_1", "");
				String num_2=share.getString("repeat"+i+"num_2", "");
				Cursor cursor = db.query("course", new String[]{"course_school","course_price","course_how_time","course_teacher_1","course_teacher_2","course_space","course_how_week","course_class","course_grade","course_property"}, "course_num_1=? AND course_num_2=?", new String[]{num_1,num_2}, "", "", "course_num_2");
				while(cursor.moveToNext()){
					edit.putString("repeat"+i+"school", cursor.getString(cursor.getColumnIndex("course_school"))).commit();
					edit.putString("repeat"+i+"price", cursor.getString(cursor.getColumnIndex("course_price"))).commit();
					edit.putString("repeat"+i+"how_time", cursor.getString(cursor.getColumnIndex("course_how_time"))).commit();
					edit.putString("repeat"+i+"teacher_1", cursor.getString(cursor.getColumnIndex("course_teacher_1"))).commit();
					edit.putString("repeat"+i+"teacher_2", cursor.getString(cursor.getColumnIndex("course_teacher_2"))).commit();
					edit.putString("repeat"+i+"space", cursor.getString(cursor.getColumnIndex("course_space"))).commit();
					edit.putString("repeat"+i+"how_week", cursor.getString(cursor.getColumnIndex("course_how_week"))).commit();
					edit.putString("repeat"+i+"class", cursor.getString(cursor.getColumnIndex("course_class"))).commit();
					edit.putString("repeat"+i+"grade", cursor.getString(cursor.getColumnIndex("course_grade"))).commit();
					edit.putString("repeat"+i+"property", cursor.getString(cursor.getColumnIndex("course_property"))).commit();
					l=l+1;
					progressbar4.setProgress(l);
				}
			}
		}
		
		db.close();
		
		
		//System.out.println(cursor.getString(cursor.getColumnIndex("course_id"))+" "+cursor.getString(cursor.getColumnIndex("course_name"))+" "+cursor.getString(cursor.getColumnIndex("course_num_2")));
		
		
		
		return null;
	}
	

	protected void onProgressUpdate(Integer...progress){
		if(i==0){
			progresstext_all.setText("正在获取...");
		}
		if(i==101){
			progressbar1.setVisibility(View.GONE);
			progresstext1.setVisibility(View.GONE);
			progresstext2.setVisibility(View.VISIBLE);
			progressbar2.setVisibility(View.VISIBLE);
		}
		if(i==102){
			progressbar2.setVisibility(View.GONE);
			progresstext2.setVisibility(View.GONE);
			progresstext3.setVisibility(View.VISIBLE);
			progressbar3.setVisibility(View.VISIBLE);
		}
		if(i==103){
			progresstext3.setVisibility(View.GONE);
			progressbar3.setVisibility(View.GONE);
			progresstext4.setVisibility(View.VISIBLE);
			progressbar4.setVisibility(View.VISIBLE);
		}
	}
	

	protected void onPostExecute(String result){
			progresstext_all.setText("成功！");
			if(CourseActivity.isMainExist){
				CourseActivity.instance.finish();
			}
			Dialog dialog = new AlertDialog.Builder(ImportCourseActivity.this)
				.setTitle("成功")
				.setMessage("快去看看吧")
				.setPositiveButton("好的", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent();  
			            intent.setClass(ImportCourseActivity.this, CourseActivity.class);  
			            startActivity(intent); 
			            ImportCourseActivity.this.finish();
					}
				}).create();
			dialog.show();
	}
	
	}
	
	private boolean isOpenNetwork() {  
		ConnectivityManager connManager = (ConnectivityManager)getSystemService(ImportCourseActivity.CONNECTIVITY_SERVICE);  
		if(connManager.getActiveNetworkInfo() != null) {  
			return connManager.getActiveNetworkInfo().isAvailable();  
		}  
		return false;  
	}
	

}
