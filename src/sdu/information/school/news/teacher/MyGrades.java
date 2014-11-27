package sdu.information.school.news.teacher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;







import sdu.information.school.news.teacher.R;
import sdu.information.school.news.instruments.DirverInformations;
import sdu.information.school.news.instruments.Login;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MyGrades extends Activity {
	private String username;
	private String password;
	private EditText i;
	private EditText j;
	private CheckBox k;
	private TextView true1,flase1;
	
	private SharedPreferences mygrades_info;
	private ListView listview;
	private ProgressBar progressBar1;
	private ProgressBar progressBar2;
	private TextView	 protex1;
	private TextView	 protex2;
	private Button      jidian;    
	
	private ImageButton img_finish;
	private TextView write_off;
	private TextView refresh;
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private TextView protext;
	private TextView time;
	
	
	private List<String>list_spinner = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private SimpleAdapter simpleAdapter=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_grades);
		
		progressBar1 = (ProgressBar)findViewById(R.id.my_grades_progressBar1);
		progressBar2 = (ProgressBar)findViewById(R.id.my_grades_progressBar2);
		
		protex1=(TextView)findViewById(R.id.my_grades_progress_text1);
		protex2=(TextView)findViewById(R.id.my_grades_progress_text2);
		
		listview = (ListView)findViewById(R.id.my_grades_list);
		time=(TextView)findViewById(R.id.my_grades_time);
		
		img_finish=(ImageButton)findViewById(R.id.my_grades_img_1);
		write_off =(TextView)findViewById(R.id.my_grades_write_off);
		refresh	  =(TextView)findViewById(R.id.my_grades_refresh);
		protext	  =(TextView)findViewById(R.id.my_grades_pro_tex);
		write_off.setOnTouchListener(new WriteOffListener());
		refresh.setOnTouchListener(new RefreshListener());
		img_finish.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				MyGrades.this.finish();
			}
		});
		
		list_spinner.add("所有成绩");
		list_spinner.add("本学期成绩");
		list_spinner.add("交网费");
		
		SharedPreferences grade = getSharedPreferences("grade", Activity.MODE_PRIVATE);
		if(grade.getInt("isExist", 0)==1){
			for(int i=1;i<grade.getInt("howList", 0);i++){
				Map<String,String>map=new HashMap<String,String>();
				map.put("num1", grade.getString(i+"num1", ""));
				map.put("name", grade.getString(i+"name", ""));
				map.put("num2", grade.getString(i+"num2", ""));
				map.put("price", grade.getString(i+"price", ""));
				map.put("grades", grade.getString(i+"grades", ""));
				list.add(map);
			}
			time.setText(grade.getString("time", ""));
			
			
			simpleAdapter = new SimpleAdapter(
					MyGrades.this,
					list, 
					R.layout.mygrades_item, 
					new String[]{"num1","name","num2","price","grades"}, 
					new int[]{R.id.mygrades_item_num1,R.id.mygrades_item_name,R.id.mygrades_item_num2,R.id.mygrades_item_price,R.id.mygrades_item_grades}
					);
			progressBar1.setVisibility(View.GONE);
			protex1.setVisibility(View.GONE);
			progressBar2.setVisibility(View.GONE);
			protex2.setVisibility(View.GONE);
			protext.setVisibility(View.GONE);
			listview.setAdapter(simpleAdapter);
			time.setVisibility(View.VISIBLE);
			listview.setVisibility(View.VISIBLE);
		}
		else{
			mygrades_info = getSharedPreferences("mygrades_pass", Activity.MODE_PRIVATE);
			if(mygrades_info.contains("username")){
				
				if(isOpenNetwork()){
				username = mygrades_info.getString("username", "");
				password = mygrades_info.getString("password", "");
				if((username.equals("")|password.equals(""))==false){
				protex1.setVisibility(View.VISIBLE);
				progressBar1.setVisibility(View.VISIBLE);
				protext.setVisibility(View.VISIBLE);
				GetListTask getListTask = new GetListTask();
				time.setVisibility(View.GONE);
				listview.setVisibility(View.GONE);
				
					getListTask.execute();
				}
				else{
					Toast.makeText(MyGrades.this,"请输入学号和密码", Toast.LENGTH_SHORT).show();
				}
				}
				else{
					Dialog dialog = new AlertDialog.Builder(MyGrades.this)
					.setTitle("出问题了")
					.setMessage("好像没有可用的网络。。。")
					.setNegativeButton("我知道了", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							
						}
					}).create();
					dialog.show();
				}
			}
			else{
				showDialog();
			}
		}
		
	}


	class WriteOffListener implements OnTouchListener{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			switch (event.getAction()) { 	  
	        case MotionEvent.ACTION_DOWN:	//按下
	        	write_off.setBackgroundColor(Color.parseColor("#023e6b"));
	            break;
	        case MotionEvent.ACTION_UP://抬起
	        	write_off.setBackgroundColor(Color.parseColor("#006699"));
	        	mygrades_info = getSharedPreferences("mygrades_pass", Activity.MODE_PRIVATE);
	        	Editor edit = mygrades_info.edit();
	        	edit.clear().commit();
	        	SharedPreferences grade = getSharedPreferences("grade", Activity.MODE_PRIVATE);
	        	Editor edit1=grade.edit();
	        	edit1.clear().commit();
	        	list.clear();
	        	if(simpleAdapter!=null){
	        	simpleAdapter.notifyDataSetChanged();
	        	}
	        	showDialog();
	            break;
	        default:
	            break;
			}
			
			return true;
		}
		
	}
	
	class RefreshListener implements OnTouchListener{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			switch (event.getAction()) { 	  
	        case MotionEvent.ACTION_DOWN:	//按下
	        	refresh.setBackgroundColor(Color.parseColor("#023e6b"));
	            break;
	        case MotionEvent.ACTION_UP://抬起
	        	refresh.setBackgroundColor(Color.parseColor("#006699"));
	        	
	        	mygrades_info = getSharedPreferences("mygrades_pass", Activity.MODE_PRIVATE);
				if(mygrades_info.contains("username")){
					if(isOpenNetwork()){
					username = mygrades_info.getString("username", "");
					password = mygrades_info.getString("password", "");
					if((username.equals("")|password.equals(""))==false){
					protex1.setVisibility(View.VISIBLE);
					progressBar1.setVisibility(View.VISIBLE);
					protext.setVisibility(View.VISIBLE);
					GetListTask getListTask = new GetListTask();
					listview.setVisibility(View.GONE);
					time.setVisibility(View.GONE);
					getListTask.execute();
					}
					else{
						Toast.makeText(MyGrades.this,"请输入学号和密码", Toast.LENGTH_SHORT).show();
					}
					}
					else{
						Dialog dialog2 = new AlertDialog.Builder(MyGrades.this)
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
					showDialog();
				}
	            break;
	        default:
	            break;
			}
			return true;
		}
		
	}
	
	

	class GetListTask extends AsyncTask<Integer, Integer, String>{
		private HttpClient client; 
		private HttpResponse httpResponse;
		private String table;
		private Document doc = null;
		private int progress_x=1;
		private int i=0;	//进度条1
		private int j=0;	//进度条2
		@Override
		protected String doInBackground(Integer... params) {
			progressBar1.setMax(100);
			progressBar1.setProgress(i);
			
			String LoginUrl = "http://jwxt.sdu.edu.cn:7890/pls/wwwbks/bks_login2.login?jym2005=";
			client = new DefaultHttpClient();	
			HttpPost httpPost = new HttpPost(LoginUrl);	
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("stuid",username));   
			params1.add(new BasicNameValuePair("pwd",password));    			
			
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(params1, HTTP.UTF_8));	
				try {
					client.execute(httpPost);				
				//	String responseCode=httpResponse.getStatusLine().getStatusCode()+"";
				//	System.out.println(responseCode);
					httpPost.abort(); 											
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			i=i+30;
			progressBar1.setProgress(i);
			
			
			HttpGet httpGet = new HttpGet("http://jwxt.sdu.edu.cn:7890/pls/wwwbks/bkscjcx.yxkc");
			try {
				httpResponse = client.execute(httpGet);		
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			i=i+30;
			progressBar1.setProgress(i);
			
			StringBuffer sb = new StringBuffer();	
			HttpEntity httpEntity = httpResponse.getEntity();
	        InputStream is = null;			
			try {
				is = httpEntity.getContent();	
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}  
	        BufferedReader br = null;
			try {
				SharedPreferences code = getSharedPreferences("config", Activity.MODE_PRIVATE);
				br = new BufferedReader(new InputStreamReader(is, code.getString("code", "gb2312")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
	        String data = "";  
	        try {
				while ((data = br.readLine()) != null) {  
				    sb.append(data);  
				}
				table = sb.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        i=i+40;
	        progressBar1.setProgress(i);
	        i=i+1;
	        publishProgress(i);
	        
	        
	        doc=Jsoup.parse(table);
	        Elements a=doc.select("table[bgcolor=#F2EDF8]");
	        Element  b=doc.select("table[border=3]").get(0);	//限选课
	        Element  c=doc.select("table[border=3]").get(1);	//任选课
	        Login login_1 = new Login(a);
	        Login login_2 = new Login(b);
	        Login login_3 = new Login(c);
	        SharedPreferences grade = getSharedPreferences("grade", Activity.MODE_PRIVATE);
	        Editor edit=grade.edit();
	        edit.clear().commit();
	        progressBar2.setMax(login_1.trs()+login_2.trs1()+login_3.trs1()-3);
	        for(int i=1;i<login_1.trs();i++){
	        	edit.putString(progress_x+"num1", login_1.jousp(i, 0)).commit();
	        	edit.putString(progress_x+"name", login_1.jousp(i, 1)).commit();
	        	edit.putString(progress_x+"num2", login_1.jousp(i, 2)).commit();
	        	edit.putString(progress_x+"price", login_1.jousp(i, 3)).commit();
	        	edit.putString(progress_x+"grades", login_1.jousp(i, 5)).commit();
	        	progress_x=progress_x+1;
	        	j=j+1;
	        	progressBar2.setProgress(j);
	        }
	        for(int i=1;i<login_2.trs1();i++){
	        	edit.putString(progress_x+"num1", login_2.jousp1(i, 0)).commit();
	        	edit.putString(progress_x+"name", login_2.jousp1(i, 1)).commit();
	        	edit.putString(progress_x+"num2", login_2.jousp1(i, 2)).commit();
	        	edit.putString(progress_x+"price", login_2.jousp1(i, 3)).commit();
	        	edit.putString(progress_x+"grades", login_2.jousp1(i, 5)).commit();
	        	progress_x=progress_x+1;
	        	j=j+1;
	        	progressBar2.setProgress(j);
	        }
	        for(int i=1;i<login_3.trs1();i++){
	        	edit.putString(progress_x+"num1", login_3.jousp1(i, 0)).commit();
	        	edit.putString(progress_x+"name", login_3.jousp1(i, 1)).commit();
	        	edit.putString(progress_x+"num2", login_3.jousp1(i, 2)).commit();
	        	edit.putString(progress_x+"price", login_3.jousp1(i, 3)).commit();
	        	edit.putString(progress_x+"grades", login_3.jousp1(i, 5)).commit();
	        	progress_x=progress_x+1;
	        	j=j+1;
	        	progressBar2.setProgress(j);
	        }
	        edit.putInt("howList", progress_x).commit();
	        edit.putInt("isExist", 1).commit();
	        
	        final Calendar c1 = Calendar.getInstance();
			c1.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
	        String year = String.valueOf(c1.get(Calendar.YEAR));
	        String month = String.valueOf(c1.get(Calendar.MONTH) + 1);
	        String date = String.valueOf(c1.get(Calendar.DAY_OF_MONTH));
	        String hour = String.valueOf(c1.get(Calendar.HOUR_OF_DAY));
	        String minute = String.valueOf(c1.get(Calendar.MINUTE));
	        String second = String.valueOf(c1.get(Calendar.SECOND));
	        
	        if(hour.length()==1){
	        	hour="0"+hour;
	        }
	        if(minute.length()==1){
	        	minute="0"+minute;
	        }
	        if(second.length()==1){
	        	second="0"+second;
	        }
	        
	        edit.putString("time", "刷新时间："+year+"."+month+"."+date+"      "+hour+" : "+minute+" : "+second).commit();
	        
			return null;
		}
		
		protected void onProgressUpdate(Integer...progress){
			if(i==101){
				progressBar1.setVisibility(View.GONE);
				protex1.setVisibility(View.GONE);
				progressBar2.setVisibility(View.VISIBLE);
				protex2.setVisibility(View.VISIBLE);
			}
		}
		
		protected void onPostExecute(String result){
			list.clear();
			SharedPreferences grade = getSharedPreferences("grade", Activity.MODE_PRIVATE);
			for(int i=1;i<grade.getInt("howList", 0);i++){
				Map<String,String>map=new HashMap<String,String>();
				map.put("num1", grade.getString(i+"num1", ""));
				map.put("name", grade.getString(i+"name", ""));
				map.put("num2", grade.getString(i+"num2", ""));
				map.put("price", grade.getString(i+"price", ""));
				map.put("grades", grade.getString(i+"grades", ""));
				list.add(map);
			}
			time.setText(grade.getString("time", ""));
			simpleAdapter = new SimpleAdapter(
					MyGrades.this,
					list, 
					R.layout.mygrades_item, 
					new String[]{"num1","name","num2","price","grades"}, 
					new int[]{R.id.mygrades_item_num1,R.id.mygrades_item_name,R.id.mygrades_item_num2,R.id.mygrades_item_price,R.id.mygrades_item_grades}
					);
			listview.setAdapter(simpleAdapter);
			progressBar2.setVisibility(View.GONE);
			protex2.setVisibility(View.GONE);
			protext.setVisibility(View.GONE);
			time.setVisibility(View.VISIBLE);
			listview.setVisibility(View.VISIBLE);
		}
		
	}
	
	private boolean isOpenNetwork() {  
		ConnectivityManager connManager = (ConnectivityManager)getSystemService(MyGrades.CONNECTIVITY_SERVICE);  
		if(connManager.getActiveNetworkInfo() != null) {  
			return connManager.getActiveNetworkInfo().isAvailable();  
		}  
		return false;  
	}
	
	private void showDialog(){
		 LayoutInflater factory = LayoutInflater.from(MyGrades.this);
		 View myView = factory.inflate(R.layout.mygrades_dialog, null);
		 i = (EditText)myView.findViewById(R.id.MyGrades_usename);
		 j = (EditText)myView.findViewById(R.id.MyGrades_password);
		 k = (CheckBox)myView.findViewById(R.id.mygrades_issave);
		 true1=(TextView)myView.findViewById(R.id.dialog_true);
		 flase1=(TextView)myView.findViewById(R.id.dialog_flase);
		 
		 k.setChecked(true);
		 final Dialog dialog = new Dialog(MyGrades.this,R.style.Translucent_NoTitle);
		 			dialog.setContentView(myView);
       
		 true1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isOpenNetwork()){
					username = i.getText().toString().trim();
					password = j.getText().toString().trim();
						if((username.equals("")|password.equals(""))==false){
							protex1.setVisibility(View.VISIBLE);
							progressBar1.setVisibility(View.VISIBLE);
							protext.setVisibility(View.VISIBLE);
							if(k.isChecked()){
								mygrades_info=getSharedPreferences("mygrades_pass", Activity.MODE_PRIVATE);
								Editor edit = mygrades_info.edit();
								edit.putString("username", username).commit();
								edit.putString("password", password).commit();
							}
							
							GetListTask getListTask = new GetListTask();
							listview.setVisibility(View.GONE);
							time.setVisibility(View.GONE);
							getListTask.execute();
							dialog.dismiss();
						}
						else{
							Toast.makeText(MyGrades.this,"请输入学号和密码", Toast.LENGTH_SHORT).show();
						}
					}
				else{
					Toast.makeText(MyGrades.this,"没有网络连接", Toast.LENGTH_SHORT).show();
				}
			}
		});
		flase1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		}); 
		/* 
		Window dialogWindow = dialog.getWindow();
		WindowManager m = getWindowManager();
		WindowManager.LayoutParams p = dialogWindow.getAttributes();
		p.width = (int) (DirverInformations.weight(this) * 0.8);
		p.height = (int) (DirverInformations.height(this) * 0.8);
		dialogWindow.setAttributes(p); */
		dialog.show();
	}
	
}
