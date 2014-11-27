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
import sdu.information.school.news.instruments.Image;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class InformationFee extends Activity {

	private Button VerificationButton;
	private Button submitButton;
	private HttpClient client=null;
	private byte[] data=null;
	private ImageView VerificationImage;
	private	 ProgressBar 	progressbar;
	private ProgressBar		progressbar2;
	private Bitmap bitmap=null;
	private TextView resultview;
	private String result1;
	
	private String username;
	private String password;
	private String verification;
	
	private EditText edit_username;
	private EditText edit_password;
	private EditText edit_verification;
	
	private String table;
	private ArrayAdapter<String> adapter;
	private ImageButton img;
	
	private TextView protex;
	private RadioGroup group;
	private String how_much=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_information_fee);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		
		VerificationButton = (Button)findViewById(R.id.VerificationButton);
		VerificationButton.setOnClickListener(new VerificationListener());
		submitButton = (Button)findViewById(R.id.information_submi);
		submitButton.setOnClickListener(new SubmitListener());
		VerificationImage  = (ImageView)findViewById(R.id.VerificationImage);
		progressbar		   = (ProgressBar)findViewById(R.id.information_progress);
		progressbar2	   = (ProgressBar)findViewById(R.id.information_progress2);
		
		edit_username = (EditText)findViewById(R.id.information_username);
		edit_password = (EditText)findViewById(R.id.information_password);
		edit_verification = (EditText)findViewById(R.id.information_verification);
		protex=(TextView)findViewById(R.id.information_fee_pro_tex);
		img=(ImageButton)findViewById(R.id.information_fee_img_1);
		group=(RadioGroup)findViewById(R.id.informationfee_radioGroup);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				int radioButtonId = arg0.getCheckedRadioButtonId();
				RadioButton rb = (RadioButton)InformationFee.this.findViewById(radioButtonId);
				
				how_much=rb.getText()+"";
			}
		});
		
		
		
		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				InformationFee.this.finish();
			}
		});
		
		resultview = (TextView)findViewById(R.id.resultview);
		
		
	}
	
	class VerificationListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(isOpenNetwork()){
				VerificationImage.setVisibility(View.GONE);
				progressbar.setVisibility(View.VISIBLE);
				GetImageTask getImageTask = new GetImageTask();
				getImageTask.execute();
			}
			else{
				Dialog dialog = new AlertDialog.Builder(InformationFee.this)
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
		
	}
	
	class GetImageTask extends AsyncTask<Integer,Integer,String>{

		@Override
		protected String doInBackground(Integer... params) {

			HttpResponse httpResponse = null;
			client = new DefaultHttpClient();
			HttpGet httpGet=new HttpGet("http://www.swxxf.sdu.edu.cn/randCode.action");
			try {
				httpResponse = client.execute(httpGet);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpEntity httpEntity = httpResponse.getEntity();  	//获取Http实体
	        InputStream is = null;
	        try {
				is = httpEntity.getContent();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				data = Image.readInputStream(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
	        Matrix matrix = new Matrix(); 
	        matrix.postScale(2f, 2f);  
	        //bmp.getWidth(), bmp.getHeight()分别表示缩放后的位图宽高  
	        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),  
	                matrix, true); 
			return null;
		}
		
		protected void onProgressUpdate(Integer...progress){
		
		}
		

		protected void onPostExecute(String result){
			progressbar.setVisibility(View.GONE);
			VerificationImage.setVisibility(View.VISIBLE);
	        VerificationImage.setImageBitmap(bitmap);
		}
		
	}
	
	class SubmitListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(isOpenNetwork()){
				resultview.setText("");
				resultview.setVisibility(View.GONE);
				username = edit_username.getText().toString();
				password = edit_password.getText().toString();
				verification = edit_verification.getText().toString();
				progressbar2.setVisibility(View.VISIBLE);
				protex.setVisibility(View.VISIBLE);
				PostTask postTask = new PostTask();
				postTask.execute();
				//resultview.setText(result);
			}
			else{
				Dialog dialog = new AlertDialog.Builder(InformationFee.this)
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
		
	}
	
	class PostTask extends AsyncTask<Integer,Integer,String>{
		private BufferedReader reader=null;
		private Document doc = null;
		
		@Override
		protected String doInBackground(Integer... params) {
			HttpResponse httpResponse = null;
			HttpPost httpPost = new HttpPost("http://www.swxxf.sdu.edu.cn/userLogin");
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("userName",username));   
			params1.add(new BasicNameValuePair("passwd",password)); 
			params1.add(new BasicNameValuePair("yktType","0")); 
			params1.add(new BasicNameValuePair("authCode",verification)); 
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(params1, HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				client.execute(httpPost);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			httpPost.abort();
			
			HttpGet httpGet=null;
			if(how_much==null){
				httpGet = new HttpGet("http://www.swxxf.sdu.edu.cn/addBalance?balance=10");
			}
			else{
				httpGet = new HttpGet("http://www.swxxf.sdu.edu.cn/addBalance?balance="+how_much);
			}
			try {
				httpResponse = client.execute(httpGet);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			InputStream in = null;
			HttpEntity httpEntity = httpResponse.getEntity();
			try {
				in = httpEntity.getContent();
				reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
				StringBuffer sb = new StringBuffer();
				 String data = "";  
			        try {
			        	
						while ((data = reader.readLine()) != null) {  
						    sb.append(data);  
						}
						table = sb.toString();
						doc = Jsoup.parse(table);
						Elements a = doc.select("div#content");
						result1 = a.text();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return null;
		}
		
		protected void onProgressUpdate(Integer...progress){
			
		}
		
		protected void onPostExecute(String result){
			progressbar2.setVisibility(View.GONE);
			protex.setVisibility(View.GONE);
			resultview.setVisibility(View.VISIBLE);
			resultview.setText(result1);
	//		System.out.println(table);
		}
		
	}
	
	private boolean isOpenNetwork() {  
		ConnectivityManager connManager = (ConnectivityManager)getSystemService(StartActivity.CONNECTIVITY_SERVICE);  
		if(connManager.getActiveNetworkInfo() != null) {  
			return connManager.getActiveNetworkInfo().isAvailable();  
		}  
		return false;  
	}



}
